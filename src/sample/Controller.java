package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    private ArrayList<Double> time;
    private ArrayList<Double> uValues;

    @FXML
    private LineChart<Number, Number> utChart;

    @FXML
    private LineChart<Number, Number> secondChart;

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
        double I = 0;
        double C = Double.parseDouble(paramFour.getText());
        double eNa = Double.parseDouble(paramOne.getText());
        double eK = Double.parseDouble(paramTwo.getText());
        double eL = Double.parseDouble(paramThree.getText());
        double gNa = 120;
        double gK = 36;
        double gL = 0.3;
        double u =0;

        FirstOrderDifferentialEquations ode = new BigSquidNeuronODE(C,eNa,eK,eL,gNa,gK,gL,I);
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

            ode = new BigSquidNeuronODE(C, eNa, eK, eL, gNa, gK, gL, I);
            yStart = new double[]{m0, n0, h0, u0};
            System.out.println(Arrays.toString(yStart));
            yStop = new double[]{0, 1, 0, 1};


            integrator1.integrate(ode, T15, yStart, te, yStop);
        }

        time = path.getTimes();
        uValues = path.getuValues();

        XYChart.Series<Number, Number> uSeries = path.getuSeries();
        XYChart.Series<Number, Number> ISeries = new XYChart.Series();
        XYChart.Series<Number, Number> INaSeries = path.getINaSeries();
        XYChart.Series<Number, Number> IKSeries = path.getIKSeries();
        XYChart.Series<Number, Number> ILSeries = path.getILSeries();
        XYChart.Series<Number, Number> mSeries = path.getmSeries();
        XYChart.Series<Number, Number> nSeries = path.getnSeries();
        XYChart.Series<Number, Number> hSeries = path.gethSeries();
        XYChart.Series<Number, Number> huSeries = path.gethSeries();
        XYChart.Series<Number, Number> nuSeries = path.gethSeries();
        XYChart.Series<Number, Number> muSeries = path.gethSeries();

        for (int i = 0; i < uValues.size(); i++) {
            if (time.get(i) > 7.5) ISeries.getData().add(new XYChart.Data<>(time.get(i), I));
            else ISeries.getData().add(new XYChart.Data<>(time.get(i), 0));

        }
        utChart.getData().add(uSeries);
        secondChart.setAnimated(false);
        secondChart.getData().addAll(nuSeries,huSeries,muSeries);


        BigSquidNeuronMathClass BDSM = new BigSquidNeuronMathClass(uValues,time);
        freq.setText(BDSM.doMaths().get("frequency").toString());
        max.setText(BDSM.doMaths().get("max").toString());

    }
}
