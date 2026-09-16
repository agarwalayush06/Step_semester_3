package week6.class_problems;

public class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double balanceDue;

    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount;

    private static int ticketsIssued = 0;
    private static int ticketCounter = 1000;

    public final String ticketId;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendee ID");
        }

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Invalid base price");
        }

        this.attendeeId = attendeeId.trim();
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
        this.ticketId = "TCK-" + (++ticketCounter);
        ticketsIssued++;
    }

    public EventTicket(double basePrice) {
        this("STU1", basePrice);
    }

    public void pay(double amount) {
        if (amount > 0) {
            balanceDue = Math.max(0, balanceDue - amount);
        }
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
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

    public void printTicket() {
        System.out.println("Standard Event Ticket | Balance Due: " + balanceDue);
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String attendeeId : attendeeIds) {
            try {
                new EventTicket(attendeeId, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }

        if (ticket instanceof WorkshopTicket) {
            return "Direct descendant";
        }

        return "Base ticket";
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;

        for (EventTicket ticket : tickets) {
            if (ticket != null) {
                total += ticket.getBalanceDue();
            }
        }

        return total;
    }

    public static String batchPrint(EventTicket[] tickets) {
        StringBuilder result = new StringBuilder();

        for (EventTicket ticket : tickets) {
            ticket.printTicket();

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket;
                result.append(workshop.getAnnouncement())
                      .append(" [Track via downcast: ")
                      .append(workshop.getTrack())
                      .append("] | ");
            } else {
                result.append(ticket.getAnnouncement())
                      .append(" | ");
            }
        }

        return result.toString();
    }

    protected String getAnnouncement() {
        return "Standard | Balance: " + balanceDue;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        return code.charAt(0) == 'F'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isDigit(code.charAt(3))
                && Character.isUpperCase(code.charAt(4));
    }

    public static int getTicketsIssued() {
        return ticketsIssued;
    }

    public static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int skipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                skipped++;
                continue;
            }

            processed++;

            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " + skipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }
}