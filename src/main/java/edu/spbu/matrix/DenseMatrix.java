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
      else return ((SparseMatrix) o).SparseTrans().multiplicationSD(this.SparseTrans()).SparseTrans();
  }

    public DenseMatrix multiplicationDD(DenseMatrix o) {
        DenseMatrix res = new DenseMatrix(size);
        for (int i = 0; i < size; i++) {
             for (int j = 0; j < size; j++)
                for (int k = 0; k < size; k++)
                    res.matrix[i][j] += this.matrix[i][k] * o.matrix[k][j];
        }

        return res;
    }


    /*public SparseMatrix multiplicationDS(SparseMatrix o) {
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
    }*/


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


    public void printD(BufferedWriter matrix) {
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++)
                    matrix.write(this.matrix[i][j] + " ");
                matrix.write("\n");
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
  @Override public Matrix dmul(Matrix o, int threadNumber)
  {
      assert threadNumber > 0;
      DenseMatrix oth = ((DenseMatrix) o);
      final int rowCount = this.size;             // Число строк результирующей матрицы.
      final int colCount = oth.size;         // Число столбцов результирующей матрицы.
      final int[][] res = new int[rowCount][colCount];  // Результирующая матрица.

      final int cellsForThread = (rowCount * colCount) / threadNumber;  // Число вычисляемых ячеек на поток.
      int beg = 0;
      final MultThread[] mulThreads = new MultThread[threadNumber];


      for (int ti = threadNumber - 1; ti >= 0; --ti) {
          int end = beg + cellsForThread;
          if (ti == 0) {
              end = rowCount * colCount;
          }
          mulThreads[ti] = new MultThread(this, oth, res, beg, end);
          mulThreads[ti].start();
          beg = end;
      }

      // Ожидание завершения потоков.
      try {
          for (final MultThread multiplierThread : mulThreads)
              multiplierThread.join();
      }
      catch (InterruptedException e) {
          e.printStackTrace();
      }

      return new DenseMatrix(res,size);
  }

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
      DenseMatrix oth = ((DenseMatrix) o);
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++)
              {
                  if (this.matrix[i][j] != oth.matrix[i][j])
                      return false;
              }
          }
      return true;
  }
}

