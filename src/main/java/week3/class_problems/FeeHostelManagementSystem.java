package week3.class_problems;

class FeeAccount {
    private double totalFee;
    private double amountPaid;

    FeeAccount(double totalFee, double amountPaid) {
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
    HostelFeeAccount(double totalFee, double amountPaid) {
        super(totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot() {
        if (occupied < beds) {
            occupied++;
        }
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        totalStudents++;
    }

    String fullStatus() {
        String roomNumber = room == null ? "unallotted" : room.roomNo;

        return name + " | Due: Rs " + feeAccount.getDue()
                + " | Room: " + roomNumber;
    }
}

public class FeeHostelManagementSystem {
    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, SrmStudent student) {
        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot();
            student.room = room;
        }
    }

    public static void main(String[] args) {
        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1, 0),
            new HostelRoom("C-507", 1, 0)
        };

        SrmStudent ravi = new SrmStudent(
                "Ravi",
                "RA001",
                new HostelFeeAccount(150000, 10000)
        );

        SrmStudent anitha = new SrmStudent(
                "Anitha",
                "RA002",
                new HostelFeeAccount(180000, 0)
        );

        SrmStudent karthik = new SrmStudent(
                "Karthik",
                "RA003",
                new HostelFeeAccount(200000, 0)
        );

        ravi.feeAccount.pay(10000);
        anitha.feeAccount.pay(-5000);

        safeAllot(rooms, ravi);
        safeAllot(rooms, anitha);

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());

        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}