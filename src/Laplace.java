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
        if ((e-e1)>=0) { // Test if the budget is sufficient
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
}
