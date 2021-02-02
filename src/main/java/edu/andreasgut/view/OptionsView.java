package edu.andreasgut.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {

    private Button audioStop, audioStart;
    private final int OPTIOHEIGHT = 200;
    private ViewManager viewManager;

    public OptionsView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.setAlignment(Pos.CENTER);
        this.setHeight(OPTIOHEIGHT);
        audioStop = new Button("Musik stoppen");
        audioStart = new Button("Musik starten");
        audioStart.setDisable(true);
        this.getChildren().addAll(audioStop, audioStart);
        audioStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                viewManager.getSoundManager().stopMusic();
                System.out.println("Musik wird gestoppt");
                audioStop.setDisable(true);
                audioStart.setDisable(false);
            }});
        audioStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                viewManager.getSoundManager().getCurrentAudioclip().play();
                System.out.println("Musik wird gestartet");
                audioStart.setDisable(true);
                audioStop.setDisable(false);

            }});

    }


}
