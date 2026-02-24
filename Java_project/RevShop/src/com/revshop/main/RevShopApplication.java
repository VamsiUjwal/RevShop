package com.revshop.main;

import com.revshop.controller.*;
import com.revshop.model.User;
import com.revshop.util.ConsoleUtil;

import java.util.Scanner;

public class RevShopApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AuthController authController = new AuthController();
        BuyerController buyerController = new BuyerController();
        SellerController sellerController = new SellerController();

        while (true) {

            System.out.println("\n==== REVSHOP ====");
            System.out.println("1. Register Buyer");
            System.out.println("2. Register Seller");
            System.out.println("3. Login");
            System.out.println("4. Exit");


            int choice = ConsoleUtil.readInt(sc, "Enter choice: ");






            switch (choice) {

                case 1 -> authController.registerBuyer();
                case 2 -> authController.registerSeller();
                case 3 -> {

                    User user;

                    try {
                        user = authController.login();
                        System.out.println("\nLogin Successful!");

                    } catch (Exception e) {
                        System.out.println("Login failed: " + e.getMessage());
                        break;
                    }

                    if (user.getRole().equals("BUYER")) {

                        try {
                            buyerController.showBuyerSummary(user);
                        } catch (Exception ignored) {}

                        buyerController.buyerMenu(user);

                    } else {

                        try {
                            sellerController.showSellerSummary(user);
                        } catch (Exception ignored) {}

                        sellerController.sellerMenu(user);
                    }
                }



                case 4 -> System.exit(0);
            }
        }
    }
}

