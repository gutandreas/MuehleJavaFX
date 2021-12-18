package edu.andreasgut.game;

import edu.andreasgut.view.FieldViewPlay;
import edu.andreasgut.view.ViewManager;

public class HumanPlayer extends Player {

    public HumanPlayer(ViewManager viewManager, String name, boolean local) {
        super(viewManager, name, local);
    }


    @Override
    public void prepareKill(ViewManager viewManager) {

        //lokaler Spieler
        if (viewManager.getGame().getCurrentPlayer().isLocal()){
            viewManager.getGame().setClickOkay(true);
            viewManager.getGame().setKillPhase(true);
            ((FieldViewPlay) viewManager.getFieldView()).setKillCursor();
        }
        //nicht lokaler Spieler
        else {
            viewManager.getGame().setClickOkay(false);
        }
    }

    @Override
    public void preparePutOrMove(ViewManager viewManager) {
        if (viewManager.getGame().getCurrentPlayer().isLocal()) {
            viewManager.getGame().setClickOkay(true);
        }
    }
}
