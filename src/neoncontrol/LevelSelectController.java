/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author addav
 */
public class LevelSelectController{

    @FXML
    private ImageView background;
    @FXML
    private ImageView tutorial;
    @FXML
    private ImageView level1;
    @FXML
    private ImageView level2;
    @FXML
    private ImageView exit;
    
    @FXML
    private void setOnExitClicked(MouseEvent event) {
        System.exit(0);
    }
    
}
