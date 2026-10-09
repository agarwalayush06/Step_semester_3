package week9.assigment_problems.budget_streak;

public class Main {
    public static int[] longestStreak(int[] costs, int budget) {
        int left = 0;
        int sum = 0;
        int bestLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];

            while (sum > budget && left <= right) {
                sum -= costs[left++];
            }

            int length = right - left + 1;

            if (length > bestLength) {
                bestLength = length;
                bestStart = left;
            }
        }

        return new int[]{bestLength, bestStart};
    }

    public static void main(String[] args) {
        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        int budget = 8;

        int[] result = longestStreak(costs, budget);
        System.out.println("(" + result[0] + ", " + result[1] + ")");
        System.out.println("Time Complexity: O(n)");
        System.out.println("Space Complexity: O(1)");
    }
}