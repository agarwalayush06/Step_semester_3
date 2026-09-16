package week3.class_problems;

class BrokenSrmStudent {
    static String name;
    static String regNo;
    static int attendance;

    BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }

    void printData() {
        System.out.println(name);
    }
}

class FixedSrmStudent {
    String name;
    String regNo;
    int attendance;

    static String university = "SRM Institute of Science and Technology";
    static int admissionCount = 0;

    FixedSrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        admissionCount++;
        this.regNo = "RA2311003010" + admissionCount;
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class StaticInstanceDemo {
    public static void main(String[] args) {
        System.out.println("Broken version:");

        BrokenSrmStudent ravi = new BrokenSrmStudent("Ravi", "RA001", 82);
        BrokenSrmStudent meera = new BrokenSrmStudent("Meera", "RA002", 74);

        ravi.printData();
        meera.printData();

        System.out.println();
        System.out.println("Fixed version:");

        FixedSrmStudent student1 = new FixedSrmStudent("Ravi", 82);
        FixedSrmStudent student2 = new FixedSrmStudent("Meera", 74);

        student1.printIdCard();
        student2.printIdCard();

        FixedSrmStudent.printTotalAdmissions();
    }
}