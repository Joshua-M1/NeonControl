/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author jakot
 */
public class LevelController implements Initializable {

    @FXML
    private ImageView background;
    @FXML
    private AnchorPane pane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        background.fitWidthProperty().bind(Main.stage.widthProperty());
        background.fitHeightProperty().bind(Main.stage.heightProperty());
        background.preserveRatioProperty().set(false);
        
        Level lvl1 = new Level();
        pane.getChildren().addAll(lvl1.getWallList());

        StickSpring sp = new StickSpring(new Vector(0, 0), 0);
        /*sp.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.0275));
        sp.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.1525));
        sp.layoutXProperty().bind(Main.stage.widthProperty().multiply(0.0001));
        sp.layoutYProperty().bind(Main.stage.heightProperty().multiply(0.0001));*/
        
        pane.getChildren().add(sp);
        pane.getChildren().add(sp.getHB1());
        pane.getChildren().add(sp.getHB2());
        pane.getChildren().add(sp.getHB3());
        Play play = new Play(lvl1, new PhysicsEngine(0.3), sp, Main.scene);
        play.start();
    }     
}
