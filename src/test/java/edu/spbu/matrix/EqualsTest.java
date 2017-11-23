package edu.spbu.matrix;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class EqualsTest {
    public static final String MATRIX1_NAME = "em1.txt";
    public static final String MATRIX2_NAME = "em2.txt";


    BufferedReader st;
    SparseMatrix Sparse1;
    SparseMatrix Sparse2;

    public EqualsTest() {
        try {
            st = new BufferedReader(new FileReader(MATRIX1_NAME));
            Sparse1 = new SparseMatrix(st);

            st = new BufferedReader(new FileReader(MATRIX2_NAME));
            Sparse2 = new SparseMatrix(st);
        } catch (IOException e)

        {
            e.printStackTrace();
        }
    }
    @Test
    public void matEq ()
    {

        Assert.assertTrue(Sparse1.equals(Sparse2));

    }
}
