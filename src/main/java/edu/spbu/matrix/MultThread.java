package edu.spbu.matrix;

class MultThread extends Thread
{
    private DenseMatrix m1;
    private DenseMatrix m2;
    private final int beg;
    private final int end;
    private final int [][] res;
    private final int sumLength;



    public MultThread(DenseMatrix firstMatrix, DenseMatrix secondMatrix, final int [][] resultMatrix, final int beg, final int end)
    {
        this.m1 = firstMatrix;
        this.m2 = secondMatrix;
        this.beg = beg;
        this.end = end;
        this.res = resultMatrix;

        sumLength = secondMatrix.size;
    }

    /**
     * @param row Номер строки ячейки.
     * @param col Номер столбца ячейки.
     */
    private void calcValue(final int row, final int col)
    {
        int sum = 0;
        for (int i = 0; i < sumLength; ++i)
            sum += m1.matrix[row][i] * m2.matrix[i][col];
        res[row][col] = sum;
    }

    /** Рабочая функция потока. */
    @Override
    public void run()
    {
      //  System.out.println("Thread " + getName() + " started. Calculating cells from " + beg + " to " + end + "...");

        final int colCount = m2.size;  // Число столбцов результирующей матрицы.
        for (int i = beg; i < end; ++i)
            calcValue(i / colCount, i % colCount);

      //  System.out.println("Thread " + getName() + " finished.");
    }
}