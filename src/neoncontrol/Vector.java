package neoncontrol;
//Joshua Morency
public class Vector {
    private double x, y;
    
    public Vector(){
        x = 0;
        y = 0;
    }
    
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Vector(Vector v){
        this.x = v.getX();
        this.y = v.getY();
    }
    
    public void setVectorAlternate(double magnitude, double angle){
        x = ((int)(100*magnitude*(Math.cos(angle*Math.PI/180))))/100.0;
        y = ((int)(100*-magnitude*(Math.sin(angle*Math.PI/180))))/100.0;
    }
    
    public void setX(double x){
        this.x = x;
    }
    
    public void setY(double y){
        this.y = y;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    
    public Vector add(Vector v2){
        return new Vector(x + v2.getX(), y + v2.getY());
    }
    
    public Vector multiplyConstant(double c){
        return new Vector(x*c, y*c);
    }
    
    public double dot(Vector v2){
        return x*v2.getX() + y*v2.getY();
    }
    
    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
