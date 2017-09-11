package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.xml.XMLHelper;

public class Main extends Application {

    private XMLHelper xmlHelper;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ADCA-Client");
        primaryStage.setScene(new Scene(root, 1300, 720));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setXmlHelper(XMLHelper xmlHelper) {
        this.xmlHelper = xmlHelper;
    }
}
