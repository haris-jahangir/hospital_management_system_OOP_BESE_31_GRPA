package billing;

public interface Billable {
    double calculateCost();
    String getServiceName();
    String getServiceCategory();
}