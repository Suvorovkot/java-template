package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;




public class DenseMatrix implements Matrix {
  public int size;
  public int matrix[][];
  public Map<Integer, row> map;

  public DenseMatrix(int[][] matrix, int size) {
    this.size = size;
    this.matrix = matrix;
  }

  public DenseMatrix(int size) {
    this.matrix = new int[size][size];
    this.size = size;
  }

  public DenseMatrix(BufferedReader s) {
    try {
      String t = s.readLine();
      //System.out.println(t);
      String[] array = t.split(" ");
      int k = array.length;
      this.size = k;
      this.matrix = new int[size][size];
      int number;

      for (int j = 0; j < k; j++) {
        number = Integer.parseInt(array[j]);
        this.matrix[0][j] = number;
      }

      for (int i = 1; i < k; i++) {
        t = s.readLine();
        array = t.split(" ");

        for (int j = 0; j < k; j++) {
          number = Integer.parseInt(array[j]);
          this.matrix[i][j] = number;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Matrix mul(Matrix o)
  {
      if (o instanceof DenseMatrix) return this.multiplicationDD((DenseMatrix) o);
      else return this.multiplicationDS((SparseMatrix) o);
  }

    public DenseMatrix multiplicationDD(DenseMatrix o) {
        DenseMatrix res = new DenseMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    res.matrix[i][j] += this.matrix[i][k] * o.matrix[j][k];
                }
            }
        }
        return res;
    }


    public SparseMatrix multiplicationDS(SparseMatrix o) {
        o = o.SparseTrans();
        SparseMatrix res = new SparseMatrix(size);
        for (int i = 0; i < size; i++) {
            row resRow = new row();
            Iterator<Map.Entry<Integer, row>> iter1 = o.map.entrySet().iterator();// итератор строк
            while (iter1.hasNext()) {
                Map.Entry entry1 = iter1.next();
                Integer key1 = (Integer) entry1.getKey();// ключ строки
                HashMap<Integer, Integer> value1 = (HashMap<Integer, Integer>) entry1.getValue();// сама строка
                Iterator iterElement = value1.entrySet().iterator();// итератор элементов
                int resValue = 0;
                while (iterElement.hasNext()) {
                    Map.Entry entryElement = (Map.Entry) iterElement.next();
                    Integer keyElement = (Integer) entryElement.getKey();// ключ элемента
                    Integer valueElement = (Integer) entryElement.getValue();//значение элемента
                    resValue = resValue + this.matrix[i][keyElement] * valueElement;
                }
                if (resValue != 0) {
                    resRow.put(key1, resValue);
                }
            }
            if (resRow != null) {
                res.map.put(i, resRow);
            }
        }
        return res;
    }


    public DenseMatrix SparseTrans() {
        int[][] mTr = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int aT = this.matrix[i][j];
                mTr[i][j] = this.matrix[j][i];
                mTr[j][i] = aT;
            }
        }
        return new DenseMatrix(mTr, size);
    }







  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override public Matrix dmul(Matrix o, int threadNumber)
  {
    return null;
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
      boolean ch = false;
      DenseMatrix oth = ((DenseMatrix) o).SparseTrans();
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              for (int k = 0; k < size; k++) {
                  ch = this.matrix[i][k] == oth.matrix[j][k];
              }
          }
      }
      return ch;
  }
}

