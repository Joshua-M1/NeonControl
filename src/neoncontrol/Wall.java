/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 *
 * @author jakot
 */
public class Wall extends ImageView{
    
    private double xPos, yPos, angle, xSize, ySize;
    
    private ArrayList<Rectangle> hitboxesList = new ArrayList<>();
    
    public Wall(){
        setImage(new Image("Graphics/wall.png"));
    }
    
    public Wall(double xPos, double yPos, double xSize, double ySize, double angle){
        setHitboxesList(xPos, yPos, xSize, ySize);
        setXPos(xPos);
        setYPos(yPos);
        setXSize(xSize);
        setYSize(ySize);
        setAngle(angle);
        
        
        setImage(new Image("Graphics/wall.png"));
    }
    
    public ArrayList<Rectangle> getHitboxesList(){
        return hitboxesList;
    }
    
    public Vector getNormalVector(int wallCount){
        Vector normal = new Vector();
        normal.setVectorAlternate(1,(hitboxesList.get(wallCount).getRotate()+90));
        normal.setX(-normal.getX());
        return normal;
    }

    public void setHitboxesList(double xPos, double yPos, double xSize, double ySize){
        hitboxesList.add(new Rectangle(xPos, yPos, xSize, 10)); //top 
        hitboxesList.get(0).setFill(Color.RED);
        hitboxesList.add(new Rectangle(xPos+xSize-(ySize/2), yPos+(ySize/2), ySize, 10)); //right
        hitboxesList.get(1).setFill(Color.YELLOW);
        hitboxesList.add(new Rectangle(xPos, yPos+ySize, xSize, 10));//bottom
        hitboxesList.get(2).setFill(Color.BLUE);
        hitboxesList.add(new Rectangle(xPos-(ySize/2),yPos+(ySize/2),ySize,10)); //left
        hitboxesList.get(3).setFill(Color.GREEN);
        
        
        for(int i = 0; i < 4; i++){
            hitboxesList.get(i).setRotate(i*90);
        }   
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
        rotateHitboxes(angle);
        this.angle = angle;
        this.setRotate(angle);
        
    }
    
    public void rotateHitboxes(double angle){
        double centerX;
        double centerY = yPos+(ySize/2);
        double sizeRatio = (-1/(2*xSize/ySize))+0.5;
        for(int i = 0; i < 4; i++){
            Rectangle hitbox = hitboxesList.get(i);
            double curX = hitbox.getX();
            
            if(i==0||i==2)
                centerX = xPos;
            else
                centerX = xPos + (xSize*sizeRatio);  
            
            hitbox.setX((Math.cos(Math.toRadians(-angle))*(curX-centerX)) + (Math.sin(Math.toRadians(-angle))*(hitbox.getY()-centerY)) + centerX);
            hitbox.setY(-(Math.sin(Math.toRadians(-angle))*(curX-centerX)) + (Math.cos(Math.toRadians(-angle))*(hitbox.getY()-centerY)) + centerY);
            hitbox.setRotate((hitbox.getRotate()+angle)%360);
        }
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
    
   
    
}
