package main;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.util.ArrayList;

public class tagWindowController
{
    public static Stage tagStage = new Stage();

    public Button OKButton;
    public TextField insertIndexTextField;
    public ChoiceBox tagChooser;




    public static void buildTagWindow()
    {
        tagStage = new Stage();
        tagStage.setTitle("Insert Tag Action Window");
        tagStage.setScene(Main.tagScene);
        tagStage.showAndWait();

    }






    public void OKButtonOnAction(ActionEvent actionEvent)
    {
        /* Procedures
         *   load the user inputs into int and string, do nothing if they are not number.
         *   send the text to TTPTextBox in mainMenu
         *   close the window
         * */


        //detect whether the index that user provide is a number
        try{
            Integer.parseInt(insertIndexTextField.getText().replaceAll(" ", ""));
        }catch(NumberFormatException e)
        {
            return;
        }

//        upload the command to mainMenu
        System.out.println("YOU CHOSE + " + tagChooser.getSelectionModel().selectedItemProperty().getValue());

        mainMenuController.addTextIntoTTPTextBox("insert " + operation.TagIndicatorText
                + tagChooser.getSelectionModel().selectedItemProperty().getValue()
                + operation.SplitterText + " to "
                + insertIndexTextField.getText().replaceAll(" ", "") + ";\n");


        //clear the text boxes
        insertIndexTextField.setText("");
        //todo, clear the tag


        tagStage.close();
    }







}
