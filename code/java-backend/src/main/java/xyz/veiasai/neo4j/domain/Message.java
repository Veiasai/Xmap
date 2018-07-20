package xyz.veiasai.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.id.UuidStrategy;
import org.springframework.data.annotation.CreatedDate;

@NodeEntity
public class Message {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    @ApiModelProperty(hidden = true)
    private  String id;

    private String title;

    private String authorName;

    @ApiModelProperty(hidden = true)
    private String Date;

    private String content;

    @Relationship(type = "AUTHOR")
    @JsonIgnore
    private Author author;

    @Relationship(type = "BUILDING")
    @JsonIgnore
    private Building building;

    @ApiModelProperty(hidden = true)
    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
