package week6.class_problems;

public class PremiumWorkshopTicket extends WorkshopTicket {
    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice,
                                  String track, double kitFee) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
    }

    @Override
    public void printTicket() {
        System.out.println("Premium Workshop Ticket | Track: " + getTrack()
                + " | Kit Fee: " + kitFee
                + " | Balance Due: " + balanceDue);
    }

    public double getKitFee() {
        return kitFee;
    }
}