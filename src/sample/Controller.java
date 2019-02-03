package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    private double eNa;
    private double eL;
    private double eK;
    private double c;
    private double gNa;
    private double gK;
    private double gL;
    private boolean CE = true;

    @FXML
    private LineChart<?, ?> utChart;

    @FXML
    private LineChart<?, ?> secondChart;

    @FXML
    private Text freq;

    @FXML
    private Text max;

    @FXML
    private Text enaText;

    @FXML
    private Text elText;

    @FXML
    private Text eText;

    @FXML
    private Text cText;

    @FXML
    private ComboBox<String> combobox;

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

    @FXML
    void typePicked(ActionEvent event) {
        System.out.println(combobox.getValue());
        if(combobox.getValue() == "C, E..."){
            enaText.setText("E(Na)");
            elText.setText("E(L)");
            eText.setText("E(K)");
            cText.setVisible(true);
            paramFour.setVisible(true);
            cText.setText("C");
            paramOne.setText(Double.toString(eNa));
            paramTwo.setText(Double.toString(eK));
            paramThree.setText(Double.toString(eL));
            paramFour.setText(Double.toString(c));
            CE = true;
        } else {
            enaText.setText("g(Na)");
            elText.setText("g(K)");
            eText.setText("g(L)");
            paramOne.setText(Double.toString(gNa));
            paramTwo.setText(Double.toString(gK));
            paramThree.setText(Double.toString(gL));
            cText.setVisible(false);
            paramFour.setVisible(false);
            CE = false;
        }
    }

    @FXML
    void initialize(){
        combobox.getItems().removeAll(combobox.getItems());
        combobox.getItems().addAll("C, E...", "g Na, g K...");
        combobox.getSelectionModel().select("C, E...");
        c = 1.0;
        eNa = 115.0;
        eK = -12.0;
        eL = 10.6;
        gNa = 120.0;
        gK = 36.0;
        gL = 0.3;

        paramOne.setText(Double.toString(eNa));
        paramTwo.setText(Double.toString(eK));
        paramThree.setText(Double.toString(eL));
        paramFour.setText(Double.toString(c));

        assign();
    }

    public void assign(){
        try {
            if (CE) {
                eNa = Double.parseDouble(paramOne.getText());
                paramOne.setText(Double.toString(eNa));
                eL = Double.parseDouble(paramTwo.getText());
                paramTwo.setText(Double.toString(eL));
                eK = Double.parseDouble(paramThree.getText());
                paramThree.setText(Double.toString(eK));
                c = Double.parseDouble(paramFour.getText());
                paramFour.setText(Double.toString(c));

                System.out.println("eNa = " + eNa + " eL " + eL + " eK" + eK + " C " + c);
                System.out.println("gNa = " + gNa + " gK " + gK + " eL " + eL);
            } else {
                gNa = Double.parseDouble(paramOne.getText());
                paramOne.setText(Double.toString(gNa));
                gK = Double.parseDouble(paramTwo.getText());
                paramTwo.setText(Double.toString(gK));
                gL = Double.parseDouble(paramThree.getText());
                paramThree.setText(Double.toString(gL));
                System.out.println("eNa = " + eNa + " eL " + eL + " eK" + eK + " C " + c);
                System.out.println("gNa = " + gNa + " gK " + gK + " eL" + eL);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

    }




}
