package neoncontrol;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
    private ImageView SoundEffectLbl;
    @FXML
    private ImageView MusicLbl;
    @FXML
    private ImageView SpeedLbl;
    @FXML
    private ImageView VolumeLbl;
    @FXML
    private ImageView playBT;
    @FXML
    private ImageView exitBT;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Slider massSlider;
    @FXML
    public CheckBox BGMCheckBox;
    @FXML
    public CheckBox SFXCheckBox;
    @FXML
    private Slider volumeSlider;
    public static double weightValue = 0.3, volume = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Play.boing.getVolume() != 0)
            SFXCheckBox.setSelected(true);
        
        if(Play.BGM.getStatus().toString().equals("PLAYING"))
            BGMCheckBox.setSelected(true);
        
        bg.fitWidthProperty().bind(Main.stage.widthProperty());
        bg.fitHeightProperty().bind(Main.stage.heightProperty());
        bg.preserveRatioProperty().set(false);
        
        exitBT.xProperty().bind(Main.stage.widthProperty().multiply(0.5));
        exitBT.yProperty().bind(Main.stage.heightProperty().multiply(0.33));
        exitBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.2));
        exitBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        exitBT.preserveRatioProperty().set(false);
        
        playBT.xProperty().bind(Main.stage.widthProperty().multiply(-0.05));
        playBT.yProperty().bind(Main.stage.heightProperty().multiply(0.33));
        playBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.25));
        playBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.125));
        playBT.preserveRatioProperty().set(false);
        
        massSlider.translateXProperty().bind(Main.stage.widthProperty().multiply(.21));
        massSlider.translateYProperty().bind(Main.stage.heightProperty().multiply(.338));
        massSlider.setValue(weightValue);
        
        volumeSlider.translateXProperty().bind(Main.stage.widthProperty().multiply(0.21));
        volumeSlider.translateYProperty().bind(Main.stage.heightProperty().multiply(.28));
        volumeSlider.setValue(volume);
        
        BGMCheckBox.translateXProperty().bind(Main.stage.widthProperty().multiply(.18));
        BGMCheckBox.translateYProperty().bind(Main.stage.heightProperty().multiply(.20));
        
        SFXCheckBox.translateXProperty().bind(Main.stage.widthProperty().multiply(.18));
        SFXCheckBox.translateYProperty().bind(Main.stage.heightProperty().multiply(.15));
        
        SoundEffectLbl.xProperty().bind(Main.stage.widthProperty().multiply(0.12));
        SoundEffectLbl.yProperty().bind(Main.stage.heightProperty().multiply(0.125));
        SoundEffectLbl.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.6));
        SoundEffectLbl.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.25));
        
        MusicLbl.xProperty().bind(Main.stage.widthProperty().multiply(0.145));
        MusicLbl.yProperty().bind(Main.stage.heightProperty().multiply(0.18));
        MusicLbl.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.6));
        MusicLbl.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.35));
        
        
        VolumeLbl.xProperty().bind(Main.stage.widthProperty().multiply(0.19));
        VolumeLbl.yProperty().bind(Main.stage.heightProperty().multiply(0.25));
        VolumeLbl.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.3));
        VolumeLbl.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.15));
        
        SpeedLbl.xProperty().bind(Main.stage.widthProperty().multiply(.149));
        SpeedLbl.yProperty().bind(Main.stage.heightProperty().multiply(0.31));
        SpeedLbl.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.6));
        SpeedLbl.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.25));
        
        Play.ding.volumeProperty().bind(volumeSlider.valueProperty().multiply(0.4));
        Play.tap.volumeProperty().bind(volumeSlider.valueProperty().multiply(0.4));
        Play.BGM.volumeProperty().bind(volumeSlider.valueProperty().multiply(0.1));
        Play.boing.volumeProperty().bind(volumeSlider.valueProperty().multiply(0.2));
        
    }    

    @FXML
    private void setOnPlayClicked(MouseEvent event) throws IOException {
        volume = volumeSlider.getValue();
        weightValue = massSlider.getValue();
        Parent root = FXMLLoader.load(getClass().getResource("Level.fxml"));
        Scene newScene = AnchorPane.getScene();
        newScene.setRoot(root);
    }
    
    @FXML
    private void setOnToggleBGM(){
        if(!BGMCheckBox.isSelected()){
            Play.BGM.stop();
        }
        else{
            Play.BGM.play();
        }
    }
    
    @FXML
    private void setOnToggleSFX(){
        if(!SFXCheckBox.isSelected()){
            Play.ding.setVolume(0);
            Play.tap.setVolume(0);
            Play.boing.setVolume(0);
        }
        else{
            Play.ding.setVolume(0.4*volume);
            Play.boing.setVolume(0.2*volume);
            Play.tap.setVolume(0.4*volume);
        }
    }

    @FXML
    private void setOnExitClicked(MouseEvent event) {
        System.exit(0);
    }
}
