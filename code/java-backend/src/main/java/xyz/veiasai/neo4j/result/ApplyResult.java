package xyz.veiasai.neo4j.result;

import xyz.veiasai.neo4j.domain.Building;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ApplyResult extends Result {

    private Set<Map<String, Object>> Apply;

    public Set<Map<String, Object>> getApply() {
        return Apply;
    }

    public void setApply(Set<Map<String, Object>> apply) {
        Apply = apply;
    }
}
