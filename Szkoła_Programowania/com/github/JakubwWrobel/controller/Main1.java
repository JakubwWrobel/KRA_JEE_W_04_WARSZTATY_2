package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;
import com.github.JakubwWrobel.dao.UserDAO;
import com.github.JakubwWrobel.dao.UserGroupDAO;
import com.github.JakubwWrobel.models.Exercise;
import com.github.JakubwWrobel.models.Solution;
import com.github.JakubwWrobel.models.User;
import com.github.JakubwWrobel.models.UserGroup;
import jbcrypt.BCrypt;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main1 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Witaj w twojej szkole programowania, postępuj według instrukcji w razie potrzeby wyjdź z programu wpisując słowo 'quit'");
        String userInput = "";
        while (!userInput.equals("quit")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Wybierz panel do którego chcesz przejść: ");
            System.out.println("1 - Panel administracyjny | 2 - Panel użytkownika");
            userInput = Checking.checkingString(scanner.nextLine());
            if (userInput.equals("1")) {
//Panel ADMIN
                System.out.println("Zaloguj się: ");
                boolean isAdmin= UserController.isAdmin();
                if(isAdmin){
                    userInput = adminPanel(userInput);
                }
            } else if (userInput.equals("2")) {
//Panel USER
                userInput = userPanel(userInput);
            } else if (userInput.equals("quit")) {
//FINISH PROGRAM
            }
        }
        scanner.close();
    }

    private static String userPanel(String userInput) {
        System.out.println();
        System.out.println("Wybierz panel do którego chcesz przejść: ");
        System.out.println("1 - Zadania | 5 - Powrót do menu");
        userInput = Checking.checkingString(scanner.nextLine());

        while (!userInput.equals("comeback")) {
            if (userInput.equals("1")) {
                System.out.println("Wybierz akcję, którą chcesz wykonać: ");
                System.out.println("1 - Dodać Rozwiązanie | 2 - Sprawdzić Swoje Rozwiązania | 10 - Wróć Do Panelu Użytkownika");
                userInput = Checking.checkingString(scanner.nextLine());
                switch (userInput) {
                    case "1":
                        SolutionController.resolveExercise();
                        break;
                    case "2":
                        SolutionController.showAllSolutionsByUser();
                        break;
                    case "10":
                        userInput = "comeback";
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Podane zadanie nie istnieje");
                        break;
                }
            } else if (userInput.equals("5")) {
                userInput = "comeback";
            } else {
                System.out.println("Wybierz panel do którego chcesz przejść: ");
                System.out.println("1 - Zadania | 5 - Powrót do menu");
                userInput = Checking.checkingString(scanner.nextLine());
            }
        }

        return userInput;
    }

    private static String adminPanel(String userInput) {
        System.out.println("Wybierz panel do którego chcesz przejść: ");
        System.out.println("1 - Użytkownicy | 2 - Grupy Użytkowników | 3 - Zadania | 4 - Rozwiązania | 5 - Powrót do menu");
        userInput = Checking.checkingString(scanner.nextLine());

        while (!userInput.equals("comeback")) {
            if (userInput.equals("1")) {
                System.out.println("Wybierz zadanie, które chcesz wykonać: ");
                System.out.println("1 - Dodać Użytkownika | 2 - Zaktualizować dane użytkownika | 3 - Przypisać/Zaaktualizować Użytkownikowi Grupę |4 - Usunąć Użytkownika | 5 - Znaleźć wszystkich użytkowników po numerze grupy | 6 - Pokazać wszystkich użytkowników | 10 - Wróć Do Panelu Użytkownika");
                userInput = Checking.checkingString(scanner.nextLine());
                switch (userInput) {
                    case "1":
                        UserController.addUser();
                        break;
                    case "2":
                        UserController.updateUser();
                        break;
                    case "3":
                        UserController.assignGroupToUser();
                        break;
                    case "4":
                        UserController.removeUser();
                        break;
                    case "5":
                        UserController.findAllByGroupId();
                        break;
                    case "6":
                        UserController.findAll();
                        break;
                    case "10":
                        userInput = "comeback";
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Podane zadanie nie istnieje");
                        break;
                }
            } else if (userInput.equals("2")) {
                System.out.println("Wybierz zadanie, które chcesz wykonać: ");
                System.out.println("1 - Dodać grupę użytkowników | 2 - Zaaktualizować Grupę Użytkowników | 3 - Usunąć Grupę Użytkowników | 4 - Wyświetlić wszystkich Grupy Użytkowników | 5 - Wróć Do Panelu Administratora");
                userInput = Checking.checkingString(scanner.nextLine());
                switch (userInput) {
                    case "1":
                        UserGroupController.create();
                        break;
                    case "2":
                        UserGroupController.update();
                        break;
                    case "3":
                        UserGroupController.delete();
                        break;
                    case "4":
                        UserGroupController.findAll();
                        break;
                    case "5":
                        userInput = "comeback";
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Podane zadanie nie istnieje");
                        break;
                }
            } else if (userInput.equals("3")) {
                System.out.println("Wybierz zadanie, które chcesz wykonać: ");
                System.out.println("1 - Dodać Zadanie | 2 - Zaaktualizować Zadanie | 3 - Usunąć Zadanie | 4 - Wyświetlić Wszystkie Zadanie | 5 - Wróć Do Panelu Administratora");
                userInput = Checking.checkingString(scanner.nextLine());
                switch (userInput) {
                    case "1":
                        ExerciseController.create();
                        break;
                    case "2":
                        ExerciseController.update();
                        break;
                    case "3":
                        ExerciseController.delete();
                        break;
                    case "4":
                        ExerciseController.finAll();
                        break;
                    case "5":
                        userInput = "comeback";
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Podane zadanie nie istnieje");
                        break;
                }
            } else if (userInput.equals("4")) {
                System.out.println("Wybierz zadanie, które chcesz wykonać: ");
                System.out.println("1 - Dodać Rozwiązanie | 2 - Przypisz Zadanie Do Rozwiązania | 3 - Usunąć Rozwiązanie | 4 - Wyświetlić Wszystkie Rozwiązania | 5 - Wyświetl Wszystkie Rozwiązania Na Podstawie Numeru Zadania | 6 - Wyświetl Wszystkie Rozwiązania Na Podstawie ID Użytkownika | 7 - Przypisz Użytkownika Do Rozwiązania | 8 - Wróć Do Panelu Administratora");
                userInput = Checking.checkingString(scanner.nextLine());
                switch (userInput) {
                    case "1":
                        SolutionController.create();
                        break;
                    case "2":
                        SolutionController.assignExerciseToSolution();
                        break;
                    case "3":
                        SolutionController.delete();
                        break;
                    case "4":
                        SolutionController.findAll();
                        break;
                    case "5":
                        SolutionController.findAllByExerciseId();
                        break;
                    case "6":
                        SolutionController.showAllSolutionsByUser();
                        break;
                    case "7":
                        SolutionController.assignUserToExercise();
                        break;
                    case "8":
                        userInput = "comeback";
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Podane zadanie nie istnieje");
                        break;
                }
            } else if (userInput.equals("5")) {
                userInput = "comeback";
            } else {
                System.out.println("Wybierz panel do którego chcesz przejść: ");
                System.out.println("1 - Użytkownicy | 2 - Grupy Użytkowników | 3 - Zadania | 4 - Rozwiązania | 5 - Powrót do menu");
                userInput = Checking.checkingString(scanner.nextLine());
            }
        }

        return userInput;


    }
}
