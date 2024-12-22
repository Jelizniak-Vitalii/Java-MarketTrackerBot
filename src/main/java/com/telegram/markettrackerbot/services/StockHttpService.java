package com.telegram.markettrackerbot.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class StockHttpService {
  @Value("${xrapidapikey}")
  private String xrapidapikey;

  @Value("${xrapidapi}")
  private String xrapidapi;

  public String search(String text) throws IOException {
    HttpURLConnection connection = null;

    try {
      String params = "keyword=" + URLEncoder.encode(text, "UTF-8") + "&pageSize=5";
      URL url = new URL(xrapidapi + "/search?" + params);

      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      connection.setRequestProperty("X-RapidAPI-Key", xrapidapikey);
      connection.setRequestProperty("Content-Type", "application/json");

      int statusCode = connection.getResponseCode();

      if (statusCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = reader.readLine()) != null) {
            response.append(line);
          }

          return response.toString();
        }
      } else {
        throw new IOException("HTTP Error: " + statusCode);
      }
    } catch (Exception e) {
      throw new IOException("Unexpected error occurred: " + e.getMessage(), e);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

//  async getStockData(tickerId) {
//    return this.apiService.get(
//      `${this.#baseUrl}/get-realtime-quote`,
//    { tickerId },
//    { 'X-RapidAPI-Key': process.env.X_RAPID_API_KEY }
//    );
//  }
}
