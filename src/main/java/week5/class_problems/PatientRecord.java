package week5.class_problems;

public class PatientRecord {
    private String patientId;
    protected String wardCode;
    double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().isEmpty() || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid patientId");
        }

        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}