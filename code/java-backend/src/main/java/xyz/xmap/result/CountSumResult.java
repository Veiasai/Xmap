package xyz.xmap.result;

import xyz.xmap.domain.CountSum;

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
