package com.telegram.markettrackerbot.database.jdbc.dao;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.telegram.markettrackerbot.database.jdbc.db.DataBaseConnection;
import com.telegram.markettrackerbot.database.jdbc.models.JdbcUserModel;

@Repository
public class JdbcUserModelDao {
  private final DataBaseConnection databaseConnection;

  public JdbcUserModelDao(DataBaseConnection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  public JdbcUserModel getUserByChatId(Long chatId) throws IOException {
    String sql = "SELECT * FROM users WHERE chat_id = ?";

    try (
      Connection connection = databaseConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setLong(1, chatId);

      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next() ? new JdbcUserModel(
          resultSet.getLong("chat_id"),
          resultSet.getString("name")
        ) : null;
      }
    } catch (SQLException e) {
      throw new IOException("Error while getting user by chat id: " + chatId, e);
    }
  }

  public String getStockByTickerId(String tickerId) throws IOException {
    String sql = "SELECT * FROM users WHERE ? = ANY(stocks)";

    try (
      Connection connection = databaseConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setString(1, tickerId);

      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next() ? tickerId : null;
      }
    } catch (SQLException e) {
      throw new IOException("Error while getting stock by ticker id: " + tickerId, e);
    }
  }

  public String[] getStocksByChatId(Long chatId) throws IOException {
    String sql = "SELECT stocks FROM users WHERE chat_id = ?";

    try (
      Connection connection = databaseConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setLong(1, chatId);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          Array array = resultSet.getArray("stocks");
          return (String[]) array.getArray();
        } else {
          return new String[0];
        }
      }
    } catch (SQLException e) {
      throw new IOException("Error while getting user stocks by chat id: " + chatId, e);
    }
  }

  public void createUser(Long chat_id, String name) throws IOException {
    String sql = "INSERT INTO users (name, chat_id, stocks) VALUES (?, ?, ?)";

    try (
      Connection connection = databaseConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setString(1, name);
      statement.setLong(2, chat_id);
      statement.setArray(3, connection.createArrayOf("TEXT", List.of().toArray()));

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new IOException("Error while creating user: " + chat_id, e);
    }
  }

  public void removeStockFromUser(Long userId, String stockId) throws SQLException {
    String sql = "UPDATE users SET stocks = array_remove(stocks, ?) WHERE id = ?";

    try (
      Connection connection = databaseConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setString(1, stockId);
      statement.setLong(2, userId);

      statement.executeUpdate();
    }
  }

  public void addStock(Long chatId, String stockId) throws IOException {
    String sql = "UPDATE users SET stocks = array_append(stocks, ?) WHERE chat_id = ?";

    try (
      Connection connection = databaseConnection.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)
    ) {
      statement.setString(1, stockId);
      statement.setLong(2, chatId);

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new IOException("Error while updating user stocks: " + chatId, e);
    }
  }
}
