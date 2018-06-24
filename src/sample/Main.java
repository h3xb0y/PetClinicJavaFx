package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.db.SqlControl;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("resources/MainScreen.fxml"));
        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane,700,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ветеринарная клиника");
        primaryStage.show();
        System.out.println(SqlControl.ANSI_BLUE+"Приложение запущено");
        SqlControl.prepare();
    }

}
