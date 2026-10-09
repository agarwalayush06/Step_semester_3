package week9.class_problems.warehouse_scan;

public class Main {
    public static void warehouseSummary(int[][] grid) {
        int total = 0;
        int max = -1;
        int maxRow = 0;
        int maxCol = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                total += grid[row][col];

                if (grid[row][col] > max) {
                    max = grid[row][col];
                    maxRow = row;
                    maxCol = col;
                }
            }
        }

        System.out.println("Total: " + total);
        System.out.println("Max Coordinate: (" + maxRow + ", " + maxCol + ")");
    }

    public static void main(String[] args) {
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        warehouseSummary(grid);

        System.out.println("Time Complexity: O(m * n)");
        System.out.println("Space Complexity: O(1)");
    }
}