package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class deleteWindowController
{

    public static Stage deleteStage;
    public TextField deleteFromTextField;
    public TextField deleteToTextField;
    public Button OKButton;
    public CheckBox countFromRightCheckBox;

    public static void buildDeleteWindow()
    {
        deleteStage = new Stage();
        deleteStage.setTitle("Replace Action Window");
        deleteStage.setScene(Main.deleteScene);
        deleteStage.showAndWait();
    }

    public void okOnAction(ActionEvent actionEvent)//when click OK!!
    {
        /* Procedures
         *   load the user inputs into int, do nothing if they are not number.
         *   load the direction from the checkBox
         *   send the text to TTPTextBox in mainMenu
         *   close the window
         * */

        int deleteFrom;
        int deleteTo;

        try//if user input a string, block the user by doing nothing
        {
            deleteFrom = Integer.parseInt(deleteFromTextField.getText().replaceAll(" ", ""));
            deleteTo = Integer.parseInt(deleteToTextField.getText().replaceAll(" ", ""));
        }
        catch (NumberFormatException e) {return;}


        String direction = "";
        if(countFromRightCheckBox.isSelected()) direction = "right";//read the direction information from the checkBox
        else direction = "left";

        //upload the command to mainMenu
        mainMenuController.addTextIntoTTPTextBox("delete " + deleteFrom + " to " + deleteTo + " from " + direction + ";\n");

        //clear the text boxes
        deleteFromTextField.setText("");
        deleteToTextField.setText("");

        deleteStage.close();
    }


}
