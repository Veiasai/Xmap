package xyz.veiasai.neo4j.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.id.UuidStrategy;

import java.util.Collection;
import java.util.HashSet;

@NodeEntity
@ApiModel(value = "数据组")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSet {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @ApiModelProperty(hidden = true)
    private String id;

    @JsonIgnore
    @Relationship(type="NODE-IN-DATASET")
    @ApiModelProperty(value = "点位组",required = true)
    private Collection<Node> nodes = new HashSet<>();

    @Relationship(type="AUTHOR")
    private Author author;

    @Relationship(type="BUILDING")
    private Building building;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
