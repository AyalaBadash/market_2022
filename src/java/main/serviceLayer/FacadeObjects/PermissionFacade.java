package main.serviceLayer.FacadeObjects;

public class PermissionFacade {
    private String name;

    public PermissionFacade(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
