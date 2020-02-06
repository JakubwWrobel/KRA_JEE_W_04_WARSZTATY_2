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
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        create();
//        assignExerciseToSolution();
//         assignUserToExercise();
//        delete();
//        findAll();
//        showAllSolutionsByUser();
//        findAllByExerciseId();
//        resolveExercise();
    }

    protected static void create() {
        boolean running = true;

        while (running) {
            System.out.println("Czy chcesz utworzyć nowe rozwiązanie?");
            System.out.println("Yes/No");
            String userInput = Checking.checkingString(scanner.nextLine()).toLowerCase();
            if (userInput.equals("no")) {
                break;
            } else if (userInput.equals("yes")) {
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
                } else if (userInput2.equals("no")) {
                    running = false;
                }
            }
        }
    }

    protected static void delete() {
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID rozwiązania, które chcesz usunąć: ");
            solutionDAO.findAll();
            int userInput = Checking.checkingInt();
            Solution solution = solutionDAO.read(userInput);
            if (solution == null) {
            } else {
                solutionDAO.delete(userInput);
                System.out.println("Rozwiązanie zostało usunięte");
                running = false;
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

    protected static void resolveExercise() {
        boolean running = true;
        while (running) {
            User user = showAllSolutionsByUser();
            if (user == null) {

            } else {
                System.out.println("Które zadanie(Exercise ID) chcesz rozwiązać: ");
                int userInput2 = Checking.checkingInt();
                Exercise exercise = exerciseDAO.read(userInput2);
                Exercise exercise1 = solutionDAO.isExerciseAssignedToUser(user, exercise);
                if (exercise == null) {
                    System.out.println("Podane zadanie nie istnieje");
                } else if (exercise1 == null){
                    System.out.println("Podane zadanie nie jest przypisane do użytkownika");
                } else {
                    System.out.println("Podaj opis rozwiązania zadania: ");
                    String description = Checking.checkingString(scanner.nextLine());
                    solutionDAO.resolveExercise(user, description, exercise1);
                    System.out.println("Zadanie zostało rozwiązane");
                    running = false;
                }
            }
        }
    }


    //PRZEROBIĆ ABY POKAZYWAŁO ID
    protected static void assignExerciseToSolution() {
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID rozwiązania do którego chcesz przypisać zadanie: ");
            findAll();
            int userInput = Checking.checkingInt();
            Solution solution = solutionDAO.read(userInput);
            if (solution == null) {
                System.out.println("Rozwiązanie o takim ID nie istnieje");
            } else {
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
        }
    }

/*
    private static void update() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("Podaj ID rozwiązania które chcesz edytować");
            solutionDAO.findAll();
            int userInput = Checking.checkingInt();
            Solution solution = solutionDAO.read(userInput);
            if (solution == null) {
                System.out.println("Rozwiązanie o takim ID nie istnieje");
            } else {
        }
    }
*/


    protected static void assignUserToExercise() {
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID zadania, do którego chcesz przypisać do użytkownika");
            findAll();
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

    protected static User showAllSolutionsByUser() {
        boolean running = true;
        int userInput;
        while (running) {
            showAllUsers(userDAO.findAll());
            System.out.println("Podaj ID użytkownika w celu wyświetlenia przypisanych rozwiązań");
            userInput = Checking.checkingInt();
            User user = userDAO.read(userInput);
            if (user == null) {
                System.out.println("Podany użytkownik nie istnieje");
            } else {
                Solution[] solutions = solutionDAO.findAllByUserId(userInput);
                if (solutions == null) {
                    System.out.println("Podany użytkownik nie posiada przypisanych zadań");
                    running = false;
                } else {
                    for (Solution solution : solutions) {
                        System.out.println(String.format("ID: %s\nCreated: %s\nDescription: %s\nExercise ID: %s", solution.getId(), solution.getCreated(), solution.getDescription(), solution.getExercise_id().getId()));
                        running = false;
                        return user;

                    }
                }
            }
        }
        return null;
    }

    protected static void findAllByExerciseId() {
        boolean running = true;
        int userInput;
        while (running) {
            exerciseDAO.findAll();
            System.out.println("Podaj ID zadania dla którego chcesz wyświetlić rozwiązania: ");
            userInput = Checking.checkingInt();
            Exercise exercise = exerciseDAO.read(userInput);
            if (exercise == null) {
                System.out.println("Podane zadanie nie istnieje");
            } else {
                Solution[] solutions = solutionDAO.findAllByExerciseId(userInput);
                for (Solution solution : solutions) {
                    System.out.println(String.format("ID: %s\nDescription: %s\nExercise_ID: %s\nUser_ID: %s\nCreated: %s\n", solution.getId(), solution.getDescription(), solution.getExercise_id(), solution.getUsers_id(), solution.getCreated()));
                }

                running = false;
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

    protected static Solution[] findAll() {
        Solution[] solutions = solutionDAO.findAll();
        int userId;
        int exerciseID;
        for (Solution solution : solutions) {
            if (solution.getUsers_id() == null) {
                userId = 0;
            } else {
                userId = solution.getUsers_id().getId();
            }
            if (solution.getExercise_id() == null) {
                exerciseID = 0;
            } else {
                exerciseID = solution.getExercise_id().getId();
            }
            System.out.println(String.format("Solution ID: %s\nCreated: %s\nUpdated: %s \nDescription: %s\nUser_ID: %s\nExercise_ID: %s\n", solution.getId(), solution.getCreated(), solution.getUpdated(), solution.getDescription(), userId, exerciseID));
        }
        return solutions;
    }

}
