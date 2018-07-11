package xyz.veiasai.neo4j.domain.relation;

import org.neo4j.ogm.annotation.*;
import xyz.veiasai.neo4j.domain.Node;
import xyz.veiasai.neo4j.domain.Path;

@RelationshipEntity(type = "PATH")
public class PATH {
    @Property(name = "node")
    private String node;

    @Property(name = "path")
    private String path;

    @StartNode
    private Path path1;

    @EndNode
    private Node node1;

    public Path getPath1() {
        return path1;
    }

    public void setPath1(Path path1) {
        this.path1 = path1;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
