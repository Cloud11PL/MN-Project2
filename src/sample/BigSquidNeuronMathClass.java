package sample;


import java.util.ArrayList;
import java.util.HashMap;

public class BigSquidNeuronMathClass {
    private ArrayList<Double> u;
    private ArrayList<Double> time;

    public BigSquidNeuronMathClass(ArrayList<Double> u, ArrayList<Double> time) {
        this.u = u;
        this.time = time;
    }

    public HashMap<String,Double> doMaths() {

        HashMap<String,Double> results = new HashMap<>();
        ArrayList<Double> times = new ArrayList<>();
        ArrayList<Double> maxU = new ArrayList<>();

        for (int i = 1; i < u.size() - 1; i++) {
            if (u.get(i) > u.get(i - 1) && u.get(i) > u.get(i + 1) && u.get(i) > 1) {
                times.add(time.get(i));
                maxU.add(u.get(i));
            }
        }

        double avg ;
        double sum = 0;
        double sumstd = 0;
        double std;
        double max = 0;
        double fs;
        double sumTime = 0;

        for (int i = 0; i < times.size(); i++) {
            if (max < maxU.get(i)) max = maxU.get(i);

            sum += maxU.get(i);
            if (i > 0) sumTime += times.get(i) - times.get(i - 1);
            else sumTime = time.get(i);
        }

        avg = sum / maxU.size();
        fs = 1 / (sumTime / (times.size() - 1));

        for (int i = 0; i < times.size(); i++) {
            sumstd += (Math.pow(maxU.get(i) - avg, 2) / (times.size() - 1));
        }

        std = Math.pow(sumstd, 0.5);


        results.put("frequency",fs);
        results.put("max",max);
        results.put("std",std);
        results.put("avg",avg);

        return results;


    }

}
