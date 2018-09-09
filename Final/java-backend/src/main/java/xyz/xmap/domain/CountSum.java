package xyz.xmap.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class CountSum {

    private Building building;

    private int nodeSum;

    private int pathSum;

    private int messageSum;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getNodeSum() {
        return nodeSum;
    }

    public void setNodeSum(int nodeSum) {
        this.nodeSum = nodeSum;
    }

    public int getPathSum() {
        return pathSum;
    }

    public void setPathSum(int pathSum) {
        this.pathSum = pathSum;
    }

    public int getMessageSum() {
        return messageSum;
    }

    public void setMessageSum(int messageSum) {
        this.messageSum = messageSum;
    }
}
