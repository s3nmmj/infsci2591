/**
 * Assignment: Project 1, Problem 1
 */

import java.math.BigInteger;

public class Problem1 {
    private final static int ONE_MINUTE = 60 * 1000;

    // Algorithm 1.6: nth Fibonacci Term (Recursive)
    public static BigInteger fib_rec(int n) {
        if (n <= 1) {
            return BigInteger.valueOf(n);
        } else {
            return fib_rec(n - 1).add(fib_rec(n - 2));
        }
    }

    // Algorithm 1.7: nth Fibonacci Term (Iterative)
    public static BigInteger fib(int n) {
        BigInteger[] f = new BigInteger[n + 1];
        f[0] = BigInteger.ZERO;
        if (n > 0) {
            f[1] = BigInteger.ONE;
            for (int i = 2; i <= n; i++) {
                f[i] = f[i - 1].add(f[i - 2]);
            }
        }

        return f[n];
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long duration = 0;
        int n = 0;
        BigInteger result = null;

        while (true) {
            BigInteger temp = fib_rec(n);
            long tempDuration = System.currentTimeMillis() - startTime;
            if (tempDuration > ONE_MINUTE) {
                // for the current n, it can not be calculated within 1 minute.
                n = n - 1;
                break;
            }
            // result saves the result that can be calculated within 1 minute.
            duration = tempDuration;
            result = temp;
            n++;
        }
        assert result != null;
        System.out.printf("Recursive: Duration: %d milliseconds, N = %d, Result = %s \n", duration, n, result);
        System.out.printf("The largest number that the recursive algorithm can accept as its argument " +
                "and still compute the answer within 60 seconds is %d.\n", n);

        startTime = System.nanoTime();
        BigInteger resultIterate = fib(n);
        duration = System.nanoTime() - startTime;
        System.out.printf("Iterative: Duration: %d nanoseconds, N = %d, Result = %s \n", duration, n, resultIterate);
        System.out.printf("The time it takes the iterative algorithm to compute the answer is %d nanoseconds\n", duration);
    }
}
