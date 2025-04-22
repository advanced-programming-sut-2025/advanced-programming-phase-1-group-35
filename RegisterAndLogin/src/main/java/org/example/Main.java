package org.example;

import Model.App;
import View.AppView;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        App.deserializeApp();
        new AppView().run();
    }
}