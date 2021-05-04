package edu.andreasgut.game;

public class ScorePoints {

    private int ownOpenMorrisPoints, ownClosedMorrisPoints, ownNewClosedMorrisPoints, ownNewOpenMorrisPoints, ownPossibleMovesPoints, enemyOpenMorrisPoints, enemyClosedMorrisPoints, enemyBlockOpenMorrisPoints;

    public ScorePoints(int ownOpenMorrisPoints, int ownClosedMorrisPoints, int ownNewClosedMorrisPoints, int ownNewOpenMorrisPoints, int ownPossibleMovesPoints, int enemyOpenMorrisPoints, int enemyClosedMorrisPoints, int enemyBlockOpenMorrisPoints) {
        this.ownOpenMorrisPoints = ownOpenMorrisPoints;
        this.ownClosedMorrisPoints = ownClosedMorrisPoints;
        this.ownNewClosedMorrisPoints = ownNewClosedMorrisPoints;
        this.ownNewOpenMorrisPoints = ownNewOpenMorrisPoints;
        this.ownPossibleMovesPoints = ownPossibleMovesPoints;
        this.enemyOpenMorrisPoints = enemyOpenMorrisPoints;
        this.enemyClosedMorrisPoints = enemyClosedMorrisPoints;
        this.enemyBlockOpenMorrisPoints = enemyBlockOpenMorrisPoints;
    }


    public int getOwnOpenMorrisPoints() {
        return ownOpenMorrisPoints;
    }

    public void setOwnOpenMorrisPoints(int ownOpenMorrisPoints) {
        this.ownOpenMorrisPoints = ownOpenMorrisPoints;
    }

    public int getOwnClosedMorrisPoints() {
        return ownClosedMorrisPoints;
    }

    public void setOwnClosedMorrisPoints(int ownClosedMorrisPoints) {
        this.ownClosedMorrisPoints = ownClosedMorrisPoints;
    }

    public int getEnemyBlockOpenMorrisPoints() {
        return enemyBlockOpenMorrisPoints;
    }

    public void setEnemyBlockOpenMorrisPoints(int enemyBlockOpenMorrisPoints) {
        this.enemyBlockOpenMorrisPoints = enemyBlockOpenMorrisPoints;
    }

    public int getEnemyOpenMorrisPoints() {
        return enemyOpenMorrisPoints;
    }

    public void setEnemyOpenMorrisPoints(int enemyOpenMorrisPoints) {
        this.enemyOpenMorrisPoints = enemyOpenMorrisPoints;
    }

    public int getEnemyClosedMorrisPoints() {
        return enemyClosedMorrisPoints;
    }

    public void setEnemyClosedMorrisPoints(int enemyClosedMorrisPoints) {
        this.enemyClosedMorrisPoints = enemyClosedMorrisPoints;
    }

    public int getOwnPossibleMovesPoints() {
        return ownPossibleMovesPoints;
    }

    public void setOwnPossibleMovesPoints(int ownPossibleMovesPoints) {
        this.ownPossibleMovesPoints = ownPossibleMovesPoints;
    }

    public int getOwnNewClosedMorrisPoints() {
        return ownNewClosedMorrisPoints;
    }

    public void setOwnNewClosedMorrisPoints(int ownNewClosedMorrisPoints) {
        this.ownNewClosedMorrisPoints = ownNewClosedMorrisPoints;
    }

    public int getOwnNewOpenMorrisPoints() {
        return ownNewOpenMorrisPoints;
    }

    public void setOwnNewOpenMorrisPoints(int ownNewOpenMorrisPoints) {
        this.ownNewOpenMorrisPoints = ownNewOpenMorrisPoints;
    }
}
