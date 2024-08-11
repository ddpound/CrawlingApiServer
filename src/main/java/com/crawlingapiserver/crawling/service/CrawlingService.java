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


@Log4j2
@Service
public class CrawlingService {


    public void readCommands(CommandModel commandModel){
        // ObjectMapper 인스턴스를 생성합니다
        try {
            log.info("command read : {}", commandModel.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
