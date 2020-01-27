package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.dao.SolutionDAO;
import com.github.JakubwWrobel.models.Solution;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class SolutionController {
    private static SolutionDAO solutionDAO = new SolutionDAO();

    public static void main(String[] args) {
        create();
    }

    private static void create() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Podaj datę utworzenia użytkownika: ");

            DateFormat formatter = null;
            Date convertedDate = null;

            String yyyyMMdd = "20110914";
            formatter =new SimpleDateFormat("yyyyMMdd");
            try {
                convertedDate =(Date) formatter.parse(yyyyMMdd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            java.sql.Date sqlStartDate = new java.sql.Date(convertedDate.getTime());


            Solution solution = new Solution(sqlStartDate, sqlStartDate, "new test");
            solutionDAO.create(solution);


            scanner.close();
            running = false;
        }

    }
}
