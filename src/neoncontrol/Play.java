package neoncontrol;

import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.*;

public class Play{
    private Level level;
    private PhysicsEngine physics;
    private StickSpring ss;
    private Scene keyChecker;
    boolean collided = false;
    private AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            collided = false;

            level.getWallList().forEach((wall) -> {
                
                if(ss.getHB2().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle()));
                    collided = true;
                }
                else if(ss.getHB1().intersects(wall.getHB().getBoundsInLocal()) && !collided){
                    ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 180));
                    collided = true;
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

    public Play(){
        
    }
    
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
                case ESCAPE: gameTimer.stop();break;
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
