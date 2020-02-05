package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;
import com.github.JakubwWrobel.dao.UserGroupDAO;
import com.github.JakubwWrobel.models.UserGroup;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;


public class UserGroupController {
    private static UserGroupDAO userGroupDAO = new UserGroupDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        update();
//        create();
//        delete();
//        findAll();

    }


    protected static void update() {
        boolean running = true;
        userGroupDAO.showAll();
        System.out.println("Podaj ID grupy, którą chcesz zaaktualizować");
        while (running) {
            UserGroup userGroup = userGroupDAO.read(Checking.checkingInt());
            if (userGroup == null) {
                System.out.println("Podana grupa nie istnieje");
            } else {
                System.out.println("Podaj nową nazwę groupy: ");
                String newGroupName = Checking.checkingString(scanner.nextLine());
                userGroupDAO.update(userGroup);
                System.out.println("Groupa została uaktualniona");

                running = false;
            }
        }
    }

    protected static void delete() {
        int userInput = Checking.checkingInt();
        System.out.println();
    }

    protected static void create() {
        System.out.println("Enter Group Name: ");
        String groupName = Checking.checkingString(scanner.nextLine());

        UserGroup userGroup = new UserGroup(groupName);
        userGroupDAO.create(userGroup);

    }

    protected static void findAll() {
        UserGroup[] userGroups = userGroupDAO.showAll();
        if (userGroups == null) {
            System.out.println("Nie istnieje żadna grupa użytkowników");
        } else {
            for (UserGroup userGroup : userGroups) {
                System.out.println(String.format("ID grupy: %s\nNazwa Grupy: %s\n", userGroup.getUserGroupId(), userGroup.getUserGroupName()));
            }
        }
    }
}
