package week3.assigment_problems;

class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId, Employee employee) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        totalRecords++;
    }

    String fullProfile() {
        String slotNumber = slot == null ? "no parking assigned" : slot.slotNo;

        double pay;

        if (employee instanceof ManagerEmployee) {
            pay = ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            pay = ((InternEmployee) employee).effectiveSalary();
        } else {
            pay = employee.getSalary();
        }

        return name + " | Pay: Rs " + pay + " | Slot: " + slotNumber;
    }
}

public class HRParkingManagementSystem {

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static void safeAllot(ParkingSlot[] slots, CompanyEmployeeRecord record) {
        ParkingSlot slot = findAvailableSlot(slots);

        if (slot != null) {
            slot.occupiedCount++;
            record.slot = slot;
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] slots = {
            new ParkingSlot("A1", 1, 0),
            new ParkingSlot("A2", 1, 0)
        };

        CompanyEmployeeRecord divya =
                new CompanyEmployeeRecord(
                        "Divya",
                        "E001",
                        new ManagerEmployee("E001", "Divya", 70000, 8000)
                );

        CompanyEmployeeRecord karan =
                new CompanyEmployeeRecord(
                        "Karan",
                        "E002",
                        new Employee("E002", "Karan", 40000)
                );

        CompanyEmployeeRecord meera =
                new CompanyEmployeeRecord(
                        "Meera",
                        "E003",
                        new InternEmployee("E003", "Meera", 12000, 10000)
                );

        safeAllot(slots, divya);
        safeAllot(slots, karan);

        System.out.println(divya.fullProfile());
        System.out.println(karan.fullProfile());
        System.out.println(meera.fullProfile());

        System.out.println("Total records: "
                + CompanyEmployeeRecord.totalRecords);
    }
}
