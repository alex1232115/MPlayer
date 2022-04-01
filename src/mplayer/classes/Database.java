package mplayer.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/database?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "fkfittd11";

    public User getUser(String login, String password) throws SQLException {
        try (Connection connection = DriverManager
            .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                
                //Подготавливаем объект к запросу из базы данных
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE login = ? and password = ?")) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery(); //Выполняем запрос
            if(resultSet.next()) { // и получаем данные
                User user = new User(resultSet.getString(1), resultSet.getString(2)); // создаем объект пользователя из полученных данных
                return user;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }
    
    public boolean createUser(String login, String password) throws SQLException {
        try (Connection connection = DriverManager
            .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (`login`, `password`) VALUES(?, ?)")) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            int i = preparedStatement.executeUpdate();

            if(i == 1){
                return true;
            }           	

        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    } //RegistrationController

    public List<Track> getTracks() {
        List<Track> tracks = new ArrayList<Track>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM track")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Track track = new Track(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                tracks.add(track);
            }
        } catch (Exception e) { }
        return tracks;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}