package week6.assigment_problems;

public class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public void announce() {
        System.out.println(getAnnouncement());
    }

    @Override
    protected String getAnnouncement() {
        return "Elite Runner | Bib: " + bibNumber
                + " | Category: " + getCategory()
                + " | Sponsor Bonus: " + sponsorBonus
                + " | Balance: " + balanceDue;
    }

    public double getSponsorBonus() {
        return sponsorBonus;
    }
}