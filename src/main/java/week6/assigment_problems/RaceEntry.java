package week6.assigment_problems;

public class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double balanceDue;
    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount;

    private static int bibCounter = 0;
    private final int entryCode;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        if (entryFee <= 0) {
            throw new IllegalArgumentException("Invalid entry fee");
        }

        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.balanceDue = entryFee;
        this.entryCode = ++bibCounter;
    }

    public void pay(double amount) {
        if (amount > 0) {
            balanceDue = Math.max(0, balanceDue - amount);
        }
    }

    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Paying via " + mode);
    }

    protected void applyLateFee(double amount) {
        if (amount > 0 && lateFeeCount < lateFeeHistory.length) {
            balanceDue += amount;
            lateFeeHistory[lateFeeCount++] = amount;
        }
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public double[] getLateFeeHistory() {
        double[] result = new double[lateFeeCount];
        System.arraycopy(lateFeeHistory, 0, result, 0, lateFeeCount);
        return result;
    }

    public void announce() {
        System.out.println("Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue);
    }

    public int getEntryCode() {
        return entryCode;
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bibNumber : bibNumbers) {
            try {
                new RaceEntry(bibNumber, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder result = new StringBuilder();

        for (RaceEntry entry : entries) {
            result.append(entry.getAnnouncement());

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                result.append(" [Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("]");
            }

            result.append(" | ");
        }

        return result.toString();
    }

    protected String getAnnouncement() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + balanceDue;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        return code.charAt(0) == 'M'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isDigit(code.charAt(3))
                && Character.isUpperCase(code.charAt(4));
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int skipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                skipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " + skipped + " null skipped | "
                + relay + " relay | " + individual + " individual";
    }
}