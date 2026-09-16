package week6.assigment_problems;

public class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    @Override
    public void announce() {
        System.out.println(getAnnouncement());
    }

    @Override
    protected String getAnnouncement() {
        return "Runner Entry | Bib: " + bibNumber
                + " | Category: " + category
                + " | Balance: " + balanceDue;
    }

    public String getCategory() {
        return category;
    }
}