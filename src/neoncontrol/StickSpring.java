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
    private Rectangle spring1HB = new Rectangle(16,6);
    private Rectangle spring2HB = new Rectangle(16,6);
    private Rectangle stickHB = new Rectangle(16,64);

    public StickSpring() {
        this.setImage(new Image("Graphics/spring 1.png"));
        setPos(200,200);
        setHitboxes();
    }
    
    public StickSpring(Vector velVector, double angle){
        this.velVector = velVector;
        this.setImage(new Image("Graphics/spring 1.png"));
        setPos(200, 200);
        setHitboxes();
        setAngle(angle);
    }
    
    public void reset(){
        setVelocityVec(new Vector(0,0));
        setPos(200, 200);
        setAngle(0);
        resetHitboxes();
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
        double change = angle-this.angle;
        this.angle = angle;
        this.setRotate(angle);
        stickHB.setRotate(angle);
        spring1HB.setRotate(angle);
        spring2HB.setRotate(angle);
        spring1HB.setX(getX()+9+(36*Math.sin(Math.toRadians(angle))));
        spring1HB.setY(getY()+52-(36*Math.cos(Math.toRadians(angle))));
        spring2HB.setX(getX()+9-(36*Math.sin(Math.toRadians(angle))));
        spring2HB.setY(getY()+52+(36*Math.cos(Math.toRadians(angle))));
    }
    
    public double getCenterX(){
        return getXPos()+8;
    }
    
    public double getCenterY(){
        return getYPos()+30;
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
        spring1HB.setY(this.getY()+12);
        spring1HB.setStroke(Color.RED);
        spring1HB.setFill(Color.TRANSPARENT);
        spring2HB.setX(this.getX()+9);
        spring2HB.setY(this.getY()+86);
        spring2HB.setStroke(Color.GREEN);
        spring2HB.setFill(Color.TRANSPARENT);
        stickHB.setX(this.getX()+8);
        stickHB.setY(this.getY()+24);
        stickHB.setStroke(Color.BLUE);
        stickHB.setFill(Color.TRANSPARENT);
    }
    
    public void resetHitboxes(){
        spring1HB.setX(this.getX()+8);
        spring1HB.setY(this.getY()+12);
        spring2HB.setX(this.getX()+8);
        spring2HB.setY(this.getY()+86);
        stickHB.setX(this.getX()+8);
        stickHB.setY(this.getY()+24);
    }
}
