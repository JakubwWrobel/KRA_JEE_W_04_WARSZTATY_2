package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;
import com.github.JakubwWrobel.dao.UserGroupDAO;
import com.github.JakubwWrobel.models.UserGroup;

import java.util.Scanner;


public class UserGroupController {
    private static UserGroupDAO userDAO = new UserGroupDAO();
    public static void main(String[] args) {
        update();
//        create();
//        delete();

    }


    private static void update() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        userDAO.showAll();
        System.out.println("Podaj ID grupy, którą chcesz zaaktualizować");
        while(running){
            UserGroup userGroup = userDAO.read(Checking.checkingInt());
            if(userGroup == null){
                System.out.println("Podana groupa nie istnieje");
            }else{
                System.out.println("Podaj nową nazwę groupy: ");
                String newGroupName = Checking.checkingString(scanner.nextLine());
                userDAO.update(userGroup);
                System.out.println("Groupa została uaktualniona");

                running =false;
                scanner.close();
            }
        }
    }

    private static void delete() {
        int userInput = Checking.checkingInt();
        System.out.println();
    }

    private static void create() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Group Name: ");
        String groupName = Checking.checkingString(scanner.nextLine());

        UserGroup userGroup = new UserGroup(groupName);
        userDAO.create(userGroup);

        scanner.close();
    }
}
