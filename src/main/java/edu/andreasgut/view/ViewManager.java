package edu.andreasgut.view;


import edu.andreasgut.sound.SoundManager;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewManager {

    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private BorderPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private SoundManager soundManager;

    public ViewManager() {

        mainPane = new BorderPane();
        mainPane.setTop(new MainMenuBar(this));
        mainPane.getTop().setVisible(false);
        mainPane.setCenter(new StartView(this));
        //mainPane.setCenter(new FieldView(this));
        mainPane.setLeft(new ScoreView(this));
        mainPane.getLeft().setVisible(false);
        mainPane.setBottom(new OptionsView(this));

        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add("edu/andreasgut/style.css");

        soundManager = new SoundManager(this);


    }



    public Stage getMainStage(){
        return mainStage;
    }

    public BorderPane getMainPane(){
        return mainPane;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}


