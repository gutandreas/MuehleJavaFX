package edu.andreasgut.game;

public class ScorePoints {

    private int ownNewClosedMorrisPoints, ownNewOpenMorrisPoints, ownClosedMorrisPoints, ownOpenMorrisPoints, ownTwoStonesTogetherPoints, ownTwoStonesWithGapPoints, ownPossibleMovesPoints, enemyNewClosedMorrisPoints, enemyNewOpenMorrisPoints, enemyClosedMorrisPoints, enemyOpenMorrisPoints, enemyTwoStonesTogetherPoints, enemyTwoStonesWithGapPoints, enemyPossibleMovesPoints;

    public ScorePoints(int ownNewClosedMorrisPoints, int ownNewOpenMorrisPoints, int ownClosedMorrisPoints, int ownOpenMorrisPoints, int ownTwoStonesTogetherPoints, int ownTwoStonesWithGapPoints, int ownPossibleMovesPoints, int enemyNewClosedMorrisPoints, int enemyNewOpenMorrisPoints, int enemyClosedMorrisPoints, int enemyOpenMorrisPoints, int enemyTwoStonesTogetherPoints, int enemyTwoStonesWithGapPoints, int enemyPossibleMovesPoints) {
        this.ownNewClosedMorrisPoints = ownNewClosedMorrisPoints;
        this.ownNewOpenMorrisPoints = ownNewOpenMorrisPoints;
        this.ownClosedMorrisPoints = ownClosedMorrisPoints;
        this.ownOpenMorrisPoints = ownOpenMorrisPoints;
        this.ownTwoStonesTogetherPoints = ownTwoStonesTogetherPoints;
        this.ownTwoStonesWithGapPoints = ownTwoStonesWithGapPoints;
        this.ownPossibleMovesPoints = ownPossibleMovesPoints;
        this.enemyNewClosedMorrisPoints = enemyNewClosedMorrisPoints;
        this.enemyNewOpenMorrisPoints = enemyNewOpenMorrisPoints;
        this.enemyClosedMorrisPoints = enemyClosedMorrisPoints;
        this.enemyOpenMorrisPoints = enemyOpenMorrisPoints;
        this.enemyTwoStonesTogetherPoints = enemyTwoStonesTogetherPoints;
        this.enemyTwoStonesWithGapPoints = enemyTwoStonesWithGapPoints;
        this.enemyPossibleMovesPoints = enemyPossibleMovesPoints;
    }

    public int getOwnNewClosedMorrisPoints() {
        return ownNewClosedMorrisPoints;
    }

    public int getOwnNewOpenMorrisPoints() {
        return ownNewOpenMorrisPoints;
    }

    public int getOwnClosedMorrisPoints() {
        return ownClosedMorrisPoints;
    }

    public int getOwnOpenMorrisPoints() {
        return ownOpenMorrisPoints;
    }

    public int getOwnTwoStonesTogetherPoints() {
        return ownTwoStonesTogetherPoints;
    }

    public int getOwnTwoStonesWithGapPoints() {
        return ownTwoStonesWithGapPoints;
    }

    public int getOwnPossibleMovesPoints() {
        return ownPossibleMovesPoints;
    }

    public int getEnemyNewClosedMorrisPoints() {
        return enemyNewClosedMorrisPoints;
    }

    public int getEnemyNewOpenMorrisPoints() {
        return enemyNewOpenMorrisPoints;
    }

    public int getEnemyClosedMorrisPoints() {
        return enemyClosedMorrisPoints;
    }

    public int getEnemyOpenMorrisPoints() {
        return enemyOpenMorrisPoints;
    }

    public int getEnemyTwoStonesTogetherPoints() {
        return enemyTwoStonesTogetherPoints;
    }

    public int getEnemyTwoStonesWithGapPoints() {
        return enemyTwoStonesWithGapPoints;
    }

    public int getEnemyPossibleMovesPoints() {
        return enemyPossibleMovesPoints;
    }
}
