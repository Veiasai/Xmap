package xyz.xmap.result;

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
