package geneplatform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    ListView<String> directories;
    @FXML
    Button remove_button,add_button,next_button;

    public ObservableList data ;
    public void on_add() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Directory");
        File file =fileChooser.showSaveDialog(directories.getScene().getWindow());
        if(file!=null)
        {
            data.add(file.getName());

        }

    }
    public void on_next() throws IOException {
        Stage mystage = (Stage)next_button.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("scenetwo.fxml"));
        mystage.setScene(new Scene(root, 800, 800));
    }
    public void on_remove()
    {
        System.out.println(directories.getSelectionModel().getSelectedIndex());
        int selected_index = directories.getSelectionModel().getSelectedIndex();
        if(selected_index!=-1)
            data.remove(selected_index);
        else
            return;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data=FXCollections.observableArrayList();
        directories.setItems(data);
        directories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //directories.setCellFactory(ComboBoxListCell.forListView(names));
        //directories.setMaxHeight(Control.USE_PREF_SIZE);

    }
}


//<BorderPane fx:controller="sample.Controller" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" stylesheets="@gene.css" xmlns:fx="http://javafx.com/fxml" >
