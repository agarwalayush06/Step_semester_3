package week9.assigment_problems.footfall_report;

public class Main {
    public static int[] footfallReport(int[] visitors, int[][] queries) {
        int[] prefix = new int[visitors.length + 1];

        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            result[i] = prefix[end + 1] - prefix[start];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};

        int[] result = footfallReport(visitors, queries);

        System.out.print("Results: [");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            if (i < result.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Time Complexity: O(n + q)");
        System.out.println("Space Complexity: O(n)");
    }
}