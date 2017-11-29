package edu.spbu.matrix;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class EqualsTest {
    public static final String MATRIX1_NAME = "em1.txt";
    public static final String MATRIX2_NAME = "em2.txt";


    BufferedReader st;
    SparseMatrix em1;
    DenseMatrix em2;

    public EqualsTest() {
        try {
            st = new BufferedReader(new FileReader(MATRIX1_NAME));
            em1 = new SparseMatrix(st);

            st = new BufferedReader(new FileReader(MATRIX2_NAME));
            em2 = new DenseMatrix(st);
        } catch (IOException e)

        {
            e.printStackTrace();
        }
    }
    @Test
    public void matEq ()
    {

      //  Assert.assertFalse(em1.equals(em2));
        Assert.assertTrue(em1.equals(em2));

    }
}
