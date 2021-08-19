package Raycast3D;

public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2 Rotate(double angle){
        double rx = Math.cos(angle) * x + Math.sin(angle) * y;
        double ry = -Math.sin(angle) * x + Math.cos(angle) * y;
        return new Vector2(rx,ry);
    }

    public Vector2 Add(Vector2 other){
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 Sub(Vector2 other){
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 MultiplyConstant(double constant){
        return new Vector2(x * constant, y * constant);
    }

    public Vector2 DivideConstant(double constant){
        return new Vector2(x/constant, y/constant);
    }

    public Vector2 Round(){
        return new Vector2(Math.floor(x), Math.floor(y));
    }

    public double Length(){
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public String toString(){
        return x + " "  + y;
    }
}
