package com.github.JakubwWrobel.dao;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.models.User;

import java.sql.*;

public class UserDAO {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";
    private PreparedStatement statement;

    public User create(User user) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println(String.format("ID: %s\nNazwa użytkownika: %s\nEmail: %s\n", resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email")));

                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
            System.out.println("Podany użytkownik nie istnieje");
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }


//DOBRZE ZAIMPLEMENTOWAŁEM TUTAJ RZUCANIE WYJĄTKU SQLIntegrityConstraintViolationException??
    public void update(User user) throws SQLIntegrityConstraintViolationException {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());

            statement.executeUpdate();


        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia");
            e.printStackTrace();
        }
    }

    public void delete(User user){
        try(Connection conn = GetConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, user.getId());
            statement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Błąd połączenia z bazą");
        }
    }

    public void showAllUsers() {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(String.format("ID: %s\nNazwa użytkownika: %s\nEmail: %s\n", resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email")));
            }

        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
    }
}
