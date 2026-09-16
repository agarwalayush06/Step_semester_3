package week4.class_problems;

class BusTicketAccount {
    static String depotName;

    static {
        depotName = "SRM Bus Depot";
    }

    protected String bookingId;
    protected double ticketFare;

    public BusTicketAccount(String bookingId, double ticketFare) {
        if (bookingId == null || bookingId.trim().isEmpty() || ticketFare < 0) {
            throw new IllegalArgumentException("Invalid account");
        }

        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        if (minutesLate < 0) {
            throw new IllegalArgumentException("Invalid minutes");
        }

        return ticketFare * minutesLate * 0.01;
    }

    public void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            return;
        }

        double penalty = account.calculatePenalty(minutesLate);

        System.out.println(account.bookingId +
                " processed | Amount: Rs " + amount +
                " | Penalty: Rs " + penalty);
    }

    public static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts == null || amounts == null || minutesLateArray == null) {
            return;
        }

        int length = Math.min(
                accounts.length,
                Math.min(amounts.length, minutesLateArray.length)
        );

        int processed = 0;
        int nullSkipped = 0;
        int sleeper = 0;
        int regular = 0;
        double grandTotalPenalty = 0.0;

        for (int i = 0; i < length; i++) {
            BusTicketAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {
                account.processAccount(account, amounts[i], minutesLateArray[i]);

                double penalty = account.calculatePenalty(minutesLateArray[i]);
                grandTotalPenalty += penalty;

                processed++;

                if (account instanceof SleeperBusTicketAccount) {
                    sleeper++;
                } else {
                    regular++;
                }
            } catch (IllegalArgumentException e) {
                nullSkipped++;
            }
        }

        System.out.println(
                processed + " processed | " +
                nullSkipped + " null skipped | " +
                sleeper + " sleeper | " +
                regular + " regular | grand total penalties = Rs " +
                grandTotalPenalty
        );
    }
}

class SleeperBusTicketAccount extends BusTicketAccount {

    public SleeperBusTicketAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public SleeperBusTicketAccount(String bookingId) {
        super(bookingId);
    }

    @Override
    public void processAccount(
            BusTicketAccount account,
            double amount,
            int minutesLate) {

        double penalty = calculatePenalty(minutesLate);

        System.out.println(account.bookingId +
                " sleeper processed | Amount: Rs " + amount +
                " | Penalty: Rs " + penalty);
    }
}

public class FleetReconciliationEngine {

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
            new SleeperBusTicketAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {
            1200,
            900,
            700
        };

        int[] minutesLateArray = {
            10,
            5,
            0
        };

        BusTicketAccount.processBatch(
                accounts,
                amounts,
                minutesLateArray
        );
    }
}