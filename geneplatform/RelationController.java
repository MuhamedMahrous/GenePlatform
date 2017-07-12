/*
Author:				Muhamed Mahrous
Date:				15/10/2016
Version:			1.0
Project ID:			I.E. Framework
CS Class:			N/A
Programming Language:		JAVA
OS/Hardware dependencies:	None

Problem Description:
The controller of the Compound Relation knowledge space.

Overall Design:           The initialize method initializes drag operations and the data members of the class.
                          and the validate method handles the validation of this knowledge space.
System structure:          This is a controller in the MVC architecture.
Data representation:
 ObservableLists :      1.first_list : holds the first pattern.
                        2.second_list: holds the second pattern.
                        3.generics: holds all allowed NLP Part of speech tags.

Algorithms:	        None.


Program Assumptions and Restrictions :
None; validation handles this.

*/

package geneplatform;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import NLL.*;
import NLL.Relation_Node;

public class RelationController
        implements Initializable
{
    //
//    /**
//     * Initializes the controller class.
//     */
    public boolean validated=false;
    @FXML
    ListView ner_first_arg;

    @FXML
    ListView ner_second_arg;

    private  int my_id=-1;
    public ObservableList first_arg=FXCollections.observableArrayList();
    public Compound_list first_list = new Compound_list();

    public ObservableList second_arg=FXCollections.observableArrayList();
    public Compound_list second_list =new Compound_list();

    public List<String> generics = Arrays.asList(
            "CD",
            "DT",
            "EX",
            "FW",
            "IN",
            "JJ",
            "JJR",
            "JJS",
            "LS",
            "MD",
            "NN",
            "NNS",
            "NNP",
            "NNPS",
            "PDT",
            "POS",
            "PRP",
            "PRP$",
            "RB",
            "RBR",
            "RBS",
            "RP",
            "SYM",
            "TO",
            "UH",
            "VB",
            "VBD",
            "VBG",
            "VBN",
            "VBP",
            "VBZ",
            "WDT",
            "WP",
            "WP$",
            "WRB");
    @Override
    public void initialize(URL url, ResourceBundle rb)  {

        first_list = new Compound_list();
        second_list = new Compound_list();
     //   first_arg.add("THIS IS FIRST");
        ner_first_arg.setItems(first_arg);
       // second_arg.add("THIS IS SECOND");
        ner_second_arg.setItems(second_arg);

        FIRST_initilize_drag();
        Second_initilize_drag();


    }


    public void FIRST_initilize_drag()
    {
        ner_first_arg.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ner_first_arg.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //      System.out.println("Event on NER: drag over");
                FIRST_dragOver(event);
            }
        });
        ner_first_arg.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //System.out.println("Event on NER: drag dropped");
                FIRST_dragDropped(event);
            }
        });

        ner_first_arg.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //          System.out.println("Event on NER: drag done");
                FIRST_dragDone(event);
            }
        });

        ner_first_arg.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case DELETE: {
                        int selected_index = ner_first_arg.getSelectionModel().getSelectedIndex();
                        if(selected_index!=-1)
                        {
                            first_arg.remove(selected_index);
                            first_list.remove_element(selected_index);
                        }
                        else
                        {
                            //DO NOTHING
                        }
                    }

                }
            }
        });

    }

    private void FIRST_dragOver(DragEvent event)
    {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();
        // System.out.println("TYPE1: "+event.getGestureSource().getClass()+"and Listview class = "+ListView.class);
        //System.out.println("TYPE2: "+ws.getClass()+"and VBox class = "+VBox.class);
        if (event.getGestureSource().getClass()==ListView.class

                && dragboard.hasString())
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            //        System.out.println("NER ENTERED");
        }
        else {
            event.acceptTransferModes(TransferMode.NONE);
        }

        event.consume();

    }

    @SuppressWarnings("unchecked")
    private void FIRST_dragDropped(DragEvent event)
    {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasString())
        {

            // Relation ADDING TO THE LIST
            if (dragboard.getString().equals("ONE TO ONE"))
            {

                first_arg.add(dragboard.getString());
                first_list.add_Relation("0");
                dragCompleted = true;

            }
            else if (dragboard.getString().equals("ALL TO ONE"))
            {
                first_arg.add(dragboard.getString());
                first_list.add_Relation("2");
                dragCompleted = true;

            }
            else if (dragboard.getString().equals("ONE TO ALL"))
            {
                first_arg.add(dragboard.getString());
                first_list.add_Relation("1");
                dragCompleted = true;

            }
            else if (dragboard.getString().equals("ALL TO ALL"))
            {
                first_arg.add(dragboard.getString());
                first_list.add_Relation("3");
                dragCompleted = true;

            }


            else if (dragboard.getString().equals("FILE"))
            {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Directory");
                File file =fileChooser.showOpenDialog(ner_first_arg.getScene().getWindow());
                if(file!=null)
                {
                    first_arg.add(file.getName());
                    first_list.add_file(file.getPath());
                    dragCompleted=true;

                    // System.out.println(ner);
                }


            }
            else if (dragboard.getString().equals("STRING")) {
                TextInputDialog dialog = new TextInputDialog("");

                dialog.setTitle("Text Input Dialog");
                dialog.setHeaderText("String input");
                dialog.setContentText("Please enter a string:");

                Optional<String> result = dialog.showAndWait();
                // The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(string -> {
                    first_arg.add(string);
                    first_list.add_String(string);
                });
                dragCompleted = true;
            }


            else if (dragboard.getString().equals("GENERIC"))
            {


                ChoiceDialog<String> dialog = new ChoiceDialog<>("NN", generics);
                dialog.setTitle("Choice Dialog");
                dialog.setHeaderText("Generic NER");
                dialog.setContentText("Choose generic NER:");
                Optional<String> result = dialog.showAndWait();

                // The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(generic ->
                {
                    first_arg.add(generic);
                    first_list.add_Generic(generic);

                });
                dragCompleted = true;

            }
            else
            {
                error_alert(event);
                dragCompleted=false;
            }



        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
     /*   for (int i = 0; i < first_list.get_size(); i++) {
            System.out.println("Element #"+i+" INFO="+first_list.get_index_info(i)+" TYPE="+first_list.get_index_type(i));
        }*/
    }

    private void FIRST_dragDone(DragEvent event)
    {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE)
        {
            // DO SOMETHING AFTER SUCCESS DROPPING TOOK PLACE
//            System.out.println("NER COMPLETED SUCCESSFULLY");

        }

        event.consume();
    }
    public void error_alert(Event event)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR!");
        alert.setHeaderText("DRAG ERROR!");
        alert.setContentText("THIS ACTION IS NOT PERMITTED");
        alert.showAndWait();
        event.consume();
        System.out.println(event.isConsumed());
    }


    // SECOND ARGUMENT INITIALIZATION AND HANDLING

    public void Second_initilize_drag()
    {
        ner_second_arg.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ner_second_arg.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //      System.out.println("Event on NER: drag over");
                Second_dragOver(event);
            }
        });
        ner_second_arg.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //        System.out.println("Event on NER: drag dropped");
                Second_dragDropped(event);
            }
        });

        ner_second_arg.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //          System.out.println("Event on NER: drag done");
                Second_dragDone(event);
            }
        });

        ner_second_arg.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case DELETE: {
                        int selected_index = ner_second_arg.getSelectionModel().getSelectedIndex();
                        if(selected_index!=-1)
                        {
                            second_arg.remove(selected_index);
                            second_list.remove_element(selected_index);
                        }
                        else
                        {
                            //DO NOTHING
                        }
                    }

                }
            }
        });

    }

    private void Second_dragOver(DragEvent event)
    {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();
        // System.out.println("TYPE1: "+event.getGestureSource().getClass()+"and Listview class = "+ListView.class);
        //System.out.println("TYPE2: "+ws.getClass()+"and VBox class = "+VBox.class);
        if (event.getGestureSource().getClass()==ListView.class

                && dragboard.hasString())
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            //        System.out.println("NER ENTERED");
        }
        else {
            event.acceptTransferModes(TransferMode.NONE);
        }

        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void Second_dragDropped(DragEvent event)
    {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasString())
        {

            // Relation ADDING TO THE LIST
            if (dragboard.getString().equals("ONE TO ONE"))
            {

                second_arg.add(dragboard.getString());
                second_list.add_Relation("0");
                dragCompleted = true;

            }
            else if (dragboard.getString().equals("ALL TO ONE"))
            {
                second_arg.add(dragboard.getString());
                second_list.add_Relation("2");
                dragCompleted = true;

            }
            else if (dragboard.getString().equals("ONE TO ALL"))
            {
                second_arg.add(dragboard.getString());
                second_list.add_Relation("1");
                dragCompleted = true;

            }
            else if (dragboard.getString().equals("ALL TO ALL"))
            {
                second_arg.add(dragboard.getString());
                second_list.add_Relation("3");
                dragCompleted = true;

            }


            else if (dragboard.getString().equals("FILE"))
            {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Directory");
                File file =fileChooser.showOpenDialog(ner_second_arg.getScene().getWindow());
                if(file!=null)
                {
                    second_arg.add(file.getName());
                    second_list.add_file(file.getPath());
                    dragCompleted=true;

                    // System.out.println(ner);
                }


            }
            else if (dragboard.getString().equals("STRING")) {
                TextInputDialog dialog = new TextInputDialog("");

                dialog.setTitle("Text Input Dialog");
                dialog.setHeaderText("String input");
                dialog.setContentText("Please enter a string:");

                Optional<String> result = dialog.showAndWait();
                // The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(string -> {
                    second_arg.add(string);
                    second_list.add_String(string);
                });
                dragCompleted = true;
            }


            else if (dragboard.getString().equals("GENERIC"))
            {


                ChoiceDialog<String> dialog = new ChoiceDialog<>("NN", generics);
                dialog.setTitle("Choice Dialog");
                dialog.setHeaderText("Generic NER");
                dialog.setContentText("Choose generic NER:");
                Optional<String> result = dialog.showAndWait();

                // The Java 8 way to get the response value (with lambda expression).
                result.ifPresent(generic ->
                {
                    second_arg.add(generic);
                    second_list.add_Generic(generic);

                });
                dragCompleted = true;

            }
            else
            {
                error_alert(event);
                dragCompleted=false;
            }



        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
     /*   for (int i = 0; i < first_list.get_size(); i++) {
            System.out.println("Element #"+i+" INFO="+first_list.get_index_info(i)+" TYPE="+first_list.get_index_type(i));
        }*/
    }

    private void Second_dragDone(DragEvent event)
    {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE)
        {
            // DO SOMETHING AFTER SUCCESS DROPPING TOOK PLACE
//            System.out.println("NER COMPLETED SUCCESSFULLY");

        }

        event.consume();
    }

    public void validate() {
        int x = Validate_compoundlist(first_list);
        int y = Validate_compoundlist(second_list);
        System.out.println(x);
        System.out.println(y);
        if (x == 0 && y == 0) {
            validated = true;
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("SUCCESSFULLY VALIDATED");
            success.showAndWait();
            BlockModel my_model = new Relation_Model(first_list,second_list);
            my_id=Model.getInstance().addBlock(my_model,my_id);
            System.out.println("I AM AT #"+my_id);

        } else if (x == 0 && y != 0) {
            validated = false;
            Alert success = new Alert(Alert.AlertType.ERROR);
            success.setContentText("RIGHT LIST IS NOT VALID");
            success.showAndWait();
        } else if (x != 0 && y == 0) {
            validated = false;
            Alert success = new Alert(Alert.AlertType.ERROR);
            success.setContentText("LEFT LIST NOT VALID");
            success.showAndWait();
        } else
        {
            validated = false;
            Alert success = new Alert(Alert.AlertType.ERROR);
            success.setContentText("BOTH LISTS ARE NOT VALID");
            success.showAndWait();
        }

    }

    public int Validate_compoundlist(Compound_list list)
    {
        if( list.get_size()<3)
        {
            if(list.get_size()==1)
            {
                return 0;
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("COMPILING ERROR");
                a.setContentText("NOT ENOUGH ELEMENTS");
                a.showAndWait();
                return -1;
            }
        }

        else
        {
            GUI_Node first,third;
            first=list.get_list().get(0);
            third=list.get_list().get(2);
            try{Relation_Node second = (Relation_Node) list.get_list().get(1);
                String result=Check.checktype(first.type,second.name,third.type);
                if(result.equals("ERROR"))
                {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setHeaderText("COMPILING ERROR");
                    a.setContentText("Error in triple relation constraint");
                    return -1;
                }

                else {
                    if (list.get_size()==3)
                        return 0;
                    else
                    {
                        int new_size = list.get_size()-2;
                        Compound_list new_list = new Compound_list();
                        if (result.equals("File"))
                            new_list.add_file("");
                        else if (result.equals("Generic"))
                            new_list.add_Generic("");
                        else if (result.equals("String"))
                            new_list.add_String("");
                        for (int i = 1; i < new_size ; i++) {
                            new_list.add_index(i,list.get_list().get(i+2));
                        }
                        int x=Validate_compoundlist(new_list);
                        if(x==-1) return -1;
                    }
                }
            }
            catch (ClassCastException e)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("PARSING ERROR");
                a.setContentText("Error in relation");
                return -1;

            }


        }
        return 0;
    }


}
//xmlns:fx="http://javafx.com/fxml"