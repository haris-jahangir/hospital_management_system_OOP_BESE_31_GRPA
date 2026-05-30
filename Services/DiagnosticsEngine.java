package Services;

import Actors.Patient;
import Actors.Doctor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiagnosticsEngine {
    private static final Map<String, String[]> DIAGNOSIS_RULES = new HashMap<>();

    static {
        // Mapping: diagnosis keyword -> recommended lab tests
        DIAGNOSIS_RULES.put("fever", new String[] { "Blood Test", "Urine Test" });
        DIAGNOSIS_RULES.put("infection", new String[] { "Blood Test", "Urine Test" });
        DIAGNOSIS_RULES.put("chest pain", new String[] { "ECG", "Blood Test", "X-Ray" });
        DIAGNOSIS_RULES.put("heart", new String[] { "ECG", "Blood Test" });
        DIAGNOSIS_RULES.put("fracture", new String[] { "X-Ray" });
        DIAGNOSIS_RULES.put("headache", new String[] { "CT Scan", "MRI" });
        DIAGNOSIS_RULES.put("pregnancy", new String[] { "Ultrasound", "Blood Test" });
        DIAGNOSIS_RULES.put("diabetes", new String[] { "Blood Test", "Urine Test" });
    }

    public static ArrayList<String> recommendTests(String diagnosis) {
        ArrayList<String> tests = new ArrayList<>();
        diagnosis = diagnosis.toLowerCase();

        for (Map.Entry<String, String[]> rule : DIAGNOSIS_RULES.entrySet()) {
            if (diagnosis.contains(rule.getKey())) {
                for (String test : rule.getValue()) {
                    if (!tests.contains(test)) {
                        tests.add(test);
                    }
                }
            }
        }

        return tests;
    }

    public static ArrayList<String> recommendTreatments(String diagnosis) {
        ArrayList<String> treatments = new ArrayList<>();
        diagnosis = diagnosis.toLowerCase();

        if (diagnosis.contains("infection")) {
            treatments.add("Medication");
        }
        if (diagnosis.contains("fracture")) {
            treatments.add("Surgery");
            treatments.add("Physiotherapy");
        }
        if (diagnosis.contains("heart")) {
            treatments.add("Medication");
        }
        if (diagnosis.contains("diabetes")) {
            treatments.add("Medication");
        }
        if (diagnosis.contains("wound")) {
            treatments.add("Wound Care");
        }

        if (treatments.isEmpty()) {
            treatments.add("Medication"); // Default
        }

        return treatments;
    }

    public static void runDiagnosticWorkflow(ConsultationService consultation,
            ServiceRecord record,
            String serviceIdPrefix) {
        String diagnosis = consultation.getDiagnosis();
        if (diagnosis == null || diagnosis.isEmpty()) {
            System.out.println("[DIAGNOSTICS] No diagnosis provided. Cannot recommend tests.");
            return;
        }

        System.out.println("\n[DIAGNOSTICS ENGINE] Analyzing diagnosis: " + diagnosis);

        // Recommend lab tests
        ArrayList<String> tests = recommendTests(diagnosis);
        if (!tests.isEmpty()) {
            System.out.println("[DIAGNOSTICS] Recommended Lab Tests: " + tests);
            // Note: In real implementation, would auto-create LabTestService objects
        }

        // Recommend treatments
        ArrayList<String> treatments = recommendTreatments(diagnosis);
        if (!treatments.isEmpty()) {
            System.out.println("[DIAGNOSTICS] Recommended Treatments: " + treatments);
        }
    }
}