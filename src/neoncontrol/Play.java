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
    private Level level;
    private PhysicsEngine physics;
    private StickSpring ss;
    private Scene keyChecker;
    int count = 0;
    private boolean collided = false, paused = false;

    private AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle (long l){
            collided = false;

            level.getWallList().forEach((wall) -> {
                
                if(ss.getHB2().intersects(wall.getHB().getBoundsInLocal()) && !ss.getHB1().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 90));
                    System.out.println(ss.getVelocityVec().toString());
                    collided = true;
                    EventHandler<ActionEvent> eventHandler = e -> {runAnimation(ss, 1);
                        
                    };  
                    Timeline animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
                    animation.setCycleCount(6);
                    animation.play();
                }
                
                else if(ss.getHB1().intersects(wall.getHB().getBoundsInLocal()) && !ss.getHB2().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + + 180 + 90));
                    System.out.println(ss.getVelocityVec().toString() + " 2");
                    collided = true;
                    EventHandler<ActionEvent> eventHandler = e -> { runAnimation(ss, 2);
                    };  
                    Timeline animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
                    animation.setCycleCount(6);
                    animation.play();
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
                case LEFT:
                case A: ss.setAngle(ss.getAngle() - 10); break;
                case RIGHT:
                case D: ss.setAngle(ss.getAngle() + 10); break;
                
                
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
    
    public void runAnimation(StickSpring ss, int index) {
        try{
            if(index == 1)
                switch(count){
                    case 0: ss.setImage(new Image("Graphics/spring 5.png")); Thread.sleep(2);count++; break;
                    case 1: ss.setImage(new Image("Graphics/spring 6.png")); Thread.sleep(2);count++; break;
                    case 2: ss.setImage(new Image("Graphics/spring 7.png")); Thread.sleep(2);count++; break;
                    case 3: ss.setImage(new Image("Graphics/spring 6.png")); Thread.sleep(2);count++; break;
                    case 4: ss.setImage(new Image("Graphics/spring 5.png")); Thread.sleep(2);count++; break;
                    case 5: ss.setImage(new Image("Graphics/spring 1.png")); Thread.sleep(2);count=0; break;
                }
            if(index == 2)
                switch(count){
                    case 0: ss.setImage(new Image("Graphics/spring 2.png")); Thread.sleep(2);count++; break;
                    case 1: ss.setImage(new Image("Graphics/spring 3.png")); Thread.sleep(2);count++; break;
                    case 2: ss.setImage(new Image("Graphics/spring 4.png")); Thread.sleep(2);count++; break;
                    case 3: ss.setImage(new Image("Graphics/spring 3.png")); Thread.sleep(2);count++; break;
                    case 4: ss.setImage(new Image("Graphics/spring 2.png")); Thread.sleep(2);count++; break;
                    case 5: ss.setImage(new Image("Graphics/spring 1.png")); Thread.sleep(2);count=0; break;
                }
        }
        catch(InterruptedException ex){
            System.out.println("Animation Bug");
        }
        
    }
}
