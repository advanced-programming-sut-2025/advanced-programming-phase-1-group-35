package org.example;

import View.AppView;

import java.io.IOException;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public Random rand = new Random();

    public static void main(String[] args) throws IOException {
//        App.deserializeApp();
        new AppView().run();
    }
}