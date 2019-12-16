import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


//===========================Q3============================
public class Laplace {
    private float e; // Total budget of the object
    private boolean test; // Test mode enabled or not

    public Laplace(float e) {
        this.e = e;
        this.test = false;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    // Noise generation function depending of :
    // s : sensibility of the request
    // e1 : Part of the budget to consume
    public double genNoise(int s, float e1){
        if ((e-e1)>0) { // Test if the budget is sufficient
            if (!test) this.e -= e1; // Consume the budget if we are not in test mode
            // Compute the noise
            double b = s/e1;
            double u = Math.random()-0.5;
            return (-b*Math.signum(u)*Math.log(1-2*Math.abs(u))); // X = sigma - b*sign(u)*ln(1-2|u|)
        } else {
            System.out.println("Requete impossible à cause d'un manque de budget !");
            return 0;
        }
    }

    public static void main (String[] args) throws IOException {
        // ===================Q4=====================
        Laplace lp = new Laplace(1.2f);
        lp.setTest(true);
        double[] tab_test=new double[10000];
        for (int i=0;i<tab_test.length;i++) tab_test[i]=lp.genNoise(50,1); // Generate an array of 10000 noise

        // Analysis of the results in a Hashmap
        // key : start of the range (500, 480 etc.)
        // value : count in the range
        HashMap<Integer,Integer> analyse=new HashMap<Integer,Integer>();
        for(int i=-500;i<500;i+=20){
            analyse.put(i,0);
            for (double d : tab_test) if (d>=i && d<=i+20) analyse.put(i,analyse.get(i)+1);
        }

        // Printing in a .csv file
        FileWriter csvWriter = new FileWriter("perturbations.csv");
        csvWriter.append("Range");
        csvWriter.append(",");
        csvWriter.append("Count");
        csvWriter.append("\n");
        for (int i=-500;i<500;i+=20) {
            csvWriter.append(Integer.toString(i));
            csvWriter.append(",");
            csvWriter.append(analyse.get(i).toString());
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
