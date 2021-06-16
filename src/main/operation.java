package main;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;


public class operation
{
    //replace 0$`renamer`!#splitter#$googke
    //$`renamer`!#splitter#$
    public final static String SplitterRegex = "\\$`renamer`!#splitter#\\$";
    public final static String SplitterText = "$`renamer`!#splitter#$";
    public final static String TagIndicatorText = "$`renamer`!#useOfTag#$";

    public final static String[] supportedTagType = {"TRACK", "ALBUM", "ARTIST"};


    public static void processSettingText()
    {
        /* Brief Flowchart for this function
        *
        * make sure the textBox is contained. If the text Box is empty, then end the function
        *
        * retrieve text from GUI, TTP
        *
        * Initialize the newNameList by fileNames because of the order below
        *   -    when change in fileInstance (new file come in),
        *
        *       change in fileInstance -> change in fileNames
        *        -> change in newNameList -> Based on newNameList, change newNameList according to the command
        *
        * Break down the command by " ; "
        *
        * Run through all the commands, like "replace...", "delete ...", "insert..."
        * {
        *       get the first part of the command: like "replace", "delete"
        *       use switch to launch correct functions according to the command header
        * }
        *
        * */

        //new name list record is recorded inside a single refresh preview action
        //whenever the new refresh preview action starts, it should be initialize with fileNames
        Main.newNameList = new LinkedList<>();//initialize the new Name List
        //because we don't want to see..
        //for example, "replace 0 to 000" in "0" into "000000000..." while we press refresh several times
        Main.newNameList.addAll(Main.fileNames);

        if(mainMenuController.Pub_TTPTextBox.getText().equals(""))
        {
            //if the command list is empty, then the preview list should show fileNameList while the user clicks refresh
            return;//if nothing in the text box, end the process directly
        }

        String command = mainMenuController.Pub_TTPTextBox.getText();

        //split the commands into single command. For example, split replace(...); delete(...);
        String[] tasks = command.replaceAll("\n", "").split(";");

        //target means single command like replace (..), now we need to identify the type of the command
        for(String target : tasks)
        {
            //break down single command like replace (..) into [replace] [(...)]
            String[] singleCommandBreakDown = target.trim().split(" ");
            //room number 0 should be the type of the command, for example, replace.

            switch (singleCommandBreakDown[0].toLowerCase(Locale.ROOT))
            {
                case "replace":
                    replace(target);//finished
                    break;
                case "delete":
                    delete(target);//finished
                    break;
                case "insert":
                    insert(target);
                    break;

                default:
                    System.out.println("No Valid Command Detected");
            }
        }
        //update the ListView on GUI
    }



    public static void replace(String parameter)//finished 2020.1.29
    {
        /*
        * replace command only exists in a form "replace [x] $`renamer`!#splitter#$ [y]"
        *
        * hence, split the text by the splitter: "$`renamer`!#splitter#$"
        *
        * but if the size of the result array is bigger than 1,
        * which means [x] or [y] contains the identifier "$`renamer`!#splitter#$",
        * we should report error to the user
        *
        * -- change in fileInstance routine:
        * change in fileInstance -> change in fileNames -> change in newNameList -> Based on newNameList, change newNameList through processText(), ->
        *
        * -------------------------------------------
        * * Procedure
        *
        * - cut the phrase "replace "
        * - get the be-replace-string and the replace-with-string stored
        * - poll a file name from originalNameList, apply the rule and put it into newNameList
        *
        * */



        parameter = parameter.substring(8);//delete the phrase "replace "

        String[] XandY = parameter.split(SplitterRegex);//means [X] and [Y]

        //preload the data
        Queue<String> originalNameList = Main.newNameList;//load original name list
        Main.newNameList = new LinkedList<>();// clear the new name list to put new names

        for(int indx = 0; indx < Main.fileInstances.size(); indx ++)
        {
            //poll a data from original name List, -> replace operation -> store in new name list
            Main.newNameList.offer(originalNameList.poll().replace(XandY[0], XandY[1]));
        }

    }

    //finished
    public static void delete(String parameter)
    {
        /** Procedure
         * Notice, parameter comes in with a form "delete 1 to 2 from right;"
         *
         *  - delete the phrase "delete"
         *  - fetch the deletion index: where we start to delete and where to finish from the command
         *  - determine the direction of counting index
         *  - formatting indexes
         *  - load the names
         *  - rename them
         *
         * */

        //


        parameter = parameter.substring(7);//delete the phrase "delete "


        String[] parameterBreakDown = parameter.split(" ");//split the parameter

        int deleteFrom = Integer.parseInt(parameterBreakDown[0]);//store index
        int deleteTo = Integer.parseInt(parameterBreakDown[2]);//store index

        boolean countIndexFromRight = parameter.toLowerCase().contains("right") || parameterBreakDown[4].equals("right");

        if(!parameterBreakDown[4].equals("left") && !countIndexFromRight)//bug finding
        {
            System.out.println("ERROR -------- Invalid deletion direction: " + parameterBreakDown[4]);
            return;
        }


        //transform the form of indexing of deleting characters into a form accepted by java
        deleteFrom --;//because in java, everything start from 0
        deleteTo --;//but while user using the software, the first character is not at 0

        //deleteFrom < deleteTo should always true, if not, exchange them
        if(deleteFrom > deleteTo)
        {
            int cache = deleteFrom;
            deleteFrom = deleteTo;
            deleteTo = cache;
        }


        //in this stage, the names are not renamed yet
        //but the indexes are prepared

        //preload the data
        Queue<String> originalNameList = new LinkedList<>();
        originalNameList.addAll(Main.newNameList);

        Main.newNameList = new LinkedList<>();

        for(int indx_fileInstances = 0; indx_fileInstances < Main.fileInstances.size(); indx_fileInstances ++)
        {
            //poll a data from original name List, -> calculate the index if index count from right -> perform operation -> store in new name list


            String targetString = originalNameList.poll();//poll a data

            String result = "";
            int thisDeleteTo = deleteTo;
            int thisDeleteFrom = deleteFrom;

            if(countIndexFromRight)
            {
                //if index count from right, as the size of the string depends on the single string processing, we have to calculate it whenever we process astring
                //the deleteFrom, after changing the direction, will be larger than deleteTo.
                //So exchange them to let deleteFrom always smaller then deleteTo
                //System.out.println("this delete from " + thisDeleteFrom + " to " + thisDeleteTo);
                thisDeleteTo = (targetString.length() - deleteFrom) - 1;
                thisDeleteFrom = (targetString.length() - deleteTo) -1;
                //System.out.println("-----------------------[after transformation] deleteFrom " + thisDeleteFrom + " to " + thisDeleteTo);
            }


            //operate a string
            for (int indx_characterPositionPointer = 0; indx_characterPositionPointer < targetString.length();
                 indx_characterPositionPointer ++)//this procedure for a single string
            {
                // indx_characterPositionPointer will go through all characters in a fileName
                // if index is not in the delete list
                if(indx_characterPositionPointer < thisDeleteFrom || indx_characterPositionPointer > thisDeleteTo)
                    result += targetString.charAt(indx_characterPositionPointer);
            }

            //System.out.println("Result [" + indx_fileInstances + "] is " + result);
            Main.newNameList.offer(result);//store in new name list
        }


    }


    public static void insert(String parameter)
    {
        /** Procedure
         * command should be like this "insert [tagIndicator]Text[splitter] to 2",
         * For tags, the command should be something like "insert FieldKey.TRACK to 2"
         * we always insert texts behind the target character
         *
         * - delete the phrase "insert" from the "parameter"
         * - separate theText and insertPosition
         * - determine whether the command use tag
         * - - if so - set tag type according to the command
         * - - enter each file's tag information into theText and insert in to the file name
         *
         * - if not use tag, insert theText to insertPosition
         * */

        parameter = parameter.substring(7);

        //parameter should have been seperated into "Text" and " to 2"
        String[] parameterCache = parameter.split(SplitterRegex);

        //todo 如果要寫一個 right or left indexing, 要加入一個轉化器, 暫時先不寫 有時間再來實現


        String theText = parameterCache[0];//get the text

        boolean useTag = false;
        FieldKey tagType = FieldKey.TRACK;//a default value is tag
        System.out.println("theText == " + theText);

        //if the command contains the use of tags expression, we should indicates that
        if(theText.contains(TagIndicatorText))
        {
            System.out.println("USED TAG");
            useTag = true;
            //delete the tag Indicator [\\$`renamer`!#useOfTag#\\$]
            theText = theText.substring(TagIndicatorText.length());

            if(theText.equals(supportedTagType[0])) tagType = FieldKey.TRACK;//TRACK Info
            else if (theText.equals(supportedTagType[1])) tagType = FieldKey.ALBUM;
            else if (theText.equals(supportedTagType[2])) tagType = FieldKey.ARTIST;
            else System.out.println("\n\n!!!!!!!!!!!!!!!! You've declared the use of Tag, " +
                        "but the type \"" + theText + "\" is unsupported\n\n");
        }else System.out.println("NO USE OF TAG");

        //get the position index
        int insertPosition = Integer.parseInt(parameterCache[1].substring(4).replaceAll(" ", ""));

        //System.out.println("the Text == [" + theText + "], insertPosition == [" + insertPosition + "]" );


        //preload the data
        Queue<String> originalNameList = Main.newNameList;//load original name list
        Main.newNameList = new LinkedList<>();// clear the new name list to put new names

        Queue<File> fileInstancesCOPY = new LinkedList();
        fileInstancesCOPY.addAll(Main.fileInstances);

        //insert position may vary because I implemented a feature to trim the insertPosition if the position index is
        // larger than the length of the file Name,
        // so I decided to let the insert position vary according to the maximum length of the file name
        // ,therefore we need to save the original number of the insert position
        // so we can change the insert position as we want along different file names.
        int originalInsertPosition = insertPosition;

        for(int indx = 0; indx < Main.fileInstances.size(); indx ++)
        {
            String originalName = originalNameList.poll();

            insertPosition = originalInsertPosition;//initialize the insert Position

            if(insertPosition > originalName.length())//if the position index is longer than the file name,
                insertPosition = originalName.length(); //we shall trim the insert position

            if(useTag)
            {
                try {
                    theText = (AudioFileIO.read(fileInstancesCOPY.poll())).getTag().getFirst(tagType);
                    if(theText.length() == 1) theText = "0" + theText;//and 0 before 1-9, so it became 01-09
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (CannotReadException e) {
                    e.printStackTrace();
                } catch (ReadOnlyFileException e) {
                    e.printStackTrace();
                } catch (TagException e) {
                    e.printStackTrace();
                } catch (InvalidAudioFrameException e) {
                    e.printStackTrace();
                }
            }
            Main.newNameList.offer(originalName.substring(0, insertPosition) + theText
                    + originalName.substring(insertPosition, originalName.length()));
        }

    }




    public static void renameTheFile()
    {
        /** Procedure
         *
         * for, traverse FileInstances
         * {
         *  Rename Operation
         *
         * }
         *
         *
         * */

        for(File target : Main.fileInstances)
        {
            target.renameTo(new File(target.getParent() + "\\" + Main.newNameList.poll()));
        }
        System.out.println("Rename Finished");

    }







}
