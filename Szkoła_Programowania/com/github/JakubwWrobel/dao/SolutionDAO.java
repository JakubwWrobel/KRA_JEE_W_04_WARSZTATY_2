package com.github.JakubwWrobel.dao;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.models.Solution;
import com.github.JakubwWrobel.models.User;

import java.sql.*;
import java.util.Arrays;

public class SolutionDAO {
    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solution () VALUES ()";
    private static final String READ_SOLUTION_QUERY =
            "SELECT * FROM solution where id = ?";
    private static final String UPDATE_SOLUTION_QUERY =
            "UPDATE solution SET users_id = ?, created = NOW() where id = ?";
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
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                solution.setId(resultSet.getInt(1));
            }
            return solution;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            return null;
        }
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


    public void assignSolutionToTheUser(User user, int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(UPDATE_SOLUTION_QUERY);
            statement.setInt(1, user.getId());
            statement.setInt(2, userInput);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
    }
    public Solution read(int userInput){
        try(Connection conn = GetConnection.getConnection()){
            statement = conn.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, userInput);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                return solution;
            }else {
                return null;
            }
        }catch (SQLException e){
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    public int delete() {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return -1;
    }

    public Solution[] findAll(){
        try(Connection conn = GetConnection.getConnection()){
            statement = conn.prepareStatement(FIND_ALL_SOLUTION_QUERY);
            ResultSet resultSet = statement.executeQuery();
            Solution[] solutions = new Solution[0];
            while(resultSet.next()){
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solutions = addToArray(solutions,solution);
            }

            for(int i = 0; i < solutions.length; i++){
                System.out.println(String.format("ID: %s\n", solutions[i].getId()));
            }
            return solutions;
        }catch (SQLException e){
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    private Solution[]addToArray(Solution[] solutions, Solution solution){
        Solution[] tempSolutions = Arrays.copyOf(solutions, solutions.length + 1);
        tempSolutions[tempSolutions.length - 1] = solution;
        return tempSolutions;
    }


}
