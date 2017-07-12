/*
Author:				Muhamed Mahrous
Date:				15/10/2016
Version:			1.0
Project ID:			I.E. Framework
CS Class:			N/A
Programming Language:		JAVA
OS/Hardware dependencies:	None

Problem Description:
The main framework class.


Overall Design:
System structure:          The class responsible of starting the GUI.
Data representation: None.
Algorithms:	        None.


Program Assumptions and Restrictions :
None.
*/

package geneplatform;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scenetwo.fxml"));
      primaryStage.setTitle("I.E. Framework");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
       primaryStage.setScene(new Scene(root,width , height));
     //  primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
