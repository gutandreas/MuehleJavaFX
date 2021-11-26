package edu.andreasgut.game;
import edu.andreasgut.view.ViewManager;


abstract public class ComputerPlayer extends Player {

    protected GameTree gameTree = new GameTree();
    protected boolean automaticTrigger;
    protected ScorePoints putPoints;
    protected ScorePoints movePoints;
    protected int levelLimit;


    //Lokaler Computer
    public ComputerPlayer(ViewManager viewManager, String name, ScorePoints putPoints, ScorePoints movePoints, int levelLimit) {
        super(viewManager, name, true);
        automaticTrigger = true;
        this.putPoints = putPoints;
        this.movePoints = movePoints;
        this.levelLimit = levelLimit;
    }

    //Online-Battle-Computer
    public ComputerPlayer(ViewManager viewManager, String name, String uuid, ScorePoints putPoints, ScorePoints movePoints, int levelLimit) {
        super(viewManager, name, uuid);
        automaticTrigger = false;
        this.putPoints = putPoints;
        this.movePoints = movePoints;
        this.levelLimit = levelLimit;
    }


    abstract Position put(Board board, int playerIndex);

    abstract Move move(Board board, int playerIndex, boolean allowedToJump);

    abstract Position kill(Board board, int ownPlayerIndex, int otherPlayerIndex);

    abstract public void triggerPut(ViewManager viewManager);

    abstract public void triggerMove(ViewManager viewManager);

    abstract public void triggerKill(ViewManager viewManager);




}
