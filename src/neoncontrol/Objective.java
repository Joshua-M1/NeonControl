/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
