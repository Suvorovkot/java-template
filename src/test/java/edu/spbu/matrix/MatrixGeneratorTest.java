package edu.spbu.matrix;

import edu.spbu.matrix.MatrixGenerator;
import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatrixGeneratorTest
{
  String fileName="test.txt";
  @After
  public void cleanUp() {
    File f = new File(fileName);
    if(f.exists())
      f.delete();
  }

  @Test
  public void testGenerate() throws Exception {
    new MatrixGenerator(1,3,fileName,10).generate();
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    int lineCount=0;
    int emptyLineCount=0;
    for (String line = reader.readLine();line !=null; line = reader.readLine()) {
      if (line.equals("0 0 0 0 0 0 0 0 0 0")) {
        emptyLineCount++;
      } else {
        assertEquals(10, line.split(" ").length);
      }
      lineCount++;
    }
    assertTrue(emptyLineCount>1);
    assertEquals(10,lineCount);
  }
}
