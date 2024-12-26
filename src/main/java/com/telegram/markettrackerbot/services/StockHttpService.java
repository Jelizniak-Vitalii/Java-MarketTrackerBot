package com.telegram.markettrackerbot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class StockHttpService {
  private static final Logger logger = LoggerFactory.getLogger(StockHttpService.class);

  @Value("${xrapidapikey}")
  private String xrapidapikey;

  @Value("${xrapidapi}")
  private String xrapidapi;

  private String get(String path) {
    HttpURLConnection connection = null;

    try {
      URL url = new URL(xrapidapi + path);

      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      connection.setRequestProperty("X-RapidAPI-Key", xrapidapikey);
      connection.setRequestProperty("Content-Type", "application/json");

      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
      StringBuilder response = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        response.append(line);
      }

      return response.toString();
    } catch (Exception e) {
      logger.error("Unexpected error occurred while sending request: {}", e.getMessage());
      return null;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public String search(String text) {
    try {
      return get("/search?" + "keyword=" + URLEncoder.encode(text, "UTF-8") + "&pageSize=5");
    } catch (Exception e) {
      logger.error("Unexpected error occurred while searching for stock: {}", e.getMessage());
      return null;
    }
  }

  public String getStockByTickerId(String tickerId) {
    return get("/get-realtime-quote?" + "tickerId=" + tickerId);
  }
}
