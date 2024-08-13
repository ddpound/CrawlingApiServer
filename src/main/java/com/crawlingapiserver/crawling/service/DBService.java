package com.crawlingapiserver.crawling.service;

import lombok.extern.log4j.Log4j2;
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

}
