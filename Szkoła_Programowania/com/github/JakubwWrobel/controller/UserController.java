package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;

import com.github.JakubwWrobel.dao.UserGroupDAO;
import com.github.JakubwWrobel.models.User;
import com.github.JakubwWrobel.dao.UserDAO;
import com.github.JakubwWrobel.models.UserGroup;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class UserController {
    private static UserDAO userDAO = new UserDAO();
    private static UserGroupDAO userGroupDAO = new UserGroupDAO();


    public static void main(String[] args) {
        System.out.println("Display Model");
//        addUser();
//        readUser();
        updateUser();
//        removeUser();
//        findAll();


    }

    private static void addUser() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Enter User Name: ");
            String userName = Checking.checkingString(scanner.nextLine());

            System.out.println("Enter User Email: ");
            String email = Checking.isValidEmailAddress(scanner.nextLine());

            System.out.println("Enter User Password: ");
            String password = Checking.checkingString(scanner.nextLine());

            User u = new User(userName, email, password);
            try {
                userDAO.create(u);
                System.out.println("Would you like to assign the group to the user?");
                System.out.println("Yes/No");
                String userGroup = Checking.checkingString(scanner.nextLine()).toLowerCase();
                if (userGroup.equals("yes")) {
                    while (running) {
                        System.out.println("Provide an ID of the user group: ");
                        userGroupDAO.showAll();
                        UserGroup userGroup1 = userGroupDAO.read(Checking.checkingInt());
                        if (userGroup1 == null) {
                            System.out.println("Following group does not exist");
                        } else {
                            int userInput = userGroup1.getUserGroupId();
                            userGroupDAO.insertUserGroupToUser(u, userInput);
                            running = false;
                        }
                    }
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Podany email jest już wykorzystany");
            }
        }
        System.out.println("Użytkownik został dodany");
        scanner.close();
    }


    private static void readUser() {

        int userInputId = Checking.checkingInt();
        userDAO.read(userInputId);
    }

    private static void updateUser() {
        Scanner scanner = new Scanner(System.in);
        userDAO.findAll();
        System.out.println();
//UPDATE
        boolean running = true;
        while (running) {
            System.out.println("Podaj ID użytkownika którego chcesz edytować: ");
            User user = userDAO.read(Checking.checkingInt());
            if (user == null) {
            } else {
                //INPUT FROM USER:
                System.out.println("Podaj nową nazwę użytkownika: ");
                user.setUsername(Checking.checkingString(scanner.nextLine()));
                System.out.println("Podaj nowy email użytkownika: ");
                user.setEmail(Checking.isValidEmailAddress(scanner.nextLine()));
                System.out.println("Podaj nowe hasło użytkownika: ");
                user.setPassword(Checking.checkingString(scanner.nextLine()));

                while (running) {
                    System.out.println("Podaj nowe ID grupy do której chcesz przypisać użytkownika: ");
                    UserGroup userGroup = userGroupDAO.read(Checking.checkingInt());
                    if (userGroup == null) {
                        System.out.println("Podana groupa nie istnieje");
                    } else {
                        user.setUserGroup(userGroup);
                        try {
                            userDAO.update(user);
                            running = false;
                        } catch (SQLIntegrityConstraintViolationException e) {
//CZY POWINIEM TUTAJ OBSŁUGIWAĆ TEN BŁĄÐ?
                            System.out.println("Podany email już istnieje, ta wartość nie może się potwarzać");
                        }
                    }
                }
            }
        }
        System.out.println("Użytkownik został zaaktualizowany");
        scanner.close();
    }




    private static void removeUser() {
//Czy scanner powinien być otwierany za każdym razem?
        boolean running = true;
        while (running) {
            userDAO.findAll();
            System.out.println("Podaj ID użytkownika, którego chcesz usunąć");
            Scanner scanner = new Scanner(System.in);
            int userInput = Checking.checkingInt();
            User user = userDAO.read(userInput); //CAN GIVE NULL
            if (user == null) {
            } else {
                userDAO.delete(user);
                System.out.println("Użytkownik został usunięty");
                running = false;
            }
        }

    }
    private static User[] findAll() {
        User[] users = userDAO.findAll();
        boolean running = true;
        while (running) {
            if (users == null) {
                System.out.println("Nie ma użytkowników do zwrócenia");
                return null;
            } else {
                for (int i = 0; i < users.length; i++) {
                    System.out.println(String.format("ID: %s\nNazwa użytkownika: %s\nEmail: %s\nGrupa: %s\n", users[i].getId(), users[i].getUsername(), users[i].getEmail(), users[i].getUserGroup().getUserGroupName()));
                }
                running = false;
                return users;
            }
        }
        return null;
    }

}

