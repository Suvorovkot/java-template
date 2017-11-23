package edu.spbu.matrix;

import java.util.concurrent.Callable;

public class CallableMultiplier implements Callable<Matrix> {
    SparseMatrix Sparse1;
    SparseMatrix Sparse2;
    public CallableMultiplier(SparseMatrix m1,SparseMatrix m2){
        Sparse1 = m1;
        Sparse2 = m2;
    }
    @Override public Matrix call()
    {
        return Sparse1.multiplicationSS(Sparse2);
    }
}
