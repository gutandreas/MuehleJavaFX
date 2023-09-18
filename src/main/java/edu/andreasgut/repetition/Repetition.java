package edu.andreasgut.repetition;

import edu.andreasgut.game.Board;
import edu.andreasgut.game.Position;



import java.util.LinkedList;

public class Repetition {

    // TODO: Implementiere die folgende Methode so, dass sie eine Position aufnimmt und eine Position zurückgibt,
    //       welche auf der gegenüberliegenden Seite des Spielbretts liegt (also an der Mitte punktgespiegelt wurde).
    public static Position mirrorPositionAtTheCenter(Position position) {

        Position mirroredPosition = null;

        return mirroredPosition;
    }


    // TODO: Implementiere eine Methode, die testet, ob drei übergebene Positionen eine Mühle eines Spielers bilden.
    //       Dazu soll zuerst geprüft werden, ob die drei Positionen überhaupt an Stellen liegen, die eine Mühle
    //       bilden könnten. Ist dies der Fall, so muss überprüft werden, ob an diesen Stellen tatsächlich überall
    //       ein Stein des Spielers mit dem übergebenen Index liegt. Nur wenn beides erfüllt ist, soll "true"
    //       zurückgegeben werden.Hinweis: Die Positionen kommen immer geordnet. Position 1 ist also kleiner als
    //       Position 2 usw.
    public static boolean isThisAMorrisOfPlayerWithIndex(int playerIndex, Position position1, Position position2,
                                                         Position position3, Board board) {

        return false;
    }

    // TODO: Implementiere die folgende Methode so, dass sie ein Array von Positions als Parameter aufnimmt und eine
    //       LinkedList<Position> mit demselben Inhalt zurückgibt
    public static LinkedList<Position> convertArrayToLinkedList(Position[] positions) {

        LinkedList<Position> positionLinkedList = new LinkedList<Position>();

        return positionLinkedList;
    }

    // TODO: Immplementiere die folgende Methode so, dass sie eine LinkedList<Position> aufnimmt und ebenfalls eine
    //       LinkedList<Position> zurückgibt, in der alle Elemente umgekehrt angeordnert sind. Hinweis: Du brauchst dazu
    //       die Methode pollLast(), die auf LinkedLists angewandt werden kann.
    public static LinkedList<Position> reverseListOrder(LinkedList<Position> linkedList) {

        LinkedList<Position> reversedList = new LinkedList<Position>();

        return reversedList;
    }



    // TODO: Implementiere die folgende Methode so, dass sie eine LinkedList<Position> zurückgibt, die alle Positionen
    //       enthält, die vom übergebenen Playerindex besetzt sind.
    public static LinkedList<Position> getAllPositionsOfPlayerWithIndex(int playerIndex, Board board){

        LinkedList<Position> myPositions = new LinkedList<Position>();

        return myPositions;
    }

    // TODO: Implementiere die folgende Methode so, dass die Positionen zweier Knoten im Baum getauscht werden,
    //       sodass sowohl die Eltern- wie auch die Kindknoten angepasst werden. Hinweis: Verwende dazu die Methode
    //       replaceChildren, die auf einem TreeNode aufgerufen werden kann.
    public static void swapTwoNodes(TreeNode node1, TreeNode node2){

    }


}
