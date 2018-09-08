package xyz.veiasai.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.id.UuidStrategy;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@NodeEntity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Author {
    @Id
    @NotNull
    private String id;

    private Integer state;

    @JsonIgnore
    @Relationship(type="AUTHOR")
    private Collection<Node> nodes = new HashSet<>();

    @JsonIgnore
    @Relationship(type="AUTHOR")
    private Collection<Path> paths = new HashSet<>();

    public Author() {
    }

    public Author(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
}
