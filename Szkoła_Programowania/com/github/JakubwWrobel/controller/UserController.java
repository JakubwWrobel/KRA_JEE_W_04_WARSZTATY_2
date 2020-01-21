package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;

import com.github.JakubwWrobel.models.User;
import com.github.JakubwWrobel.dao.UserDAO;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class UserController {
    private static UserDAO userDAO = new UserDAO();


    public static void main(String[] args) {
        System.out.println("Display Model");
//        addUser();
//        removeUser();
//        readUser();
//        updateUser();
        removeUser();

    }

    private static void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter User Name: ");
        String userName = Checking.checkingString(scanner.nextLine());

        System.out.println("Enter User Email: ");
        String email = Checking.isValidEmailAddress(scanner.nextLine());

        System.out.println("Enter User Password: ");
        String password = Checking.checkingString(scanner.nextLine());

        User u = new User(userName, email, password);
        userDAO.create(u);

        scanner.close();
    }

    private static void readUser() {
        int userInputId = Checking.checkingInt();
        userDAO.read(userInputId);
    }

    private static void updateUser() {
        Scanner scanner = new Scanner(System.in);
        userDAO.showAllUsers();
        System.out.println();
        System.out.println("Podaj ID użytkownika którego chcesz edytować: ");
        int userInputId = Checking.checkingInt();
        User user = userDAO.read(userInputId);
//UPDATE
        boolean running = true;
        while (running) {
            //INPUT FROM USER:
            System.out.println("Podaj nową nazwę użytkownika: ");
            user.setUsername(Checking.checkingString(scanner.nextLine()));
            System.out.println("Podaj nowy email użytkownika: ");
            user.setEmail(Checking.isValidEmailAddress(scanner.nextLine()));
            System.out.println("Podaj nowe hasło użytkownika: ");
            user.setPassword(Checking.checkingString(scanner.nextLine()));
            try {
                userDAO.update(user);
                running = false;
            } catch (SQLIntegrityConstraintViolationException e) {
//CZY POWINIEM TUTAJ OBSŁUGIWAĆ TEN BŁĄÐ?
                System.out.println("Podany email już istnieje, ta wartość nie może się potwarzać");
            }
        }
        scanner.close();
    }


    private static void removeUser() {
//Czy scanner powinien być otwierany za każdym razem?
        boolean running = true;
        while (running) {
            userDAO.showAllUsers();
            System.out.println("Podaj ID użytkownika, którego chcesz usunąć");
            Scanner scanner = new Scanner(System.in);
            int userInput = Checking.checkingInt();
            User user = userDAO.read(userInput); //CAN GIVE NULL
            if(user == null){
            }else{
                userDAO.delete(user);
                System.out.println("Użytkownik został usunięty");
                running = false;
            }
        }

    }

}

