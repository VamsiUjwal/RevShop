package com.revshop.util;
import java.util.Scanner;
public class ConsoleUtil {

    public static void printHeader(String title) {
        System.out.println("\n=====================================");
        System.out.println(" " + title);
        System.out.println("=====================================");
    }

    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printAlert(String message) {
        System.out.println("[ALERT] " + message);
    }
    public static int readInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }



}

