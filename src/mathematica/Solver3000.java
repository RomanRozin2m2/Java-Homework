package mathematica;

public class Solver3000 {

    public double solve(double num, double result, Oper operation ){
        if(operation == Oper.ADDITION){
            return result - num;
        }
        else if(operation == Oper.SUBSTRACTION){
            return result + num;
        }
        else if(operation == Oper.MULTIPLICATION){
            return result / num;
        }
        else{
            return result * num;
        }
    }
}
