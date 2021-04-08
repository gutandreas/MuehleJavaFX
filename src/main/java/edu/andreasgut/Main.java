package edu.andreasgut;

import edu.andreasgut.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ViewManager viewManager = null;
        try {
            viewManager = new ViewManager();
            primaryStage = viewManager.getMainStage();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
