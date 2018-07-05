package xyz.veiasai.neo4j.result;

import xyz.veiasai.neo4j.domain.DataSet;

import java.util.Collection;

public class DataSetResult extends Result {
    private DataSet dataSet;

    private Collection<DataSet> DataSets;

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public Collection<DataSet> getDataSets() {
        return DataSets;
    }

    public void setDataSets(Collection<DataSet> dataSets) {
        DataSets = dataSets;
    }
}
