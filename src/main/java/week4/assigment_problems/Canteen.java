package week4.assigment_problems;

public class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }

        int codeComparison =
                this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeComparison != 0) {
            return codeComparison;
        }

        return Integer.compare(
                this.canteenName.length(),
                other.canteenName.length()
        );
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] result = canteens.clone();

        for (int i = 1; i < result.length; i++) {
            Canteen current = result[i];
            int j = i - 1;

            while (j >= 0 && result[j].compareTo(current) > 0) {
                result[j + 1] = result[j];
                j--;
            }

            result[j + 1] = current;
        }

        return result;
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(canteens);

        for (Canteen canteen : ranked) {
            System.out.println(canteen.canteenCode);
        }
    }
}