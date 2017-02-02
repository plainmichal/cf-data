package com.mehou.pcf.controller;

import com.mehou.pcf.mapper.PcfDataMapper;
import com.mehou.pcf.model.PcfData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by michaltokarz on 27/01/2017.
 */
@RestController("/api")
public class PcfController {
    private volatile PcfData pcfData;

    @Autowired
    private PcfDataMapper pcfDataMapper;

    public PcfController() {
        this.pcfData = null;
    }

    @GetMapping("/pcfData")
    public PcfData getPcfData() {
        return this.pcfData;
    }

    @Scheduled(fixedDelay = 5 * 1000 * 60)
    private void setPcfData() {
        this.pcfData = pcfDataMapper.map();
    }
}
