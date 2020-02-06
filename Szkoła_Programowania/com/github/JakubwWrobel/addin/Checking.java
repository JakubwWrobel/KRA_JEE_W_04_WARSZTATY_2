package com.github.JakubwWrobel.addin;

import com.github.JakubwWrobel.controller.UserController;
import com.github.JakubwWrobel.models.User;
import jbcrypt.BCrypt;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Checking {
    private static Scanner scanner = new Scanner(System.in);

    public static String checkingString(String str) {
        boolean running = true;

        while (running) {
            if (str.isEmpty() || str == null) {
                System.out.println("Podana wartość nie może być pusta");
                str = scanner.nextLine();
            } else {
                return str;
            }
        }
        return null;
    }

    public static String isValidEmailAddress(String email) {
        boolean running = true;

        while (running) {
            try {
                if (email.isEmpty() || email == null) {
                    System.out.println("Podana wartość nie może być pusta");
                    email = scanner.nextLine();
                } else {
                    InternetAddress emailAddr = new InternetAddress(email);
                    emailAddr.validate();
                    running = false;
                    return email;
                }
            } catch (AddressException ex) {
                System.out.println("To nie jest poprawny format pliku mail");
                email = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Well... ");
            }
        }
        return null;
    }

    public static int checkingInt(){
//CHECKING IF INPUT IS AN INTEGER AND IS NOT EMPTY
        while(!scanner.hasNextInt() || scanner.findInLine("(?=\\S) ") != null){
            System.out.println("ID jest liczbą, podaj poprawne ID");
            scanner.next();
        }
        int id = scanner.nextInt();
        return id;
    }

}

