package java_training;

import java.util.Arrays;

interface Filter {
    void sort(int number[]);
}

public class Lambdasorting {

    public static void main(String[] args) {
        int number[] = {10, 9, 3, 7, 2, 19, 44, 17};

       
        Filter fil = (int arr[]) -> {
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = 0; j < arr.length - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        };

       
        fil.sort(number);

       
        System.out.println("Sorted Array: " + Arrays.toString(number));
    }
}
