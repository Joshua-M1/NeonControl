/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author jakot
 */
public class MainMenuController implements Initializable {
    
    private Stage stage;
    @FXML
    private ImageView playBT;
    @FXML
    private ImageView settingsBT;
    @FXML
    private ImageView exitBT;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private ImageView bg;
    public static MediaPlayer BGM = new MediaPlayer(new Media(new File("src/Audio/HOME - Resonance.mp3").toURI().toString()));
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MainMenuController.BGM.setVolume(0.3);
        MainMenuController.BGM.setAutoPlay(true);
        MainMenuController.BGM.play();
        
        exitBT.layoutXProperty().bind(Main.stage.widthProperty().multiply(0.375));
        exitBT.layoutYProperty().bind(Main.stage.heightProperty().multiply(0.7));
        exitBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.25));
        exitBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        exitBT.preserveRatioProperty().set(false);
        
        playBT.layoutXProperty().bind(Main.stage.widthProperty().multiply(0.375));
        playBT.layoutYProperty().bind(Main.stage.heightProperty().multiply(0.4));
        playBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.25));
        playBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        playBT.preserveRatioProperty().set(false);
        
        settingsBT.layoutXProperty().bind(Main.stage.widthProperty().multiply(0.375));
        settingsBT.layoutYProperty().bind(Main.stage.heightProperty().multiply(0.55));
        settingsBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.25));
        settingsBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        settingsBT.preserveRatioProperty().set(false);
        
        bg.fitWidthProperty().bind(Main.stage.widthProperty());
        bg.fitHeightProperty().bind(Main.stage.heightProperty());
        bg.preserveRatioProperty().set(false);
    }    

    @FXML
    private void setOnPlayClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Level.fxml"));
        Scene newScene = AnchorPane.getScene();
        newScene.setRoot(root);   
    }
    
    private void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void setOnSettingsClicked(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SettingsMenu.fxml"));
        Scene newScene = AnchorPane.getScene();
        newScene.setRoot(root);
        
    }

    @FXML
    private void setOnExitClicked(MouseEvent event) {
        System.exit(0);
    }
    
}
