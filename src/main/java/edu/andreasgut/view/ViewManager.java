package edu.andreasgut.view;


import edu.andreasgut.sound.SoundManager;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewManager {

    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private BorderPane gamePane, startPane;
    private Scene gameScene, startScene;
    private Stage mainStage;
    private SoundManager soundManager;
    private StartImageView startImageViewRight, startImageViewLeft;
    private StartView startView;
    private OptionsView optionsView;
    private MainMenuBar mainMenuBar;
    private FieldView fieldView;
    private ScoreView scoreView;


    public ViewManager() {

        startImageViewRight = new StartImageView(this);
        startImageViewLeft = new StartImageView(this);
        startView = new StartView(this);
        optionsView = new OptionsView(this);
        mainMenuBar = new MainMenuBar(this);



        soundManager = new SoundManager(this);

        startPane = new BorderPane();
        startPane.setTop(mainMenuBar);
        startPane.setCenter(startView);
        startPane.setLeft(startImageViewLeft);
        startPane.setRight(startImageViewRight);
        startPane.setBottom(optionsView);

        mainStage = new Stage();
        startScene = new Scene(startPane, WIDTH, HEIGHT);
        startScene.getStylesheets().add("edu/andreasgut/style.css");
        mainStage.setScene(startScene);
        mainStage.show();




    }

    public void setGameScene(){

        fieldView = new FieldView(this);
        scoreView = new ScoreView(this);

        gamePane = new BorderPane();
        gamePane.setTop(mainMenuBar);
        gamePane.setCenter(fieldView);
        gamePane.setLeft(scoreView);
        gamePane.setBottom(optionsView);

        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        mainStage.setScene(gameScene);
        gameScene.getStylesheets().add("edu/andreasgut/style.css");

    }



    public Stage getMainStage(){
        return mainStage;
    }

    public BorderPane getGamePane(){
        return gamePane;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}


