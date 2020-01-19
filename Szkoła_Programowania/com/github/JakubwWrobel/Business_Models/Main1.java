package com.github.JakubwWrobel.Business_Models;

import com.github.JakubwWrobel.ADDins.GetConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
public class Main1 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę użytkownika: ");

        try{
            Connection connection = GetConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user");
            preparedStatement.executeQuery();

        }catch (SQLException e){
            System.out.println("test");

        User u = new User(1,"Jakub","mail","ala");
            System.out.println(u.getPassword());
            System.out.println(u.checkPassword("alaa"));


        }

        boolean running = true;
        while (running == true) {
            try {
                String str = scanner.nextLine();
                if ( str == null || str.isEmpty() || isNull(str)){
                    throw new NullPointerException();
                }

                running = false;
            } catch (NullPointerException e) {
                System.out.println("Pusty element");
            }
        }
    }

    public static boolean isNull(String str) {
        if (str.equals("null")) {
            System.out.println("To jest NULL");
            return true;
        } else {
            return false;
        }
    }
}
