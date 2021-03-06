package xyz.xmap.result;

import xyz.xmap.domain.Path;

import java.util.Collection;

public class PathResult extends Result {
    private Collection<Path> Paths;
    private Path path;

    public Collection<Path> getPaths() {
        return Paths;
    }

    public void setPaths(Collection<Path> paths) {
        Paths = paths;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
