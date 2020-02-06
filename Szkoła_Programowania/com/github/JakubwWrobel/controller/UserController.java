package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;

import com.github.JakubwWrobel.addin.GetConnection;
import com.github.JakubwWrobel.dao.UserGroupDAO;
import com.github.JakubwWrobel.models.User;
import com.github.JakubwWrobel.dao.UserDAO;
import com.github.JakubwWrobel.models.UserGroup;
import jbcrypt.BCrypt;

import java.sql.*;
import java.util.Scanner;

public class UserController {
    private static UserDAO userDAO = new UserDAO();
    private static UserGroupDAO userGroupDAO = new UserGroupDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Display Model");
//        addUser();
//        readUser();
//        updateUser();
//        removeUser();
//        findAll();
//        findAllByGroupId();
//        assignGroupToUser();
//        isAdmin();


    }

    protected static void addUser() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Podaj nazwę użytkownika ");
            String userName = Checking.checkingString(scanner.nextLine());

            System.out.println("Podaj email użytkownika ");
            String email = Checking.isValidEmailAddress(scanner.nextLine());

            System.out.println("Podaj hasło użytkownika ");
            String password = Checking.checkingString(scanner.nextLine());

            User u = new User(userName, email, password);
            try {
                userDAO.create(u);
                System.out.println("Chcesz przypisać użytkownika do grupy?");
                System.out.println("Yes/No");
                String userGroup = Checking.checkingString(scanner.nextLine()).toLowerCase();
                if (userGroup.equals("yes")) {
                    while (running) {
                        System.out.println("Podaj ID grupy");
                        userGroupDAO.showAll();
                        UserGroup userGroup1 = userGroupDAO.read(Checking.checkingInt());
                        if (userGroup1 == null) {
                            System.out.println("Podana grupa nie istnieje");
                        } else {
                            int userInput = userGroup1.getUserGroupId();
                            userGroupDAO.insertUserGroupToUser(u, userInput);
                            running = false;
                        }
                    }
                }
                running = false;
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Podany email jest już wykorzystany");
            }
        }
        System.out.println("Użytkownik został dodany");
    }


    protected static User readUser() {
        boolean running = true;
        while (running) {
            findAll();
            System.out.println("Podaj ID użytkownika: ");
            int userInputId = Checking.checkingInt();
            User user = userDAO.read(userInputId);
            if(user == null){
                System.out.println("Podany użytkownik nie istnieje");
            }else {
                return user;
            }
        }
        return null;
    }

    protected static void updateUser() {
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
                            System.out.println("Użytkownik został zaaktualizowany");
                            running = false;
                        } catch (SQLIntegrityConstraintViolationException e) {
//CZY POWINIEM TUTAJ OBSŁUGIWAĆ TEN BŁĄÐ?
                            System.out.println("Podany email już istnieje, ta wartość nie może się potwarzać");
                        }
                    }
                }
            }
        }
    }


    protected static void assignGroupToUser() {
        boolean running = true;
        int userInput;
        while (running) {
            findAll();
            System.out.println("Podaj ID użytkownika do którego chcesz przypisać groupę użytkowników");
            userInput = Checking.checkingInt();
            User user = userDAO.read(userInput);
            if (user == null) {
                System.out.println("Podany użytkownik nie istnieje");
            } else {
                while (running) {
                    UserGroupController.findAll();
                    System.out.println("Podaj ID grupy do której chcesz przypisać użytkownika: ");
                    UserGroup userGroup = userGroupDAO.read(Checking.checkingInt());
                    if (userGroup == null) {
                        System.out.println("Podana grupa nie istnieje");
                    } else {
                        userGroupDAO.insertUserGroupToUser(user, userGroup.getUserGroupId());
                        System.out.println("Użytkownik został przypisany do grupy");
                        running = false;
                    }
                }
            }
        }
    }

    protected static void removeUser() {
        boolean running = true;
        while (running) {
            userDAO.findAll();
            System.out.println("Podaj ID użytkownika, którego chcesz usunąć");
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

    protected static void findAllByGroupId() {
        userGroupDAO.showAll();
        System.out.println("Podaj ID group dla której chcesz wyświetlić użytkowników");
        int userGroupId;
        int userInput = Checking.checkingInt();
        UserGroup userGroup = userGroupDAO.read(userInput);
        if (userGroup == null) {
            System.out.println("Podana groupa nie istnieje");
        } else {
            User[] users = userDAO.findAllByGroupId(userInput);

            for (User user : users) {
                if (user.getUserGroup() == null) {
                    userGroupId = 0;
                } else {
                    userGroupId = user.getUserGroup().getUserGroupId();
                }
                System.out.println(String.format("ID: %s\nUsername: %s\nEmail: %s\nUser Group ID: %s\n", user.getId(), user.getUsername(), user.getEmail(), userGroupId));
            }
        }
    }

    protected static User[] findAll() {
        User[] users = userDAO.findAll();
        boolean running = true;
        int userGroupId;
        while (running) {
            if (users == null) {
                System.out.println("Nie ma użytkowników do zwrócenia");
                return null;
            } else {
                for (int i = 0; i < users.length; i++) {
                    if (users[i].getUserGroup() == null) {
                        userGroupId = 0;
                    } else {
                        userGroupId = users[i].getUserGroup().getUserGroupId();
                    }
                    System.out.println(String.format("ID: %s\nNazwa użytkownika: %s\nEmail: %s\nGrupa: %s\n", users[i].getId(), users[i].getUsername(), users[i].getEmail(), userGroupId));
                }
                running = false;
                return users;
            }
        }
        return null;
    }
    public static boolean isAdmin() {
        System.out.println("Podaj hasło: ");
        String password = Checking.checkingString(scanner.nextLine());
        String passwordAdmin = userDAO.isAdmin();
        if (BCrypt.checkpw(password, passwordAdmin)) {
            System.out.println("Ok");
            return true;
        } else {
            System.out.println("Błędne hasło");
            return false;
        }
    }
}
