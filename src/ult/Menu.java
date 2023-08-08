package ult;

import java.util.ArrayList;

public class Menu extends ArrayList<String> {
    //Constructor
    public Menu() {
    }

    //Methods
    public void addOption(String option) {
        add(option);
    }

    public void showOption(String welcome) {
        System.out.println(welcome);
        System.out.println("There are some options below:");
        for (String s : this) {
            System.out.println(s);
        }
    }

    public int getOption(String message, String errorMessage) {
        Input input = new Input();
        return input.getInt(message, errorMessage);
    }
}
