package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class Main extends Application {

    //universal variables
    public static Queue<File> fileInstances = new LinkedList<>();
    public static Queue<String> fileNames = new LinkedList<>();
    public static Queue<String> newNameList = new LinkedList<>();


    //scenes
    public static Scene mainMenuScene;
    public static Scene replaceScene;
    public static Scene deleteScene;
    public static Scene insertScene;
    public static Scene tagScene;


    public static Stage openPrimaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        //define scenes
        Parent root = FXMLLoader.load(getClass().getResource("menuScene.fxml"));
        mainMenuScene = new Scene(root, 837, 549);

        Parent replaceRoot = FXMLLoader.load(getClass().getResource("replaceWindowScene.fxml"));
        replaceScene = new Scene(replaceRoot, 600, 400);

        Parent deleteRoot = FXMLLoader.load(getClass().getResource("deleteWindowScene.fxml"));
        deleteScene = new Scene(deleteRoot, 600, 400);

        Parent insertRoot = FXMLLoader.load(getClass().getResource("insertWindowScene.fxml"));
        insertScene = new Scene(insertRoot, 600, 400);

        Parent tagRoot = FXMLLoader.load(getClass().getResource("tagWindowScene.fxml"));
        tagScene = new Scene(tagRoot, 600, 400);

        //build the main stage
        primaryStage.setTitle("File Renamer (Music)");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        openPrimaryStage =  primaryStage;

        //call the initializers

    }


    public static void main(String[] args) {
        launch(args);
    }




}
