package misc;

public enum CalcEnum {
    PLUS,MINUS,MULTIPLY,DIVIDE,EXPONENTIATE,OPEN_BRACKET,CLOSE_BRACKET;

    public int getPriority(){
        if(this == PLUS || this == MINUS){
            return 1;
        }
        else if(this == MULTIPLY || this == DIVIDE){
            return 2;
        }
        else if(this == EXPONENTIATE){
            return 3;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public static CalcEnum getOperation(char c){
        if (c == '+'){
            return CalcEnum.PLUS;
        }
        else if (c == '-'){
            return CalcEnum.MINUS;
        }
        else if (c == '/'){
            return CalcEnum.DIVIDE;
        }
        else if (c == '*'){
            return CalcEnum.MULTIPLY;
        }
        else if (c == '^'){
            return CalcEnum.EXPONENTIATE;
        }
        else if (c == '('){
            return CalcEnum.OPEN_BRACKET;
        }
        else if (c == ')') {
            return CalcEnum.CLOSE_BRACKET;
        }
        throw new IllegalArgumentException("Illegal argument in getOperation.");
    }
}
