package io.github.thdudk.gui;

import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

// RUN THE PROGRAM FROM THIS CLASS
// I'm not sure why that's needed but the program refuses to run otherwise
// ref: https://www.reddit.com/r/JavaFX/comments/k7aa9q/comment/gexontj/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button
public class Core {
    public static void main(String[] args) {
        try(val reader = new BufferedReader(new InputStreamReader(System.in))) {
            pickAction: while(true) {
                System.out.println("Actions: ");
                System.out.println("0: Quit");
                System.out.println("1: New Diagram");
                System.out.println("2. Open Example Diagram");
                System.out.print("\nPick an action: ");
                switch(reader.readLine()) {
                    case "0" -> {break pickAction;}
                    case "1" -> {App.main(args);}
                    case "2" -> {}
                    default -> {
                        System.out.println("Invalid input, please try again.");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
