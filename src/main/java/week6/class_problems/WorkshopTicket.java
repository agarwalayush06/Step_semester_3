package week6.class_problems;

public class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    public String getTrack() {
        return track;
    }

    @Override
    public void printTicket() {
        System.out.println("Workshop Ticket | Track: " + track
                + " | Balance Due: " + balanceDue);
    }

    @Override
    protected String getAnnouncement() {
        return "Workshop | Track: " + track
                + " | Balance: " + balanceDue;
    }
}