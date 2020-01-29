package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;
import com.github.JakubwWrobel.dao.ExerciseDAO;
import com.github.JakubwWrobel.dao.SolutionDAO;
import com.github.JakubwWrobel.dao.UserDAO;
import com.github.JakubwWrobel.models.Exercise;
import com.github.JakubwWrobel.models.Solution;
import com.github.JakubwWrobel.models.User;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class SolutionController {
    private static SolutionDAO solutionDAO = new SolutionDAO();
    private static ExerciseDAO exerciseDAO = new ExerciseDAO();
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        create();
//        assignExerciseToSolution();
//            assignUserToExercise();
    }

    private static void create() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Czy chcesz utworzyć nowe rozwiązanie?");
            System.out.println("Yes/No");
            String userInput = Checking.checkingString(scanner.nextLine()).toLowerCase();
            if(userInput.equals("no")){
                break;
            }
            else if(userInput.equals("yes")) {
                Solution solution = new Solution();
                solutionDAO.create(solution);
                System.out.println(String.format("Zadanie zostało utworzne, jego ID to: %s\n", solution.getId()));
                //ExerciseToSolution
                System.out.println("Chcesz przypisać zadanie do tego rozwiązania?");
                System.out.println("Yes/No");
                String userInput2 = Checking.checkingString(scanner.nextLine()).toLowerCase();
                if (userInput2.equals("yes")) {
                    assignExerciseToSolutionAfterCreatingSolution(solution);
                    running = false;
                    scanner.close();
                } else if(userInput2.equals("no")) {
                    running = false;
                    scanner.close();
                }
            }

        }

    }


    private static void assignExerciseToSolutionAfterCreatingSolution(Solution solution) {
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID zadania, które ma być przypisane do tego rozwiązania");
            showAllExercises(exerciseDAO.findAll());
            int userInput2 = Checking.checkingInt();
            Exercise exercise = exerciseDAO.read(userInput2);
            if (exercise == null) {
            } else {
                solutionDAO.insertExerciseIntoSolution(solution, userInput2);
                System.out.println("Rozwiązanie zostało przypisane do zadania");
                running = false;
            }
        }
    }


    //PRZEROBIĆ ABY POKAZYWAŁO ID
    private static void assignExerciseToSolution() {
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID rozwiązania do którego chcesz przypisać zadanie: ");
            solutionDAO.findAll();
            int userInput = Checking.checkingInt();
            Solution solution = solutionDAO.read(userInput);
            if (solution == null) {
                System.out.println("Rozwiązanie o takim ID nie istnieje");
            } else {
                while (running) {
                    System.out.println("Podaj ID zadania, które ma być przypisane do tego rozwiązania");
                    exerciseDAO.findAll();
                    int userInput2 = Checking.checkingInt();
                    Exercise exercise = exerciseDAO.read(userInput2);
                    if (exercise == null) {
                    } else {
                        solutionDAO.insertExerciseIntoSolution(solution, userInput2);
                        System.out.println("Rozwiązanie zostało przypisane do zadania");
                        running = false;
                    }
                }
            }
        }
    }

    private static void assignUserToExercise() {
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID zadania, do którego chcesz przypisać do użytkownika");
            solutionDAO.findAll();
            Solution solution = solutionDAO.read(Checking.checkingInt());
            if (solution == null) {

            } else {
                while (running) {
                    System.out.println("Podaj ID użytkownika do którego chcesz przypisać zadanie: ");
                    showAllUsers(userDAO.findAll());
                    User user = userDAO.read(Checking.checkingInt());
                    if (user == null) {

                    } else {
                        System.out.println("Zadanie zostało przypisane poniższemu użytkownikowi");
                        solutionDAO.assignSolutionToTheUser(user, solution.getId());
                        running = false;
                    }
                }
            }

        }
    }

    private static Exercise[] showAllExercises(Exercise[] exercises) {
        for (int i = 0; i < exercises.length; i++) {
            System.out.println(String.format("ID: %s\nTitle: %s\nDescription: %s\n", exercises[i].getId(), exercises[i].getTitle(), exercises[i].getDescription()));
        }
        return exercises;
    }

    private static User[] showAllUsers(User[] users) {
        for (int i = 0; i < users.length; i++) {
            System.out.println(String.format("ID: %s\nUsername: %s\nEmail: %s\n", users[i].getId(), users[i].getUsername(), users[i].getEmail()));
        }
        return users;
    }

}
