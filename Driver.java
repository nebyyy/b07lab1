import java.io.File;
import java.io.IOException;
public class Driver {
    public static void main(String[] args) {
        
        //test the empty constructor
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        // test reading from a file and test the file constructor
        Polynomial p1 = null;
        try {
            File file = new File("polynomial_file.txt");
            p1 = new Polynomial(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //test the constructor that takes two arrays
        double[] c2 = {-1.0,2.0,4.0};
        int[] e2 = {0,1,2};
        Polynomial p2 = new Polynomial(c2,e2);
        

        // test the addition and multiply methods
        Polynomial s = p2.add(p1);
        Polynomial s2 = p1.multiply(p2);

        //test the evaluate method
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        
        //test the hasroot method
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        //test saveToFile
        try {
            s2.saveToFile("output.txt");
            System.out.println("Polynomial saved to output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}