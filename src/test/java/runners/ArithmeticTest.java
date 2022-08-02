package runners;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

class Arithmetic{
    public int sum(int a, int b){
        return a +b;
    }
}
@RunWith(Parameterized.class)
public class ArithmeticTest {
    private int firstN;
    private int secondN;
    private int expectedResult;
    private Arithmetic arithmetic;

    public ArithmeticTest(int firstN,int secondN, int expectedResult){
        super();
        this.firstN = firstN;
        this.secondN = secondN;
        this.expectedResult = expectedResult;
    }

    @Before
    public void initialize(){
        arithmetic = new Arithmetic();
    }

    @Parameterized.Parameters
    public static Collection input(){
        return Arrays.asList(new Object[][]{
                {1,2,3},{11,22,33}, {111,222,333}});
    }

    @Test
    public void testArithmeticTest(){
        System.out.println("Sum of numbers: " + expectedResult);
        Assert.assertEquals(expectedResult, arithmetic.sum(firstN,secondN));
        System.out.println(Thread.currentThread().getId());
    }
}
