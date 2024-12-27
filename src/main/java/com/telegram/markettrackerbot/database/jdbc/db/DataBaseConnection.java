package com.telegram.markettrackerbot.database.jdbc.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataBaseConnection {
  @Value("${db.url}")
  private String URL;

  @Value("${db.user}")
  private String USER;

  @Value("${db.password}")
  private String PASSWORD;

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
