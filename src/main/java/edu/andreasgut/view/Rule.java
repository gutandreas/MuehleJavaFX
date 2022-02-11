package edu.andreasgut.view;

import java.util.LinkedList;

public class Rule {

    private final String title;
    private final String description;
    private final String tags;
    private static LinkedList<Rule> rules = new LinkedList<>();

    static {
        rules.add(new Rule("Ziel des Spiels", "Ziel des Spiels ist es, durch das Bilden von Mühlen, dem/der Gegenspieler(in) Steine wegzunehmen, bis er/sie weniger als 3 Steine hat und somit keine Mühlen mehr bauen kann.", "Ziel, Mühle, Steine"));
        rules.add(new Rule("Ende des Spiels", "Das Spiel ist beendet, wenn ein(e) Spieler(in) in der Zugphase weniger als 3 Steine hat oder keinen Zug mehr tätigen kann", "Ende, eingeklemmt, verloren"));
        rules.add(new Rule("Anzahl Steine", "Jede(r) Spieler(in) hat 9 Steine, die er/sie setzen muss. Sind alle Steine gesetzt, so beginnt man abwechselnd die Steine entlang der Linien zu verschieben", "Anzahl, Steine, setzen"));
        rules.add(new Rule("Setzphase", "Das Spiel beginnt mit der Setzphase, in der alle Steine abwechselnd gesetzt werden", "setzen, abwechseln"));
        rules.add(new Rule("Zugphase", "Sind alle Steine gesetzt, so wird abwechselnd mit jeweils einem Stein entlang der Linien gezogen. Es darf jeweils nur 1 Feld weitergerückt werden, ausser man hat nur noch 3 Steine und befindet sich in der Springphase (siehe Springphase", "ziehen"));
        rules.add(new Rule("Springphase", "Hat ein(e) Spieler(in) nur noch 3 Steine, so darf er/sie mit jedem der 3 Steine auf ein beliebiges freies Feld hüpfen.", "springen, hüpfen"));
        rules.add(new Rule("Geschlossene Mühle", "Als geschlossene Mühle werden 3 Steine derselben Farbe in einer horizontalen oder vertikalen Linie bezeichnet. Wird eine Mühle gebildet, so darf ein gegnerischer Stein entfernt werden. Gegnerische Steine, die ebenfalls in einer geschlossenen Mühle liegen, dürfen nicht entfernt werden, ausser es sind nur noch 3 Steine übrig.", "verboten, Ausnahme, Mühle"));
        rules.add(new Rule("Offene Mühle", "Als offene Mühle werden 2 Steine derselben Farbe bezeichnet, die im folgenden Zug zu einer geschlossenen Mühle ergänzt werden können", "offen"));
        rules.add(new Rule("Zwickmühle", "Eine Zwickmühle entsteht, wenn beim Öffnen einer Mühle, eine andere Mühle geschlossen werden kann. Eigene Zwickmühlen sind sehr wertvoll, gegnerische Zwickmühlen gilt es möglichst zu vermeiden.", "Doppelmühle, öffnen, schliesssen"));
        rules.add(new Rule("Verbotene Steine", "Steine, die in einer Mühle liegen, dürfen im Normalfall nicht entfernt werden. Hat ein(e) Spieler(in) die eigenen Steine alle in geschlossenen Mühlen, so darf kein Stein entfernt werden, es sei denn, der/die Spieler(in) besitzt nur noch 3 Steine.", "Sonderfall, verboten"));
        rules.add(new Rule("Unentschieden", "In einigen Regelwerken endet das Spiel unentschieden, wenn nach 20 bzw. 50 Zügen keine Mühle erreicht werden konnte. Diese Spielversion enthält keine Zugobergrenze, die zu einem Unentschieden führt.", "unentschieden"));
    }

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
        return rules;
    }
}
