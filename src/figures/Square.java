package figures;

import util.Util;

public class Square extends Shape{

    int length;

    public Square(int len){
        length = len;
        Util.print("Создан квадрат со стороной " + length + ".");
    }

    public double calculateArea(){
        return length * length;
    }

    public double calculatePerimeter(){
        return length * 4;
    }
}
