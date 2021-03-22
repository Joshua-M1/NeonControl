/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
//comment


/**
 *
 * @author addav
 */
public class StickSpring extends ImageView{
    private double yPos;
    private double xPos;
    private double angle;
    
    private Vector velVector;
    private double mass;
    
    private ArrayList<Rectangle> HBList = new ArrayList<>();
    private Rectangle spring1HB;
    private Rectangle spring2HB;
    private Rectangle stickHB;

    public StickSpring() {
        this.setImage(new Image("Graphics/spring 1.png"));
        setPos(200,300);
    }
    
    public StickSpring(Vector velVector, double angle){
        this.velVector = velVector;
        this.setImage(new Image("Graphics/spring 1.png"));
        spring1HB = new Rectangle(209, 400, 17, 20);
        spring2HB = new Rectangle(209, 300, 17, 20);
        stickHB = new Rectangle(209, 320, 17, 80);
        setAngle(angle);
        this.angle -= 90;
        setPos(200, 300);

        this.setImage(new Image("Graphics/spring 1.png"));
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
        this.setY(yPos);
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
        this.setX(xPos);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.setRotate(angle);
    }

    public Vector getVelocityVec(){
        return velVector;
    }
    
    public void setVelocityVec(Vector velVector){
        this.velVector = velVector;
    }
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    public void move(double xMove, double yMove){
        this.setXPos(getXPos()+xMove);
        this.setYPos(getYPos()+yMove);
        for(Rectangle hb : HBList){
            hb.setX(hb.getX()+xMove);
            hb.setY(hb.getY()+yMove);
        }
    }
    
    //this will need fixing
    public void rotate(){
        double currentAngle = this.getAngle();
    }
    
    public double calculateNewX(){
        double newX = getXPos()+getVelocityVec().getX();
        return newX;
    }
    
    public double calculateNewY(){
        double newY = getYPos()+getVelocityVec().getY();
        return newY;
    }
    
    public void setPos(double newXPos, double newYPos){
        this.setXPos(newXPos);
        this.setYPos(newYPos);
    }  
    
    public Rectangle getHB1() {
        return spring1HB;
    }
    
    public Rectangle getHB2() {
        return spring2HB;
    }
    
    public Rectangle getHB3() {
        return stickHB;
    }
    
    public ArrayList<Rectangle> getHBList(){
        return HBList;
    }
    
    public void setHitboxes(){
        HBList.add(spring1HB);
        HBList.add(spring2HB);
        HBList.add(stickHB);
        spring1HB.setX(this.getX()+9);
        spring1HB.setY(this.getY()+4);
        spring1HB.setFill(Color.RED);
        spring2HB.setX(this.getX()+9);
        spring2HB.setY(this.getY()+86);
        spring2HB.setFill(Color.GREEN);
        stickHB.setX(this.getX()+9);
        stickHB.setY(this.getY()+24);
        stickHB.setFill(Color.BLUE);  
    }
}
