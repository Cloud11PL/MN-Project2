package sample;

import javafx.scene.chart.XYChart;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;


public class BigSquidNeuronPath implements StepHandler {


    private ArrayList<Double> mValues = new ArrayList<>();
    private ArrayList<Double> nValues = new ArrayList<>();
    private ArrayList<Double> hValues = new ArrayList<>();
    private ArrayList<Double> uValues = new ArrayList<>();
    private ArrayList<Double> times = new ArrayList<>();
    private ArrayList<Double> Inas=new ArrayList<>();
    private ArrayList<Double> Iks=new ArrayList<>();
    private ArrayList<Double> Ils=new ArrayList<>();
    private ArrayList<Double> mu=new ArrayList<>();
    private ArrayList<Double> hu=new ArrayList<>();
    private ArrayList<Double> nu=new ArrayList<>();
    private double time = 0;

    private XYChart.Series uSeries = new XYChart.Series();
    private XYChart.Series INaSeries = new XYChart.Series();
    private XYChart.Series IKSeries = new XYChart.Series();
    private XYChart.Series ILSeries = new XYChart.Series();
    private XYChart.Series mSeries = new XYChart.Series();
    private XYChart.Series nSeries = new XYChart.Series();
    private XYChart.Series hSeries = new XYChart.Series();
    private XYChart.Series huSeries = new XYChart.Series();
    private XYChart.Series muSeries = new XYChart.Series();
    private XYChart.Series nuSeries = new XYChart.Series();

    private double ENa;
    private double EK;
    private double EL;
    private double gNa;
    private double gK;
    private double gL;

    public BigSquidNeuronPath(double ENa, double EK, double EL, double gNa, double gK, double gL) {
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
    ArrayList<Double> getmValues() {
        return mValues;
    }
    ArrayList<Double> getnValues() {
        return nValues;
    }
    ArrayList<Double> gethValues() {
        return hValues;
    }
    ArrayList<Double> getuValues() {
        return uValues;
    }
    ArrayList<Double> getTimes() {
        return times;
    }
    double getTime() {
        return time;
    }

    @Override
    public void init(double v, double[] doubles, double v1){}

    XYChart.Series<Number, Number> getuSeries() {
        return uSeries;
    }
    XYChart.Series<Number, Number> getINaSeries() {
        return INaSeries;
    }
    XYChart.Series<Number, Number> getIKSeries() {
        return IKSeries;
    }
    XYChart.Series<Number, Number> getILSeries() {
        return ILSeries;
    }
    XYChart.Series<Number, Number> getmSeries() {
        return mSeries;
    }
    XYChart.Series<Number, Number> getnSeries() {
        return nSeries;
    }
    XYChart.Series<Number, Number> gethSeries() {
        return hSeries;
    }
    XYChart.Series<Number, Number> getHuSeriesSeries() {
        return huSeries;
    }
    XYChart.Series<Number, Number> getNuSeriesuSeriesSeries() {
        return nuSeries;
    }
    XYChart.Series<Number, Number> getMuSeriesuSeriesSeries() {
        return muSeries;
    }



    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {

        double t = stepInterpolator.getCurrentTime();
        double[] x = stepInterpolator.getInterpolatedState();
        time = t;

        double iNa = gNa * Math.pow(x[0], 3) * x[2] * (x[3] - ENa);
        double iK = gK * Math.pow(x[1], 4) * (x[3] - EK);
        double iL = gL * (x[3] - EL);

        uSeries.getData().add(new XYChart.Data<>(time, x[3]));

        INaSeries.getData().add(new XYChart.Data<>(time, iNa));
        IKSeries.getData().add(new XYChart.Data<>(time, iK));
        ILSeries.getData().add(new XYChart.Data<>(time, iL));

        mSeries.getData().add(new XYChart.Data<>(time, x[0]));
        nSeries.getData().add(new XYChart.Data<>(time, x[1]));
        hSeries.getData().add(new XYChart.Data<>(time, x[2]));

        huSeries.getData().add(new XYChart.Data<>(x[3], x[2]));
        nuSeries.getData().add(new XYChart.Data<>(x[3], x[1]));
        muSeries.getData().add(new XYChart.Data<>(x[3], x[0]));

        Inas.add(iNa);
        Iks.add(iK);
        Ils.add(iL);

        mValues.add(x[0]);
        nValues.add(x[1]);
        hValues.add(x[2]);
        uValues.add(x[3]);
        times.add(time);

    }
}