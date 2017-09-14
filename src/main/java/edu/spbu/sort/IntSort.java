package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class IntSort {
  private static void swap(int[] array, int i, int j){
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }
  public static void heapify(int[] array, int size, int pos) {
    while (2 * pos + 1 < size) {
      int t = 2 * pos + 1;
      if (2 * pos + 2 < size && array[2 * pos + 1] < array[2 * pos + 2]) {
        t = 2 * pos + 2;
      }
      if (array[pos] < array[t]) {
        swap(array, pos, t);
        pos = t;
      } else {
        break;
      }
    }
  }
  public static int[] heapMaker(int[] array)
    {
      int n = array.length;
      for (int i = n - 1; i >= 0; i--) {
        heapify(array, n, i);
      }
      return array;
    }

  public static void sort (int array[]) {
    int n = array.length;
    heapMaker(array);
    while(n>0){
      swap(array,0, n-1);
      n--;
      heapify(array, n, 0);
    }
  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}
