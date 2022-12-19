import java.util.Arrays;

/**

 * Problem: Quiz 12
 */

/**
 * 19. Modify Algorithm 8.4 (Find Smallest and Largest Keys by Pairing Keys) so that it works when n (the number
 * of keys in the given array) is odd and show that its time complexity is given by
 * 3n/2 - 2    if n is even
 * 3n/2 - 3/2  if n is odd
 */
public class Quiz12 {

    /**
     * Algorithm 8.4, Find Smallest and Largest Keys by Pairing Keys
     */
    public static void findBoth2Modified(int n, int[] S, int[] small, int[] large) {
        int i = 0;
        if (n % 2 == 0) {
            // even
            if (S[0] > S[1]) {
                large[0] = S[0];
                small[0] = S[1];
            } else {
                large[0] = S[1];
                small[0] = S[0];
            }
            i = 2;
        } else {
            // odd
            small[0] = S[0];
            large[0] = S[0];
            i = 1;
        }

        while (i < n - 1) {
            if (S[i] < S[i + 1]) {
                if (S[i] < small[0]) {
                    small[0] = S[i];
                }
                if (S[i + 1] > large[0]) {
                    large[0] = S[i + 1];
                }
            } else {
                if (S[i] > large[0]) {
                    large[0] = S[i];
                }
                if (S[i + 1] < small[0]) {
                    small[0] = S[i + 1];
                }
            }
            i += 2;
        }
    }

    public static void main(String[] args) {
        runTestCase(1, 7, new int[]{1, 8, 3, 9, 6, 5, 4});
        runTestCase(2, 5, new int[]{9, 8, 7, 6, 10});
        runTestCase(3, 9, new int[]{11, 2, 3, 4, 5, 7, 8, 9, 10});
    }

    private static void runTestCase(int index, int n, int[] array) {
        System.out.printf("Test Case %d:\n", index);
        System.out.printf("\tInput: n = %d, %s\n", n,
                          Arrays.toString(array).replace("[", "{").replace("]", "}"));
        int[] small = new int[1];
        int[] large = new int[1];
        findBoth2Modified(n, array, small, large);
        System.out.printf("\tOutput: %d, %d\n", small[0], large[0]);
    }
}
