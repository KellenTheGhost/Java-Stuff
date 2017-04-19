/* Kellen Parker
 * CS-1181L
 * Instructor M. Cheatham
 * Project 4
 */
package project4_parker;

import java.util.Arrays;

public class Sort implements Runnable{

    private String sort;
    private int[] arr;
    
    public Sort(String sort, int[] arr){
        this.sort = sort;
        this.arr = arr;
    }
    
    @Override
    public void run() {
        switch (sort) {
            case "Selection":
                selectionSort(arr);
                break;
            case "Bubble":
                bubbleSort(arr);
                break;
            case "Insertion":
                insertionSort(arr);
                break;
            case "Quick":
                quickSort(arr,0,arr.length-1);
                break;
            case "Merge":
                merge();
                break;
        }
        Project4_Parker.queue(arr);
    }        
    
    public static void main(String[] args) {
        int[] arr = {0,20,2,3,14,5,91,7,8};
        Sort.quickSort(arr, 0, arr.length-1);
    }
    
    public static void quickSort(int[] arr, int start, int end) {
        if(end - start <= 1){
            return;
        }
        int high = end;
        int low = start;
        int pointer = arr[low+(high-low)/2];
        while(low < high){
            while(arr[low] < pointer){
                low++;
            }
            while(arr[high] > pointer){
                high--;
            }
            if(low < high){
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
                low++;
                high--;
            }
        }
        if(start != low){
            quickSort(arr,start, high);
        }
        if(high != end){
            quickSort(arr,low,end);
        }
    }

    public static void selectionSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = 0;
            for (int j = 1; j <= i; j++) {
                if (arr[j] > arr[index]) {
                    index = j;
                }
            }
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < (arr.length - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j >= 1; j--) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void merge() {
        int[] arr1 = Project4_Parker.poll();
        int[] arr2 = Project4_Parker.poll();
        int pointer1 = 0;
        int pointer2 = 0;
        int[] arr = new int[arr1.length + arr2.length];
        for (int i = 0; i < arr.length; i++) {
            if (pointer1 >= arr1.length && pointer2 < arr2.length) {
                arr[i] = arr2[pointer2];
                pointer2++;
                continue;
            }
            if (pointer2 >= arr2.length && pointer1 < arr1.length) {
                arr[i] = arr1[pointer1];
                pointer1++;
                continue;
            }

            if (arr1[pointer1] < arr2[pointer2]) {
                arr[i] = arr1[pointer1];
                pointer1++;
            } else {
                arr[i] = arr2[pointer2];
                pointer2++;
            }
        }
        
    }

    
}
