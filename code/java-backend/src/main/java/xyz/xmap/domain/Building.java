package xyz.xmap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import xyz.xmap.converter.LocationConverter;
import xyz.xmap.pojo.Location;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@NodeEntity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Building {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    private String id;

    @NotNull
    private String name;

    @Convert(LocationConverter.class)
    private Location location;

    @Relationship(type = "IN-ADDRESS", direction = Relationship.INCOMING)
    private Address address;

    @JsonIgnore
    @Relationship(type="BUILDING")
    private Collection<Node> nodes = new HashSet<>();

    @JsonIgnore
    @Relationship(type="BUILDING")
    private Collection<Path> paths = new HashSet<>();

    public Building() {
    }

    public Building(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public Collection<Path> getPaths() {
        return paths;
    }

    public void setPaths(Collection<Path> paths) {
        this.paths = paths;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
