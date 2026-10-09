package week8.class_problems.food_ordering;

import java.util.ArrayList;
import java.util.List;

abstract class Payment {
    protected double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    public abstract void pay();
}

class CashPayment extends Payment {
    public CashPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.printf("Paid ₹%.2f using Cash.%n", amount);
    }
}

class CardPayment extends Payment {
    public CardPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.printf("Paid ₹%.2f using Card.%n", amount);
    }
}

class UPIPayment extends Payment {
    public UPIPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.printf("Paid ₹%.2f using UPI.%n", amount);
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class FoodOrder {
    private List<FoodItem> items = new ArrayList<>();

    public void addItem(FoodItem item) {
        if (item.getPrice() > 0) {
            items.add(item);
            System.out.println(item.getName() + " added to order.");
        }
    }

    public double getTotal() {
        double total = 0;

        for (FoodItem item : items) {
            total += item.getPrice();
        }

        return total;
    }

    public void displayOrder() {
        System.out.println("\nOrder Details:");

        for (FoodItem item : items) {
            System.out.printf("%s: ₹%.2f%n",
                    item.getName(), item.getPrice());
        }

        System.out.printf("Total: ₹%.2f%n", getTotal());
    }

    public void checkout(Payment payment) {
        if (items.isEmpty()) {
            System.out.println("Cannot checkout: Order is empty.");
            return;
        }

        if (payment == null) {
            System.out.println("Payment method is required.");
            return;
        }

        payment.pay();
        items.clear();
        System.out.println("Order completed successfully.");
    }
}

public class Main {
    public static void main(String[] args) {
        FoodOrder order = new FoodOrder();

        order.addItem(new FoodItem("Burger", 120));
        order.addItem(new FoodItem("Pizza", 250));
        order.addItem(new FoodItem("Coffee", 80));

        order.displayOrder();

        order.checkout(new UPIPayment(order.getTotal()));
    }
}