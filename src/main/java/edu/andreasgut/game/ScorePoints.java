package edu.andreasgut.game;

public class ScorePoints {

    private int ownNumberOfStonesPoints, ownNewOpenMorrisPoints, ownClosedMorrisPoints, ownOpenMorrisPoints, ownTwoStonesTogetherPoints, ownTwoStonesWithGapPoints, ownPossibleMovesPoints, enemyNumberOfStonesPoints, enemyClosedMorrisPoints, enemyOpenMorrisPoints, enemyTwoStonesTogetherPoints, enemyTwoStonesWithGapPoints, enemyPossibleMovesPoints;

    public ScorePoints(int ownNumberOfStonesPoints, int ownClosedMorrisPoints, int ownOpenMorrisPoints, int ownTwoStonesTogetherPoints, int ownTwoStonesWithGapPoints, int ownPossibleMovesPoints, int enemyNumberOfStonesPoints, int enemyClosedMorrisPoints, int enemyOpenMorrisPoints, int enemyTwoStonesTogetherPoints, int enemyTwoStonesWithGapPoints, int enemyPossibleMovesPoints) {
        this.ownNumberOfStonesPoints = ownNumberOfStonesPoints;
        this.ownNewOpenMorrisPoints = ownNewOpenMorrisPoints;
        this.ownClosedMorrisPoints = ownClosedMorrisPoints;
        this.ownOpenMorrisPoints = ownOpenMorrisPoints;
        this.ownTwoStonesTogetherPoints = ownTwoStonesTogetherPoints;
        this.ownTwoStonesWithGapPoints = ownTwoStonesWithGapPoints;
        this.ownPossibleMovesPoints = ownPossibleMovesPoints;
        this.enemyNumberOfStonesPoints = enemyNumberOfStonesPoints;
        this.enemyClosedMorrisPoints = enemyClosedMorrisPoints;
        this.enemyOpenMorrisPoints = enemyOpenMorrisPoints;
        this.enemyTwoStonesTogetherPoints = enemyTwoStonesTogetherPoints;
        this.enemyTwoStonesWithGapPoints = enemyTwoStonesWithGapPoints;
        this.enemyPossibleMovesPoints = enemyPossibleMovesPoints;
    }

    public int getOwnNumberOfStonesPoints() {
        return ownNumberOfStonesPoints;
    }

    public int getOwnKillPoints() {
        return ownNumberOfStonesPoints;
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

    public int getEnemyNumberOfStonesPoints() {
        return enemyNumberOfStonesPoints;
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
