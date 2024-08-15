package com.crawlingapiserver.crawling.controller;


import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.model.ResponseStateModel;
import com.crawlingapiserver.crawling.service.CrawlingService;
import com.crawlingapiserver.crawling.service.DBService;
import com.crawlingapiserver.crawling.service.DBSettingService;
import com.crawlingapiserver.crawling.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "crawling")
@RestController
public class CrawlingController {

    private final CrawlingService crawlingService;

    private final DBSettingService dbSettingService;

    private final DBService dbService;

    private final UrlService urlService;

    @PostMapping(value = "run")
    public ResponseEntity<String> runCrawling(@RequestBody CommandModel commandModel) throws SQLException {

        ResponseStateModel urlExtractionValidation = urlService.urlExtractionValidation(commandModel);
        if(!urlExtractionValidation.getState()){
            return new ResponseEntity<>(urlExtractionValidation.getMessage() , urlExtractionValidation.getHttpStatus());
        }

        // 검증 완료되면 추출 시작

        // 요청할 url list를 추출하기
        List<String> targetList = urlService.urlExtraction(commandModel);


        if(dbService.validationDbSetting(commandModel)){
            Connection connection = DriverManager.getConnection(commandModel.getDatabase().getUrl(), commandModel.getDatabase().getUsername(), commandModel.getDatabase().getPassword());
            dbService.testSetting(connection);
        }else{
            return new ResponseEntity<>("db value is not found : " + commandModel.getDatabase() , HttpStatus.BAD_REQUEST);
        }

        crawlingService.readCommands(commandModel);

        return new ResponseEntity<>("good", HttpStatus.OK);
    }


}
