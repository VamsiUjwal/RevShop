package com.revshop.controller;

import com.revshop.model.User;
import com.revshop.service.AuthService;
import com.revshop.service.impl.AuthServiceImpl;

import java.util.Scanner;

public class AuthController {

    private final AuthService authService = new AuthServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    public User login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        return authService.login(email, password);
    }

    public void registerBuyer() {
        User user = collectUserDetails();
        authService.registerBuyer(user);
        System.out.println("Buyer registered successfully.");
    }

    public void registerSeller() {
        User user = collectUserDetails();

        System.out.print("Business Name: ");
        String business = sc.nextLine();

        System.out.print("GST Number: ");
        String gst = sc.nextLine();

        authService.registerSeller(user, business, gst);
        System.out.println("Seller registered successfully.");
    }

    private User collectUserDetails() {
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Security Question: ");
        String question = sc.nextLine();

        System.out.print("Security Answer: ");
        String answer = sc.nextLine();

        return new User(name, email, password, null, question, answer);
    }
}

