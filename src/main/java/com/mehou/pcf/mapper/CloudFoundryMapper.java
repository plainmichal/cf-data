package com.mehou.pcf.mapper;

import com.mehou.pcf.model.PcfData;
import com.mehou.pcf.model.app.App;
import com.mehou.pcf.model.app.Status;
import com.mehou.pcf.model.org.Org;
import com.mehou.pcf.model.org.OrgQuota;
import com.mehou.pcf.model.space.Space;
import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.client.v2.Metadata;
import org.cloudfoundry.client.v2.applications.ApplicationEntity;
import org.cloudfoundry.client.v2.applications.ListApplicationsRequest;
import org.cloudfoundry.client.v2.applications.ListApplicationsResponse;
import org.cloudfoundry.client.v2.organizations.ListOrganizationSpacesRequest;
import org.cloudfoundry.client.v2.organizations.ListOrganizationSpacesResponse;
import org.cloudfoundry.client.v2.organizations.ListOrganizationsRequest;
import org.cloudfoundry.client.v2.spaces.SpaceResource;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.organizations.OrganizationInfoRequest;
import org.cloudfoundry.operations.organizations.OrganizationQuota;
import org.cloudfoundry.operations.organizations.Organizations;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michaltokarz on 30/01/2017.
 */
public class CloudFoundryMapper implements PcfDataMapper {
    private final CloudFoundryOperations operations;
    private final CloudFoundryClient client;

    public CloudFoundryMapper(CloudFoundryOperations operations, CloudFoundryClient client) {
        this.operations = operations;
        this.client = client;
    }

    @Override
    public PcfData map() {
        PcfData.Builder pcfDataBuilder = PcfData.newBuilder();
        Organizations organizations = operations.organizations();


        organizations.list().map(orgSummary ->
                organizations.get(orgDetailByName(orgSummary.getName()))
                        .block())
                .toStream()
                .forEach(orgDetail -> {
                    OrganizationQuota quota = orgDetail.getQuota();
                    Org.Builder orgBuilder = Org.newBuilder();
                    orgBuilder
                            .withId(orgDetail.getId())
                            .withName(orgDetail.getName())
                            .withQuota(OrgQuota.newBuilder()
                                    .withLimit(quota.getTotalMemoryLimit())
                                    .withUsed(quota.getInstanceMemoryLimit() * quota.getTotalServiceInstances())
                                    .build());

                    List<SpaceResource> spaceResources = client.organizations().listSpaces(ListOrganizationSpacesRequest.builder()
                            .organizationId(orgDetail.getId()).build()).block().getResources();

                    Map<String, Space.Builder> spaceBuilders = new HashMap<>();
                    spaceResources.forEach(spaceResource -> {
                        Space.Builder spaceBuilder = Space.newBuilder();
                        spaceBuilder.withId(spaceResource.getMetadata().getId());
                        spaceBuilder.withName(spaceResource.getEntity().getName());
                        spaceBuilders.put(spaceResource.getMetadata().getId(), spaceBuilder);

                    });

                    int totalPages = client.applicationsV2().list(ListApplicationsRequest.builder()
                            .organizationId(orgDetail.getId()).build())
                            .block()
                            .getTotalPages();

                    for (int i = 1; i < totalPages; i++) {
                        client.applicationsV2().list(ListApplicationsRequest.builder()
                                .organizationId(orgDetail.getId())
                                .page(i)
                                .build())
                                .block()
                                .getResources()
                                .forEach(applicationResource -> {
                                    ApplicationEntity appEntity = applicationResource.getEntity();
                                    Metadata appMetadata = applicationResource.getMetadata();

                                    Space.Builder spaceBuilder = spaceBuilders.get(appMetadata.getId());
                                    App.Builder appBuilder = App.newBuilder();
                                    appBuilder
                                            .withId(appMetadata.getId())
                                            .withName(appEntity.getName())
                                            .withBuildpack(appEntity.getBuildpack())
                                            .withStatus(Status.getEnum(appEntity.getState()))
                                            .withDiskLimit(appEntity.getDiskQuota())
                                            .withMemoryLimit(appEntity.getMemory())
                                            .withInstances(appEntity.getInstances())
                                            .withRoutes(appEntity.getRoutesUrl());

                                    spaceBuilder.withApp(appBuilder.build());
                                });
                    }

                    spaceBuilders.forEach((id, spaceBuilder) -> orgBuilder.withSpace(spaceBuilder.build()));
                    pcfDataBuilder.withOrg(orgBuilder.build());
                });

        return pcfDataBuilder.build();
}

    private OrganizationInfoRequest orgDetailByName(String name) {
        return OrganizationInfoRequest.builder().name(name).build();
    }
}
