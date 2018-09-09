package xyz.xmap.result;

import xyz.xmap.domain.Building;

import java.util.Collection;

public class BuildingResult extends Result {

    private Building building;

    private Collection<Building> Buildings;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Collection<Building> getBuildings() {
        return Buildings;
    }

    public void setBuildings(Collection<Building> buildings) {
        Buildings = buildings;
    }
}
