package com.crawlingapiserver.crawling.config;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class DBConfig {

    /**
     * 요청 한건당 커넥션 DB
     * */
    public Connection connectionDB(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url,username,password);

    }

    public void closeConnectionDB(Connection connection){
        try {
            connection.close();
        } catch (Exception e){
            log.error(e);
        }
    }
}
