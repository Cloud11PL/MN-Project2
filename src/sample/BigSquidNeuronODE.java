
package sample;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class ODE implements FirstOrderDifferentialEquations {

    private double C;
    private double ENa;
    private double EK;
    private double EL;
    private double gNa;
    private double gK;
    private double gL;
    private double I;

    public ODE(double c, double ENa, double EK, double EL, double gNa, double gK, double gL, double i) {
        C = c;
        this.ENa = ENa;
        this.EK = EK;
        this.EL = EL;
        this.gNa = gNa;
        this.gK = gK;
        this.gL = gL;
        I = i;
    }


    @Override
    public int getDimension() {
        return 4;
    }


    @Override
    public void computeDerivatives(double t, double[] x, double[] dxdt) throws MaxCountExceededException, DimensionMismatchException {


        double am = (0.1 * (25 - x[3])) / (Math.exp((25 - x[3]) / 10) - 1);
        double bm = 4 * Math.exp(-x[3] / 18);
        double an = (0.01 * (10 - x[3])) / (Math.exp((10 - x[3]) / 10) - 1);
        double bn = 0.125 * Math.exp(-x[3] / 80);
        double ah = 0.07 * Math.exp(-x[3] / 20);
        double bh = 1 / (Math.exp((30 - x[3]) / 10) + 1);


        dxdt[0]= am *(1-x[0])- bm *x[0]; //16a
        dxdt[1]= an *(1-x[1])- bn *x[1]; //16b
        dxdt[2]= ah *(1-x[2])- bh *x[2]; //16c
        dxdt[3]=(-1*((gNa*Math.pow(x[0],3.0)*x[2]*(x[3]-ENa))+((gK*Math.pow(x[1],4.0)*(x[3]-EK))+(gL*(x[3]-EL))))+I)/C; //20



    }
}