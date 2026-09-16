package week4.assigment_problems;

class DeliveryAccount {
    static String kitchenName;

    static {
        kitchenName = "SRM Central Kitchen";
    }

    protected String studentId;
    protected double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        if (studentId == null || studentId.trim().isEmpty() || orderValue < 0) {
            throw new IllegalArgumentException("Invalid account");
        }

        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid delay");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double fee = 0.0;

        int firstTier = Math.min(delayMinutes, 5);
        fee += firstTier * orderValue * 0.005;

        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes - 5, 10);
            fee += secondTier * orderValue * 0.01;
        }

        if (delayMinutes > 15) {
            int thirdTier = delayMinutes - 15;
            fee += thirdTier * orderValue * 0.02;
        }

        return fee;
    }

    public void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        if (account == null) {
            return;
        }

        double surgeFee = account.calculateSurgeFee(delayMinutes);

        System.out.println(
                account.studentId +
                " processed | Amount: Rs " + amount +
                " | Surge fee: Rs " + surgeFee
        );
    }

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        if (accounts == null ||
            amounts == null ||
            delayMinutesArray == null) {
            return;
        }

        int length = Math.min(
                accounts.length,
                Math.min(amounts.length, delayMinutesArray.length)
        );

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double totalSurgeFees = 0.0;

        for (int i = 0; i < length; i++) {
            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {
                account.processAccount(
                        account,
                        amounts[i],
                        delayMinutesArray[i]
                );

                totalSurgeFees +=
                        account.calculateSurgeFee(delayMinutesArray[i]);

                processed++;

                if (account instanceof PremiumDeliveryAccount) {
                    premium++;
                } else {
                    regular++;
                }
            } catch (IllegalArgumentException e) {
                nullSkipped++;
            }
        }

        System.out.println(
                processed + " processed | " +
                nullSkipped + " null skipped | " +
                premium + " premium | " +
                regular + " regular | grand total surge fees = Rs " +
                totalSurgeFees
        );
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {500, 400, 300};
        int[] delays = {10, 5, 0};

        processBatch(accounts, amounts, delays);
    }
}

class PremiumDeliveryAccount extends DeliveryAccount {

    public PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumDeliveryAccount(String studentId) {
        super(studentId);
    }

    @Override
    public void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        double surgeFee = calculateSurgeFee(delayMinutes);

        System.out.println(
                account.studentId +
                " premium processed | Amount: Rs " +
                amount +
                " | Surge fee: Rs " +
                surgeFee
        );
    }
}