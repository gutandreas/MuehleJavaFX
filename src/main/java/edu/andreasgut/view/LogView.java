package edu.andreasgut.view;

import edu.andreasgut.communication.Messenger;
import edu.andreasgut.game.ComputerPlayer;
import edu.andreasgut.view.fxElements.NextStepButton;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Random;

public class LogView extends VBox {

    private final Label titleLabel;
    private final Label statusLabel;
    private NextStepButton nextComputerStepButton;
    private TextArea chatTextArea;
    private TextField chatTextField;
    private Button chatSendButton, chatComplimentButton, chatOffendButton;
    private VBox chatVBox;
    private final ViewManager viewManager;


    public LogView(ViewManager viewManager, boolean computerOnlineBattle) {
        this.viewManager = viewManager;

        titleLabel = new Label("Verlauf");
        titleLabel.getStyleClass().add("labelTitle");
        statusLabel = new Label("");
        statusLabel.setWrapText(true);


        this.getChildren().addAll(titleLabel, statusLabel);

        if (computerOnlineBattle) {
            nextComputerStepButton = new NextStepButton("Stein setzen");
            nextComputerStepButton.getStyleClass().add("nextStepButton");
            nextComputerStepButton.setOnAction(click -> {
                if (nextComputerStepButton.isPut()) {
                    ((ComputerPlayer) viewManager.getGame().getCurrentPlayer()).triggerPut(viewManager);
                }
                if (nextComputerStepButton.isMove()) {
                    ((ComputerPlayer) viewManager.getGame().getCurrentPlayer()).triggerMove(viewManager);
                }
                if (nextComputerStepButton.isKill()) {
                    ((ComputerPlayer) viewManager.getGame().getCurrentPlayer()).triggerKill(viewManager);
                }
                nextComputerStepButton.setDisable(true);
                System.out.println("Computer next step Button pressed");
            });
            nextComputerStepButton.setDisable(true);
            this.getChildren().addAll(nextComputerStepButton);

            chatTextArea = new TextArea();
            chatTextArea.setEditable(false);
            chatTextField = new TextField();
            chatTextField.setPromptText("Chatnachricht...");
            chatTextField.setDisable(true);
            chatSendButton = new Button("Senden");
            chatSendButton.setDisable(true);
            chatComplimentButton = new Button("Gegner loben");
            chatComplimentButton.getStyleClass().add("complimentButton");
            chatComplimentButton.setDisable(true);
            chatComplimentButton.setOnAction(click -> {
                Messenger.sendChatMessage(viewManager, getRandomCompliment());
            });
            chatOffendButton = new Button("Gegner beleidigen");
            chatOffendButton.getStyleClass().add("offendButton");
            chatOffendButton.setOnAction(click -> {
                Messenger.sendChatMessage(viewManager, getRandomOffense());
            });
            chatOffendButton.setDisable(true);
            chatSendButton.setOnAction(click -> {

                if (chatTextField.getText().length() == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "Es können keine leeren Nachrichten verschickt werden!", ButtonType.OK);
                    alert.setAlertType(Alert.AlertType.NONE);
                    alert.setTitle("Leere Nachricht");
                    alert.showAndWait();
                }

                Messenger.sendChatMessage(viewManager, chatTextField.getText());
                chatTextField.setText("");
            });
            if (viewManager.getGame().isJoinExistingGame()) {
                activateChatElements(true);
            }

            chatVBox = new VBox();
            chatVBox.getStyleClass().add("chatVBox");

            chatVBox.getChildren().addAll(chatTextArea, chatTextField, chatSendButton, chatComplimentButton, chatOffendButton);

            this.getChildren().add(chatVBox);


        }

    }

    public void setStatusLabel(String string) {
        Platform.runLater(() -> {
            statusLabel.setText(string);
        });

    }

    public void postChatMessage(String name, String message) {
        chatTextArea.setWrapText(true);
        if (chatTextArea.getText().length() != 0) {
            chatTextArea.appendText("\n");
        }
        chatTextArea.appendText(name + ": " + message);
    }

    public NextStepButton getNextComputerStepButton() {
        return nextComputerStepButton;
    }

    public void activateNextComputerStepButton() {
        nextComputerStepButton.setDisable(false);
    }

    public void disableNextComputerStepButton() {
        nextComputerStepButton.setDisable(true);
    }

    public void activateChatElements(boolean active) {
        chatTextField.setDisable(!active);
        chatSendButton.setDisable(!active);
        chatOffendButton.setDisable(!active);
        chatComplimentButton.setDisable(!active);
    }

    private String getRandomOffense() {
        String[] offenses = {"Uuuuuh, das war blöd...",
                "Mein Cousin spielt besser. ...und der ist 3.",
                "Das war ja gar nix...",
                "Und so willst du gewinnen?",
                "Deine Strategie ist... ...speziell.",
                "Effiziente Strategie, um zu verlieren.",
                "Also so gewinnst du garantiert nicht!",
                "Meine Grossmutter gewinnt gegen dich im Schlaf!",
                "Meinst du diesen Zug wirklich ernst?",
                "Hoffentlich gibt's Spiele, die du besser spielst!",
                "Hoffentlich hast du ein anderes Talent!",
                "Ist das wirklich alles, was du kannst?",
                "Ist dein Gehirn schon an?",
                "Weisst du wirklich, was das Ziel des Spiels ist?",
                "Fährst du nebenbei noch Auto?",
                "Du bist ein guter Egobooster für mich!",
                "Soll ich dir das Ziel des Spiels nochmals erklären?"};
        Random random = new Random();
        int randomInt = random.nextInt(offenses.length);

        return offenses[randomInt];
    }

    private String getRandomCompliment() {
        String[] offenses = {"Cleverer Zug!",
                "Du spielst beeindruckend!",
                "Gute Strategie!",
                "Du bist ein wirklich harter Gegner!",
                "Wow, der war gut!",
                "Echt stark gespielt!",
                "Saubere Leistung!",
                "Du spielst gut!",
                "Du machst mir das Leben schwer!",
                "Gut gespielt!"};
        Random random = new Random();
        int randomInt = random.nextInt(offenses.length);

        return offenses[randomInt];
    }


}
