package neoncontrol;
//Joshua Morency
public class PhysicsEngine {
    private final double GRAVITY;
    
    public PhysicsEngine(double weight){
        GRAVITY = weight;
    }
    
    public Vector collisionSide(Vector v, Wall wall){
        Vector v2 = v.add(wall.getNormal().multiplyConstant(v.dot(wall.getNormal()) * -2));
        v2.setX(((int)(v2.getX() * 100))/100.0);
        v2.setY(((int)(v2.getY() * 100))/100.0);
        return v2;
    }
    
    public Vector collisionSpring(Vector v, double angle){
        double x, y;
        x = -Math.sqrt((v.getX() * v.getX()) + (v.getY() * v.getY()) + 2) * Math.cos(angle*(Math.PI/180));
        y = -Math.sqrt((v.getX() * v.getX()) + (v.getY() * v.getY()) + 2) * Math.sin(angle*(Math.PI/180)); //the negative signs might be an issue
        return new Vector((((int)(x * 100)))/100.0, (((int)(y * 100)))/100.0);
    }
    
    public Vector calculateMove(Vector v){
        return v.add(new Vector(0, GRAVITY));
    }
}
