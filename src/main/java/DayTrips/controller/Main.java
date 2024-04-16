package DayTrips.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    public static Stage stage;
    public static double x, y;
    public static double xxx, yyy;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DayTripsD5/Sample.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Day trip booker");
        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.setX(140);
        primaryStage.setY(130);
        x = primaryStage.getX();
        y = primaryStage.getY();
        root.setOnMousePressed(event -> {
            xxx = event.getSceneX();
            yyy = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xxx);
            primaryStage.setY(event.getScreenY() - yyy);
            x = primaryStage.getX();
            y = primaryStage.getY();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}


