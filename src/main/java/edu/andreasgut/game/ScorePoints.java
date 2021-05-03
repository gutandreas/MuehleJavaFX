package edu.andreasgut.game;

public class ScorePoints {

    private int ownOpenMillPoints, ownClosedMillPoints, ownPossibleMovesPoints, enemyOpenMillPoints, enemyClosedMillPoints, enemyBlockOpenMillPoints;

    public ScorePoints(int ownOpenMillPoints, int ownClosedMillPoints, int ownPossibleMovesPoints, int enemyOpenMillPoints, int enemyClosedMillPoints, int enemyBlockOpenMillPoints) {
        this.ownOpenMillPoints = ownOpenMillPoints;
        this.ownClosedMillPoints = ownClosedMillPoints;
        this.ownPossibleMovesPoints = ownPossibleMovesPoints;
        this.enemyOpenMillPoints = enemyOpenMillPoints;
        this.enemyClosedMillPoints = enemyClosedMillPoints;
        this.enemyBlockOpenMillPoints = enemyBlockOpenMillPoints;
    }


    public int getOwnOpenMillPoints() {
        return ownOpenMillPoints;
    }

    public void setOwnOpenMillPoints(int ownOpenMillPoints) {
        this.ownOpenMillPoints = ownOpenMillPoints;
    }

    public int getOwnClosedMillPoints() {
        return ownClosedMillPoints;
    }

    public void setOwnClosedMillPoints(int ownClosedMillPoints) {
        this.ownClosedMillPoints = ownClosedMillPoints;
    }

    public int getEnemyBlockOpenMillPoints() {
        return enemyBlockOpenMillPoints;
    }

    public void setEnemyBlockOpenMillPoints(int enemyBlockOpenMillPoints) {
        this.enemyBlockOpenMillPoints = enemyBlockOpenMillPoints;
    }

    public int getEnemyOpenMillPoints() {
        return enemyOpenMillPoints;
    }

    public void setEnemyOpenMillPoints(int enemyOpenMillPoints) {
        this.enemyOpenMillPoints = enemyOpenMillPoints;
    }

    public int getEnemyClosedMillPoints() {
        return enemyClosedMillPoints;
    }

    public void setEnemyClosedMillPoints(int enemyClosedMillPoints) {
        this.enemyClosedMillPoints = enemyClosedMillPoints;
    }

    public int getOwnPossibleMovesPoints() {
        return ownPossibleMovesPoints;
    }

    public void setOwnPossibleMovesPoints(int ownPossibleMovesPoints) {
        this.ownPossibleMovesPoints = ownPossibleMovesPoints;
    }
}
