package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class mainMenuController //* The Controller for menuScene, the first menu and the main menu.
{

    public ListView originalNameListView;
    public ListView previewNameListView;
    public TextArea TTPTextBox;
    public static TextArea Pub_TTPTextBox;
    public static Button RTFButton;





    public static void addTextIntoTTPTextBox(String newText)
    {

        Pub_TTPTextBox.setText(Pub_TTPTextBox.getText() + newText);

    }



    public void refreshlistView()//finished 2021.1.29
    {
        /**Procedure
         * clear the old list of names
         * get new name list from fileInstances
         * refresh it on ListView
         *
         * refresh the new Preview
         *
         * */

        //add the names into fileNames queue
        Main.fileNames = new LinkedList<>();//first clear the original list of names
        for(File target : Main.fileInstances)// then import names from the fileInstances
        {
            Main.fileNames.offer(target.getName());
        }

        //before we add items into the original Name ListView, we have to clear it up
        while (originalNameListView.getItems().size() > 0)
            originalNameListView.getItems().remove(0);//so always delete the first element, until the list size == 0


        originalNameListView.getItems().addAll(Main.fileNames);

        //refresh the new preview
        refreshPreviewList();
    }

    public void refreshPreviewList()//finished 2021.1.29
    {
        /* Function:
        *   - Update the newNameList through Process Text Function()
        *   - Load the newNameList into ListView: previewNameList
        *       - which include the deletion of original elements
        *       - and the load of new elements
        * */
        //update the newNameList
        Pub_TTPTextBox = TTPTextBox;
        operation.processSettingText();

        //update the ListView

        //delete all elements on the original listView
        //because everytime we delete an element in the list, the size and index of elements changed
        while (previewNameListView.getItems().size() > 0)
            previewNameListView.getItems().remove(0);//so always delete the first element, until the list size == 0

        //add new name list
        previewNameListView.getItems().addAll(Main.newNameList);
    }




    //buttons on Action Area

    public void refreshPreviewOnAction(ActionEvent actionEvent)
    {
        refreshPreviewList();
    }

    public void clearListButtonOnAction(ActionEvent actionEvent)
    {
        Main.fileInstances = new LinkedList<>();
        refreshlistView();
    }


    public void replaceOnAction(ActionEvent actionEvent)
    {
        Pub_TTPTextBox = TTPTextBox;
        replaceWindowController.buildReplaceWindow();
    }

    public void insertOnAction(ActionEvent actionEvent)
    {
        Pub_TTPTextBox = TTPTextBox;
        insertWindowController.buildInsertWindow();
    }

    public void deleteOnAction(ActionEvent actionEvent)
    {
        Pub_TTPTextBox = TTPTextBox;
        deleteWindowController.buildDeleteWindow();
    }

    //public void continuousOnAction(ActionEvent actionEvent) {}

    public void readMusicButtonOnAction(ActionEvent actionEvent)
    {
        Pub_TTPTextBox = TTPTextBox;
        tagWindowController.buildTagWindow();
    }

    //public void helpButtonOnAction(ActionEvent actionEvent) {}

    public void openFolderOnAction(ActionEvent actionEvent)
    {
        //choose a file logic
        DirectoryChooser dictChooser = new DirectoryChooser();
        dictChooser.setTitle("Open a Folder");
        //open the file chooser window, this instance will return the result
        File selectedFolder = dictChooser.showDialog(Main.openPrimaryStage);
        System.out.println("the folder is " + selectedFolder);

        if(selectedFolder == null) return; //if the user didn't select a folder, then do nothing and exit.


            //import new files into fileInstances

        //left side returns a file[], so I cannot simply plug it into fileInstances.addAll(Collections<>)
        //so instead, we do a for loop to enter files into fileInstance

        File[] fileList = selectedFolder.listFiles();
        for(File target : fileList)
        {
            Main.fileInstances.offer(target);
        }
        //successfully imported the files into fileInstance
        refreshlistView();


    }

    public void openFileOnAction(ActionEvent actionEvent)
    {
        //choose a file logic
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a directory or a file");
        //open the file chooser window, this instance will return the results, and the user may select multiple files
        List<File> selectedFile = fileChooser.showOpenMultipleDialog(Main.openPrimaryStage);
        if(selectedFile == null) return; //if the user didn't select a folder, then do nothing and exit.
        //print the file selected for debug purpose
        System.out.print("the file is ");
        for(File target : selectedFile)
        {
            System.out.print("\"" + target + "\", ");
        }

        refreshlistView();
    }

    //Rename the file (RTF)
    public void RTFButtonOnAction(ActionEvent actionevent)
    {
        operation.renameTheFile();
    }


    //Export Naming Setting (ENS) Button On Action
    public void ENSButtonOnAction(ActionEvent actionEvent) throws IOException
    {
        /** Procedure
         *
         *  - Open Directory delector window
         *  - create a new .rp file and print the TTP text in it
         *
         * */

        //save a file logic
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("rename preference");
        //set the type of file user can save
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Rename Preference File (*.rp)", "*.rp");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Save your file");


        //open the file chooser window, this instance will return the results
        File selectedFile = fileChooser.showSaveDialog(Main.openPrimaryStage);

        //user may click cancel, so selectedFile could be null
        if(selectedFile != null)
        {
            //print the TTP into file
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(selectedFile)));
            pw.print(TTPTextBox.getText());
            pw.close();//save the file
        }

    }

    //Import Naming Setting (INS) Button On Action
    public void INSButtonOnAction(ActionEvent actionEvent) throws IOException
    {
        /**Procesure
         *
         * - open directory selector and select a *.rp file
         * - read the file and put the text into TTP TextBox
         *
         * */

        //choose a file logic
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Rename Preference File (*.rp)", "*.rp");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Choose a rename preference (*.rp) file");


        //open the file chooser window, this instance will return the results
        File selectedFile = fileChooser.showOpenDialog(Main.openPrimaryStage);
        if(selectedFile == null) return; //if the user didn't select a folder, then do nothing and exit.

        BufferedReader br = new BufferedReader(new FileReader(selectedFile));

        String commandFromFile = "";
        String readLine = br.readLine();


        while(readLine != null)
        {
            commandFromFile = commandFromFile + readLine + "\n";
            readLine = br.readLine();
        }

        if(commandFromFile.length() != 0)
            TTPTextBox.setText(commandFromFile);




    }






}
