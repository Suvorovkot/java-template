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
  SparseMatrix res1;
  DenseMatrix res2;

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
      (Dense1.multiplicationDS(Sparse2)).printS(bw);
      bw.close();

    } catch (IOException e)

    {
      e.printStackTrace();
    }

    res2 = Dense1.multiplicationDD(Dense2);
  }
  @Test
  public void mulS_D () {
    SparseMatrix s_d = (SparseMatrix) Sparse1.multiplicationSD(Dense2);

    Assert.assertTrue(s_d.equals(res2));
  }

  @Test
  public void mulS_S () {
    SparseMatrix s_s = (SparseMatrix) Sparse1.multiplicationSS(Sparse2);
    Assert.assertTrue(s_s.equals(res2));
  }

  @Test
  public void mulD_S () {
    SparseMatrix d_s = Sparse2.SparseTrans().multiplicationSD(Dense1.SparseTrans()).SparseTrans();
    Assert.assertTrue(d_s.equals(res2));

  }
  @Test
  public void mulD_D () {
    DenseMatrix d_d = (DenseMatrix) Dense1.multiplicationDD(Dense2);
    Assert.assertTrue(d_d.equals(res2));

  }


}
