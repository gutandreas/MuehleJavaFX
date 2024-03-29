package edu.andreasgut.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LogView extends VBox {

    private Label titleLabel, statusLabel;
    private Button nextComputerStepButton;
    private ViewManager viewManager;

    public LogView(ViewManager viewManager, boolean computerOnlineBattle) {
        this.viewManager = viewManager;

        titleLabel = new Label("Verlauf");
        titleLabel.getStyleClass().add("labelTitle");
        statusLabel = new Label("");
        statusLabel.setWrapText(true);


        this.getChildren().addAll(titleLabel, statusLabel);

        if (computerOnlineBattle){
            nextComputerStepButton = new Button("Nächster Schritt des Computers");
            nextComputerStepButton.setOnAction(click -> {
                viewManager.getGame().callComputer();
                nextComputerStepButton.setDisable(true);
                System.out.println("Computer next step Button pressed");});
            this.getChildren().addAll(nextComputerStepButton);
        }
    }

    public void setStatusLabel(String string) {
        statusLabel.setText(string);
    }
}
