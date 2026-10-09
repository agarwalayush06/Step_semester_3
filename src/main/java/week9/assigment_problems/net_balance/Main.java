package week9.assigment_problems.net_balance;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static int countPeriods(int[] transactions, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        counts.put(0, 1);

        int sum = 0;
        int total = 0;

        for (int value : transactions) {
            sum += value;
            total += counts.getOrDefault(sum - k, 0);
            counts.put(sum, counts.getOrDefault(sum, 0) + 1);
        }

        return total;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};
        int k = 7;

        System.out.println("Count: " + countPeriods(transactions, k));
        System.out.println("Time Complexity: O(n) average");
        System.out.println("Space Complexity: O(n)");
    }
}