package edu.andreasgut.view;

import edu.andreasgut.game.Game;
import edu.andreasgut.sound.AudioPlayer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewManager {

    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private BorderPane gamePane, startPane;
    private Scene gameScene, startScene;
    private Stage mainStage;
    private AudioPlayer audioPlayer;
    private StartImageView startImageViewRight, startImageViewLeft;
    private StartMenuView startMenuView;
    private OptionsView optionsView;
    private MainMenuBar mainMenuBar;
    private LogView logView;
    private FieldView fieldView;
    private ScoreView scoreView;
    private Game game;


    public ViewManager() {

        audioPlayer = new AudioPlayer();

        createStartScene();

        mainStage = new Stage();
        mainStage.setResizable(false);
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

    public void createGameScene(FieldView fieldView, ScoreView scoreView, LogView logView){

        this.fieldView = fieldView;
        this.scoreView = scoreView;
        scoreView.getStyleClass().add("scoreview");
        this.logView = logView;
        logView.getStyleClass().add("logview");


        gamePane = new BorderPane();
        gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        gameScene.getStylesheets().add("edu/andreasgut/style.css");

    }

    public void changeToGameScene(){
        gamePane.setTop(mainMenuBar);
        gamePane.setCenter(fieldView);
        gamePane.setLeft(scoreView);
        gamePane.setRight(logView);
        gamePane.setBottom(optionsView);
        mainStage.setScene(gameScene);
    }



    public Stage getMainStage(){
        return mainStage;
    }

    public BorderPane getGamePane(){
        return gamePane;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
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

    public LogView getLogView() { return logView; }

    public void setStartMenuView(StartMenuView startMenuView) {
        this.startMenuView = startMenuView;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setOptionsView(OptionsView optionsView) {
        this.optionsView = optionsView;
    }

    public void setLogView(LogView logView) {
        this.logView = logView;
    }

    public void setFieldView(FieldView fieldView) {
        this.fieldView = fieldView;
    }

    public void setScoreView(ScoreView scoreView) {
        this.scoreView = scoreView;
    }
}


