package com.crawlingapiserver.crawling.controller;


import com.crawlingapiserver.crawling.model.CommandModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

@RequestMapping(value = "crawling")
@RestController
public class CrawlingController {


    public void readCommands(CommandModel commandModel){
        // ObjectMapper 인스턴스를 생성합니다
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File inputJsonFile = new File(String.valueOf(""));
            CommandModel jacksonCommandModel = objectMapper.readValue(inputJsonFile, CommandModel.class);

            System.out.println("json file read : " + jacksonCommandModel);

            ArrayList<Object> targetList = jacksonCommandModel.getTargetList();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
