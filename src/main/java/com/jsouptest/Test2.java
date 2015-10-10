package com.jsouptest;

/**
 * Created by chenzhilei on 15/9/27.
 */
public class Test2 {

    public static void bubble(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int tmp;
                if (array[i] > array[j]) {
                    tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }

        for (int i = 0; i < array.length; i++) {
            int i1 = array[i];
            System.out.println(i1);
        }
        System.out.println("===");
    }


    public static void select(int[] array) {

        int max = 0;
        int tmp = 0;
        for (int i = 0; i < array.length; i++) {
            max = i;//
            for (int j = i + 1; j < array.length; j++) {
                if (array[max] < array[j]) {
                    max = j;//记下较大数位置，再次比较，直到最大
                }
            }
            if (i != max) {
                tmp = array[i];
                array[i] = array[max];
                array[max] = tmp;
            }
        }
        for (int i = 0; i < array.length; i++)
            System.out.println(array[i] + " ");
    }

    public static void main(String[] args) {
        int[] array = {25, 15, 42, 16, 12, 36};
        select(array);
    }
}
