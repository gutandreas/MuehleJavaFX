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
        //Parent root = null;
        ViewManager viewManager = null;
        try {
            viewManager = new ViewManager();
            primaryStage = viewManager.getMainStage();
            primaryStage.show();
            //root = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }




        /*Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("edu/andreasgut/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();*/

    }
}
