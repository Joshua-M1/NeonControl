package neoncontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Duration;
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
            Play.ding.setVolume(0.4);
            Play.boing.setVolume(0.2);
            Play.tap.setVolume(0.4);
            Play.BGM.setVolume(0.1);
            Play.BGM.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    Play.BGM.seek(Duration.ZERO);
                    Play.BGM.play();
                }
            });
            Play.BGM.play();
        }
    }
    
    public static void main(String[] args) {
        Application.launch(Run.class);
    }
    
}
