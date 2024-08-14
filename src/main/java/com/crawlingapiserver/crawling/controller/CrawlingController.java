package com.crawlingapiserver.crawling.controller;


import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.service.CrawlingService;
import com.crawlingapiserver.crawling.service.DBService;
import com.crawlingapiserver.crawling.service.DBSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "crawling")
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    private final DBSettingService dbSettingService;

    private final DBService dbService;

    @PostMapping(value = "run")
    public ResponseEntity<String> runCrawling(@RequestBody CommandModel commandModel) throws SQLException {

        if(commandModel.getDatabase() != null){

            if(commandModel.getDatabase().getUrl() == null){
                return new ResponseEntity<>("url is not found", HttpStatus.BAD_REQUEST);
            }

            Connection connection = DriverManager.getConnection(commandModel.getDatabase().getUrl(), commandModel.getDatabase().getUsername(), commandModel.getDatabase().getPassword());
            dbService.testSetting(connection);
        }


        crawlingService.readCommands(commandModel);

        return new ResponseEntity<>("good", HttpStatus.OK);
    }


}
