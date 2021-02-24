package main;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

public class replaceWindowController
{

    public static Stage replaceStage;
    public TextField findTextArea;
    public TextField replaceWithTextArea;

    public static void buildReplaceWindow()
    {
        replaceStage = new Stage();
        replaceStage.setTitle("Replace Action Window");
        replaceStage.setScene(Main.replaceScene);
        replaceStage.showAndWait();
    }



    //On Action

    public void okOnAction(ActionEvent actionEvent)//when click OK!!
    {
        /* Procedures
        *   close the window
        *   clear the text in the boxes
        *   send the text to main Menu, the command box
        * */
        //if the input is valid, process the input and put it into the [Task That Perform (TTP)] in main Menu Window
        boolean validInputs = !(findTextArea.getText() == null && findTextArea.getText().equals(""))
                || !(replaceWithTextArea.getText() == null && replaceWithTextArea.getText().equals(""));
        if(validInputs)
            mainMenuController.addTextIntoTTPTextBox("replace " + findTextArea.getText() + operation.SplitterText + replaceWithTextArea.getText() + ";\n");
        else;
        //clear the user inputs
        findTextArea.setText("");
        replaceWithTextArea.setText("");

        replaceStage.close();
    }


}

/*
* fxIDs:
* okButton
* findTextArea
* replaceWithTextArea

* on Action
* okOnAction
*
* */