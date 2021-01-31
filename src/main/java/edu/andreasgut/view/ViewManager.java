package edu.andreasgut.view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewManager {

    private static final int HEIGHT = 800;
    private static final int WIDTH = 1200;
    private BorderPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public ViewManager() {
        mainPane = new BorderPane();
        mainPane.setTop(new MainMenuBar());
        mainPane.setCenter(new FieldView());
        mainPane.setLeft(new ScoreView());

        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainScene.getStylesheets().add("edu/andreasgut/style.css");
    }

    public Stage getMainStage(){
        return mainStage;
    }

    public BorderPane getMainPane(){
        return mainPane;
    }


}


