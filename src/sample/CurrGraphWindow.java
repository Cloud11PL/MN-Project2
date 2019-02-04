package sample;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;

public class CurrGraphWindow {

    @FXML
    private ScatterChart<Number, Number> currGraph;

    public ScatterChart<Number, Number> getCurrGraph() {
        return currGraph;
    }

    public void setCurrGraph(ScatterChart<Number, Number> currGraph) {
        this.currGraph = currGraph;
    }

    @FXML
    void initialize() {
        currGraph.getXAxis().setLabel("Time [s]");
        currGraph.getYAxis().setLabel("Current [µ]");
    }
}
