package com.github.JakubwWrobel.dao;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.models.Solution;

import java.sql.*;

public class SolutionDAO {
    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solution (created, updated, description) VALUES (?, ?, ?)";
    private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM solution where id = ?";
    private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solution SET title = ?, description = ? where id = ?";
    private static final String DELETE_SOLUTION_QUERY =
            "DELETE FROM solution WHERE id = ?";
    private static final String FIND_ALL_SOLUTION_QUERY =
            "SELECT * FROM solution";
    private static final String UPDATE_SOLUTION_TO_EXERCISE_QUERY =
            "UPDATE solution SET exercise_id = ? WHERE id = ?";
    private PreparedStatement statement;

    public Solution create(Solution solution) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, (Date) solution.getCreated());
            statement.setDate(2, (Date) solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.executeQuery();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    public void insertExerciseIntoSolution(Solution solution, int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(UPDATE_SOLUTION_TO_EXERCISE_QUERY);
            statement.setInt(1, userInput);
            statement.setInt(2, solution.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
    }


    public void update() {
        try (Connection conn = GetConnection.getConnection()) {

        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
    }
//    public Solution read(){
//        try(Connection conn = GetConnection.getConnection()){
//
//        }catch (SQLException e){
//            System.out.println("Błąd połączenia z bazą");
//        }
//    }

    public void delete() {
        try (Connection conn = GetConnection.getConnection()) {

        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
    }

//    public Solution[] findAll(){
//        try(Connection conn = GetConnection.getConnection()){
//
//        }catch (SQLException e){
//            System.out.println("Błąd połączenia z bazą");
//        }
//    }


}
