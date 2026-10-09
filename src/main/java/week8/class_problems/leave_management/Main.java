package week8.class_problems.leave_management;

import java.time.LocalDate;

abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract int getLeaveLimit();

    public String getName() {
        return name;
    }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public int getLeaveLimit() {
        return 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public int getLeaveLimit() {
        return 15;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name) {
        super(name);
    }

    @Override
    public int getLeaveLimit() {
        return 10;
    }
}

enum LeaveStatus {
    PENDING, APPROVED, REJECTED
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate,
                        LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean review(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: "
                    + status + " request cannot be changed.");
            return false;
        }

        if (newStatus == LeaveStatus.PENDING) {
            System.out.println("Cannot change status: "
                    + "Request must be approved or rejected.");
            return false;
        }

        status = newStatus;

        System.out.println("Leave request for " + employee.getName()
                + " " + status.toString().toLowerCase() + ".");
        System.out.println("Status: "
                + status.toString().substring(0, 1)
                + status.toString().substring(1).toLowerCase());

        return true;
    }

    public void display() {
        System.out.println("Leave request submitted by "
                + employee.getName() + " for "
                + startDate + " to " + endDate + ".");
        System.out.println("Status: Pending");
    }
}

class LeaveManager {
    public LeaveRequest submitRequest(Employee employee,
                                      LocalDate startDate,
                                      LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            System.out.println("Leave request failed: Invalid dates.");
            return null;
        }

        LeaveRequest request =
                new LeaveRequest(employee, startDate, endDate);

        request.display();
        return request;
    }

    public void reviewRequest(LeaveRequest request,
                             LeaveStatus decision) {
        if (request != null) {
            request.review(decision);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest request1 = manager.submitRequest(
                john,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        manager.reviewRequest(request1, LeaveStatus.APPROVED);

        LeaveRequest request2 = manager.submitRequest(
                jane,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        manager.reviewRequest(request1, LeaveStatus.PENDING);
    }
}