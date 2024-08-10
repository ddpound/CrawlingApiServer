package com.crawlingapiserver.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sample")
public class SampleController {

    @GetMapping(value = "main")
    public String sample(){
        return "sample-main";
    }
}
