/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

/**
 *
 * @author jakot
 */
public class Wall extends ImageView{
    
    private double xPos, yPos, angle, xSize, ySize;
    private Vector normal = new Vector();
    private Rectangle hb;
    
    public Wall(){
        setImage(new Image("Graphics/wall.png"));
    }
    
    public Wall(double xPos, double yPos, double xSize, double ySize, double angle){
        hb = new Rectangle();
        setXPos(xPos);
        setYPos(yPos);
        setXSize(xSize);
        setYSize(ySize);
        setAngle(angle);
        hb.xProperty().bind(this.xProperty());
        hb.yProperty().bind(this.yProperty());
        hb.heightProperty().bind(this.fitHeightProperty());
        hb.widthProperty().bind(this.fitWidthProperty());
        hb.rotateProperty().bind(this.rotateProperty());
        normal.setVectorAlternate(1, angle);
        double x = normal.getX();
        normal.setX(-normal.getY());
        normal.setY(-x);
        if(Math.abs(normal.getX()) == 0)
            normal.setX(0);
        if(Math.abs(normal.getY()) == 0)
            normal.setY(0);
        setImage(new Image("Graphics/wall.png"));
    }
    
    public Rectangle getHB(){
        return hb;
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
        this.setX(xPos);
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
        this.setY(yPos);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.setRotate(angle);
        hb.setRotate(angle);
    }


    public double getXSize() {
        return xSize;
    }

    public void setXSize(double xSize) {
        this.xSize = xSize;
        this.setFitWidth(xSize);
    }

    public double getYSize() {
        return ySize;
    }

    public void setYSize(double ySize) {
        this.ySize = ySize;
        this.setFitHeight(ySize);
    }
    
    public void setPos(double newXPos, double newYPos){
        setXPos(newXPos);
        setYPos(newYPos);
       
    }
    
    public Vector getNormal(){
        
        return normal;
    }
    
}
