package com.github.JakubwWrobel.dao;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.models.Exercise;
import com.github.JakubwWrobel.models.User;

import java.sql.*;
import java.util.Arrays;

public class ExerciseDAO {
    private static final String CREATE_EXCERCISE_QUERY =
            "INSERT INTO exercise (title, description) VALUES (?, ?)";
    private static final String READ_EXCERCISE_QUERY =
            "SELECT * FROM exercise where id = ?";
    private static final String UPDATE_EXCERCISE_QUERY =
            "UPDATE exercise SET title = ?, description = ? where id = ?";
    private static final String DELETE_EXCERCISE_QUERY =
            "DELETE FROM exercise WHERE id = ?";
    private static final String FIND_ALL_EXCERCISE_QUERY =
            "SELECT * FROM exercise";
    private PreparedStatement statement;


    public Exercise create(Exercise exercise) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(CREATE_EXCERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
        }
        return null;
    }

    public void update(Exercise exercise) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(UPDATE_EXCERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
        }
    }

    public Exercise read(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(READ_EXCERCISE_QUERY);
            statement.setInt(1, userInput);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setDescription(resultSet.getString("description"));
                exercise.setTitle(resultSet.getString("title"));
                return exercise;

            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            return null;
        }
    }

    public void delete(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            boolean running = true;
            while (running) {
                if (read(userInput) == null) {
                } else {
                    statement = conn.prepareStatement(DELETE_EXCERCISE_QUERY);
                    statement.setInt(1, userInput);
                    statement.executeUpdate();

                    running = false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
        }
    }

    public Exercise[] addToArray(Exercise[] exercises, Exercise exercise) {
        Exercise[] tempExercises = Arrays.copyOf(exercises, exercises.length + 1);
        tempExercises[tempExercises.length - 1] = exercise;
        return tempExercises;
    }

    public Exercise[] findAll() {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(FIND_ALL_EXCERCISE_QUERY);
            ResultSet resultSet = statement.executeQuery();
            Exercise[] exercises = new Exercise[0];

            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercises, exercise);
            }
            return exercises;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
            e.getErrorCode();
        }
        return null;
    }

}