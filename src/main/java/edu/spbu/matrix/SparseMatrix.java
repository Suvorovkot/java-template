package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SparseMatrix  implements Matrix {

    public int size;
    public Map<Integer, row> map;

    public SparseMatrix(Map<Integer, row> m, int size) {
        this.size = size;
        this.map = m;
    }


    public SparseMatrix(int size) {
        this.size = size;
        this.map = new HashMap<>();
    }

    public SparseMatrix(BufferedReader s) {
        try {

            String temp = s.readLine();
            String[] arr = temp.split(" ");
            int k = arr.length;
            int number;
            size = k;
            map = new HashMap<Integer, row>();
            row tmap = new row();

            for (int j = 0; j < size; j++) {
                number = Integer.parseInt(arr[j]);
                if (number != 0) {
                    tmap.put(j, number);
                }
            }
            if ((tmap != null)&&(tmap.size()>0)) {
                map.put(0, tmap);
            }


            for (int i = 1; i < size; i++) {

                temp = s.readLine();
                arr = temp.split(" ");
                tmap = new row();
                for (int j = 0; j < size; j++) {
                    number = Integer.parseInt(arr[j]);
                    if (number != 0) {
                        tmap.put(j, number);
                    }
                }
                if ((tmap != null)&&(tmap.size()>0)) {
                    map.put(i, tmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SparseMatrix SparseTrans() {
        Iterator<Map.Entry<Integer, row>> iter = map.entrySet().iterator(); // получаем строки
        HashMap<Integer, row> matrixTr = new HashMap<Integer, row>();
        while (iter.hasNext()) {
            Map.Entry entry = iter.next();
            Integer keyRow = (Integer) entry.getKey();// получаем номер строки
            HashMap<Integer, row> value = (HashMap<Integer, row>) entry.getValue(); // получаем элементы определенной строки
            Iterator iterRow = value.entrySet().iterator(); // берем по элементно
            while (iterRow.hasNext()) {
                row RowTr = new row();
                Map.Entry entryRow = (Map.Entry) iterRow.next();
                Integer keyElements = (Integer) entryRow.getKey();
                Integer valueElements = (Integer) entryRow.getValue();
                RowTr = matrixTr.get(keyElements);
                if (RowTr == null) {
                    RowTr = new row();
                }
                RowTr.put(keyRow, valueElements);
                matrixTr.put(keyElements, RowTr);
            }

        }
        return new SparseMatrix(matrixTr, size);
    }

    @Override public Matrix mul(Matrix other) {
        if (other instanceof SparseMatrix) return this.multiplicationSS((SparseMatrix) other);
        else return this.multiplicationSD((DenseMatrix) other);
    }



    public SparseMatrix multiplicationSS(SparseMatrix other) {

        SparseMatrix resS = new SparseMatrix(size);
        Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();
        other = other.SparseTrans();
        while (iter1.hasNext()) {
            Map.Entry entry1 = iter1.next();
            Integer key1 = (Integer) entry1.getKey();
            HashMap<Integer, Integer> value1 = (HashMap<Integer, Integer>) entry1.getValue();// строки первой матрицы
            row resRow = new row();
            Iterator<Map.Entry<Integer, row>> iter2 = other.map.entrySet().iterator();
            while (iter2.hasNext()) {
                Map.Entry entry2 = iter2.next();
                Integer key2 = (Integer) entry2.getKey();
                HashMap<Integer, Integer> value2 = (HashMap<Integer, Integer>) entry2.getValue();// строки второй матрицы
                Iterator iterElement = value1.entrySet().iterator();
                int resValue = 0;
                while (iterElement.hasNext()) {
                    Map.Entry entryElement = (Map.Entry) iterElement.next();
                    Integer keyElement1 = (Integer) entryElement.getKey();
                    Integer valueElement1 = (Integer) entryElement.getValue();
                    if (value2.get(keyElement1) != null) {
                        int a = value2.get(keyElement1);
                        resValue = resValue + valueElement1 * a;
                    }
                }
                if (resValue != 0) {
                    resRow.put(key2, resValue);
                }
            }
            if (resRow != null) {
                resS.map.put(key1, resRow);
            }
        }
        return resS;
    }

    public SparseMatrix multiplicationSD(DenseMatrix other) {
        SparseMatrix res = new SparseMatrix(size);
        other = other.SparseTrans();
        int[][] a = other.matrix;
        Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();// итератор спарс матрицы
        while (iter1.hasNext()) {
            Map.Entry entry1 = iter1.next();
            Integer key1 = (Integer) entry1.getKey();
            HashMap<Integer, Integer> value1 = (HashMap<Integer, Integer>) entry1.getValue();// получаем определенную строку
            row resRow = new row();
            for (int i = 0; i < size; i++) {
                int resValue = 0;
                Iterator iterElement = value1.entrySet().iterator(); // получаем элементы определенной строки
                while (iterElement.hasNext()) {
                    Map.Entry entryElement = (Map.Entry) iterElement.next();
                    Integer keyElement = (Integer) entryElement.getKey();// столбец элемента
                    Integer valueElement = (Integer) entryElement.getValue();// сам элемент
                    if (other.matrix[i][keyElement] != 0) {
                        resValue = resValue + valueElement * a[i][keyElement];
                    }
                }
                if (resValue != 0) {
                    resRow.put(i, resValue);
                }
            }
            if (resRow != null) {
                res.map.put(key1, resRow);
            }

        }

        return res;
    }
    /*public SparseMatrix multiplicationDS(DenseMatrix other) {
        SparseMatrix res = new SparseMatrix(size);
        other = other.SparseTrans();
        int[][] a = other.matrix;
        for (int i = 0; i < size; i++) {
        Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();// итератор спарс матрицы
        while (iter1.hasNext()) {
            Map.Entry entry1 = iter1.next();
            Integer key1 = (Integer) entry1.getKey();
            int resValue = 0;
            HashMap<Integer, Integer> value1 = (HashMap<Integer, Integer>) entry1.getValue();// получаем определенную строку
            row resRow = new row();
                Iterator iterElement = value1.entrySet().iterator(); // получаем элементы определенной строки
                while (iterElement.hasNext()) {
                    Map.Entry entryElement = (Map.Entry) iterElement.next();
                    Integer keyElement = (Integer) entryElement.getKey();// столбец элемента
                    Integer valueElement = (Integer) entryElement.getValue();// сам элемент
                    if (other.matrix[i][keyElement] != 0) {
                        resValue = resValue + valueElement * a[i][keyElement];
                    }
                }
                if (resValue != 0) {
                    resRow.put(i, resValue);
                }
            }
            if (resRow != null) {
                res.map.put(key1, resRow);
            }

        }

        return res;
    }
    */

    public void printS(BufferedWriter matrix) {
        try {
            int e;
            for (int i = 0; i < size; i++) {
                row a = map.get(i);
                if (a != null) {
                    for (int j = 0; j < size; j++) {
                        if (a.get(j) != null) {
                            e = a.get(j);
                            matrix.write(e + " ");
                        } else {
                            matrix.write("0" + " ");
                        }
                    }
                    matrix.write("\n");

                } else {
                    for (int j = 0; j < size; j++) {
                        matrix.write("0 ");
                    }
                    matrix.write("\n");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }








    /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */

  @Override public Matrix dmul(Matrix o,int threadNumber) {
      return null;
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
      DenseMatrix oth = ((DenseMatrix) o);
      int [][] b = oth.matrix;
      for (int i = 0; i < size; i++) {
          row a = map.get(i);
          if (a != null)
          {
              for (int j = 0; j < size; j++) {
                  if (a.get(j) != null && b[i][j] != 0) {
                      if(b[i][j] != a.get(j))
                      {   System.out.print(b[i][j]+' '+a.get(j)+'\n');
                          return false;}

                  }
              }
          }
          else for (int j = 0; j < size; j++) {
              if (b[i][j] != 0)
              {   System.out.print(b[i][j]+'\n');
                  return false;}
          }
      }
      return true;
  }
}
