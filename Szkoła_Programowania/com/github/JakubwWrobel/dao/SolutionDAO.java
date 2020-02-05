package com.github.JakubwWrobel.dao;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.models.Exercise;
import com.github.JakubwWrobel.models.Solution;
import com.github.JakubwWrobel.models.User;

import java.sql.*;
import java.util.Arrays;

public class SolutionDAO {
    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solution (created, updated) VALUES (null, null)";
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
    private static final String FIND_ALL_SOLUTIONS_BY_USER_ID =
            "SELECT * FROM solution WHERE users_id = ?";
    private static final String FIND_ALL_SOLUTIONS_BY_EXERCISE_ID =
            "SELECT * FROM solution WHERE exercise_id = ?";

    private PreparedStatement statement;
    private static ExerciseDAO exerciseDAO = new ExerciseDAO();
    private static UserDAO userDAO = new UserDAO();

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

    public Solution read(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(READ_SOLUTION_QUERY);
            statement.setInt(1, userInput);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                return solution;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    public int delete(int id) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(DELETE_SOLUTION_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return -1;
    }

    public Solution[] findAll() {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(FIND_ALL_SOLUTION_QUERY);
            ResultSet resultSet = statement.executeQuery();
            Solution[] solutions = new Solution[0];
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setCreated(resultSet.getTime("created"));
                solution.setUpdated(resultSet.getTime("updated"));
                solution.setDescription(resultSet.getString("description"));
//TAK LEPIEJ CZY
                if (resultSet.getInt("users_id") != 0) {
                    solution.setUsers_id(userDAO.read(resultSet.getInt("users_id")));
                } else {
                    solution.setUsers_id(null);
                }
/*                int userGroupId = resultSet.getInt("user_group_id");
                UserGroup userGroup = userGroupDAO.read(userGroupId);
                user.setUserGroup(userGroup);*/
//MOZE TAK??
                int exerciseID = resultSet.getInt("exercise_id");
                Exercise exercise = exerciseDAO.read(exerciseID);
                if (exercise == null) {
                    solution.setExercise_id(null);
                } else {
                    solution.setExercise_id(exerciseDAO.read(resultSet.getInt("exercise_id")));
                }


                solution.setUpdated(resultSet.getTime("updated"));
                solutions = addToArray(solutions, solution);
            }
            return solutions;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    private Solution[] addToArray(Solution[] solutions, Solution solution) {
        Solution[] tempSolutions = Arrays.copyOf(solutions, solutions.length + 1);
        tempSolutions[tempSolutions.length - 1] = solution;
        return tempSolutions;
    }

    public Solution[] findAllByUserId(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_BY_USER_ID);
            statement.setInt(1, userInput);
            ResultSet resultSet = statement.executeQuery();
            Solution[] solutions = new Solution[0];
            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    Solution solution = new Solution();
                    solution.setId(resultSet.getInt("id"));
                    solution.setDescription(resultSet.getString("description"));
                    solution.setExercise_id(exerciseDAO.read(resultSet.getInt("exercise_id")));
                    solution.setUsers_id(userDAO.read(resultSet.getInt("users_id")));
                    solution.setCreated(resultSet.getTime("created"));
                    solutions = addToArray(solutions, solution);
                } while (resultSet.next());
            }
            return solutions;
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą");
        }
        return null;
    }

    public Solution[] findAllByExerciseId(int userInput) {
        try (Connection conn = GetConnection.getConnection()) {
            statement = conn.prepareStatement(FIND_ALL_SOLUTIONS_BY_EXERCISE_ID);
            statement.setInt(1, userInput);
            ResultSet resultSet = statement.executeQuery();
            Solution[] solutions = new Solution[0];
            while (resultSet.next()) {
                Solution solution = new Solution();
                solution.setId(resultSet.getInt("id"));
                solution.setDescription(resultSet.getString("description"));
                solution.setExercise_id(exerciseDAO.read(resultSet.getInt("exercise_id")));
                solutions = addToArray(solutions, solution);
            }
            return solutions;
        } catch (SQLException e) {
            System.out.println("Błąd połączenie z bazą danych");
        }
        return null;
    }


}
