package neoncontrol;

import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Play{
    private boolean paused = false;
    private Level level;
    private PhysicsEngine physics;
    private StickSpring ss;
    private Scene keyChecker;
    int count = 0;
    private boolean collided = false;

    private AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle(long l){
            collided = false;

            level.getWallList().forEach((wall) -> {
                
                if(ss.getHB2().intersects(wall.getHB().getBoundsInLocal()) && !ss.getHB1().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 90));
                    collided = true;
                }
                if(ss.getHB2().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle()));
                collided = true;
                }
                else if(ss.getHB1().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 180));
                    EventHandler<ActionEvent> eventHandler = e -> {
                        switch(count){
                            case 0: ss.setImage(new Image("Graphics/spring 2.png")); count++; break;
                            case 1: ss.setImage(new Image("Graphics/spring 3.png")); count++; break;
                            case 2: ss.setImage(new Image("Graphics/spring 4.png")); count++; break;
                            case 3: ss.setImage(new Image("Graphics/spring 3.png")); count++; break;
                            case 4: ss.setImage(new Image("Graphics/spring 2.png")); count++; break;
                            case 5: ss.setImage(new Image("Graphics/spring 1.png")); count++; break;
                        }
                    };
                    Timeline animation = new Timeline(new KeyFrame(Duration.millis(20), eventHandler));
                    animation.setCycleCount(10);
                    animation.setAutoReverse(true);
                    animation.play();
                    collided = true;                    
                    count = 0;
                }
                else if(ss.getHB3().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSide(ss.getVelocityVec(), wall));
                    collided = true;
                }
            });
            if(!collided)
                ss.setVelocityVec(physics.calculateMove(ss.getVelocityVec()));
            
            ss.move(ss.getVelocityVec().getX(),ss.getVelocityVec().getY()); 
        }
    };
    
    public Play(Level level, PhysicsEngine physics, StickSpring ss, Scene keyChecker){
        this.level = level;
        this.physics = physics;
        this.ss = ss;
        this.keyChecker = keyChecker;
    }    
    
    public void start(){
        gameTimer.start();
        keyChecker.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()){
                case ESCAPE: if(paused){gameTimer.start(); paused = false;} else{gameTimer.stop(); paused = true;} break;
                case A: ss.setAngle(ss.getAngle() - 5); break;
                case D: ss.setAngle(ss.getAngle() + 5); break;
            }
        });
    }
    
    public void showMenu(Menu menu){
        
    }

    
    public void setPhysics(PhysicsEngine physics){
        this.physics = physics;
    }
    
    public void setLevel(Level level){
        this.level = level;
    }
    
    public PhysicsEngine getPhysics(){
        return physics;
    }
    
    public Level getLevel(){
        return level;
    }
    
    public void setRotateCW(StickSpring sp){
        sp.setAngle(sp.getAngle()+1);
    }
    
    public void setRotateCCW(StickSpring sp){
        sp.setAngle(sp.getAngle()-1);
    }
}
