package com.java.quartz.controller;


import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="/demo")
@Slf4j
public class DemoController {

    @RequestMapping(value = "/getDemoStr", method = RequestMethod.POST)
    public String getDemoStr(@RequestBody Map retMap) throws MySQLIntegrityConstraintViolationException {
        if(!retMap.isEmpty()){
            log.info("retMap::"+retMap.get("homePageDataList"));
        }


        return "aaaaaaaaa";
    }
}
