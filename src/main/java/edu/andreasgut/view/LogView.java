package edu.andreasgut.view;

import edu.andreasgut.game.ComputerPlayer;
import edu.andreasgut.online.Messenger;
import edu.andreasgut.view.fxElements.NextStepButton;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.swing.event.ChangeListener;
import java.util.Locale;
import java.util.Optional;

public class LogView extends VBox {

    private Label titleLabel, statusLabel;
    private NextStepButton nextComputerStepButton;
    private TextArea chatTextArea;
    private TextField chatTextField;
    private Button chatSendButton;
    private VBox chatVBox;
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
                if (nextComputerStepButton.isPut()){
                    ((ComputerPlayer) viewManager.getGame().getCurrentPlayer()).triggerPut(viewManager);
                }
                if (nextComputerStepButton.isMove()){
                    ((ComputerPlayer) viewManager.getGame().getCurrentPlayer()).triggerMove(viewManager);
                }
                if (nextComputerStepButton.isKill()){
                    ((ComputerPlayer) viewManager.getGame().getCurrentPlayer()).triggerKill(viewManager);
                }
                nextComputerStepButton.setDisable(true);
                System.out.println("Computer next step Button pressed");});
            nextComputerStepButton.setDisable(true);
            this.getChildren().addAll(nextComputerStepButton);

            chatTextArea = new TextArea();
            chatTextArea.setEditable(false);
            chatTextArea.textProperty().addListener(change ->{
                    chatTextArea.setScrollTop(Double.MAX_VALUE);
                });

            chatTextField = new TextField();
            chatSendButton = new Button("Senden");
            chatSendButton.setOnAction(click -> {

                if (chatTextField.getText().length() == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Es können keine leeren Nachrichten verschickt werden!", ButtonType.OK);
                    alert.setAlertType(Alert.AlertType.NONE);
                    alert.setTitle("Leere Nachricht");
                    alert.showAndWait();
                }

                Messenger.sendChatMessage(viewManager, chatTextField.getText());
            });
            chatVBox = new VBox();
            chatVBox.getStyleClass().add("chatVBox");

            chatVBox.getChildren().addAll(chatTextArea, chatTextField, chatSendButton);

            this.getChildren().add(chatVBox);


        }

    }

    public void setStatusLabel(String string) {
        Platform.runLater(()-> {
            statusLabel.setText(string);
        });

    }

    public void postChatMessage(String name, String message){
        chatTextArea.setWrapText(true);
        String oldText;
        if(chatTextArea.getText().length() != 0){
            oldText = chatTextArea.getText() + "\n";
        }
        else {
            oldText = chatTextArea.getText();
        }
        chatTextArea.setText(oldText + name + ": " + message);
    }

    public NextStepButton getNextComputerStepButton() {
        return nextComputerStepButton;
    }

    public void activateNextComputerStepButton(){
        nextComputerStepButton.setDisable(false);
    }

    public void disableNextComputerStepButton(){
        nextComputerStepButton.setDisable(true);
    }
}
