/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neoncontrol;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/**
 *
 * @author addav
 */
public class Level extends ImageView{
    private ArrayList<Wall> wallList = new ArrayList<Wall>();
    int levelCount = 0;
    private ImageView instructions = new ImageView(new Image("Graphics/tutorial.png"));
    private double stageWidth = Main.stage.getWidth();
    private double stageHeight = Main.stage.getHeight();

    public Level(Pane pane) {
        setBorders();
        setTutorial(pane);
        this.setImage(new Image("Graphics/background level.jpg"));
    }

    public Level(ArrayList<Wall> wallList, Image background) {
        this.wallList = wallList;
        setImage(background);
        setBorders();
    }
    
    public void setWallList(ArrayList<Wall> wallList){
        this.wallList = wallList;
    }
    
    public void setBackground(Image background){
        setImage(background);
    }
    
    public void addWall(Wall w){
        wallList.add(w);
    }

    public ArrayList<Wall> getWallList() {
        return wallList;
    }

    public Image getBackground() {
        return this.getImage();
    }
    
    public Wall getWall(int index){
        return wallList.get(index);
    }
    
    public int getCount(){
        return levelCount;
    }
    
    public void setNextLevel(Pane pane, int count){
        pane.getChildren().removeAll(getWallList());
        if(levelCount==0){
            pane.getChildren().remove(instructions);
        }
        
        levelCount++;
        for(Wall wall : getWallList())
            pane.getChildren().removeAll(wall.getHitboxesList());
        ArrayList<Wall> tempList = new ArrayList<>();
        tempList.addAll(wallList);
        wallList.removeAll(tempList);
        
        if(count != -1){
            levelCount = count;
        }
        
        switch(levelCount){
            case 0: setTutorial(pane); break;
            case 1: setLevel1(); break;
            case 2: setLevel2(); break;
            case 3: setLevel3(); break;
            case 4: setLevel4(); break;
            case 5: setLevel5(); break;
            case 6: setLevel6(); break;
            default: break; 
        }
        setBorders();
        pane.getChildren().addAll(getWallList());
        for(Wall wall : getWallList())
            pane.getChildren().addAll(wall.getHitboxesList());
    }
    
    private void setBorders(){
        addWall(new Wall(-stageWidth*0.456,stageHeight*0.347,stageWidth*0.93,stageHeight*0.1,90)); //left wall
        addWall(new Wall(stageWidth*0.536,stageHeight*0.347,stageWidth*0.93,stageHeight*0.1,270)); //right wall
        addWall(new Wall(-stageWidth*0.137,stageHeight*-0.015,stageWidth*1.3,stageHeight*0.1,180)); //top wall
        addWall(new Wall(-stageWidth*0.137,stageHeight-25,stageWidth*1.3,stageHeight*0.1,0)); //bottom wall
    }
    
    private void setLevel1(){
        addWall(new Wall(stageWidth*0.25,stageHeight*0.85,stageWidth*0.13,stageHeight*0.07,0));  
        addWall(new Wall(stageWidth*0.65,stageHeight*0.45,stageWidth*0.13,stageHeight*0.07,0));
        addWall(new Wall(stageWidth*0.45,stageHeight*0.65,stageWidth*0.13,stageHeight*0.07,0));
        addWall(new Objective(stageWidth*0.7,stageHeight*0.3,stageWidth*0.07,stageWidth*0.07,0));
    }
    
    private void setLevel2(){
        addWall(new Wall(stageWidth*0.89,stageHeight*0.77,stageWidth*0.13,stageHeight*0.35,45));
        addWall(new Objective(stageWidth*0.9,stageHeight*0.125,stageWidth*0.07,stageWidth*0.07,0));
    }
    
    private void setLevel3(){
        addWall(new Wall(stageWidth*0.1,stageHeight*0.1,stageWidth*0.46,stageWidth*0.07,90));
        addWall(new Wall(stageWidth*0.3,stageHeight*0.9,stageWidth*0.46,stageWidth*0.07,270));
        addWall(new Wall(stageWidth*0.5,stageHeight*0.1,stageWidth*0.46,stageWidth*0.07,90));
        addWall(new Objective(stageWidth*0.80,stageHeight*0.30,stageWidth*0.07,stageWidth*0.07,0));
    }
    
    private void setLevel4(){
        addWall(new Wall(stageWidth*0.68,stageHeight*0.55,stageWidth*0.13,stageHeight*0.07,0));
        addWall(new Wall(stageWidth*0.88,stageHeight*0.55,stageWidth*0.13,stageHeight*0.07,0));
        addWall(new Wall(stageWidth*0.78,stageHeight*0.25,stageWidth*0.13,stageHeight*0.07,0));
        addWall(new Wall(stageWidth*0.15,stageHeight*0.8,stageWidth*0.59,stageWidth*0.07,330));
        addWall(new Objective(stageWidth*0.81,stageHeight*0.75,stageWidth*0.07,stageWidth*0.07,0));
    }
    
    private void setLevel5(){
        addWall(new Wall(stageWidth*0.25,stageHeight*0.4,stageWidth*0.5,stageHeight*0.15,45));
        addWall(new Wall(stageWidth*0.25,stageHeight*0.40,stageWidth*0.5,stageHeight*0.15,135));
        addWall(new Objective(stageWidth*0.7,stageHeight*0.4,stageWidth*0.07,stageWidth*0.07,0));
    }
    
    private void setLevel6(){
        addWall(new Wall(stageWidth*0.53,stageHeight*0.51,stageWidth*0.15,stageHeight*0.07,90));
        addWall(new Wall(stageWidth*0.395,stageHeight*0.51,stageWidth*0.15,stageHeight*0.07,90));
        addWall(new Wall(stageWidth*0.451,stageHeight*0.599,stageWidth*0.173,stageHeight*0.08,0));
        addWall(new Objective(stageWidth*0.504,stageHeight*0.45,stageWidth*0.07,stageWidth*0.07,0));
    }
    
    private void setLevel7(){
        addWall(new Wall(stageWidth*0.15,stageHeight*0.80,600,150,90));
        addWall(new Objective(stageWidth*0.7,stageHeight*0.35,stageWidth*0.07,stageWidth*0.07,0));
    }
    private void setLevel8(){
        addWall(new Wall(stageWidth*0.20,stageHeight*0.81,200,700,90));
        addWall(new Objective(stageWidth*0.80,stageHeight*0.30,100,100,0));
    }
    
    private void setLevel9(){
        addWall(new Wall(stageWidth*0.20,stageHeight*0.81,200,700,90));
        addWall(new Objective(stageWidth*0.80,stageHeight*0.30,100,100,0));
    }
    
    private void setLevel10(){
        addWall(new Wall(stageWidth*0.20,stageHeight*0.81,200,700,90));
        addWall(new Objective(stageWidth*0.80,stageHeight*0.30,100,100,0));
    }
    
    
    private void setTutorial(Pane pane){
        addWall(new Objective(stageWidth*0.7,stageHeight*0.4,stageWidth*0.07,stageWidth*0.07,0));
        pane.getChildren().add(instructions);
    }
}
