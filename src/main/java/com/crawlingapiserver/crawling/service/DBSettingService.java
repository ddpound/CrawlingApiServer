package com.crawlingapiserver.crawling.service;


import com.crawlingapiserver.crawling.config.DBConfig;
import com.crawlingapiserver.crawling.model.CommandModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB 세팅을 위한 서비스
 *
 * */
@Log4j2
@Service
public class DBSettingService {

    public Connection dbConnect(CommandModel commandModel) {
        try {
            return DriverManager.getConnection(commandModel.getDatabase().getUrl(), commandModel.getDatabase().getUsername(), commandModel.getDatabase().getPassword());
        }catch (Exception e) {
            log.error(e);
            return null;
        }
    }
}
