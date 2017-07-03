/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneplatform;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

import javax.swing.plaf.SeparatorUI;

/**
 * FXML Controller class
 *
 * @author muhammedmahrous
 */
public class NERController
        implements Initializable
{
//
//    /**
//     * Initializes the controller class.
//     */
    public boolean validated = false;
    @FXML
    ListView ner_first_arg;
    @FXML
    Label ner_relation,ner_second_arg;
    public ObservableList first_arg=FXCollections.observableArrayList();
    public Compound_list first_list =new Compound_list();
    public String unique_relation="";

    public int my_id = -1;
    Compound_list ner = new Compound_list();
    Model mymodel = new Model();

    @Override
    public void initialize(URL url, ResourceBundle rb)  {

        ner_first_arg.setItems(first_arg);
        FIRST_initilize_drag();
        initilize_drag(ner_relation);
        initilize_drag(ner_second_arg);

    }


    /* NER DRAG AND DROP HANDLING
     AND
     Relation DRAG AND DROP HANDLING */

    public void initilize_drag(Label label)
    {
        label.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
          //      System.out.println("Event on NER: drag over");
                dragOver(event, label);
            }
        });
        label.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
        //        System.out.println("Event on NER: drag dropped");
                dragDropped(event, label);
            }
        });

        label.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
      //          System.out.println("Event on NER: drag done");
                dragDone(event, label);
            }
        });

    }

    private void dragOver(DragEvent event, Label label)
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
    private void dragDropped(DragEvent event, Label label)
    {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasString())
        {

  //          System.out.println("NER DROPPED");
            if (label.getId().equals(ner_relation.getId()))
            {
                    if (dragboard.getString().equals("one to one"))
                    {
                        // CHECK FIRST ARG IS NOT FILE

                        label.setText(dragboard.getString());
                        unique_relation="0";
                        dragCompleted = true;

                    }
                    else if (dragboard.getString().equals("all to one"))
                    {
                        // CHECK FIRST IS FILE

                        label.setText(dragboard.getString());
                        unique_relation="2";
                        dragCompleted = true;

                    }
                    else if (dragboard.getString().equals("one to all")||dragboard.getString().equals("all to all"))
                    {
                        unique_relation="-1";
                        error_alert(event);
                        dragCompleted=false;
                    }
                    else
                    {unique_relation="-1";
                        error_alert(event);
                        dragCompleted=false;
                    }

            }
            else if (label.getId().equals(ner_second_arg.getId()))
            {
                if (dragboard.getString().equals("file"))
                {
                   if(ner_relation.getText().equals("one to one"))
                   {
                       FileChooser fileChooser = new FileChooser();
                       fileChooser.setTitle("Select Directory");
                       File file =fileChooser.showOpenDialog(ner_second_arg.getScene().getWindow());


                       if(file!=null)
                       {
                           ner_second_arg.setText(file.getName());
                           if(ner.get_size()>=1)
                               ner.remove_list();
                           ner.add_file(file.getPath());
                           dragCompleted=true;
                       }
                   }
                    else {
                    error_alert(event);
                    dragCompleted=false;
                }
                }
                else if (dragboard.getString().equals("string")) {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Text Input Dialog");
                    dialog.setHeaderText("String input");
                    dialog.setContentText("Please enter a string:");

                    Optional<String> result = dialog.showAndWait();
                    // The Java 8 way to get the response value (with lambda expression).
                    result.ifPresent(string -> {label.setText(string);
                        if(ner.get_size()>=1)
                            ner.remove_list();
                        ner.add_String(string);
                    });
                    dragCompleted = true;
                }

                else
                {
                    error_alert(event);
                    dragCompleted=false;
                }


            }

            // DO THE ACTUAL FUNCTIONALITY HERE
            //System.out.println("OK!");
            //System.out.println(dragboard.getString());
            // Data transfer is successful
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }

    private void dragDone(DragEvent event, Label label)
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


    // FIRST ARGUMENT INITIALIZATION AND HANDLING

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
                //        System.out.println("Event on NER: drag dropped");
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
                if (dragboard.getString().equals("one to one"))
                {

                    first_arg.add(dragboard.getString());
                    first_list.add_Relation("0");
                    dragCompleted = true;

                }
                else if (dragboard.getString().equals("all to one"))
                {
                    first_arg.add(dragboard.getString());
                    first_list.add_Relation("2");
                    dragCompleted = true;

                }
                else if (dragboard.getString().equals("one to all"))
                {
                    first_arg.add(dragboard.getString());
                    first_list.add_Relation("1");
                    dragCompleted = true;

                }
                else if (dragboard.getString().equals("all to all"))
                {
                    first_arg.add(dragboard.getString());
                    first_list.add_Relation("3");
                    dragCompleted = true;

                }


                else if (dragboard.getString().equals("file"))
                {


                    FileChooser fileChooser = new FileChooser();
                        System.out.println("EN");
                        File file =fileChooser.showOpenDialog(ner_first_arg.getScene().getWindow());
                        System.out.println("LE");

                        if(file!=null)
                        {
                            first_arg.add(file.getName());
                            first_list.add_file(file.getPath());
                            // System.out.println(ner);
                        }
                    dragCompleted=true;

                }
                else if (dragboard.getString().equals("string")) {
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

        if (tm == TransferMode.COPY)
        {
            // DO SOMETHING AFTER SUCCESS DROPPING TOOK PLACE
            System.out.println("NER COMPLETED SUCCESSFULLY");

        }

        event.consume();
    }
    public void validate()
    {
        String x =Validate_compoundlist(first_list);
        //int y =Validate_compoundlist(first_list);

        if(!x.equals("ERORR"))
        {
            if(ner.get_size()==1)
            {
                String y = Check.checktype(x,unique_relation,ner.get_index_type(0));


           if(!y.equals("ERROR"))
           {
               validated=true;
               Alert success = new Alert(Alert.AlertType.INFORMATION);
               success.setContentText("SUCCESSFULLY VALIDATED");
               success.showAndWait();
               BlockModel my_model = new NER_Model(first_list,unique_relation,ner);
               my_id=Model.getInstance().addBlock(my_model,my_id);
               System.out.println("I AM AT #"+my_id);
           }
           else
           {
               validated=false;
               Alert success = new Alert(Alert.AlertType.ERROR);
               success.setContentText("ERROR IN THE TRIPLE RELATION");
               success.showAndWait();
           }
            }
            else
            {
                validated=false;
                Alert success = new Alert(Alert.AlertType.ERROR);
                success.setContentText("SECOND ARGUMENT IS NOT INITIALIZED");
                success.showAndWait();
            }
        }
        else {
            validated=false;
            Alert success = new Alert(Alert.AlertType.ERROR);
            success.setContentText("LEFT LIST IS NOT VALID");
            success.showAndWait();
        }

    }

    public String Validate_compoundlist(Compound_list list)
    {
        if( list.get_size()<3)
        {
            if(list.get_size()==1)
            {
                return list.get_index_type(0);
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("COMPILING ERROR");
                a.setContentText("NOT ENOUGH ELEMENTS");
                a.showAndWait();
                return "ERORR";
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
                    a.showAndWait();
                    return "ERORR";
                }

                else {
                    if (list.get_size()==3)
                        return result;
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
                        String x=Validate_compoundlist(new_list);
                        if(x.equals("ERORR")) return "ERORR";
                    }
                }
            }
            catch (ClassCastException e)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("PARSING ERROR");
                a.setContentText("Error in relation");
                a.showAndWait();
                return "ERORR";

            }


        }
        System.out.println("REACHED THE UNREACHABLE");
        return "ERORR";
    }

}
//xmlns:fx="http://javafx.com/fxml"