package xyz.veiasai.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.id.UuidStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@NodeEntity
@ApiModel(value = "点位")
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Node {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(value = "点位名称", required = true)
    private String name;

    @ApiModelProperty(hidden = true)
    private Integer state;

    @ApiModelProperty(value = "点位图片的uuid", required = true)
    private String img;

    @ApiModelProperty(hidden = true)
    @Relationship(type = "BUILDING", direction = Relationship.INCOMING)
    private Building building;

    @ApiModelProperty(hidden = true)
    @Relationship(type = "AUTHOR", direction = Relationship.INCOMING)
    private Author author;

    @JsonIgnore
    @Relationship(type = "PATH")
    private Set<Path> paths = new HashSet<>();

    public Node() {
    }

    public Node(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(Set<Path> paths) {
        this.paths = paths;
    }

    public void addPath(Path path) {
        paths.add(path);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
