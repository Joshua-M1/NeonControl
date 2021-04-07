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
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Play{
    private Level level;
    private PhysicsEngine physics;
    private StickSpring ss;
    private final Scene keyChecker;
    private final Arrow arrow;
    private final Pane pane;
    int count = 0;
    private boolean collided = false, paused = false, A = false, D = false;
    EventHandler<ActionEvent> noEvent;
    Timeline animation = new Timeline(new KeyFrame(Duration.millis(0), noEvent));
    private ImageView level1 = new ImageView("Graphics/Level_1.png");
    
//    private static final String MEDIA_URL = "http://sfxcontent.s3.amazonaws.com/soundfx/Spring-Boing.mp3";
//    Media media = new Media(MEDIA_URL);
//    MediaPlayer mediaPlayer = new MediaPlayer(media);
   
    
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
                level.getWallList().forEach((wall) -> {
                    for(int i = 0; i<4; i++){

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
                            setArrow();
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
                            setArrow();
                        }

                        else if(Shape.intersect(ss.getHB3(), wall.getHitboxesList().get(i)).getBoundsInLocal().getWidth() != -1 && !collided){
                            //touching objective
                            ifLevelComplete(wall);

                            ss.setVelocityVec(physics.collisionSide(ss.getVelocityVec(), wall.getNormalVector(i)));
                            collided = true;
                            setArrow();
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
    
    public Play(Level level, PhysicsEngine physics, StickSpring ss, Scene keyChecker, Arrow arrow, Pane pane){
        this.level = level;
        this.physics = physics;
        this.ss = ss;
        this.keyChecker = keyChecker;
        this.arrow = arrow;
        this.pane = pane;
    }    
    
    
    public void start(){
        gameTimer.start();
        keyChecker.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()){
                case ESCAPE: if(paused && !(pane.getChildren().contains(level1))){removePauseMenu();
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
            //mediaPlayer.setAutoPlay(true);  
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
            ss.reset();
        }
    }
    
    private ArrayList<Node> pauseMenuList = new ArrayList<>();
    
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
        
        ImageView menuPane = new ImageView(new Image("Graphics/background level.jpg"));
        menuPane.fitWidthProperty().bind(Main.stage.widthProperty());
        menuPane.fitHeightProperty().bind(Main.stage.heightProperty());
        pane.getChildren().add(menuPane);
        
        ImageView tutorial = new ImageView("Graphics/Level_Tutorial.png");
        tutorial.xProperty().bind(Main.stage.widthProperty().multiply(0.03));
        tutorial.yProperty().bind(Main.stage.heightProperty().multiply(0.05));
        tutorial.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.35));
        tutorial.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.35));
        tutorial.preserveRatioProperty().set(false);  
        tutorial.setOnMouseClicked((MouseEvent e) -> {
            switchLevel(0, menuPane);
        });
        pauseMenuList.add(tutorial);
        
        level1.xProperty().bind(Main.stage.widthProperty().multiply(0.32));
        level1.yProperty().bind(Main.stage.heightProperty().multiply(0.63));
        level1.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.35));
        level1.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.35));
        level1.preserveRatioProperty().set(false);
        level1.setOnMouseClicked((MouseEvent e) -> {
            switchLevel(1, menuPane);
        });
        pauseMenuList.add(level1);
        
        ImageView level2 = new ImageView("Graphics/Level_2.png");
        level2.xProperty().bind(Main.stage.widthProperty().multiply(0.616));
        level2.yProperty().bind(Main.stage.heightProperty().multiply(0.05));
        level2.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.35));
        level2.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.35));
        level2.preserveRatioProperty().set(false);
        level2.setOnMouseClicked((MouseEvent e) -> {
            switchLevel(2, menuPane);
        });
        pauseMenuList.add(level2);
        
        ImageView exitBT = new ImageView("Graphics/Exit Button.png");
        exitBT.xProperty().bind(Main.stage.widthProperty().multiply(0.85));
        exitBT.yProperty().bind(Main.stage.heightProperty().multiply(0.85));
        exitBT.fitWidthProperty().bind(Main.stage.widthProperty().multiply(0.15));
        exitBT.fitHeightProperty().bind(Main.stage.heightProperty().multiply(0.1));
        exitBT.preserveRatioProperty().set(false);
        exitBT.setOnMouseClicked((MouseEvent e) ->System.exit(0));
        pauseMenuList.add(exitBT);
        
        pane.getChildren().addAll(pauseMenuList);    
    }
    
    public void switchLevel(int levelCount, ImageView menuPane){
        pane.getChildren().remove(menuPane);
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
    }
}
