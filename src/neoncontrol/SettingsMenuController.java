/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jakot
 */
public class SettingsMenuController implements Initializable {

    @FXML
    private ImageView bg;
    @FXML
    private ImageView playBT;
    @FXML
    private ImageView exitBT;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Slider massSlider;
    public static double weightValue = 0.3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bg.fitWidthProperty().bind(Main.stage.widthProperty());
        bg.fitHeightProperty().bind(Main.stage.heightProperty());
        bg.preserveRatioProperty().set(false);
        
        exitBT.xProperty().bind(Main.stage.widthProperty().multiply(0.5));
        exitBT.yProperty().bind(Main.stage.heightProperty().multiply(0.33));
        exitBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.2));
        exitBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        exitBT.preserveRatioProperty().set(false);
        
        playBT.xProperty().bind(Main.stage.widthProperty().multiply(-0.1));
        playBT.yProperty().bind(Main.stage.heightProperty().multiply(0.33));
        playBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.25));
        playBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        playBT.preserveRatioProperty().set(false);
    }    

    @FXML
    private void setOnPlayClicked(MouseEvent event) throws IOException {
        weightValue = massSlider.getValue();
        Parent root = FXMLLoader.load(getClass().getResource("Level.fxml"));
        Scene newScene = AnchorPane.getScene();
        newScene.getWindow().centerOnScreen();
        newScene.setRoot(root);
    }

    @FXML
    private void setOnExitClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void setOnHoverPlayBT(MouseEvent event) {
    }
}
