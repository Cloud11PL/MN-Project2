package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private ArrayList<Double> time;
    private ArrayList<Double> uValues;

    private double eNa;
    private double eL;
    private double eK;
    private double c;
    private double gNa;
    private double gK;
    private double gL;
    private boolean CE = true;

    private XYChart.Series INaSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> IKSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> ILSeries = new XYChart.Series<>();
    private XYChart.Series mSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> nSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> hSeries = new XYChart.Series<Number, Number>();
    private XYChart.Series<Number, Number> huSeries = new XYChart.Series<Number, Number>();
    private XYChart.Series nuSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> muSeries = new XYChart.Series<>();
    private XYChart.Series uSeries = new XYChart.Series();
    private XYChart.Series ISeries = new XYChart.Series();

    @FXML
    private Button buttonVar;

    @FXML
    private Button buttonRun;

    @FXML
    private Button buttonCurr;

    @FXML
    private LineChart<Number, Number> utChart;

    @FXML
    private ScatterChart<Number, Number> secondChart;

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

    /**
     * Method, attached to an onClick event, that takes set parameters and executes functions that calculate specific courses of parameters.
     * This method is also using `Platform.runLater` to display changes on the JavaFX thread.
     * @param event
     */
    @FXML
    void runPressed(ActionEvent event) {
        clearSeries();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(() -> d.run());
    }
    Thread d = new Thread(){
        public void run() {
            buttonRun.setDisable(true);
            System.out.println(Thread.currentThread().getName());

            double I = 0;

            FirstOrderDifferentialEquations ode = new BigSquidNeuronODE(c,eNa,eK,eL,gNa,gK,gL,I);
            BigSquidNeuronPath path = new BigSquidNeuronPath(eNa,eK,eL,gNa,gK,gL);
            FirstOrderIntegrator integrator1 = new EulerIntegrator(0.01);
            integrator1.addStepHandler(path);

            double Tk = 0.001;
            double te = 50;
            double T15 = (0.15 * te);

            double u0 = 0;
            double am = (0.1 * (25 - u0)) / (Math.exp((25 - u0) / 10) - 1); //17a
            double bm = 4 * Math.exp(-u0 / 18); //17b
            double an = (0.01 * (10 - u0)) / (Math.exp((10 - u0) / 10) - 1); //18a
            double bn = 0.125 * Math.exp(-u0 / 80); //18b
            double ah = 0.07 * Math.exp(-u0 / 20); //19a
            double bh = 1 / (Math.exp((30 - u0) / 10) + 1); //19b

            double m0 = am / (am + bm);
            double n0 = an / (an + bn);
            double h0 = ah / (ah + bh);
            System.out.println(m0);
            System.out.println(n0);
            System.out.println(h0);

            double[] yStart = new double[]{m0, n0, h0, u0};
            double[] yStop = new double[]{0, 1, 0, 1};

            integrator1.integrate(ode, 0, yStart, T15, yStop);


            if ((path.getTime() < (T15) + Tk) && (path.getTime() > (T15) - Tk)) {
                I = 15;
                m0 = path.getmValues().get(path.getmValues().size() - 1);
                n0 = path.getnValues().get(path.getnValues().size() - 1);
                h0 = path.gethValues().get(path.gethValues().size() - 1);
                u0 = path.getuValues().get(path.getuValues().size() - 1);

                ode = new BigSquidNeuronODE(c, eNa, eK, eL, gNa, gK, gL, I);
                yStart = new double[]{m0, n0, h0, u0};
                System.out.println(Arrays.toString(yStart));
                yStop = new double[]{0, 1, 0, 1};

                integrator1.integrate(ode, T15, yStart, te, yStop);
            }

            time = path.getTimes();
            uValues = path.getuValues();

            uSeries = path.getuSeries();

            //CurrPressed
            INaSeries = path.getINaSeries();
            INaSeries.setName("I Na");
            IKSeries = path.getIKSeries();
            IKSeries.setName("I K");
            ILSeries = path.getILSeries();
            ILSeries.setName("I L");

            //VarPressed
            mSeries = path.getmSeries();
            mSeries.setName("M");
            nSeries = path.getnSeries();
            nSeries.setName("N");
            hSeries = path.gethSeries();
            hSeries.setName("H");

            huSeries = path.getHuSeriesSeries();
            nuSeries = path.getNuSeriesuSeriesSeries();
            muSeries = path.getMuSeriesuSeriesSeries();

            for (int i = 0; i < uValues.size(); i++) {
                if (time.get(i) > 7.5) ISeries.getData().add(new XYChart.Data<>(time.get(i), I));
                else ISeries.getData().add(new XYChart.Data<>(time.get(i), 0));
            }



            Platform.runLater(() -> {
                utChart.getData().add(uSeries);
                secondChart.setAnimated(false);
                secondChart.getData().addAll(nuSeries,huSeries,muSeries);
            });

            BigSquidNeuronMathClass BDSM = new BigSquidNeuronMathClass(uValues,time);
            freq.setText(Double.toString(round(BDSM.doMaths().get("frequency"),2)));
            max.setText(Double.toString(round(BDSM.doMaths().get("max"),2)));
            buttonCurr.setDisable(false);
            buttonVar.setDisable(false);
            buttonRun.setDisable(false);
        }
    };

    /**
     * Method that rounds given value to specific places after comma.
     * @param value to round
     * @param places after comma
     * @return
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * Method that clears all series.
     */
    private void clearSeries(){
        INaSeries.getData().clear();
        IKSeries.getData().clear();
        ILSeries.getData().clear();
        mSeries.getData().clear();
        nSeries.getData().clear();
        hSeries.getData().clear();
        huSeries.getData().clear();
        nuSeries.getData().clear();
        muSeries.getData().clear();
        uSeries.getData().clear();
        ISeries.getData().clear();
    }

    /**
     * `typePicked` changes the input fields according to the user changes in combobox.
     * @param event
     */
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

    /**
     * Sets initial values.
     */
    @FXML
    void initialize(){
        buttonCurr.setDisable(true);
        buttonVar.setDisable(true);
        combobox.getItems().removeAll(combobox.getItems());
        combobox.getItems().addAll("C, E...", "g Na, g K...");
        combobox.getSelectionModel().select("C, E...");
        System.out.println(Thread.currentThread().getName());
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

    /**
     * `varPressed` opens a new window with a graph containing m, h and n series.
     * @param event
     */
    @FXML
    void varPressed(ActionEvent event) {
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("varGraphWindow.fxml"));
            Parent root3 = loader2.load();
            VarGraphWindow varGraphWindow = loader2.getController();
            Platform.runLater(() -> {
                varGraphWindow.getVarGraph().getData().addAll(mSeries,hSeries,nSeries);
            });
            Stage stage3 = new Stage();
            stage3.setTitle("Landing Graph");
            stage3.setScene(new Scene(root3, 600, 500));
            stage3.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * `currPressed` opens a new window with a graph containing INa, Ik and IL series.
     * @param event
     */
    @FXML
    void currPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("currGraphWindow.fxml"));
            Parent root2 = loader.load();
            CurrGraphWindow currGraphWindow = loader.getController();
            Platform.runLater(() -> {
                currGraphWindow.getCurrGraph().getData().addAll(INaSeries,IKSeries,ILSeries);
            });
            Stage stage2 = new Stage();
            stage2.setTitle("Landing Graph");
            stage2.setScene(new Scene(root2, 600, 500));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Assigns parameters based on user input.
     */
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
            } else {
                gNa = Double.parseDouble(paramOne.getText());
                paramOne.setText(Double.toString(gNa));
                gK = Double.parseDouble(paramTwo.getText());
                paramTwo.setText(Double.toString(gK));
                gL = Double.parseDouble(paramThree.getText());
                paramThree.setText(Double.toString(gL));
            }
        } catch(Exception e) {
            System.out.println(e);
        }

    }




}
