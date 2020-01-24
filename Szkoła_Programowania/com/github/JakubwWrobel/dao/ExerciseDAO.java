package com.github.JakubwWrobel.dao;

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
}
