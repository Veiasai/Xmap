package xyz.xmap.result;

import xyz.xmap.domain.DataSet;
import xyz.xmap.domain.Node;
import xyz.xmap.domain.Path;

import java.util.Collection;

public class FavoriteResult extends Result {

    private Collection<Node> Nodes;

    private Collection<Path> Paths;

    private Collection<DataSet> DataSets;

    public Collection<Node> getNodes() {
        return Nodes;
    }

    public void setNodes(Collection<Node> nodes) {
        Nodes = nodes;
    }

    public Collection<Path> getPaths() {
        return Paths;
    }

    public void setPaths(Collection<Path> paths) {
        Paths = paths;
    }

    public Collection<DataSet> getDataSets() {
        return DataSets;
    }

    public void setDataSets(Collection<DataSet> dataSets) {
        DataSets = dataSets;
    }
}
