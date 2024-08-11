package com.crawlingapiserver.crawling.controller;


import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.service.CrawlingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

@RequiredArgsConstructor
@RequestMapping(value = "crawling")
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    @PostMapping(value = "run")
    public ResponseEntity<String> runCrawling(@RequestBody CommandModel commandModel) {

        crawlingService.readCommands(commandModel);

        return new ResponseEntity<>("good", HttpStatus.OK);
    }


}
