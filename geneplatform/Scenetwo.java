package geneplatform;

import com.sun.webkit.Timer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import nlp.platform.NER;
import nlp.platform.Relations;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by muhammedmahrous on 23/06/17.
 */
public class Scenetwo implements Initializable {
    @FXML
    Button finish;
    @FXML
    VBox workspace;
    @FXML
    ListView nodes_list, relations_list,container_list;

    final ScrollPane sp = new ScrollPane();

    public ObservableList relations=FXCollections.observableArrayList(),
            nodes=FXCollections.observableArrayList(),container=FXCollections.observableArrayList();
    private final ObjectProperty<ListView> dragSource = new SimpleObjectProperty<>();

    public void finish()
    {
        if(Model.getInstance().getCurrentBlocks().size()!=workspace.getChildren().size())
        {

            System.out.println(Model.getInstance().getCurrentBlocks().size());
            System.out.println(workspace.getChildren().size());

        Alert success = new Alert(Alert.AlertType.ERROR);
        success.setContentText("PLEASE VALIDATE ALL BLOCKS BEFORE FINISHING!");
        success.showAndWait();
        }
        else if(workspace.getChildren().size()==0)
        {   Alert success = new Alert(Alert.AlertType.ERROR);
            success.setContentText("PLEASE ADD ONE CONTAINER AT LEAST!");
            success.showAndWait();
        }

        else
        {
            ArrayList<BlockModel> blocks = Model.getInstance().getCurrentBlocks();
            for (int i = 0; i < blocks.size(); i++)
            {
                if(blocks.get(i).getClass().equals(Relation_Model.class))
                {
                    //System.out.println("FOUND RELATION");
                    Relation_Model rel = (Relation_Model) blocks.get(i);
                    Relations rela = new Relations();
                    rela.initialize(rel.first_list,rel.second_list);


                }
                else if(blocks.get(i).getClass().equals(NER_Model.class))
                {
                    //System.out.println("FOUND NER");
                    NER_Model nera = (NER_Model) blocks.get(i);
                    NER nerb = new NER();
                    nerb.initialize(nera.first_list,nera.unique_relation,nera.ner);
                }
            }
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("SUCCESSFULLY CREATED CONFIG FILE/S");
            success.showAndWait();
            System.exit(0);

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nodes.add("FILE");
        nodes.add("STRING");
        nodes.add("GENERIC");

        
        relations.add("ONE TO ONE");//one to one  "0"
        relations.add("ONE TO ALL");//ONE TO ALL "1"
        relations.add("ALL TO ONE");//ALL TO ONE "2"
        relations.add("ALL TO ALL");//ALL TO ALL  "3"

        container.add("NER");
        container.add("RELATION");



        nodes_list.setItems(nodes);

        nodes_list.setMaxHeight(Control.USE_COMPUTED_SIZE);
        nodes_list.setMaxWidth(Control.USE_COMPUTED_SIZE);

        relations_list.setItems(relations);
        relations_list.setMaxHeight(Control.USE_COMPUTED_SIZE);
        relations_list.setMaxWidth(Control.USE_COMPUTED_SIZE);

        container_list.setItems(container);
        container_list.setMaxHeight(Control.USE_COMPUTED_SIZE);
        container_list.setMaxWidth(Control.USE_COMPUTED_SIZE);


        initilize_events_source(nodes_list);
        initilize_events_source(relations_list);
        initilize_events_source(container_list);


        initilize_events_destination(workspace);

    }
    public void initilize_events_source(ListView nodes_list)
    {
        nodes_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        nodes_list.setOnDragDetected(new EventHandler<MouseEvent>()
        {

            public void handle(MouseEvent event)
            {
                dragSource.set(nodes_list);
           //     System.out.println("Event on Source: drag detected");
                dragDetected(event, nodes_list);
            }
        });



    }
    public void initilize_events_destination(VBox ws)
    {
        ws.setOnDragOver(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
             //   System.out.println("Event on Vbox: drag over");
                dragOver(event, ws);
            }
        });
        ws.setOnDragDropped(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
               // System.out.println("Event on Source: drag dropped");
                try {
                    dragDropped(event, ws);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ws.setOnDragDone(new EventHandler <DragEvent>()
        {
            public void handle(DragEvent event)
            {
                //System.out.println("Event on Source: drag done");
                dragDone(event, ws);
            }
        });

    }

    private void dragDetected(MouseEvent event, ListView listView)
    {
        // Make sure at least one item is selected
        int selectedCount = listView.getSelectionModel().getSelectedIndices().size();

        if (selectedCount == 0)
        {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Put the the selected items to the dragboard
        ArrayList selectedItems = new ArrayList(listView.getSelectionModel().getSelectedItems());

        ClipboardContent content = new ClipboardContent();
        content.putString(selectedItems.get(0).toString());
        //System.out.println("DETECTED: "+selectedItems.get(0).toString());
        dragboard.setContent(content);
        //event.consume();
    }

    private void dragOver(DragEvent event, VBox ws)
    {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();
      // System.out.println("TYPE1: "+event.getGestureSource().getClass());
      //  System.out.println("TYPE2: "+ws.getClass()+"and VBox class = "+VBox.class);
        ListView sourcelist = (ListView)(event.getGestureSource());
      //  System.out.println(sourcelist.getId());

        if (sourcelist.getId().equals("container_list") && dragboard.hasString())
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        //  System.out.println("ENTERED");
        }
        else {
            event.acceptTransferModes(TransferMode.NONE);
        }

      //  event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDropped(DragEvent event, VBox ws) throws IOException {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

//        if(dragboard.hasString())
//        {
//
//            // DO THE ACTUAL FUNCTIONALITY HERE
//            //System.out.println("OK!");
//            //System.out.println(dragboard.getString());
//            ws.getChildren().add(new Label(dragboard.getString()));
//            // Data transfer is successful
//            dragCompleted = true;
//        }
            if (dragboard.getString().equals("NER"))
            {
                Parent root = FXMLLoader.load(getClass().getResource("NER.fxml"));
                ws.getChildren().add(root);
            }
            else if (dragboard.getString().equals("RELATION"))
            {
                Parent root = FXMLLoader.load(getClass().getResource("Relation.fxml"));
                ws.getChildren().add(root);
            }
        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
       // event.consume();
    }

    private void dragDone(DragEvent event, VBox ws)
    {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE)
        {
            // DO SOMETHING AFTER SUCCESS DROPPING TOOK PLACE
            //System.out.println("SUCCCESSSSS");

        }

      //  event.consume();
    }

}
//<AnchorPane  fHeight="400.0" prefWidth="600.0" xmlns:fx="http://javafx.com/fxml" fx:controller="sample.Scenetwo">

