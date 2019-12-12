public class LaunchMe {
    private int n;
    private int m;
    private float e;

    public LaunchMe(int n, int m, float e) {
        this.n = n;
        this.m = m;
        this.e = e;
    }

    public static void main(String[] args){
        LaunchMe launch=new LaunchMe(100,8,151);
        int[] tab=new int[launch.n];
        for(int i=0;i<launch.n;i++){
            tab[i]= (int) Math.round(Math.random()*launch.m);
            System.out.println(tab[i]);
        }
    }
}
