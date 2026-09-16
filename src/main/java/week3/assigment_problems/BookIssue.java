package week3.assigment_problems;

public class BookIssue {
    String title;
    String borrowerName;
    int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5 : 0;
    }

    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    static double totalFineCollected(BookIssue[] issues) {
        double total = 0;

        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }

        return total;
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Aditi", 18),
            new BookIssue("Effective Java", "Rohan", 5),
            new BookIssue("Refactoring", "Karan", 0),
            new BookIssue("DSA Handbook", "Meera", 21),
            new BookIssue("Design Patterns", "Arjun", 9)
        };

        for (BookIssue issue : issues) {
            System.out.println(issue.title + " - " + issue.daysOverdue + " days - "
                    + (issue.isSeverelyOverdue() ? "Severely overdue" : "OK"));
        }

        // totalFineCollected works on the complete array, while fineAmount belongs to one BookIssue object.
        System.out.println("Total fine collected: Rs "
                + totalFineCollected(issues));
    }
}