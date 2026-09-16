package week4.class_problems;

import java.util.Arrays;

public class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (tripId == null || tripId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid trip ID");
        }

        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException("Invalid fare or passenger count");
        }

        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        double[] result = new double[passengerCount];

        long totalPaise = Math.round(totalFare * 100);
        long basePaise = totalPaise / passengerCount;
        long remainder = totalPaise % passengerCount;

        for (int i = 0; i < passengerCount; i++) {
            long share = basePaise;

            if (i == passengerCount - 1) {
                share += remainder;
            }

            result[i] = share / 100.0;
        }

        return result;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }

    public static void main(String[] args) {
        FareSplitter splitter =
                new FareSplitter("TRIP001", 100000, 3);

        System.out.println(Arrays.toString(splitter.fareBreakdown()));

        FareSplitter provisional =
                new FareSplitter("TRIP003");

        System.out.println(Arrays.toString(provisional.fareBreakdown()));
    }
}