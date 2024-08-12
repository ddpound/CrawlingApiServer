package com.crawlingapiserver.crawling.controller;


import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.service.CrawlingService;
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

    @PostMapping(value = "run")
    public ResponseEntity<String> runCrawling(@RequestBody CommandModel commandModel) throws SQLException {

        Connection connection = DriverManager.getConnection(commandModel.getDatabase().getUrl(), commandModel.getDatabase().getUsername(), commandModel.getDatabase().getPassword());

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

        crawlingService.readCommands(commandModel);

        return new ResponseEntity<>("good", HttpStatus.OK);
    }


}
