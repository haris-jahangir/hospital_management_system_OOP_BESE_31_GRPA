package Services;

import Actors.Patient;
import java.util.HashMap;
import java.util.Map;

public class ServiceRecordManager {
    private static ServiceRecordManager instance;
    private Map<String, ServiceRecord> patientRecords;

    private ServiceRecordManager() {
        patientRecords = new HashMap<>();
    }

    public static ServiceRecordManager getInstance() {
        if (instance == null) {
            instance = new ServiceRecordManager();
        }
        return instance;
    }

    public ServiceRecord getServiceRecord(Patient patient) {
        String patientId = patient.getID();
        if (!patientRecords.containsKey(patientId)) {
            patientRecords.put(patientId, new ServiceRecord(patient));
        }
        return patientRecords.get(patientId);
    }

    public void addServiceToPatient(Patient patient, Service service) {
        ServiceRecord record = getServiceRecord(patient);
        record.addService(service);
    }

    public void displayPatientRecord(Patient patient) {
        ServiceRecord record = getServiceRecord(patient);
        record.displaySummary();
    }
}