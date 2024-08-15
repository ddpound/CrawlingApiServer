package com.crawlingapiserver.crawling.service;

import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.model.DatabaseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
@Service
public class DBService {

    public void testSetting(Connection connection){
        String sql = "select * from test_board;";
        String seqFileNumber = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                seqFileNumber = rs.getString("author");
                log.info(seqFileNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DB의 값이 있는지 없는지 체크 하는 메소드
     * db url, username, pw 를 체크함
     * @return boolean
     * false : 값이 하나라도 없으면 거짓
     * true : 값이 모두 있다면 참
     * */
    public Boolean validationDbSetting(CommandModel commandModel){

        if(commandModel.getDatabase() != null){
            DatabaseModel dbModel = commandModel.getDatabase();
            return dbModel.getUrl() != null && dbModel.getUsername() != null && dbModel.getPassword() != null;
        }else{
            return false;
        }
    }


}
