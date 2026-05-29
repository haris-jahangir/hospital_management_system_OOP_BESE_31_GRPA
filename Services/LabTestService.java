package Services;

import Actors.Patient;
import Actors.Doctor;
import java.util.HashMap;
import java.util.Map;

public class LabTestService extends Service {
    private String testType;
    private String technician;
    private String result;
    private boolean isAbnormal;

    // Cost mapping for different test types
    private static final Map<String, Double> TEST_COSTS = new HashMap<>();
    static {
        TEST_COSTS.put("Blood Test", 50.0);
        TEST_COSTS.put("Urine Test", 40.0);
        TEST_COSTS.put("X-Ray", 150.0);
        TEST_COSTS.put("MRI", 500.0);
        TEST_COSTS.put("CT Scan", 400.0);
        TEST_COSTS.put("Ultrasound", 200.0);
        TEST_COSTS.put("ECG", 80.0);
    }

    public LabTestService(String serviceID, Patient patient, Doctor doctor, String testType) {
        super(serviceID, patient, doctor, "LAB_TEST");
        this.testType = testType;
        this.cost = TEST_COSTS.getOrDefault(testType, 100.0);
        this.isAbnormal = false;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public void setResult(String result, boolean isAbnormal) {
        this.result = result;
        this.isAbnormal = isAbnormal;
    }

    public String getTestType() {
        return testType;
    }

    public String getResult() {
        return result;
    }

    public boolean isAbnormal() {
        return isAbnormal;
    }

    @Override
    public void execute() {
        if (!status.equals("PENDING") && !status.equals("IN_PROGRESS")) {
            System.out.println("[ERROR] Cannot execute lab test in status: " + status);
            return;
        }

        start();
        System.out.println("[LAB TEST] " + testType + " ordered by Dr. " + doctor.getName() +
                " for patient " + patient.getName());

        if (technician != null) {
            System.out.println("  Technician: " + technician);
        }

        if (result != null) {
            System.out.println("  Result: " + result);
            System.out.println("  Status: " + (isAbnormal ? "ABNORMAL - Requires follow-up" : "Normal"));
            patient.addHistoryEntry(testType + " result: " + result + (isAbnormal ? " (ABNORMAL)" : ""));
        }

        complete();
    }

    @Override
    public void displayDetails() {
        System.out.println("___ LAB TEST DETAILS ___");
        System.out.println("Service ID: " + serviceID);
        System.out.println("Patient: " + patient.getName() + " (" + patient.getID() + ")");
        System.out.println("Doctor: " + doctor.getName() + " (" + doctor.getID() + ")");
        System.out.println("Test Type: " + testType);
        System.out.println("Technician: " + (technician != null ? technician : "Not assigned"));
        System.out.println("Result: " + (result != null ? result : "Pending"));
        System.out.println("Abnormal: " + (isAbnormal ? "YES" : "NO"));
        System.out.println("Status: " + status);
        System.out.println("Cost: $" + cost);
        System.out.println("________________________");
    }
}