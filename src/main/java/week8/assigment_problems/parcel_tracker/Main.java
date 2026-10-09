package week8.assigment_problems.parcel_tracker;

import java.util.*;

enum ParcelStatus {
    BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

interface ShippingType {
    double calculateCharge(double weight);
    String getName();
}

class StandardShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 40 + 10 * weight;
    }

    public String getName() {
        return "Standard";
    }
}

class ExpressShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 80 + 15 * weight;
    }

    public String getName() {
        return "Express";
    }
}

class FragileShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 40 + 10 * weight + 50;
    }

    public String getName() {
        return "Fragile";
    }
}

interface NotificationChannel {
    void notify(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    public void notify(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    public void notify(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Parcel {
    private String id;
    private double weight;
    private ShippingType shippingType;
    private ParcelStatus status = ParcelStatus.BOOKED;
    private List<NotificationChannel> channels;

    Parcel(String id, double weight, ShippingType shippingType,
           List<NotificationChannel> channels) {
        this.id = id;
        this.weight = weight;
        this.shippingType = shippingType;
        this.channels = new ArrayList<>(channels);
    }

    public double getCharge() {
        return shippingType.calculateCharge(weight);
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public void updateStatus(ParcelStatus next) {
        if (status == ParcelStatus.BOOKED && next == ParcelStatus.CANCELLED) {
            status = ParcelStatus.CANCELLED;
            notifyChannels();
            return;
        }

        if (status == ParcelStatus.CANCELLED || status == ParcelStatus.DELIVERED) {
            System.out.println("Invalid transition: " + status + " → " + next
                    + " is not allowed.");
            return;
        }

        if (next.ordinal() != status.ordinal() + 1) {
            System.out.println("Invalid transition: " + status + " → " + next
                    + " is not allowed.");
            return;
        }

        status = next;
        notifyChannels();
    }

    public void cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + id
                    + " can be cancelled only while BOOKED.");
            return;
        }

        updateStatus(ParcelStatus.CANCELLED);
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(id, status);
        }
    }

    public String getId() {
        return id;
    }

    public String getShippingName() {
        return shippingType.getName();
    }

    public double getWeight() {
        return weight;
    }
}

class ParcelService {
    public Parcel bookParcel(String id, double weight, ShippingType shippingType,
                             List<NotificationChannel> channels) {
        Parcel parcel = new Parcel(id, weight, shippingType, channels);

        System.out.println("Parcel " + id + " booked (" +
                shippingType.getName() + ", " + weight + " kg).");

        System.out.printf(Locale.US, "Charge: ₹%.2f.%n", parcel.getCharge());

        parcel.updateStatus(ParcelStatus.BOOKED);

        return parcel;
    }
}

public class Main {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();

        List<NotificationChannel> channels = Arrays.asList(
                new SmsChannel(),
                new EmailChannel()
        );

        Parcel parcel = service.bookParcel(
                "P101", 2, new ExpressShipping(), channels
        );

        parcel.updateStatus(ParcelStatus.PICKED_UP);
        parcel.cancel();
        parcel.updateStatus(ParcelStatus.IN_TRANSIT);
        parcel.updateStatus(ParcelStatus.DELIVERED);
    }
}