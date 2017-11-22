package edu.spbu;

import edu.spbu.matrix.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MatrixPerfTest
{

  public static final String MATRIX1_NAME = "m1.txt";
  public static final String MATRIX2_NAME = "m2.txt";
  BufferedReader st;
  public MatrixPerfTest()
  {
    try {
    System.out.println("Starting loading dense matrices");
    st = new BufferedReader(new FileReader(MATRIX1_NAME));
    Matrix m1 = new DenseMatrix(st);
    System.out.println("1 loaded");
    st = new BufferedReader(new FileReader(MATRIX2_NAME));
    Matrix m2 = new DenseMatrix(st);
    System.out.println("2 loaded");
    long start = System.currentTimeMillis();
    Matrix r1 = m1.mul(m2);
    System.out.println("Dense Matrix time: " +(System.currentTimeMillis() - start));

    System.out.println("Starting loading sparse matrices");
    st = new BufferedReader(new FileReader(MATRIX1_NAME));
    m1 = new SparseMatrix(st);
    System.out.println("1 loaded");
    st = new BufferedReader(new FileReader(MATRIX2_NAME));
    m2 = new SparseMatrix(st);
    System.out.println("2 loaded");
    start = System.currentTimeMillis();
    Matrix r2 = m1.mul(m2);
    System.out.println("Sparse Matrix time: " +(System.currentTimeMillis() - start));
    System.out.println("equals: " + r1.equals(r2));
  }catch (IOException e)
    {
      e.printStackTrace();
    }
  }


}
