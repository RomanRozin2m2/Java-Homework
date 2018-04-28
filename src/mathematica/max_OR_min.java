package mathematica;

public class max_OR_min {

    public int max(int[] massive){
        int maxNum = massive[0];
        int tekNum = 0;
        for (int foest = 0; foest != massive.length; foest++){
            tekNum = massive[foest];
            if(tekNum > maxNum){
                maxNum = tekNum;
            }
        }
        return maxNum;
    }

    public int min(int[] massive){
        int minNum = massive[0];
        int tekNum = 0;
        for (int foest = 0; foest != massive.length; foest++){
            tekNum = massive[foest];
            if(tekNum < minNum){
                minNum = tekNum;
            }
        }
        return minNum;
    }

}
