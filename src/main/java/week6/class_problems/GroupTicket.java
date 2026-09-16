package week6.class_problems;

public class GroupTicket extends EventTicket {
    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);

        if (groupSize <= 0) {
            throw new IllegalArgumentException("Invalid group size");
        }

        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }

    @Override
    public void printTicket() {
        System.out.println("Group Ticket | Group Size: " + groupSize
                + " | Balance Due: " + balanceDue);
    }
}