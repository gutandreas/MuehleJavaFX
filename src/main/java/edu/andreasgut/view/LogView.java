package edu.andreasgut.view;

import edu.andreasgut.view.fxElements.NextStepButton;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LogView extends VBox {

    private Label titleLabel, statusLabel;
    private NextStepButton nextComputerStepButton;
    private ViewManager viewManager;


    public LogView(ViewManager viewManager, boolean computerOnlineBattle) {
        this.viewManager = viewManager;

        titleLabel = new Label("Verlauf");
        titleLabel.getStyleClass().add("labelTitle");
        statusLabel = new Label("");
        statusLabel.setWrapText(true);


        this.getChildren().addAll(titleLabel, statusLabel);

        if (computerOnlineBattle){
            nextComputerStepButton = new NextStepButton("Stein setzen");
            nextComputerStepButton.setOnAction(click -> {
                viewManager.getGame().callComputer(nextComputerStepButton.isPut(), nextComputerStepButton.isMove(), nextComputerStepButton.isKill());
                nextComputerStepButton.setDisable(true);
                System.out.println("Computer next step Button pressed");});
            nextComputerStepButton.setDisable(true);
            this.getChildren().addAll(nextComputerStepButton);
        }
    }

    public void setStatusLabel(String string) {
        Platform.runLater(()-> {
            statusLabel.setText(string);
        });

    }

    public NextStepButton getNextComputerStepButton() {
        return nextComputerStepButton;
    }

    public void activateNextComputerStepButton(){
        nextComputerStepButton.setDisable(false);
    }
}
