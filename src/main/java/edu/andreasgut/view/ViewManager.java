package edu.andreasgut.view;


import edu.andreasgut.sound.SoundManager;
import javafx.scene.Scene;
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
    private StartMenuView startMenuView;
    private OptionsView optionsView;
    private MainMenuBar mainMenuBar;
    private FieldView fieldView;
    private ScoreView scoreView;


    public ViewManager() {

        soundManager = new SoundManager(this);

        createStartScene();
        createGameScene();

        mainStage = new Stage();
        changeToStartScene();
        mainStage.show();
    }

    public void createStartScene(){
        startImageViewLeft = new StartImageView(this, 1);
        startImageViewLeft.getStyleClass().add("startImageview");
        startImageViewRight = new StartImageView(this, 2);
        startImageViewRight.getStyleClass().add("startImageview");
        startMenuView = new StartMenuView(this);
        startMenuView.getStyleClass().add("startMenuView");
        optionsView = new OptionsView(this);
        optionsView.getStyleClass().add("optionsView");
        mainMenuBar = new MainMenuBar(this);

        startPane = new BorderPane();
        startScene = new Scene(startPane, WIDTH, HEIGHT);
        startScene.getStylesheets().add("edu/andreasgut/style.css");

    }

    public void changeToStartScene(){
        startPane.setTop(mainMenuBar);
        startPane.setCenter(startMenuView);
        startPane.setLeft(startImageViewLeft);
        startPane.setRight(startImageViewRight);
        startPane.setBottom(optionsView);
        mainStage.setScene(startScene);
    }

    public void createGameScene(){

        fieldView = new FieldView(this);
        scoreView = new ScoreView(this);
        scoreView.getStyleClass().add("scoreview");

        gamePane = new BorderPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameScene.getStylesheets().add("edu/andreasgut/style.css");

    }

    public void changeToGameScene(){
        gamePane.setTop(mainMenuBar);
        gamePane.setCenter(fieldView);
        gamePane.setLeft(scoreView);
        gamePane.setBottom(optionsView);
        mainStage.setScene(gameScene);
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

    public StartImageView getStartImageViewRight() {
        return startImageViewRight;
    }

    public StartImageView getStartImageViewLeft() {
        return startImageViewLeft;
    }

    public StartMenuView getStartMenuView() {
        return startMenuView;
    }

    public OptionsView getOptionsView() {
        return optionsView;
    }

    public MainMenuBar getMainMenuBar() {
        return mainMenuBar;
    }

    public FieldView getFieldView() {
        return fieldView;
    }

    public ScoreView getScoreView() {
        return scoreView;
    }
}


