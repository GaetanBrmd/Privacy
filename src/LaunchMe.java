import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class LaunchMe {
    private int n; // Size of the dataset
    private int m; // Maximum of the dataset
    private float e; // Privacy parameter

    public LaunchMe(int n, int m, float e) {
        this.n = n;
        this.m = m;
        this.e = e;
    }

    public static void main(String[] args) throws IOException {

        //==========================Q2=============================
        LaunchMe launch1=new LaunchMe(1000,15,151);
        int[] tab1=new int[launch1.n];
        for(int i=0;i<launch1.n;i++) tab1[i]= (int) Math.round(Math.random()*launch1.m); // Generate the random set
        System.out.println("Tableau 1 généré !");

        // ========================Q4==============================
        Laplace lp = new Laplace(1.2f);
        lp.setTest(true);
        double[] tab_test=new double[10000];
        for (int i=0;i<tab_test.length;i++) tab_test[i]=lp.genNoise(50,1); // Generate an array of 10000 noise
        System.out.println("Tableau de perturbations généré !");
        // Analysis of the results in a Hashmap
        // key : start of the range (500, 480 etc.)
        // value : count in the range
        HashMap<Integer,Integer> analyse=new HashMap<Integer,Integer>();
        for(int i=-500;i<500;i+=20){
            analyse.put(i,0);
            for (double d : tab_test) if (d>=i && d<=i+20) analyse.put(i,analyse.get(i)+1);
        }

        // Printing in a .csv file
        FileWriter csvWriter1 = new FileWriter("perturbations.csv");
        csvWriter1.append("Range");
        csvWriter1.append(",");
        csvWriter1.append("Count");
        csvWriter1.append("\n");
        for (int i=-500;i<500;i+=20) {
            csvWriter1.append(Integer.toString(i));
            csvWriter1.append(",");
            csvWriter1.append(analyse.get(i).toString());
            csvWriter1.append("\n");
        }
        csvWriter1.flush();
        csvWriter1.close();

        //==========================Q5=============================
        int count=0;
        for (int i : tab1) if (i>10) count++; // Compute the true count the value > 10 in tab
        System.out.println("COUNT (>10) : "+count);

        //Generate different number of perturbations
        int[] nb={10,100,1000,10000,100000,500000,1000000}; // Number of perturbations
        Laplace lp1 = new Laplace(0.0001f);
        lp1.setTest(true);
        for (int nb_perturb:nb) {
            double[] perturb = new double[nb_perturb];
            for (int i = 0; i < nb_perturb; i++) perturb[i] = count + lp1.genNoise(1, 0.0001f); // Generate nb_perturb perturbations
            double count_avg = 0;
            for (int i = 0; i < nb_perturb; i++) count_avg += perturb[i]; // Compute the average of noise
            count_avg /= nb_perturb;
            System.out.println("Moyenne des " + nb_perturb + " perturbations de COUNT : " + count_avg);
        }

        //========================Q6================================
        FileWriter csvWriter = new FileWriter("ratio err sum.csv");
        csvWriter.append("Dataset size");
        csvWriter.append(",");
        csvWriter.append("Ratio");
        csvWriter.append("\n");

        int[] n={100,10000,1000000}; //Datasets sizes
        for (int y : n) {
            // Generate a dataset of size y
            LaunchMe launch2 = new LaunchMe(y, 1000, 151);
            int[] tab2 = new int[launch2.n];
            for (int i = 0; i < launch2.n; i++) {
                tab2[i] = (int) Math.round(Math.random() * launch2.m);
            }
            System.out.println("Tableau 2 généré !");

            // Compute the real sum of the dataset
            int sum = 0;
            for (int i : tab2) sum += i;
            System.out.println("SUM : " + sum);

            //Generate y noises and compute the average
            Laplace lp2 = new Laplace(0.01f);
            lp2.setTest(true);
            double err_avg = 0;
            for (int i = 0; i < y; i++) err_avg += Math.abs(lp2.genNoise(1000, 0.01f));
            err_avg /= y;
            System.out.println("Moyenne des "+y+" erreures de perturbations de SUM : " + err_avg);

            //Compute the ratio between the average and the sum
            double ratio = err_avg/sum;
            System.out.println("Ratio err_avg/SUM : "+ratio);

            // Print in the .csv file
            csvWriter.append(Integer.toString(y));
            csvWriter.append(",");
            csvWriter.append(Double.toString(ratio));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
