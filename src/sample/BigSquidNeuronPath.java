package sample;

import javafx.scene.chart.XYChart;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;


public class Path implements StepHandler {

    private ArrayList<Double> mValues = new ArrayList<>();
    private ArrayList<Double> nValues = new ArrayList<>();
    private ArrayList<Double> hValues = new ArrayList<>();
    private ArrayList<Double> uValues = new ArrayList<>();
    private ArrayList<Double> times = new ArrayList<>();
    private ArrayList<Double> Inas=new ArrayList<>();
    private ArrayList<Double> Iks=new ArrayList<>();
    private ArrayList<Double> Ils=new ArrayList<>();
    private double time = 0;

    private XYChart.Series<Number, Number> uSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> INaSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> IKSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> ILSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> mSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> nSeries = new XYChart.Series();
    private XYChart.Series<Number, Number> hSeries = new XYChart.Series();

    private double ENa;
    private double EK;
    private double EL;
    private double gNa;
    private double gK;
    private double gL;

    public Path(double ENa, double EK, double EL, double gNa, double gK, double gL) {
        this.ENa = ENa;
        this.EK = EK;
        this.EL = EL;
        this.gNa = gNa;
        this.gK = gK;
        this.gL = gL;
    }

    public ArrayList<Double> getInas() {
        return Inas;
    }

    public ArrayList<Double> getIks() {
        return Iks;
    }

    public ArrayList<Double> getIls() {
        return Ils;
    }

    public ArrayList<Double> getmValues() {
        return mValues;
    }

    public ArrayList<Double> getnValues() {
        return nValues;
    }

    public ArrayList<Double> gethValues() {
        return hValues;
    }

    public ArrayList<Double> getuValues() {
        return uValues;
    }

    public ArrayList<Double> getTimes() {
        return times;
    }

    public double getTime() {
        return time;
    }

    @Override
    public void init(double v, double[] doubles, double v1) {

    }

    public XYChart.Series<Number, Number> getuSeries() {
        return uSeries;
    }

    public XYChart.Series<Number, Number> getINaSeries() {
        return INaSeries;
    }

    public XYChart.Series<Number, Number> getIKSeries() {
        return IKSeries;
    }

    public XYChart.Series<Number, Number> getILSeries() {
        return ILSeries;
    }

    public XYChart.Series<Number, Number> getmSeries() {
        return mSeries;
    }

    public XYChart.Series<Number, Number> getnSeries() {
        return nSeries;
    }

    public XYChart.Series<Number, Number> gethSeries() {
        return hSeries;
    }



    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {

        double t = stepInterpolator.getCurrentTime();

        double[] x = stepInterpolator.getInterpolatedState();
        time = t;

        double ina = gNa * Math.pow(x[0], 3) * x[2] * (x[3] - ENa);
        double ik = gK * Math.pow(x[1], 4) * (x[3] - EK);
        double il = gL * (x[3] - EL);

        uSeries.getData().add(new XYChart.Data<>(time, x[3]));

        INaSeries.getData().add(new XYChart.Data<>(time, ina));
        IKSeries.getData().add(new XYChart.Data<>(time, ik));
        ILSeries.getData().add(new XYChart.Data<>(time, il));

        mSeries.getData().add(new XYChart.Data<>(time, x[0]));
        nSeries.getData().add(new XYChart.Data<>(time, x[1]));
        hSeries.getData().add(new XYChart.Data<>(time, x[2]));

        Inas.add(ina);
        Iks.add(ik);
        Ils.add(il);

        mValues.add(x[0]);
        nValues.add(x[1]);
        hValues.add(x[2]);
        uValues.add(x[3]);
        times.add(time);

    }
}
