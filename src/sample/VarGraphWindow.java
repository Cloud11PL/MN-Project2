package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;

public class VarGraphWindow {

    @FXML
    private ScatterChart<Number, Number> varGraph;

    public ScatterChart<Number, Number> getVarGraph() {
        return varGraph;
    }

    /**
     * Sets labels.
     */
    @FXML
    void initialize() {
        varGraph.getXAxis().setLabel("j.u.");
        varGraph.getYAxis().setLabel("j.u.");
    }
}