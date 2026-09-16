package week5.class_problems;

public class DischargeSummary {
    private static String ledgerName;

    static {
        ledgerName = "Nightly Discharge Ledger";
    }

    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || medicationCodes == null) {
            throw new IllegalArgumentException("Invalid discharge");
        }

        String[] copy = medicationCodes.clone();

        for (String code : copy) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("Invalid medication code");
            }
        }

        this.patientId = patientId;
        this.medicationCodes = copy;
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length ||
            newCode == null ||
            !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException("Invalid medication code");
        }

        String[] corrected = medicationCodes.clone();
        corrected[index] = newCode;

        return new DischargeSummary(patientId, corrected);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               criticalCare + " critical-care | " +
               routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary(
                "MT2026-0142",
                new String[]{"MED-A", "bad"}
            );
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        DischargeSummary d = new DischargeSummary(
            "MT2026-0142",
            new String[]{"MED-A", "MED-B"}
        );

        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";

        System.out.println(d.getMedicationCodes()[0]);

        DischargeSummary[] summaries = {
            new CriticalCareDischargeSummary(
                "MT001",
                new String[]{"MED-X"},
                4
            ),
            null,
            new DischargeSummary(
                "MT002",
                new String[]{"MED-Y"}
            )
        };

        System.out.println(processNightlyBatch(summaries));
    }
}