package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private LineChart<?, ?> utChart;

    @FXML
    private LineChart<?, ?> secondChart;

    @FXML
    private Text freq;

    @FXML
    private Text max;

    @FXML
    private TextField paramOne;

    @FXML
    private TextField paramTwo;

    @FXML
    private TextField paramThree;

    @FXML
    private TextField paramFour;

    @FXML
    void runPressed(ActionEvent event) {

    }

}
