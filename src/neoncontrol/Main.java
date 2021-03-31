/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
/**
 *
 * @author jakot
 */
public class Main extends Application{
   
   public static Scene scene;
   public static Stage stage = new Stage();

   public void start(Stage stage) throws Exception{
       
        // Create a scene and place it in the stage
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        scene = new Scene(root);
        this.stage.setFullScreen(true);
        this.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        this.stage.setResizable(false);
        this.stage.setTitle("NeonControl"); // Set the stage title
        this.stage.setScene(scene); // Place the scene in the stage
        this.stage.show(); // Display the stage
        
    }
    
    public static void main(String[] args) {
        launch(args);
        //hello it is i
    }
    
}
