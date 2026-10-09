package week8.assigment_problems.elective_enrollment;

import java.util.*;

interface CreditPolicy {
    int getCreditLimit();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }
}

class Student {
    String name;
    int credits;
    CreditPolicy policy;

    Student(String name, int credits, CreditPolicy policy) {
        this.name = name;
        this.credits = credits;
        this.policy = policy;
    }
}

class Elective {
    String name;
    int credits;
    int capacity;
    List<Student> enrolled = new ArrayList<>();
    Queue<Student> waitlist = new LinkedList<>();

    Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    boolean isEnrolled(Student s) {
        return enrolled.contains(s);
    }

    boolean isWaiting(Student s) {
        return waitlist.contains(s);
    }

    boolean hasSpace() {
        return enrolled.size() < capacity;
    }

    void addStudent(Student s) {
        enrolled.add(s);
        s.credits += credits;
        System.out.println(s.name + " enrolled in " + name
                + " (credits: " + s.credits + "/"
                + s.policy.getCreditLimit() + ").");
    }

    void addToWaitlist(Student s) {
        waitlist.add(s);
        System.out.println(s.name + " added to waitlist (position "
                + waitlist.size() + ").");
    }

    void dropStudent(Student s) {
        if (!enrolled.remove(s)) {
            System.out.println(s.name + " is not enrolled in " + name + ".");
            return;
        }

        s.credits -= credits;
        System.out.println(s.name + " dropped " + name
                + " (credits: " + s.credits + "/"
                + s.policy.getCreditLimit() + ").");

        promoteNext();
    }

    private void promoteNext() {
        while (hasSpace() && !waitlist.isEmpty()) {
            Student s = waitlist.poll();

            if (s.credits + credits > s.policy.getCreditLimit()) {
                System.out.println("Promotion failed: " + s.name
                        + " would exceed the "
                        + s.policy.getClass().getSimpleName().replace("Policy", "")
                        + " credit limit.");
                continue;
            }

            addStudent(s);
            System.out.println(s.name + " promoted from waitlist and enrolled in "
                    + name + " (credits: " + s.credits + "/"
                    + s.policy.getCreditLimit() + ").");
        }
    }
}

class EnrollmentService {
    public void enroll(Student s, Elective e) {
        if (e.isEnrolled(s) || e.isWaiting(s)) {
            System.out.println("Enrollment rejected: " + s.name
                    + " is already enrolled or waitlisted.");
            return;
        }

        if (s.credits + e.credits > s.policy.getCreditLimit()) {
            System.out.println("Enrollment failed: " + s.name
                    + " would exceed the "
                    + s.policy.getClass().getSimpleName().replace("Policy", "")
                    + " credit limit (" + (s.credits + e.credits) + "/"
                    + s.policy.getCreditLimit() + ").");
            return;
        }

        if (e.hasSpace()) {
            e.addStudent(s);
        } else {
            System.out.println(e.name + " is full.");
            e.addToWaitlist(s);
        }
    }

    public void drop(Student s, Elective e) {
        e.dropStudent(s);
    }
}

public class Main {
    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);
        EnrollmentService service = new EnrollmentService();

        Student asha = new Student("Asha", 20, new RegularPolicy());
        Student ravi = new Student("Ravi", 22, new HonorsPolicy());
        Student neha = new Student("Neha", 12, new ExchangePolicy());
        Student kiran = new Student("Kiran", 22, new RegularPolicy());

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);

        service.drop(asha, cloud);
    }
}