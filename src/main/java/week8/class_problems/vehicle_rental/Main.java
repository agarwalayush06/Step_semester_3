package week8.class_problems.vehicle_rental;

abstract class Vehicle {
    protected String name;
    protected double ratePerDay;
    protected boolean available = true;

    public Vehicle(String name, double ratePerDay) {
        this.name = name;
        this.ratePerDay = ratePerDay;
    }

    public abstract double calculateCharge(int days);

    public boolean isAvailable() {
        return available;
    }

    public String getName() {
        return name;
    }

    public void rent() {
        available = false;
    }

    public void returnVehicle() {
        available = true;
    }
}

class StandardCar extends Vehicle {
    public StandardCar(String name) {
        super(name, 50);
    }

    @Override
    public double calculateCharge(int days) {
        return ratePerDay * days;
    }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String name) {
        super(name, 100);
    }

    @Override
    public double calculateCharge(int days) {
        return ratePerDay * days;
    }
}

class Rental {
    private Vehicle vehicle;
    private int days;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    public double getTotalCharge() {
        return vehicle.calculateCharge(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

class RentalService {
    public void rentVehicle(Vehicle vehicle, int days) {
        if (days <= 0) {
            System.out.println("Rental failed: Invalid rental duration.");
            return;
        }

        if (!vehicle.isAvailable()) {
            System.out.println("Rental failed: " + vehicle.getName()
                    + " is not available.");
            return;
        }

        Rental rental = new Rental(vehicle, days);
        vehicle.rent();

        System.out.println(vehicle.getName() + " rented for "
                + days + " days.");
        System.out.printf("Total charge: $%.2f%n",
                rental.getTotalCharge());
    }

    public void returnVehicle(Vehicle vehicle) {
        if (vehicle.isAvailable()) {
            System.out.println(vehicle.getName()
                    + " is already available.");
            return;
        }

        vehicle.returnVehicle();
        System.out.println(vehicle.getName()
                + " returned. Now available.");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        RentalService service = new RentalService();

        service.rentVehicle(luxury, 3);
        service.rentVehicle(standard, 5);
        service.returnVehicle(luxury);
    }
}