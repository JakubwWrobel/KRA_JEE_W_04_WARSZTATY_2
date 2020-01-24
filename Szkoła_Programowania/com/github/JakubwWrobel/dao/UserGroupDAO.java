package com.github.JakubwWrobel.dao;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.models.User;
import com.github.JakubwWrobel.models.UserGroup;

import java.sql.*;

public class UserGroupDAO {
    private static final String CREATE_USERGROUP_QUERY =
            "INSERT INTO user_group(name) VALUES (?)";
    private static final String UPDATE_USERGROUP_QUERY =
            "UPDATE user_group SET name = ? WHERE id = ?";
    private static final String READ_USERGROUP_QUERY =
            "SELECT * FROM user_group WHERE id = ?";
    private static final String DELETE_USERGROUP_QUERY =
            "DELETE FROM user_group WHERE id = ?";
    private static final String FIND_ALL_USERGROUPS_QUERY =
            "SELECT * FROM user_group";
    private static final String UPDATE_USERGROUP_FOR_USER_QUERY =
            "UPDATE users SET user_group_id = ? WHERE id = ?";


    public User insertUserGroupToUser(User user, int userInput){
        try(Connection conn = GetConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(UPDATE_USERGROUP_FOR_USER_QUERY);
            statement.setInt(1, userInput);
            statement.setInt(2, user.getId());
            statement.executeUpdate();

        }catch (SQLException e){
            e.getErrorCode();
            System.out.println("Błąd połączenia z bazą");
        }
        return user;
    }

    public UserGroup read(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USERGROUP_QUERY);
            statement.setInt(1, userInput);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(resultSet.getInt("id"));
                userGroup.setUserGroupName(resultSet.getString("name"));
                return userGroup;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Podana groupa nie istnieje");
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    public UserGroup update(UserGroup userGroup) {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USERGROUP_QUERY);
            statement.setInt(2, userGroup.getUserGroupId());
            statement.setString(1, userGroup.getUserGroupName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd komunikacji z bazą danych");
            e.getErrorCode();
            return null;
        }
        return userGroup;
    }

    public UserGroup create(UserGroup userGroup) {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USERGROUP_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, userGroup.getUserGroupName());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                userGroup.setId(resultSet.getInt(1));
            }
            return userGroup;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
            return null;
        }
    }

    public void delete(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USERGROUP_QUERY);
            statement.setInt(1, userInput);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
        }
    }

    public  void showAll(){
        try(Connection conn = GetConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERGROUPS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                System.out.println(String.format("ID groupy: %s\nNazwa Groupy: %s\n", resultSet.getString("id"), resultSet.getString("name")));
            }
        }catch (SQLException e){
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
        }
    }


}



