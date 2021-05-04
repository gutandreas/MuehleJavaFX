package edu.andreasgut.game;

public class ScorePoints {

    private int ownOpenMorrisPoints, ownClosedMorrisPoints, ownPossibleMovesPoints, enemyOpenMorrisPoints, enemyClosedMorrisPoints, enemyBlockOpenMorrisPoints;

    public ScorePoints(int ownOpenMorrisPoints, int ownClosedMorrisPoints, int ownPossibleMovesPoints, int enemyOpenMorrisPoints, int enemyClosedMorrisPoints, int enemyBlockOpenMorrisPoints) {
        this.ownOpenMorrisPoints = ownOpenMorrisPoints;
        this.ownClosedMorrisPoints = ownClosedMorrisPoints;
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
}
