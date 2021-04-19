package neoncontrol;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.shape.*;
import java.io.*;
import javafx.scene.control.Label;

public class Play{
    private Level level;
    private PhysicsEngine physics;
    private StickSpring ss;
    private final Scene keyChecker;
    private final Arrow arrow;
    private final Pane pane;
    private int count = 0;
    private boolean collided = false, paused = false, A = false, D = false;
    private Label bounceCounter;
    private EventHandler<ActionEvent> noEvent;
    private Timeline animation = new Timeline(new KeyFrame(Duration.millis(0), noEvent));    
    public static MediaPlayer boing = new MediaPlayer(new Media(new File("src/Audio/Spring-Boing.mp3").toURI().toString())), 
                              tap = new MediaPlayer(new Media(new File("src/Audio/Tap.m4a").toURI().toString())), 
                              ding = new MediaPlayer(new Media(new File("src/Audio/Ding.mp3").toURI().toString())), 
                              BGM = new MediaPlayer(new Media(new File("src/Audio/HOME - Resonance.mp3").toURI().toString()));
    private ArrayList<Node> pauseMenuList = new ArrayList<>();
    
    private AnimationTimer gameTimer = new AnimationTimer() {
        @Override
        public void handle (long l){
            collided = false;
            if(A){
                ss.setAngle(ss.getAngle() - 3);
            }
            if(D){
                ss.setAngle(ss.getAngle() + 3);
            }
            try{
                setArrow();
                level.getWallList().forEach((wall) -> {
                    for(int i = 0; i<4; i++){
                        if(ss.getXPos()<0 || ss.getXPos()>Main.stage.getWidth() || ss.getYPos()<0 || ss.getYPos()>Main.stage.getHeight()){
                            ss.reset();
                        }
                        if(Shape.intersect(ss.getHB2(), wall.getHitboxesList().get(i)).getBoundsInLocal().getWidth() != -1 && Shape.intersect(ss.getHB1(), wall.getHitboxesList().get(i)).getBoundsInLocal().getWidth() == -1 && !collided){
                            //touching objective
                            ifLevelComplete(wall);

                            ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 90));
                            collided = true;
                            EventHandler<ActionEvent> eventHandler = e -> {runAnimation(ss, 1);                    
                            };  
                            animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
                            animation.setCycleCount(6);
                            animation.play();
                            
                            if(!(wall instanceof Objective)){
                                boing.seek(Duration.ZERO);
                                boing.play();
                                setBounceCounter();
                            }
                            else{
                                ding.seek(Duration.ZERO);
                                ding.play();
                            }   
                        }

                        else if(Shape.intersect(ss.getHB1(), wall.getHitboxesList().get(i)).getBoundsInLocal().getWidth() != -1 && Shape.intersect(ss.getHB2(), wall.getHitboxesList().get(i)).getBoundsInLocal().getWidth() == -1 && !collided){
                            //touching objective
                            ifLevelComplete(wall);

                            ss.setVelocityVec(physics.collisionSpring(ss.getVelocityVec(), ss.getAngle() + 270));
                            collided = true;
                            EventHandler<ActionEvent> eventHandler = e -> { runAnimation(ss, 2);
                            };  
                            animation = new Timeline(new KeyFrame(Duration.millis(40), eventHandler));
                            animation.setCycleCount(6);
                            animation.play();
                            
                            if(!(wall instanceof Objective)){
                                boing.seek(Duration.ZERO);
                                boing.play();
                                setBounceCounter();
                            }
                            else{
                                ding.seek(Duration.ZERO);
                                ding.play();
                            } 
                            
                        }
                        
                        else if(Shape.intersect(ss.getHB3(), wall.getHitboxesList().get(i)).getBoundsInLocal().getWidth() != -1 && !collided
                                && compareAngles(ss, wall.getHitboxesList().get(i))){
                            //touching objective
                            ifLevelComplete(wall);
                            ss.setVelocityVec(physics.collisionSide(ss.getVelocityVec(), wall.getNormalVector(i)));
                            collided = true;
                            if(!(wall instanceof Objective)){
                                tap.seek(Duration.ZERO);
                                tap.play();
                                setBounceCounter();
                            }
                            else{
                                ding.seek(Duration.ZERO);
                                ding.play();
                            } 
                        }
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
    
    public Play(Level level, PhysicsEngine physics, StickSpring ss, Scene keyChecker, Arrow arrow, Label bounceCounter, Pane pane){
        this.level = level;
        this.physics = physics;
        this.ss = ss;
        this.keyChecker = keyChecker;
        this.arrow = arrow;
        this.pane = pane;
        this.bounceCounter = bounceCounter;
        
    }    
    
    public void start(){
        tap.setRate(1.125);
        ding.setRate(1.75);
        gameTimer.start();
        keyChecker.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()){
                case ESCAPE: if(paused /**&& !(pane.getChildren().contains(level1))**/){removePauseMenu();
                                gameTimer.start(); if(animation.getStatus().equals(Status.PAUSED))animation.play(); paused = false;} 
                             else{showPauseMenu();
                                gameTimer.stop(); if(animation.getStatus().equals(Status.RUNNING)) animation.pause(); paused = true;} break;
                case LEFT:
                case A: if(!paused) A = true; break;
                case RIGHT:
                case D: if(!paused) D = true; break;
                case R: ss.reset(); arrow.getElements().clear(); arrow.setCoordinates(200, 180, 200, 238.5);
            }
        });
        
        keyChecker.setOnKeyReleased((KeyEvent e) -> {
            switch (e.getCode()){
                case LEFT:
                case A: if(!paused) A = false; break;
                case RIGHT:
                case D: if(!paused) D = false; break;
                //Testing purposes only, remove before submitting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                case N: 
                    if(level.getCount() != 11)
                        switchLevel(level.getCount()+1);
            }
        });
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
        arrow.setVisible(true);
        double xEnd = ss.getVelocityVec().getX()*4, yEnd = ss.getVelocityVec().getY()*4;
        arrow.getElements().clear();
        if(ss.getVelocityVec().getX()*4>60){
            xEnd = 60;
        }    
        else if(ss.getVelocityVec().getX()*4<-60){
            xEnd = -60;
        }
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
            level.setNextLevel(pane, -1);
            pane.getChildren().remove(ss);
            pane.getChildren().remove(bounceCounter);
            ss.reset();
            pane.getChildren().add(ss);
            pane.getChildren().add(bounceCounter);
        }
    }
    
    public void showPauseMenu(){
        
        ImageView menuPane = new ImageView(new Image("Graphics/wall.png"));
        menuPane.setFitHeight(550);
        menuPane.setFitWidth(600);
        menuPane.setX(Main.scene.getWidth()*0.5 - 325);
        menuPane.setY(Main.scene.getHeight()*0.5 - 250);
        pauseMenuList.add(menuPane);
        
        ImageView exit = new ImageView(new Image("Graphics/Exit Button.png"));
        exit.setX(menuPane.getX() + 150);
        exit.setY(menuPane.getY() + 400);
        exit.setOnMouseClicked((MouseEvent e) ->System.exit(0));
        pauseMenuList.add(exit);
        
        ImageView mainMenu = new ImageView(new Image("Graphics/Main Menu Button.png"));
        mainMenu.setX(menuPane.getX());
        mainMenu.setY(menuPane.getY() + 250);
        mainMenu.setOnMouseClicked((MouseEvent e) ->{
            try{
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene newScene = pane.getScene();
            newScene.setRoot(root);
            
            }catch(IOException ex){}   
        });
        pauseMenuList.add(mainMenu);
        
        ImageView levelSelect = new ImageView(new Image("Graphics/Level Select Button.png"));
        levelSelect.setX(menuPane.getX() - 5);
        levelSelect.setY(menuPane.getY() + 100);
        levelSelect.setOnMouseClicked((MouseEvent e) ->{
            showLevelSelectMenu();
        });
        pauseMenuList.add(levelSelect);
        
        pane.getChildren().addAll(pauseMenuList);
    }
    
    public void removePauseMenu(){
        pane.getChildren().removeAll(pauseMenuList);
        pauseMenuList.clear();
    }
    
    public void showLevelSelectMenu(){
        removePauseMenu();
        //
        ImageView menuPane = new ImageView(new Image("Graphics/wall.png"));
        menuPane.setFitHeight(550);
        menuPane.setFitWidth(600);
        menuPane.setX(Main.scene.getWidth()*0.5 - 325);
        menuPane.setY(Main.scene.getHeight()*0.5 - 250);
        pauseMenuList.add(menuPane);
        
        pauseMenuList.addAll(createLevelList(menuPane));
        
        ImageView mainMenu = new ImageView(new Image("Graphics/Main Menu Button.png"));
        mainMenu.setX(menuPane.getX());
        mainMenu.setY(menuPane.getY() + 300);
        mainMenu.setOnMouseClicked((MouseEvent e) ->{
            try{
                Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                Scene newScene = pane.getScene();
                newScene.setRoot(root);
            }catch(IOException ex){}   
        });
        pauseMenuList.add(mainMenu);
        
        ImageView exit = new ImageView(new Image("Graphics/Exit Button.png"));
        exit.setX(menuPane.getX() + 150);
        exit.setY(menuPane.getY() + 400);
        exit.setOnMouseClicked((MouseEvent e) ->System.exit(0));
        pauseMenuList.add(exit);
        
        pane.getChildren().addAll(pauseMenuList);    
    }
    
    public ArrayList<ImageView> createLevelList(ImageView menuPane){
        ArrayList<ImageView> levelList = new ArrayList<>();
        levelList.add(new ImageView(new Image("Graphics/1.png")));
        levelList.get(0).setOnMouseClicked((MouseEvent e) ->switchLevel(1));
        levelList.add(new ImageView(new Image("Graphics/2.png")));
        levelList.get(1).setOnMouseClicked((MouseEvent e) ->switchLevel(2));
        levelList.add(new ImageView(new Image("Graphics/3.png")));
        levelList.get(2).setOnMouseClicked((MouseEvent e) ->switchLevel(3));
        levelList.add(new ImageView(new Image("Graphics/4.png")));
        levelList.get(3).setOnMouseClicked((MouseEvent e) ->switchLevel(4));
        levelList.add(new ImageView(new Image("Graphics/5.png")));
        levelList.get(4).setOnMouseClicked((MouseEvent e) ->switchLevel(5));
        levelList.add(new ImageView(new Image("Graphics/6.png")));
        levelList.get(5).setOnMouseClicked((MouseEvent e) ->switchLevel(6));
        levelList.add(new ImageView(new Image("Graphics/7.png")));
        levelList.get(6).setOnMouseClicked((MouseEvent e) ->switchLevel(7));
        levelList.add(new ImageView(new Image("Graphics/8.png")));
        levelList.get(7).setOnMouseClicked((MouseEvent e) ->switchLevel(8));
        levelList.add(new ImageView(new Image("Graphics/9.png")));
        levelList.get(8).setOnMouseClicked((MouseEvent e) ->switchLevel(9));
        levelList.add(new ImageView(new Image("Graphics/10.png")));
        levelList.get(9).setOnMouseClicked((MouseEvent e) ->switchLevel(10));
        
        for(int i = 0; i < 10; i++){
            levelList.get(i).setX(menuPane.getX()+(i%5)*100+75);
            levelList.get(i).setY(menuPane.getY()+(i/5)*100+100);
        }
        
        return levelList;
    }
    
    
    public void switchLevel(int levelCount){
        pane.getChildren().remove(bounceCounter);
        removePauseMenu();
        level.setNextLevel(pane, levelCount);
        ss.reset();
        ss.setViewOrder(-1);
        gameTimer.start();
        animation.play();
        paused = false;
        ss.setViewOrder(-1);
        A = false;
        D = false;
        pane.getChildren().add(bounceCounter);
    }
    
    public static boolean compareAngles(StickSpring ss, Rectangle r){
        double sa = (ss.getAngle()+90)%180, ra = r.getRotate();
        boolean rollOver = false;
        if(sa<0)
            sa += 180;
        double raUpper = (ra+45)%180, raLower = (ra-45)%180;
        
        if(raLower<=0){
            raLower += 180;
            rollOver = true;
        }
        
        if(ra+45>=180)
            rollOver = true;
        
        if(!rollOver)
            return sa<=raUpper && sa>=raLower;
        else
            return sa<=raUpper || sa>=raLower;
    }
    
    public void setBounceCounter(){
        String text = bounceCounter.getText();
        String[] textParts = text.split("\t");
        int bounces = Integer.parseInt(textParts[1]);
        bounces++;
        bounceCounter.setText("Bounce count:\t"+bounces);
    }
}
