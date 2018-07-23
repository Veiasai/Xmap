package xyz.veiasai.neo4j.result;

import xyz.veiasai.neo4j.domain.CountSum;

import java.util.Collection;

public class CountSumResult extends Result {
    private Collection<CountSum> countSums;

    public Collection<CountSum> getCountSums() {
        return countSums;
    }

    public void setCountSums(Collection<CountSum> countSums) {
        this.countSums = countSums;
    }
}
