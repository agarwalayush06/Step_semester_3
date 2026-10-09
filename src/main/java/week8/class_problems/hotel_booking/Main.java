package week8.class_problems.hotel_booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

abstract class Room {
    protected String name;
    protected double ratePerNight;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(String name, double ratePerNight) {
        this.name = name;
        this.ratePerNight = ratePerNight;
    }

    public abstract double calculatePrice(long nights);

    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        for (Reservation r : reservations) {
            if (r.isActive()
                    && checkIn.isBefore(r.getCheckOut())
                    && checkOut.isAfter(r.getCheckIn())) {
                return false;
            }
        }
        return true;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public String getName() {
        return name;
    }
}

class StandardRoom extends Room {
    public StandardRoom(String name) {
        super(name, 150);
    }

    @Override
    public double calculatePrice(long nights) {
        return ratePerNight * nights;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String name) {
        super(name, 200);
    }

    @Override
    public double calculatePrice(long nights) {
        return ratePerNight * nights;
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(String name) {
        super(name, 350);
    }

    @Override
    public double calculatePrice(long nights) {
        return ratePerNight * nights;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Reservation {
    private Room room;
    private Customer customer;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDate cancellationDeadline;
    private boolean active = true;

    public Reservation(Room room, Customer customer,
                       LocalDate checkIn, LocalDate checkOut,
                       LocalDate cancellationDeadline) {
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancellationDeadline = cancellationDeadline;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public boolean cancel() {
        if (!active) {
            System.out.println("Cancellation failed: Reservation is already cancelled.");
            return false;
        }

        if (!LocalDate.now().isBefore(cancellationDeadline)) {
            System.out.println("Cancellation failed: Cancellation deadline has passed.");
            return false;
        }

        active = false;
        System.out.println("Reservation for " + room.getName()
                + " cancelled successfully.");
        return true;
    }
}

class BookingManager {
    public Reservation book(Room room, Customer customer,
                            LocalDate checkIn, LocalDate checkOut,
                            LocalDate cancellationDeadline) {
        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Booking failed: Invalid date range.");
            return null;
        }

        if (!room.isAvailable(checkIn, checkOut)) {
            System.out.println("Booking failed: " + room.getName()
                    + " is not available for " + checkIn
                    + " to " + checkOut + ".");
            return null;
        }

        Reservation reservation = new Reservation(
                room, customer, checkIn, checkOut, cancellationDeadline);

        room.addReservation(reservation);

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        System.out.println(room.getName() + " booked from "
                + checkIn + " to " + checkOut + ".");
        System.out.printf("Total price: $%.2f%n",
                room.calculatePrice(nights));

        return reservation;
    }
}

public class Main {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        Customer customer = new Customer("Ayush");

        Room deluxe = new DeluxeRoom("Deluxe Room 101");
        Room standard = new StandardRoom("Standard Room 205");

        LocalDate checkIn1 = LocalDate.of(2024, 12, 1);
        LocalDate checkOut1 = LocalDate.of(2024, 12, 5);

        LocalDate checkIn2 = LocalDate.of(2024, 12, 3);
        LocalDate checkOut2 = LocalDate.of(2024, 12, 7);

        LocalDate deadline = LocalDate.of(2024, 11, 30);

        Reservation r1 = manager.book(
                deluxe, customer, checkIn1, checkOut1, deadline);

        manager.book(
                standard, customer, checkIn2, checkOut2, deadline);

        manager.book(
                deluxe, customer, checkIn2, checkOut2, deadline);

        if (r1 != null) {
            r1.cancel();
        }
    }
}