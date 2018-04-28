package misc;

public class Comparator {
    public boolean compare(Equality eq, int num1, int num2){
        if(eq == Equality.EQ){
            return num1 == num2;
        }
        else if(eq == Equality.NE){
            return num1 != num2;
        }
        else if(eq == Equality.GT){
            return num1 > num2;
        }
        else if(eq == Equality.GE){
            return num1 >= num2;
        }
        else if(eq == Equality.LT){
            return num1 < num2;
        }
        else{
            return num1 <= num2;
        }
    }
}
