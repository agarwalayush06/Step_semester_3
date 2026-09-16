package week6.class_problems;

public class HackathonTicket extends EventTicket {
    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println("Hackathon Ticket | Team: " + teamName
                + " | Balance Due: " + balanceDue);
    }

    public String getTeamName() {
        return teamName;
    }
}