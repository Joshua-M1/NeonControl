package neoncontrol;

import javafx.scene.image.Image;


/**
 *
 * @author jakot
 */
public class Objective extends Wall{
    
    public Objective(double xPos, double yPos, double xSize, double ySize, double angle){
        super(xPos,yPos,xSize,ySize,angle);
        setImage(new Image("Graphics/Objective.jpg"));
    }
}
