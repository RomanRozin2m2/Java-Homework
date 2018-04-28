package misc;

import java.util.Random;

public class Sorter {

    public static void BubbleSort(int[] massive) {
        for (int i = 0; i < massive.length - 1; i++) {
            for (int j = 0; j < massive.length - 1; j++) {
                if (massive[j] > massive[j + 1]) {
                    int tw = massive[j + 1];
                    int on = massive[j];
                    massive[j] = tw;
                    massive[j + 1] = on;
                }
            }
        }
    }

    private static int[] QuickSorter(int[] massive) {
        if (massive.length <= 1 && massive.length >= 0) {
            return massive;
        }
        else {
            System.out.println(massive.length);

            int r = massive[new Random().nextInt(massive.length)];

            int countLeft = 0;
            int countRight = 0;
            int countMiddle = 0;
            int leftIndex = 0;
            int rightIndex = 0;
            int middleIndex = 0;

            for (int f : massive) {
                if (f < r) {
                    countLeft++;
                }
                else if(f == r){
                    countMiddle++;
                }
                else {
                    countRight++;
                }
            }

            System.out.println(countLeft + " - " + countMiddle + " - " + countRight);

            int[] substringOne = new int[countLeft];
            int[] substringTwo = new int[countRight];
            int[] substringThree = new int[countMiddle];

            for (int f : massive) {
                if (f < r) {
                    substringOne[leftIndex] = f;
                    leftIndex++;
                }
                else if (f == r){
                    substringThree[middleIndex] = f;
                    middleIndex++;
                }
                else {
                    substringTwo[rightIndex] = f;
                    rightIndex++;
                }
            }

            for(int j = 0; j < substringOne.length; j++){
                System.out.print(substringOne[j] + ", ");
            }
            System.out.println(".");
            for(int j = 0; j < substringThree.length; j++){
                System.out.print(substringThree[j] + ", ");
            }
            System.out.println(".");
            for(int j = 0; j < substringTwo.length; j++){
                System.out.print(substringTwo[j] + ", ");
            }
            System.out.println(".");

            int[] sorted = new int[massive.length];
            int[] sortedOne = QuickSorter(substringOne);
            int[] sortedTwo = QuickSorter(substringTwo);
            int index = 0;

            for (int f : sortedOne) {
                sorted[index] = f;
                index++;
            }

            for (int f : substringThree) {
                sorted[index] = f;
                index++;
            }

            for (int f : sortedTwo) {
                sorted[index] = f;
                index++;
            }

            return sorted;
        }
    }

    public static void QuickSort(int[] massive){
        int[] newMassive = QuickSorter(massive);
        for (int f = 0; f < massive.length; f++){
            massive[f] = newMassive[f];
        }
    }
}
