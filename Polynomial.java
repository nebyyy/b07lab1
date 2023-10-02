import java.io.*;

public class Polynomial{
    /**
     * A class defining a polynomial
     */

    //a. === Instance Variables ===

    // a list containing coeff
    private double[] coefficients;
    // a list containing exponents
    private int[] exponents;

    // ===

    //b.-------------------------------------------------
    public Polynomial() {
        this.coefficients = new double[]{0.0};
        this.exponents = new int[]{0};
    }

    public Polynomial(double[]coefficients, int[] exponents){
        /**
        * Takes the arrays of coefficients and exponents 
        and creates a polynomial
        */
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];

        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial add(Polynomial other) {
    /**
     * Return the result of two polynomials added
     */
        int totalLength = (this.coefficients.length + other.coefficients.length);
        double[] resultCoefficients = new double[totalLength];
        int[] resultExponents = new int[totalLength];

        // copy existing exponents of initial polynomial
        for (int i = 0; i < coefficients.length; i++) {
            if (i < this.coefficients.length) {
                resultCoefficients[i] += this.coefficients[i];
                resultExponents[i] += this.exponents[i];
            }
        }
        //the length of the array equals the last index in array + 1
        int nonExistingExpIndex = this.coefficients.length;

        for (int i = 0; i < other.coefficients.length; i++){
            double otherCoefficient = other.coefficients[i];
            int otherExponent = other.exponents[i];
            int index = indexFinder(resultExponents, otherExponent);

            if (index == -1){
                //need create a new term in both result arrays and update  
                resultCoefficients[nonExistingExpIndex] = otherCoefficient;
                resultExponents[nonExistingExpIndex] = otherExponent;
                nonExistingExpIndex += 1;


            }else{
                resultCoefficients[index] += otherCoefficient;
            }
        }

        //now we need to truncate any unwanted zeros from coeff array and change 
        //expo array to match the changes
        double[][] resultArrays = zerosRemover(resultCoefficients, resultExponents);
        int arraySize = 0;
        for (int i = 0; i < resultArrays[1].length; i++) {
                arraySize += 1;
            }

        int[] finalExpoArray = new int[arraySize];

        //type cast the items in resultArrays[1] ie.our expoArray from double to int
        for (int i = 0; i < resultArrays[1].length; i++) {
            finalExpoArray[i] = (int)resultArrays[1][i];
        }
        
        return new Polynomial(resultArrays[0], finalExpoArray);
    }

 
    private int indexFinder(int[] array, int target) {
        /**find and return the index of target 
         * in an array  if it exists.
         *  Otherwise returns -1
         */
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private double[][] zerosRemover(double[] coeffArray, int[] expoArray){
        /**
         * Returns an array containing 2 arrays. 
         * First array is equal to coeffArray but without any zeros
         * Second array is equal to expoArray but any terms at the index where a 
         * coeffArray term is removed is also removed
         */

        //find the correct array size
        int arraySize = 0;
        for (int i = 0; i < coeffArray.length; i++) {
            if (coeffArray[i] != 0){
                arraySize += 1;
            }
        }
        // Create new array sizes without zeros
        double[] nonZeroCoeffArray = new double[arraySize];
        // we will make expo array a double array and change it back later
        double[] nonZeroExpoArray = new double[arraySize];
        //now array size is proper we need to populate the new coeffArrays and expoArrays
        int newIndex = 0; // Index for the new arrays

        for (int i = 0; i < coeffArray.length; i++) {
            if (coeffArray[i] != 0) {
                nonZeroCoeffArray[newIndex] = coeffArray[i];
                nonZeroExpoArray[newIndex] = (double)expoArray[i];
                newIndex += 1;
            }
        }
    
        // Create a 2D array to hold the two arrays
        double[][] resultArrays = { nonZeroCoeffArray, nonZeroExpoArray };
    
        return resultArrays;
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
            evalResult += (this.coefficients[i])*(Math.pow(x,exponents[i]));   
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
    //--------------------------------------

    //c.
    public Polynomial multiply(Polynomial other){
        /**
         * return the polynomial that results from multiplying  
         * a polynomial with other polynomial
         */
        //multiplying polys have poly1*poly2 possibilities of exponent
        int totalLength = coefficients.length * other.coefficients.length;
        double[] resultCoefficients = new double[totalLength];
        int[] resultExponents = new int[totalLength];

        int index = 0;

        for (int i = 0; i < coefficients.length; i++) {

            for (int j = 0; j < other.coefficients.length; j++) {

                double multiplyCoefficient = this.coefficients[i] * other.coefficients[j];
                int multiplyExponent = this.exponents[i] + other.exponents[j];
                
                // Check if a term with this exponent already exists
                boolean termExists = false;
                for (int k = 0; k < index; k++) {
                    if (resultExponents[k] == multiplyExponent) {
                        resultCoefficients[k] += multiplyCoefficient;
                        termExists = true;
                        break;
                    }
                }
                
                if (!termExists) {
                    resultCoefficients[index] = multiplyCoefficient;
                    resultExponents[index] = multiplyExponent;
                    index++;
                }
            }
        }
        
        //now we need to truncate any unwanted zeros from coeff array and change 
        //expo array to match the changes
        double[][] resultArrays = zerosRemover(resultCoefficients, resultExponents);
        int arraySize = 0;
        for (int i = 0; i < resultArrays[1].length; i++) {
                arraySize += 1;
            }

        int[] finalExpoArray = new int[arraySize];

        //type cast the items in resultArrays[1] ie.our expoArray from double to int
        for (int i = 0; i < resultArrays[1].length; i++) {
            finalExpoArray[i] = (int)resultArrays[1][i];
        }
        
        return new Polynomial(resultArrays[0], finalExpoArray);
    }

    //d.
    public Polynomial(File file) throws IOException {
        /**
         * constructor that takes one argument of type File and initializes the polynomial
         * based on the contents of the file.
         * You can assume that the file contains one line with no
         * whitespaces representing a valid polynomial.
         */
        //in case of empty file default values are called
        //this.coefficients = new double[]{0.0};
        //this.exponents = new int[]{0};

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            String line = input.readLine();
            if (line != null) {
                parsePolynomial(line);
            }
            input.close();
        }
    }

    private void parsePolynomial(String input) {
        /**
         * Helper to parse a polynomial ie. populate exponent and coefficient arrays.
         */
        //use a lookahead assertion to split by plus or minus without consuming the sign
        String[] terms = input.split("(?=[-+])");

        // Determine the number of terms in the polynomial
        int termCount = 0;
        for (String term : terms) {
            if (!term.isEmpty()) {
                termCount +=1;
            }
        }

        // Initialize arrays with the size of the polynomial
        this.coefficients = new double[termCount];
        this.exponents = new int[termCount];

        int index = 0; // Index to track the current term


        for (String term : terms) {
            if (!term.isEmpty()) {
                //use this to check if the first char of term 1 is pos or neg
                char firstChar = term.charAt(0);

                double coefficient = 1.0;

                if (firstChar == '-'){
                    coefficient = -1.0;
                }
                
                int exponent = 0;

                if (firstChar == '-' || firstChar == '+') {
                    term = term.substring(1); // Skip the sign character
                }
                
                if (term.contains("x")) {
                    String[] parts = term.split("x");
                        if (parts.length >= 1) {
                            coefficient *= Double.parseDouble(parts[0]);
                        }
                        if (parts.length >= 2) {
                            exponent = Integer.parseInt(parts[1]);
                        }
                } else {
                    coefficient *= Double.parseDouble(term);
                }
                this.coefficients[index] = coefficient;
                this.exponents[index] = exponent;
                index += 1;
               
            }
        }
    }

    //e.
    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writetoFile = new BufferedWriter(new FileWriter(fileName))) {
            writetoFile.write(toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        

        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];
            
            if (i > 0) {
                if (coefficient >= 0){
                    sb.append("+");
                }
            }
            
            sb.append(coefficient);
            
            if (exponent > 0) {
                sb.append("x");
                sb.append(exponent);
            }
        }
        
        return sb.toString();
    }
}
