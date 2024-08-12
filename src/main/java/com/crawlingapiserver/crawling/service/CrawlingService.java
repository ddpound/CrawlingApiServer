package com.crawlingapiserver.crawling.service;

import com.crawlingapiserver.crawling.model.CommandModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
public class CrawlingService {

    /**
     * mode
     *  1. DB Direct Insert
     *  2. Create DB query txt file
     *  3. 1번 2번 동시에 진행
     *
     * */
    public void readCommands(CommandModel commandModel){
        // ObjectMapper 인스턴스를 생성합니다
        try {
            log.info("command read : {}", commandModel.toString());
            log.info("target url : {}", commandModel.getTargetURI());
            log.info("target loop number : {}", commandModel.getTargetLoopNumber());
            log.info("target loop number : {}", commandModel.getTargetParamsSettingList());
            log.info("db data : {}", commandModel.getDatabase());
            log.info("file setting : {}", commandModel.getFileSetting());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 최종 목적 uri 를 추출해주는 메소드
     * */
    public List<String> targetUriExtract(CommandModel commandModel){


        return null;
    }

}
