package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class insertWindowController
{
    public TextField insertIndexTextField;
    public TextField insertTextField;
    public Button OKButton;

    public static Stage insertStage = new Stage();

    public static void buildInsertWindow()
    {
        insertStage = new Stage();
        insertStage.setTitle("insert Action Window");
        insertStage.setScene(Main.insertScene);
        insertStage.showAndWait();
    }

    //insert Text[splitter] to 2
    public void OKButtonOnAction(ActionEvent actionEvent)
    {
        /* Procedures
         *   load the user inputs into int and string, do nothing if they are not number.
         *   send the text to TTPTextBox in mainMenu
         *   close the window
         * */



        //direction
//        String direction = "";
//        if(countFromRightCheckBox.isSelected()) direction = "right";//read the direction information from the checkBox
//        else direction = "left";


        //detect whether the index that user gave us is a number
        try{
            Integer.parseInt(insertIndexTextField.getText().replaceAll(" ", ""));
        }catch(NumberFormatException e)
        {
            return;
        }

//        upload the command to mainMenu
        mainMenuController.addTextIntoTTPTextBox("insert " + insertTextField.getText()
                + operation.SplitterText + " to " + insertIndexTextField.getText().replaceAll(" ", "") + ";\n");


        //clear the text boxes
        insertIndexTextField.setText("");
        insertTextField.setText("");


        insertStage.close();
    }





}
