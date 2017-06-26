package Engine;

/**
 * Created by erinsong on 6/26/17.
 */
public class Data {

    public double[][][] data;

    public double[][] avg;

    public double[][] std;


    public Data(int i, int j, int k) {

        data = new double[i][j][k];
        avg = new double[i][j];
        std = new double[i][j];
    }

    public double dataget(int i, int j, int k) {
        return data[i][j][k];
    }

    public void datainc(int i, int j, int k, double inc) {

        data[i][j][k] += inc;
    }

    public double avgget(int i, int j) {

        return avg[i][j];
    }

    public double stdget(int i, int j) {
        return std[i][j];
    }


}
