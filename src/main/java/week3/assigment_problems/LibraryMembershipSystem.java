package week3.assigment_problems;

class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    void printName() {
        System.out.println(name);
    }
}

class LibraryMember {
    String name;
    String memberId;
    int booksIssued;

    static String libraryName = "SRM Central Library";
    static int memberCount = 0;

    LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + (1000 + memberCount);
    }

    void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class LibraryMembershipSystem {
    public static void main(String[] args) {
        System.out.println("Broken version:");

        BrokenLibraryMember aditi =
                new BrokenLibraryMember("Aditi", "LM-1001", 2);

        BrokenLibraryMember rohan =
                new BrokenLibraryMember("Rohan", "LM-1002", 3);

        aditi.printName();
        rohan.printName();

        System.out.println();
        System.out.println("Fixed version:");

        LibraryMember member1 = new LibraryMember("Aditi", 2);
        LibraryMember member2 = new LibraryMember("Rohan", 3);

        member1.printMemberCard();
        member2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}