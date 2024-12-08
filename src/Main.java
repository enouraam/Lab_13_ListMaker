import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
    public static void main(String[] args) {
        boolean  needsToBeSaved = false;
        ArrayList myArrList = new ArrayList();
        Scanner in = new Scanner(System.in);
        String instructions =
                "A – Add an item to the list\n" +
                        "D – Delete an item from the list\n" +
                        "I – Insert an item into the list\n" +
                        "V – View (i.e. display) the list\n" +
                        "H – Help (will display these options again)\n" +

                        "M – Move an item (THIS IS A NEW EDITING OPTION)\n" +
                        "O – Open a list file from disk\n" +
                        "S – Save the current list file to disk \n" +
                        "C – Clear removes all the elements\n" +


                        "Q – Quit the program (This should do an are you sure? type query before exiting.)";

        help(instructions);
        while (true) {
            String input = SafeInput.getRegExString(in, " ", "[AaDdIiVvHhCcQqOoSsMm]");

            if (input.equalsIgnoreCase("a")) {
                addItem(in, myArrList);
                needsToBeSaved = true;
            } else if (input.equalsIgnoreCase("d")) {
                deleteItem(in, myArrList);
                needsToBeSaved = true;
            } else if (input.equalsIgnoreCase("i")) {
                insertItem(in, myArrList);
                needsToBeSaved = true;
            } else if (input.equalsIgnoreCase("v")) {
                printList(myArrList);
            } else if (input.equalsIgnoreCase("h")) {
                help(instructions);
            } else if (input.equalsIgnoreCase("c")) {
                clearList(myArrList);
                needsToBeSaved = true;
            } else if (input.equalsIgnoreCase("s")) {
                saveFile(myArrList);
                needsToBeSaved = false;
            } else if (input.equalsIgnoreCase("m")) {
                MoveItem(in,myArrList);
                needsToBeSaved = true;
            } else if (input.equalsIgnoreCase("o")) {


                if (!needsToBeSaved)
                {
                    myArrList.clear();
                    openFile(myArrList);

                }else{

                    if (SafeInput.getYNConfirm(in,"would you like to save your list to disk? (y/n): ")){
                        saveFile(myArrList);

                    }
                    myArrList.clear();
                    openFile(myArrList);
                    needsToBeSaved = false;
                }



            } else if (input.equalsIgnoreCase("q")) {

                if (needsToBeSaved){
                    System.out.println("You have unsaved changes. Please save if you need to.");
                    if (SafeInput.getYNConfirm(in,"would you like to save your list to disk? (y/n): ")){
                        saveFile(myArrList);

                    }
                }

                if (quit(in)) {
                    break;
                }
            }
        }
    }

    private static void addItem(Scanner in, ArrayList list) {
        System.out.println("Please type what you would like to add: ");
        if (in.hasNext()) {
            String item = in.next();
            list.add(item);
            System.out.printf("\nSuccessfully added '%s'\n", item);
        }
    }
    private static void addItem(String item,ArrayList list) {

        list.add(item);

    }


    private static void deleteItem(Scanner in, ArrayList list) {
        if (list.size() > 0) {
            int listLength = list.size() - 1;
            String message = "Please enter the item index between 0 and " + listLength;
            int indexInput = SafeInput.getRangedInt(in, message, 0, listLength);
            String deletedItem = list.get(indexInput).toString();
            list.remove(indexInput);
            System.out.printf("\nSuccessfully deleted '%s'\n", deletedItem);
        } else {
            System.out.println("You do not have items on your list that you can delete");
        }
    }

    private static void insertItem(Scanner in, ArrayList list) {
        System.out.println("Please type what you would like to insert: ");
        if (in.hasNext()) {
            String item = in.next();
            int listLength = list.size();
            String message = "Please enter the index where you would like to insert your item, between 0 and " + listLength;
            int indexInput = SafeInput.getRangedInt(in, message, 0, listLength);
            list.add(indexInput, item);
            System.out.printf("\nSuccessfully inserted '%s'\n", list.get(indexInput));
        }
    }

    private static void MoveItem(Scanner in, ArrayList list) {

        if (list.size() > 0) {
            int listLength = list.size() - 1;

            String message = "Please enter the item index between 0 and " + listLength;
            int oldIndex = SafeInput.getRangedInt(in, message, 0, listLength);
            Object item = list.get(oldIndex);
            list.remove(oldIndex);


            printList(list);
            System.out.println("item "+item);
            message = "Please enter the new index between 0 and " + listLength;
            int newIndex = SafeInput.getRangedInt(in, message, 0, listLength);


            list.add(newIndex,item);


        } else {
            System.out.println("You do not have items on your list that you can move");
        }


    }

    private static void printList(ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d: %s\n", i, list.get(i));
        }
    }

    private static boolean quit(Scanner in) {
        return SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
    }

    private static void clearList(ArrayList list) {
        list.clear();
    }

    private static void help(String message) {
        System.out.print(message);
    }

    private static void openFile(ArrayList list) {

        JFrame frame = new JFrame();
        frame.setVisible(true);
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(frame);


        if (result == JFileChooser.APPROVE_OPTION) { // Check if a file was selected
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            currentFilename = selectedFile.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    addItem(line,list); // Add each line to the list
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }

        } else {
            System.out.println("File selection canceled.");
        }
    }
    private static void saveFile(ArrayList<String> list) {
        if (currentFilename != null) {
            // Save to the existing filename
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilename))) {
                for (String line : list) {
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("File saved successfully to: " + currentFilename);
            } catch (IOException e) {
                System.err.println("Error saving the file: " + e.getMessage());
            }

        } else {

            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showSaveDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                currentFilename = fileToSave.getAbsolutePath(); // Store the filename
                saveFile(list); // Recursively call saveFile to handle the new file

            } else {
                System.out.println("File save operation canceled.");
            }

        }
    }
    private static String currentFilename = null;



}