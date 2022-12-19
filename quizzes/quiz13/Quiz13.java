/**

 * Problem: Quiz 13
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Example 9.19 Bin-Packing Problem
 * Let n items with sizes s1, s2, s3,...,sn, where 0 < si <=1 be given, and suppose we are to pack the items in bins,
 * where each bin has a capacity of 1. The problem is to determine the minimum number of bins necessary to pack all
 * the items.
 */
public class Quiz13 {
    /**
     * Non-increasing first fit Bin-Packing Problem
     */
    public static class Bin {
        int id;
        double capacity;
        double available;
        List<Double> items;

        public Bin(int id, double capacity) {
            this.id = id;
            this.capacity = capacity;
            this.available = capacity;
            this.items = new LinkedList<>();
        }

        public boolean addItem(double item) {
            if (item > this.available) {
                return false;
            }

            this.items.add(item);
            this.available -= item;
            return true;
        }

        @Override
        public String toString() {
            return String.format("Bin %d, Capacity: %f, Remaining Capacity: %f, Items: %s",
                                 this.id, this.capacity, this.available, Arrays.toString(this.items.toArray()));
        }
    }

    public static List<Bin> nonIncreasingFirstFitBinPacking(double[] data, double capacity) {
        double[] inputData = new double[data.length];
        System.arraycopy(data, 0, inputData, 0, data.length);
        // sort the items in nonincreasing order
        sort(inputData);

        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(1, capacity));
        // while (there are still unpacked items)
        for (double item : inputData) {
            // get next item
            boolean packed = false;
            // while (the item is not packed and there are more started bins)
            for (Bin bin : bins) {
                // get next bin
                if (bin.addItem(item)) {
                    // if the item fits in the bin, pack it in the bin
                    packed = true;
                    break;
                }
            }
            if (!packed) {
                // if item is not packed, start a new bin
                Bin newBin = new Bin(bins.size() + 1, capacity);
                // place the item in the new bin
                newBin.addItem(item);
                bins.add(newBin);
            }
        }

        return bins;
    }

    private static void sort(double[] data) {
        for (int i = 0; i < data.length; i++) {
            int maxMin = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] > data[maxMin]) {
                    maxMin = j;
                }
            }
            swap(data, i, maxMin);
        }
    }

    private static void swap(double[] array, int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void main(String[] args) {
        System.out.println("Test Case");
        double[] testData = {0.1, 0.26, 0.35, 0.4, 0.5, 0.6, 0.75, 0.99};
        double capacity = 1.0;
        System.out.printf("\t%s\n", Arrays.toString(testData).replaceAll("[\\[\\]]", ""));
        List<Bin> bins = nonIncreasingFirstFitBinPacking(testData, capacity);
        System.out.println("Result of Non-Increasing First Fit Bin Packing for Test Case:");
        System.out.printf("\tTotal Bins: %d\n", bins.size());
        for (Bin bin : bins) {
            System.out.printf("\t%s\n", bin);
        }
    }
}
