package week9.class_problems.max_sum_subarray;

public class Main {
    public static int maxSumSubarray(int[] sales, int k) {
        if (sales == null || k < 1 || k > sales.length) {
            throw new IllegalArgumentException("Invalid window size");
        }

        int windowSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        int maxSum = windowSum;

        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i] - sales[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] sales = {2, 1, 5, 1, 3, 2};
        int k = 3;

        System.out.println("Maximum Sum: " + maxSumSubarray(sales, k));
        System.out.println("Time Complexity: O(n)");
        System.out.println("Space Complexity: O(1)");
    }
}