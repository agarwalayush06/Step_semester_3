package week8.assigment_problems.canteen_smart_card;

import java.util.*;

interface PricingPlan {
    double calculatePrice(double price);
}

class DayScholarPlan implements PricingPlan {
    public double calculatePrice(double price) {
        return price;
    }
}

class HostellerPlan implements PricingPlan {
    public double calculatePrice(double price) {
        return price * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    public double calculatePrice(double price) {
        return price * 0.80;
    }
}

class Transaction {
    private String description;
    private double amount;

    Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

class Purchase {
    private String itemName;
    private double amount;
    private boolean refunded = false;

    Purchase(String itemName, double amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

class SmartCard {
    private String cardId;
    private PricingPlan plan;
    private double balance = 0;
    private boolean blocked = false;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Purchase> purchases = new ArrayList<>();

    SmartCard(String cardId, PricingPlan plan) {
        this.cardId = cardId;
        this.plan = plan;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up rejected: Card is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up rejected: Minimum top-up is ₹100.");
            return;
        }

        if (balance + amount > 5000) {
            System.out.println("Top-up rejected: Maximum balance is ₹5000.");
            return;
        }

        balance += amount;
        transactions.add(new Transaction("Top-up", amount));

        System.out.printf(Locale.US,
                "%s topped up with ₹%.2f.%n", cardId, amount);
        printBalance();
    }

    public void purchase(String itemName, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase rejected: Card is blocked.");
            return;
        }

        if (originalPrice <= 0) {
            System.out.println("Purchase rejected: Invalid item price.");
            return;
        }

        double charged = Math.round(
                plan.calculatePrice(originalPrice) * 100.0) / 100.0;

        if (balance < charged) {
            System.out.printf(Locale.US,
                    "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    charged, balance);
            return;
        }

        balance -= charged;
        transactions.add(new Transaction(itemName, -charged));
        purchases.add(new Purchase(itemName, charged));

        System.out.printf(Locale.US,
                "%s purchased for ₹%.2f.%n", itemName, charged);
        printBalance();
    }

    public void refund(String itemName) {
        for (Purchase purchase : purchases) {
            if (purchase.getItemName().equals(itemName)
                    && !purchase.isRefunded()) {

                purchase.markRefunded();
                balance += purchase.getAmount();

                transactions.add(new Transaction(
                        "Refund: " + itemName, purchase.getAmount()));

                System.out.printf(Locale.US,
                        "Refund of ₹%.2f for %s processed.%n",
                        purchase.getAmount(), itemName);
                printBalance();
                return;
            }
        }

        for (Purchase purchase : purchases) {
            if (purchase.getItemName().equals(itemName)
                    && purchase.isRefunded()) {
                System.out.println("Refund rejected: " + itemName
                        + " has already been refunded.");
                return;
            }
        }

        System.out.println("Refund rejected: Purchase not found.");
    }

    public void block() {
        blocked = true;
        System.out.println(cardId + " blocked.");
    }

    public void unblock() {
        blocked = false;
        System.out.println(cardId + " unblocked.");
    }

    public void miniStatement() {
        System.out.print("Mini-statement for " + cardId + ": ");

        double total = 0;

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            double amount = transaction.getAmount();
            total += amount;

            System.out.printf(Locale.US,
                    "%s%.2f",
                    amount >= 0 ? "+" : "",
                    amount);

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(Locale.US, " = ₹%.2f.%n", total);
    }

    private void printBalance() {
        System.out.printf(Locale.US, "Balance: ₹%.2f.%n", balance);
    }

    public double getBalance() {
        return balance;
    }
}

public class Main {
    public static void main(String[] args) {
        SmartCard card = new SmartCard(
                "C-2045", new HostellerPlan());

        card.topUp(500);
        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Other Items", 400);
        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.miniStatement();
    }
}