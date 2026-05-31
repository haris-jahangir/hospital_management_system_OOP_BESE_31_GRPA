package billing;

import Services.Service;

public class BillableService implements Billable {

    private Service service;

    public BillableService(Service service) {
        this.service = service;
    }

    @Override
    public double calculateCost() {
        return service.getCost();
    }

    @Override
    public String getServiceName() {
        return service.getServiceType() + " [" + service.getServiceID() + "]";
    }

    @Override
    public String getServiceCategory() {
        return service.getServiceType();
    }

    public Service getService() {
        return service;
    }
}