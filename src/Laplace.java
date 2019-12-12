import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Laplace {
    private float e;
    private boolean test;

    public Laplace(float e) {
        this.e = e;
        this.test = false;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public double genNoise(int s, float e1){
        if (!test) this.e -= e1;
        if (e>0) {
            double b = s/e1;
            double u = Math.random()-0.5;
            System.out.println("u:"+u);
            System.out.println("log:"+(Math.signum(u)));
            return (-b*Math.signum(u)*Math.log(1-2*Math.abs(u)));
        } else {
            return 0;
        }
    }

    public static void main (String[] args) throws IOException {
        // Remplissage du tableau
        Laplace lp = new Laplace(1.2f);
        lp.setTest(true);
        double[] tab_test=new double[10000];
        for (int i=0;i<tab_test.length;i++){
            tab_test[i]=lp.genNoise(50,1);
            //System.out.println(lp.genNoise(6,2));
        }


        //Analyse des résultats
        HashMap<Integer,Integer> analyse=new HashMap<Integer,Integer>();
        for(int i=-500;i<500;i+=20){
            analyse.put(i,0);
            for (double d : tab_test)
                if (d>=i && d<=i+20) analyse.put(i,analyse.get(i)+1);
        }
        System.out.println(analyse);

        //Ecriture dans le csv
        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("Range");
        csvWriter.append(",");
        csvWriter.append("Count");
        csvWriter.append("\n");
        for (Integer d : analyse.keySet()) {
            csvWriter.append(d.toString());
            csvWriter.append(",");
            csvWriter.append(analyse.get(d).toString());
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}