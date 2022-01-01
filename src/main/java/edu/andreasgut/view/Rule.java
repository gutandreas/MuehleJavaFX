package edu.andreasgut.view;

import java.util.LinkedList;

public class Rule {

    String title;
    String description;
    String tags;

    public Rule(String title, String description, String tags) {
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    static public LinkedList<Rule> getRules(){
        LinkedList<Rule> rules = new LinkedList<>();

        rules.add(new Rule("Ziel des Spiels", "Gewinnen...", "Ziel, Erklärung"));
        rules.add(new Rule("Ende des Spiels", "Das Spiel ist beendet, wenn ein Spieler in der Zugphase weniger als 3 Steine hat oder keinen Zug mehr tätigen kann", "Ende, eingeklemmt, verloren"));

        return rules;
    }
}
