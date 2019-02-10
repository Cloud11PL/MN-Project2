package sample;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;

public class CurrGraphWindow {

    @FXML
    private ScatterChart<Number, Number> currGraph;

    ScatterChart<Number, Number> getCurrGraph() {
        return currGraph;
    }

    /**
     * Sets graph labels.
     */
    @FXML
    void initialize() {
        currGraph.getXAxis().setLabel("Time [s]");
        currGraph.getYAxis().setLabel("Current [µ]");
    }
}
