package edu.spbu.matrix;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class MatrixTest
{
  public static final String MATRIX1_NAME = "m1.txt";
  public static final String MATRIX2_NAME = "m2.txt";
  public static final String RESMATRIX_NAME = "res.txt";

  BufferedReader st;
  SparseMatrix Sparse1;
  DenseMatrix Dense1;
  SparseMatrix Sparse2;
  DenseMatrix Dense2;

  public MatrixTest() {
    try {
      st = new BufferedReader(new FileReader(MATRIX1_NAME));
      Sparse1 = new SparseMatrix(st);
      st = new BufferedReader(new FileReader(MATRIX1_NAME));
      Dense1 = new DenseMatrix(st);

      st = new BufferedReader(new FileReader(MATRIX2_NAME));
      Sparse2 = new SparseMatrix(st);
      st = new BufferedReader(new FileReader(MATRIX2_NAME));
      Dense2 = new DenseMatrix(st);

      BufferedWriter bw = new BufferedWriter(new FileWriter(RESMATRIX_NAME));
      ((SparseMatrix) Sparse1.mul(Dense2)).printS(bw);
      bw.close();
    } catch (IOException e)

    {
      e.printStackTrace();
    }
  }
  @Test
  public void mulS_D () {
    SparseMatrix s_d = (SparseMatrix) Sparse1.multiplicationSD(Dense2);

    Assert.assertTrue(s_d.equals( Sparse1.mul(Dense2)));
  }

  @Test
  public void mulS_S () {
    SparseMatrix s_s = (SparseMatrix) Sparse1.multiplicationSS(Sparse2);
    s_s.equals( Sparse1.mul(Sparse2));
  }

  @Test
  public void mulD_S () {
    SparseMatrix d_s = (SparseMatrix) Sparse2.multiplicationSD(Dense1);
    d_s.equals( Sparse2.mul(Dense1));

  }
  @Test
  public void mulD_D () {
    DenseMatrix d_d = (DenseMatrix) Dense1.multiplicationDD(Dense2);
    d_d.equals( Dense1.mul(Dense2));

  }


}
