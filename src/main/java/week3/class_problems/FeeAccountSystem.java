package week3.class_problems;

class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {
    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    double effectiveDue() {
        return getDue() - (getDue() * scholarshipPercent / 100);
    }
}

public class FeeAccountSystem {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA001", 150000, 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA002", 200000, 60000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA003", 180000, 0, 20);

        FeeAccount[] accounts = {plain, hostel, scholarship};

        for (FeeAccount account : accounts) {
            if (account instanceof HostelFeeAccount) {
                ((HostelFeeAccount) account).payInTwoInstallments(0);
            }

            if (account instanceof ScholarshipFeeAccount) {
                System.out.println("Scholarship account effective due: Rs "
                        + ((ScholarshipFeeAccount) account).effectiveDue());
            } else if (account instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + account.getDue());
            } else {
                System.out.println("Plain account due: Rs " + account.getDue());
            }
        }
    }
}