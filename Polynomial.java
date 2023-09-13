
public class Polynomial{
    /**
     * A class defining a polynomial
     */
    // === Instance Variables ===

    // a polynomial in the form of an array
    private double[] coefficients;
    // ===

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[]coefficients){
        /**
        * Takes the array of coefficients and turns it into a polynomial
        */
        this.coefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
    }
    
    }

    public Polynomial add(Polynomial other) {
    /**
     * Return the result of two polynomials added
     */
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] resultCoefficients = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            if (i < this.coefficients.length) {
                resultCoefficients[i] += this.coefficients[i];
            }
            if (i < other.coefficients.length) {
                resultCoefficients[i] += other.coefficients[i];
            }
        }
        return new Polynomial(resultCoefficients);
    }

    public double evaluate(double x){
        /**
        * takes one argument of type double
        representing a value of x and evaluates the polynomial accordingly. For example,
        if the polynomial is 6 - 2x + 5x3 and evaluate(-1) is invoked, the result should
        be 3
        */
        double evalResult = 0.0;
        for (int i = 0; i < this.coefficients.length; i++){
            evalResult += (this.coefficients[i])*(Math.pow(x,i));   
        }
        return evalResult;
    }

    public boolean hasRoot(double y){
        /**
        * takes one argument of type double and
        determines whether this value is a root of the polynomial or not. Note that a root
        is a value of x for which the polynomial evaluates to zero.
        */
        double evalResult = this.evaluate(y);
        if (evalResult == 0){
            return true;
        }else{
            return false;
        }
    }
}
