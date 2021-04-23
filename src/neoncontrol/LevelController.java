package neoncontrol;

import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
        
        Level lvl = new Level(pane);
        pane.getChildren().addAll(lvl.getWallList());
        for(Wall wall : lvl.getWallList())
            pane.getChildren().addAll(wall.getHitboxesList());   

        StickSpring sp = new StickSpring(new Vector(0, 0), 0);
        
        Arrow arrow = new Arrow(200, 180, 200, 180, 10);
        pane.getChildren().add(arrow);
        
        Label velLbl = new Label("Velocity Vector");
        velLbl.setLayoutX(130); velLbl.setLayoutY(75);
        velLbl.setFont(Font.font ("Verdana", 20));
        velLbl.setStyle("-fx-text-fill: red");
        pane.getChildren().add(velLbl);
        
        Circle c1 = new Circle(70);
        c1.setLayoutX(200); c1.setLayoutY(180);
        c1.setFill(Color.TRANSPARENT);
        c1.setStroke(Color.PURPLE);
        c1.setStrokeWidth(3);
        pane.getChildren().add(c1);
        
        Circle c2 = new Circle(3);
        c2.setFill(Color.RED);
        c2.setLayoutX(200); c2.setLayoutY(180);
        pane.getChildren().add(c2);
        
        Label bounceCounter = new Label("Bounce counter:\t0");
        bounceCounter.setTextFill(Color.RED);
        bounceCounter.setLayoutX(10); bounceCounter.setLayoutY(10);
        bounceCounter.setFont(Font.font ("Verdana", 15));
        pane.getChildren().add(bounceCounter);
        
        pane.getChildren().add(sp);
               
        Play play = new Play(lvl, new PhysicsEngine(SettingsMenuController.weightValue), sp, Main.scene, arrow, bounceCounter, pane);
        play.start();
    }     
}
