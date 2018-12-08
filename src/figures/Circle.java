package figures;

import java.lang.Math;

import misc.Parameter;
import util.Util;

public class Circle extends Shape{

    int radius;

    public int getParameter(Parameter par){
        if(par == Parameter.X){
            return x;
        }
        else if(par == Parameter.Y){
            return y;
        }
        else{
            return radius;
        }
    }

    public Circle(){
        Util.print("In Circle");
        radius = 0;
    }

    public Circle(int xipt, int yipt, int radio){
        Util.print("In Circle-2");
        radius = radio;
        x = xipt;
        y = yipt;
    }

    public double calculateArea(){
        return Math.PI * radius * radius;
    }

    public double calculatePerimeter(){
        return Math.PI * radius * 2;
    }

    public String toString() {
        return "class Circle: X - " + x + ". Y - " + y + ". Radius - " + radius + ".";
    }

    public boolean equals1(Circle circl) {
        if(circl.radius == radius && circl.x == x && circl.y == y){
            return true;
        }
        else{
            return false;
        }
    }
}
