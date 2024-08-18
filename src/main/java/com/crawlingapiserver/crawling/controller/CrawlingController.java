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
        if(urlExtractionValidation.getState() != 1){
            return new ResponseEntity<>(urlExtractionValidation.getMessage() , urlExtractionValidation.getHttpStatus());
        }

        // 검증 완료되면 추출 시작
        // 요청할 url list를 추출하기, 만약 targetURIListValidationAndReturnData 에 값이 있으면 list addAll 진행
        List<String> targetList = urlService.makeFinalTargetUrl(commandModel);
        if(urlService.targetURIListValidationAndReturnData(commandModel) != null) targetList.addAll(urlService.targetURIListValidationAndReturnData(commandModel));
        if(targetList != null && !targetList.isEmpty()) log.info("List extraction completed");

        Connection connection = null;

        if(dbService.validationDbSetting(commandModel)){
            connection = DriverManager.getConnection(commandModel.getDatabase().getUrl(), commandModel.getDatabase().getUsername(), commandModel.getDatabase().getPassword());
            dbService.testSetting(connection);
        }else{
            return new ResponseEntity<>("db value is not found : " + commandModel.getDatabase() , HttpStatus.BAD_REQUEST);
        }


        crawlingService.runCrawling(commandModel,targetList,connection);

        return new ResponseEntity<>("good", HttpStatus.OK);
    }

    /**
     * 사용, run 하기전에 check-uri 요청을 통해서 요청하게될 uri 리스트를 확인해볼수 있습니다.
     * */
    @PostMapping(value = "check-uri")
    public ResponseEntity<List<String>> checkYourTargetUriList(@RequestBody CommandModel commandModel){

        // 검증 완료되면 추출 시작
        // 요청할 url list를 추출하기, 만약 targetURIListValidationAndReturnData 에 값이 있으면 list addAll 진행
        List<String> targetList = urlService.makeFinalTargetUrl(commandModel);
        if(urlService.targetURIListValidationAndReturnData(commandModel) != null) targetList.addAll(urlService.targetURIListValidationAndReturnData(commandModel));
        if(targetList != null && !targetList.isEmpty()) log.info("List extraction completed");

        return new ResponseEntity<>(targetList, HttpStatus.OK);
    }


}
