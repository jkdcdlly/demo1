package com.jsouptest;

/**
 * Created by chenzhilei on 15/9/30.
 */

class Quick {
    public static int sort(int[] arr, int i, int j) {
        int key = arr[i];
        while (i < j) {
            while (i < j && key < arr[j])
                j--;
            arr[i] = arr[j];
            while (i < j && key > arr[i])
                i++;
            arr[j] = arr[i];
        }
        arr[i] = key;
        return i;
    }

    public static void quick(int[] arr) {
        int per = sort(arr,0,arr.length-1);
        sort(arr,per+1,arr.length-1);
        sort(arr,0,per-1);
    }

    public static void main(String[] args) {
        int[] array = {3, 1, 2};
        quick(array);
    }
}



