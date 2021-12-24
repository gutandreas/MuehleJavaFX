package edu.andreasgut.view;

import java.util.LinkedList;

public class Rule {

    String title;
    String description;

    public Rule(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    static public LinkedList getRules(){
        LinkedList<Rule> rules = new LinkedList<>();
        rules.add(new Rule("Ziel des Spiels", "Gewinnen..."));

        return rules;

    }
}
