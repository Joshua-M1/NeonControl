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
/**
 *
 * @author addav
 */
public class Level extends ImageView{
    private ArrayList<Wall> wallList = new ArrayList<Wall>();
    int levelCount = 0;
    private ImageView instructions = new ImageView(new Image("Graphics/tutorial.png"));

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
    
    public void setNextLevel(Pane pane){
        pane.getChildren().removeAll(getWallList());
        if(levelCount==0){
            pane.getChildren().remove(instructions);
        }
        
        levelCount++;
        ArrayList<Wall> tempList = new ArrayList<>();
        tempList.addAll(wallList);
        wallList.removeAll(tempList);
        
        
        switch(levelCount){
            case 1: setLevel1(); break;
            case 2: setLevel2(); break;
            default: break;
        }
        setBorders();
        pane.getChildren().addAll(getWallList());
    }
    
    private void setBorders(){
        addWall(new Wall(-700,300,1425,75,90)); //left wall
        addWall(new Wall(Main.stage.getWidth()-1425/2,300,1425,75,270)); //right wall
        addWall(new Wall(-210,-10,2000,75,180)); //top wall
        addWall(new Wall(-210,Main.stage.getHeight()-25,2000,75,0)); //bottom wall
    }
    
    private void setLevel1(){
        addWall(new Wall(400,500,200,50,0));  
        addWall(new Wall(1000,300,200,50,0));
        addWall(new Wall(700,400,200,50,0));
        addWall(new Objective(1100,120,100,100,0));
    }
    
    private void setLevel2(){
        addWall(new Wall(1125,500,100,200,45));
        addWall(new Objective(1100,120,100,100,0));
    }
    
    private void setTutorial(Pane pane){
        addWall(new Objective(900,300,100,100,0));
        pane.getChildren().add(instructions);
    }
}
