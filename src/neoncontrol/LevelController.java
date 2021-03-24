/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import javafx.scene.text.Font;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontPosture;

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
        ArrayList wallList = new ArrayList<Wall>();
        
        Level lvl1 = new Level();
        background.setImage(lvl1.getImage());
        pane.getChildren().addAll(lvl1.getWallList());
        StickSpring sp = new StickSpring(new Vector(0, 0), 0);
        pane.getChildren().add(sp);
        pane.getChildren().add(sp.getHB1());
        pane.getChildren().add(sp.getHB2());
        pane.getChildren().add(sp.getHB3());
        
        Arrow arrow = new Arrow(200, 180, 200, 238.5, 10);
        pane.getChildren().add(arrow);
        
        Label lb = new Label("Force Vector");
        lb.setLayoutX(135); lb.setLayoutY(75);
        lb.setFont(Font.font ("Verdana", 20));
        lb.setStyle("-fx-text-fill: red");
        pane.getChildren().add(lb);
        
        Circle c1 = new Circle(70);
        c1.setLayoutX(200); c1.setLayoutY(180);
        c1.setFill(Color.TRANSPARENT);
        c1.setStroke(Color.PURPLE);
        c1.setStrokeWidth(3);
        pane.getChildren().add(c1);
        
        Play play = new Play(lvl1, new PhysicsEngine(0.3), sp, Main.scene, arrow, lb, c1);
        play.start();
    }     
}
