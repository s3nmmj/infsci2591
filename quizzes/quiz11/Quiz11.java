import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**

 * Problem: Quiz 10
 */
public class Quiz11 {
    /**
     * 21. Write a non-recursive Quicksort algorithm. Analyze your algorithm, and show the results using order notation.
     * Note that it will be necessary to explicitly maintain a stack in your algorithm.
     */
    public static void quickSortNonRecursive(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(array.length - 1);
        while (!stack.empty()) {
            int right = stack.pop();
            int left = stack.pop();
            if (right <= left) {
                continue;
            }
            int pivot = partition(array, left, right);
            if (left < pivot - 1) {
                stack.push(left);
                stack.push(pivot - 1);
            }
            if (pivot + 1 < right) {
                stack.push(pivot + 1);
                stack.push(right);
            }
        }
    }

    public static int partition(int[] array, int start, int end) {
        int left = start;
        int right = end;
        int pivot;
        if (start < end) {
            pivot = array[left];
            while (left < right) {
                while (right > left && array[right] > pivot) {
                    right--;
                }
                if (left < right) {
                    array[left] = array[right];
                    left++;
                }

                while (left < right && pivot > array[left]) {
                    left++;
                }
                if (left < right) {
                    array[right] = array[left];
                    right--;
                }
            }

            array[left] = pivot;
        }
        return left;
    }

    public static void main(String[] args) {
        System.out.println("Test Case1:");
        int[] data10 = generateTestData(10);
        System.out.println("Before Sort:" + Arrays.toString(data10));
        quickSortNonRecursive(data10);
        System.out.println("After Sort: " + Arrays.toString(data10));

        System.out.println("\nTest Case2:");
        int[] data20 = generateTestData(20);
        System.out.println("Before Sort:" + Arrays.toString(data20));
        quickSortNonRecursive(data20);
        System.out.println("After Sort: " + Arrays.toString(data20));

        System.out.println("\nTest Case3:");
        int[] data30 = generateTestData(20);
        System.out.println("Before Sort:" + Arrays.toString(data30));
        quickSortNonRecursive(data30);
        System.out.println("After Sort: " + Arrays.toString(data30));
    }

    public static int[] generateTestData(int count) {
        int[] data = new int[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            data[i] = random.nextInt(100);
        }

        return data;
    }
}
