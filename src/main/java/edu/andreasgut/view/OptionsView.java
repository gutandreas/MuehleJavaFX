package edu.andreasgut.view;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class OptionsView extends HBox {

    private ToggleButton audioStartStopToggleButton;
    private ViewManager viewManager;
    private boolean musicOn = true;

    public OptionsView(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.getStyleClass().add("optionView");
        audioStartStopToggleButton = new ToggleButton("Musik Ein");
        audioStartStopToggleButton.setSelected(true);
        audioStartStopToggleButton.getStyleClass().add("toggleButtonOn");
        audioStartStopToggleButton.setMinWidth(100);
        this.getChildren().addAll(audioStartStopToggleButton);
        audioStartStopToggleButton.setOnAction(action -> {
                if (!audioStartStopToggleButton.isSelected()){
                viewManager.getSoundManager().stopMusic();
                System.out.println("Musik wird gestoppt");
                audioStartStopToggleButton.setText("Musik Aus");
                audioStartStopToggleButton.getStyleClass().removeAll("toggleButtonOn");
                audioStartStopToggleButton.getStyleClass().add("toggleButtonOff");
                musicOn = false;
                }
                else {
                    viewManager.getSoundManager().getCurrentAudioclip().play();
                    System.out.println("Musik wird gestartet");
                    audioStartStopToggleButton.setText("Musik Ein");
                    audioStartStopToggleButton.getStyleClass().removeAll("toggleButtonOff");
                    audioStartStopToggleButton.getStyleClass().add("toggleButtonOn");
                    musicOn = true;
                }
            });
    }

    public boolean isMusicOn() {
        return musicOn;
    }
}
