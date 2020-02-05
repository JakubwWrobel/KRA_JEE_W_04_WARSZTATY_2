package com.github.JakubwWrobel.controller;

import com.github.JakubwWrobel.addin.Checking;
import com.github.JakubwWrobel.dao.ExerciseDAO;
import com.github.JakubwWrobel.models.Exercise;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class ExerciseController {
    private static ExerciseDAO exerciseDAO = new ExerciseDAO();

    public static void main(String[] args) {
        create();
//        finAll();
//        update();
//        delete();
    }

    protected static void create() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("Witaj w ekranie tworzenia zadania.");
            System.out.println("Podaj tytuł zadania");
            String title = Checking.checkingString(scanner.nextLine());
            System.out.println("Podaj opis zadania");
            String description = Checking.checkingString(scanner.nextLine());

            Exercise exercise = new Exercise(title, description);

            if (exerciseDAO.create(exercise) == null) {
                System.out.println("Zadanie nie zostało dodane");
                scanner.close();
                running = false;
            } else {
                System.out.println("Zadanie zostało utworzone");
                scanner.close();
                running = false;
            }
        }
    }
    protected static void update(){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        int userInput;
        while(running){
            finAll();
            System.out.println("Podaj ID zadania które chcesz edytować: ");
            userInput = Checking.checkingInt();
            Exercise exercise = exerciseDAO.read(userInput);
            if(exercise == null){
                System.out.println("Podany number zadania nie istnieje");
            }else {
                System.out.println("Podaj nowy tytuł zadania: ");
                String title = Checking.checkingString(scanner.nextLine());
                System.out.println("Podaj nowy opis zadania: ");
                String description = Checking.checkingString(scanner.nextLine());

                exercise.setTitle(title);
                exercise.setDescription(description);
                exerciseDAO.update(exercise);

                running = false;
                scanner.close();
                System.out.println("Zadanie zostało zaaktualizowane");
            }

        }
    }

    protected static void delete(){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        int userInput;
        while (running) {
            System.out.println("Podaj ID zadanie do usunięcia: ");
            userInput = Checking.checkingInt();
            Exercise exercise = exerciseDAO.read(userInput);
            if(exercise == null){

            }else{
                exerciseDAO.delete(userInput);
                scanner.close();
                running = false;
                System.out.println("Rekord został usunięty");
            }
        }

    }
    protected static Exercise[] finAll(){
        Exercise[] exercises = exerciseDAO.findAll();
        for(int i = 0; i< exercises.length; i++){
            System.out.println(String.format("ID zadania: %s\nTutuł zadania: %s\nOpis zadania: %s\n",exercises[i].getId(),exercises[i].getTitle(),exercises[i].getDescription()));
        }
        return exercises;
    }
}
