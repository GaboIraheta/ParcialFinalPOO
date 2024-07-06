package org.parcialfinal_poo.gui.viewers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.parcialfinal_poo.models.DataBase.Inserts.Insert;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Insert insert = Insert.getInstance();

        insert.registrarCliente("Javier Agustin", "Iraheta Guardado", "San Jacinto",
                "6514-9127");
    }

    public static void main(String[] args) {
        launch();
    }
}