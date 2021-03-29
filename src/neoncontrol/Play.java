package neoncontrol;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Play{
    private Level level;
    private PhysicsEngine physics;
    private StickSpring ss;
    private final Scene keyChecker;
    private Arrow arrow;
    private Label lb;
    private Circle c1;
    private Pane pane;
    int count = 0;
    private boolean collided = false, paused = false, A = false, D = false;
    EventHandler<ActionEvent> noEvent;
    Timeline animation = new Timeline(new KeyFrame(Duration.millis(0), noEvent));
   
    
    private AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle (long l){
            collided = false;
            if(A){
                ss.setAngle(ss.getAngle() - 4);
            }
        
            if(D){
                ss.setAngle(ss.getAngle() + 4);
            }
            try{
                level.getWallList().forEach((wall) -> {

                    if(Shape.intersect(ss.getHB2(), wall.getHB()).getBoundsInLocal().getWidth() != -1 && Shape.intersect(ss.getHB1(), wall.getHB()).getBoundsInLocal().getWidth() == -1 && !collided){


                        //touching objective
                        ifLevelComplete(wall);

                        ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 90));
                        collided = true;
                        EventHandler<ActionEvent> eventHandler = e -> {runAnimation(ss, 1);                    
                        };  
                        animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
                        animation.setCycleCount(6);
                        animation.play();
                        setArrow();
                    }

                    else if(Shape.intersect(ss.getHB1(), wall.getHB()).getBoundsInLocal().getWidth() != -1 && Shape.intersect(ss.getHB2(), wall.getHB()).getBoundsInLocal().getWidth() == -1 && !collided){

                        //touching objective
                        ifLevelComplete(wall);

                        ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 270));
                        collided = true;
                        EventHandler<ActionEvent> eventHandler = e -> { runAnimation(ss, 2);
                        };  
                        animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
                        animation.setCycleCount(6);
                        animation.play();
                        setArrow();
                    }

                    else if(Shape.intersect(ss.getHB3(), wall.getHB()).getBoundsInLocal().getWidth() != -1 && !collided){

                        //touching objective
                        ifLevelComplete(wall);

                        ss.setVelocityVec(physics.collisionSide(ss.getVelocityVec(), wall));
                        collided = true;
                        setArrow();
                    }                
                });
                
            }catch(java.util.ConcurrentModificationException ex){
                //error with arrayList or something
            }

                if(!collided)
                    ss.setVelocityVec(physics.calculateMove(ss.getVelocityVec()));

                ss.move(ss.getVelocityVec().getX(),ss.getVelocityVec().getY()); 
        }
    };
    
    public Play(Level level, PhysicsEngine physics, StickSpring ss, Scene keyChecker, Arrow arrow, Label lb, Circle c1, Pane pane){
        this.level = level;
        this.physics = physics;
        this.ss = ss;
        this.keyChecker = keyChecker;
        this.arrow = arrow;
        this.lb = lb;
        this.c1 = c1;
        this.pane = pane;
    }    
    
    
    public void start(){
        gameTimer.start();
        keyChecker.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()){
                case ESCAPE: if(paused){removePauseMenu();
                                gameTimer.start(); if(animation.getStatus().equals(Status.PAUSED))animation.play(); paused = false;} 
                             else{showPauseMenu();
                                gameTimer.stop(); if(animation.getStatus().equals(Status.RUNNING)) animation.pause(); paused = true;} break;
                case LEFT:
                case A: if(!paused) A = true; break;
                case RIGHT:
                case D: if(!paused) D = true; break;
                case R: ss.setVelocityVec(new Vector(0,0)); ss.setPos(200, 200); ss.setAngle(0); ss.resetHitboxes();
                        arrow.getElements().clear(); arrow.setCoordinates(200, 180, 200, 238.5);
            }
        });
        
        keyChecker.setOnKeyReleased((KeyEvent e) -> {
            switch (e.getCode()){
                case LEFT:
                case A: if(!paused) A = false; break;
                case RIGHT:
                case D: if(!paused) D = false; break;
            }
        });
        
        
        
    }
    
//    public void showMenu(Menu menu){
//        
//    }

    
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
                    case 5: ss.setImage(new Image("Graphics/spring 1.png")); Thread.sleep(2);count = 0; break;
                }
            if(index == 2)
                switch(count){
                    case 0: ss.setImage(new Image("Graphics/spring 2.png")); Thread.sleep(2);count++; break;
                    case 1: ss.setImage(new Image("Graphics/spring 3.png")); Thread.sleep(2);count++; break;
                    case 2: ss.setImage(new Image("Graphics/spring 4.png")); Thread.sleep(2);count++; break;
                    case 3: ss.setImage(new Image("Graphics/spring 3.png")); Thread.sleep(2);count++; break;
                    case 4: ss.setImage(new Image("Graphics/spring 2.png")); Thread.sleep(2);count++; break;
                    case 5: ss.setImage(new Image("Graphics/spring 1.png")); Thread.sleep(2);count = 0; break;
                }
        }
        catch(InterruptedException ex){
            System.out.println("Animation Bug");
        } 
    }
    
    public void setArrow(){
        double xEnd = ss.getVelocityVec().getX()*4, yEnd = ss.getVelocityVec().getY()*4;
        arrow.getElements().clear();
        if(ss.getVelocityVec().getX()*4>60){
            xEnd = 60;
        }    
        else if(ss.getVelocityVec().getX()*4<-60){
            xEnd = -60;
        }    
//        
        if(ss.getVelocityVec().getY()*4>60){
            yEnd = 60;
        }    
        else if(ss.getVelocityVec().getY()*4<-60){
            yEnd = -60;
        }    
        arrow.setCoordinates(200, 180, 200+xEnd, 180+yEnd);
    }
    
    public void ifLevelComplete(Wall wall){

        if(wall instanceof Objective){
            level.setNextLevel(pane);
            ss.reset();    
        }
    }
    
    private ArrayList<Node> pauseMenuList = new ArrayList<>();
    
    public void showPauseMenu(){
        
        ImageView menuPane = new ImageView(new Image("Graphics/wall.png"));
        menuPane.setFitHeight(550);
        menuPane.setFitWidth(600);
        menuPane.setX(325);
        menuPane.setY(75);
        pauseMenuList.add(menuPane);
        
        ImageView exit = new ImageView(new Image("Graphics/Exit Button.png"));
        exit.setX(475);
        exit.setY(440);
        exit.setOnMouseClicked((MouseEvent e) ->System.exit(0));
        pauseMenuList.add(exit);
        
        ImageView mainMenu = new ImageView(new Image("Graphics/Main Menu Button.png"));
        mainMenu.setX(330);
        mainMenu.setY(300);
        mainMenu.setOnMouseClicked((MouseEvent e) ->{
            try{
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene newScene = pane.getScene();
            newScene.setRoot(root);
            newScene.getWindow().setHeight(600);
            newScene.getWindow().setWidth(975);
            newScene.getWindow().centerOnScreen();
            }catch(IOException ex){}
            
        });
        pauseMenuList.add(mainMenu);
        
        ImageView levelSelect = new ImageView(new Image("Graphics/Level Select Button.png"));
        levelSelect.setX(320);
        levelSelect.setY(160);
        levelSelect.setOnMouseClicked((MouseEvent e) ->System.exit(0));
        pauseMenuList.add(levelSelect);
        
        pane.getChildren().addAll(pauseMenuList);
    }
    
    public void removePauseMenu(){
        pane.getChildren().removeAll(pauseMenuList);
        pauseMenuList.clear();
    }
}
