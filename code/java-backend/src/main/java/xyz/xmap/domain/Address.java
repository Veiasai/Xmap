package xyz.xmap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@NodeEntity
public class Address {
    @Id
    @NotNull
    private String address;

    @JsonIgnore
    @Relationship(type = "IN-ADDRESS")
    private Collection<Building> buildings;

    public Address() {
    }

    public Address(@NotNull String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(Collection<Building> buildings) {
        this.buildings = buildings;
    }
}
