package xyz.xmap.result;

import xyz.xmap.domain.Author;

import java.util.Collection;

public class BuildingAdminResult extends Result {
    private Author buildingAdmin;

    private Collection<Author> BuildingAdmins;

    public Author getBuildingAdmin() {
        return buildingAdmin;
    }

    public void setBuildingAdmin(Author buildingAdmin) {
        this.buildingAdmin = buildingAdmin;
    }

    public Collection<Author> getBuildingAdmins() {
        return BuildingAdmins;
    }

    public void setBuildingAdmins(Collection<Author> buildingAdmins) {
        BuildingAdmins = buildingAdmins;
    }
}
