package week5.assigment_problems;

public class LibraryMember {
    private String membershipId;
    protected String branchCode;
    private double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        String id = membershipId == null ? "" : membershipId.trim();

        if (id.isEmpty() || id.length() < 4) {
            throw new IllegalArgumentException("Invalid membershipId");
        }

        this.membershipId = id;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";

            case "default":
                return accessorContext.equals("SAME_CLASS") ||
                       accessorContext.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

            case "protected":
                return accessorContext.equals("SAME_CLASS") ||
                       accessorContext.equals("SAME_PACKAGE") ? "ALLOWED" : "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    public static String summarizeByModifier(String[][] attempts) {
        int privateAllowed = 0, privateDenied = 0;
        int defaultAllowed = 0, defaultDenied = 0;
        int protectedAllowed = 0, protectedDenied = 0;
        int publicAllowed = 0, publicDenied = 0;

        for (String[] attempt : attempts) {
            String modifier = attempt[0];
            String context = attempt[1];

            boolean allowed = classifyAccess(modifier, context).equals("ALLOWED");

            switch (modifier) {
                case "private":
                    if (allowed) privateAllowed++;
                    else privateDenied++;
                    break;

                case "default":
                    if (allowed) defaultAllowed++;
                    else defaultDenied++;
                    break;

                case "protected":
                    if (allowed) protectedAllowed++;
                    else protectedDenied++;
                    break;

                case "public":
                    if (allowed) publicAllowed++;
                    else publicDenied++;
                    break;
            }
        }

        return "private: " + privateAllowed + " allowed / " + privateDenied +
               " denied | default: " + defaultAllowed + " allowed / " + defaultDenied +
               " denied | protected: " + protectedAllowed + " allowed / " + protectedDenied +
               " denied | public: " + publicAllowed + " allowed / " + publicDenied + " denied";
    }

    public static void main(String[] args) {
        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));
        System.out.println(summarizeByModifier(attempts));

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}