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
public class Main{
   
   public static Scene scene;
   public static Stage stage;
   
   public static class Run extends Application{
        @Override
        public void start(Stage stage) throws Exception{

             Main.stage = new Stage();
             Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
             scene = new Scene(root);
             Main.stage.setFullScreen(true);
             Main.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
             Main.stage.setResizable(false);
             Main.stage.setTitle("NeonControl"); // Set the stage title
             Main.stage.setScene(scene); // Place the scene in the stage
             Main.stage.show(); // Display the stage
        }
    }
    
    public static void main(String[] args) {
        Application.launch(Run.class);
    }
    
}
