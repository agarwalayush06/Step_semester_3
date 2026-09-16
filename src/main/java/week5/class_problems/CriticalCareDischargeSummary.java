package week5.class_problems;

public class CriticalCareDischargeSummary extends DischargeSummary {
    private final int severityLevel;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int severityLevel) {
        super(patientId, medicationCodes);
        this.severityLevel = severityLevel;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }
}