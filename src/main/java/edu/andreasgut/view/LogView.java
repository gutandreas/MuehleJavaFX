package edu.andreasgut.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LogView extends VBox {

    private Label titleLabel, statusLabel;
    private ViewManager viewManager;

    public LogView(ViewManager viewManager) {
        this.viewManager = viewManager;

        titleLabel = new Label("Verlauf");
        titleLabel.getStyleClass().add("labelTitle");
        statusLabel = new Label("");
        statusLabel.setWrapText(true);

        this.getChildren().addAll(titleLabel, statusLabel);
    }

    public void setStatusLabel(String string) {
        statusLabel.setText(string);
    }
}
