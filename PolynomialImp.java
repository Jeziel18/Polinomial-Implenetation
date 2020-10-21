import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PolynomialImp implements Polynomial {

    private ArrayList<Term> theTerms;

    public PolynomialImp(String s) {
        theTerms = new ArrayList<Term>();
        fString(s);
    }


    @Override
    public Polynomial add(Polynomial P2) {
        PolynomialImp r = new PolynomialImp("");
        ArrayList<Term> temp = new ArrayList<Term>();

        //Changing P2 to an ArrayList<Term>
        for(Term a : P2){
            temp.add(a);
        }

        for(int i = 0; i<this.theTerms.size(); ){
            for(int j = 0; j<temp.size(); ){
                // if exponets are equals Sum the coefficient
                if(this.theTerms.get(i).getExponent() == temp.get(j).getExponent()){
                    TermImp result = new TermImp(this.theTerms.get(i).getCoefficient() + temp.get(j).getCoefficient(), this.theTerms.get(i).getExponent());
                    if(!(result.getCoefficient() == 0)){
                        r.theTerms.add(result);
                    }

                    if(!(i==this.theTerms.size())){  //Pointer to move through arrayList and check every element
                        i++;
                    }
                    if(!(j==temp.size())){  //Pointer to move through arrayList and check every element
                        j++;
                    }
                }

                // if exponets 1 is grater, put it first in the polynomial
                else if(this.theTerms.get(i).getExponent() > temp.get(j).getExponent()){
                    r.theTerms.add(theTerms.get(i));
                    if(!(i==this.theTerms.size())){  // Move only theTherm array
                        i++;
                    }
                }
                // if exponets 2 is grater, put it first in the polynomial
                else{
                    r.theTerms.add(temp.get(j));
                    if(!(j==temp.size())){  // Move only the P2 array
                        j++;
                    }
                }
            }
        }

        return r;
    }

    @Override
    public Polynomial subtract(Polynomial P2) {
        return add(P2.multiply(-1));  //mulitply by -1 and use add
    }

    @Override
    public Polynomial multiply(Polynomial P2) {
        PolynomialImp r = new PolynomialImp("");
        PolynomialImp p = (PolynomialImp) P2;

        for(int i = 0; i<this.theTerms.size(); i++){
            for(int j = 0; j<p.theTerms.size(); j++){
                if(this.theTerms.get(i).getCoefficient() != 0 && p.theTerms.get(j).getCoefficient() != 0){   //Check if coefficient is 0, if it is, don't do the multiplication
                    TermImp temp = new TermImp(this.theTerms.get(i).getCoefficient() * p.theTerms.get(j).getCoefficient(), this.theTerms.get(i).getExponent() + p.theTerms.get(j).getExponent());
                    r.theTerms.add(temp);

                }


            }
        }

        for(int i = 0; i<r.theTerms.size(); i++){
            for(int j = i+1; j<r.theTerms.size(); j++){   //Cheking if the exponent are equals so it can sum them
                if(r.theTerms.get(i).getExponent() == r.theTerms.get(j).getExponent()){
                    double constantSum = r.theTerms.get(i).getCoefficient() + r.theTerms.get(j).getCoefficient();
                    if(!(constantSum == 0)){
                        r.theTerms.set(i, new TermImp(constantSum, r.theTerms.get(j).getExponent()));
                        r.theTerms.remove(j);
                        if(r.theTerms.get(j).getExponent() == r.theTerms.get(i).getExponent()){
                            constantSum = constantSum + r.theTerms.get(j).getCoefficient();
                            r.theTerms.set(i, new TermImp(constantSum, r.theTerms.get(j).getExponent()));
                            r.theTerms.remove(j);
                        }
                    }
                    else{
                        r.theTerms.remove(i);
                    }
                }
            }
        }

        return r.organizerOfPolynomial();
    }

    @Override
    public Polynomial multiply(double c) {
        PolynomialImp temp = new PolynomialImp("");

        if(c==0){  // if c = 0, return 0
            TermImp t = new TermImp(0,0);
            temp.theTerms.add(t);
        }
        else{
            for(Term a: this.theTerms){  // multiply every coefficient with the "c" and get the exponent
                TermImp newPoly = new TermImp(a.getCoefficient()*c, a.getExponent());
                temp.theTerms.add(newPoly);
            }
        }

        return temp;
    }

    @Override
    public Polynomial derivative() {
        PolynomialImp r = new PolynomialImp("");

        for(Term i: this.theTerms){
            if(i.getExponent() > 0){  // exponent has to be > 0
                TermImp temp = new TermImp(i.getCoefficient()*i.getExponent(), i.getExponent() - 1);
                r.theTerms.add(temp);
            }

        }
        return r;
    }

    @Override
    public Polynomial indefiniteIntegral() {
        PolynomialImp temp = new PolynomialImp("");

        for(Term a: this.theTerms){
            TermImp b = new TermImp(a.getCoefficient() / (a.getExponent() + 1), a.getExponent() + 1);
            temp.theTerms.add(b);
        }
        TermImp constant = new TermImp(1, 0);  //Adding the +c as 1 to the polymomial
        temp.theTerms.add(constant);

        return temp;
    }

    @Override
    public double definiteIntegral(double a, double b) {

        double result = this.indefiniteIntegral().evaluate(b) - this.indefiniteIntegral().evaluate(a);  //use evaluate and indefiniteIntegral to complete the method
        return result;
    }

    @Override
    public int degree() {
        int maxDegree = this.theTerms.get(0).getExponent();  //get the first exponent, because polynomial are in descending order
        return maxDegree;
    }

    @Override
    public double evaluate(double x) {

        double r = 0;   //calling the evaluate in TermImp
        for(Term a: this.theTerms){
            r += a.evaluate(x);
        }

        return r;
    }

    @Override
    public Iterator<Term> iterator() {
        return theTerms.iterator();
    }

    @Override
    public boolean equals(Polynomial P2){   //Verifiying if P1 and P2 are equals
        return this.toString().equals(P2.toString());

    }

    public String toString(){
        String result ="";

        if(this.theTerms.isEmpty()){   //String is empty, so is 0.00
            return "0.00";
        }

        for(Term a: this.theTerms){   //adding first element and adding string "+". Then Sum that with the next one
            result+=a+"+";
        }

        return result.substring(0, result.length() - 1);
    }

    public void fString(String s){    // TermImp  to change String to int and double
        StringTokenizer a = new StringTokenizer(s, "+");
        String nextS = null;
        Term nextT = null;
        this.theTerms.clear();
        while(a.hasMoreElements()){
            nextS = (String) a.nextElement();
            nextT = TermImp.stringToPoly(nextS);
            this.theTerms.add(nextT);
        }
    }

    public Polynomial organizerOfPolynomial() {   // Helper method that helps in organizing the terms
        PolynomialImp temp = new PolynomialImp("");
        for(Term a :this){
            temp.theTerms.add(a);   // bring the original array elements to work on them
        }
        for(int i = 0; i<temp.theTerms.size()-1; i++){
            if(temp.theTerms.get(i).getExponent() < temp.theTerms.get(i+1).getExponent()){
                TermImp m = new TermImp(temp.theTerms.get(i).getCoefficient(),temp.theTerms.get(i).getExponent());
                temp.theTerms.set(i,temp.theTerms.get(i+1));
                temp.theTerms.set(i+1,m);
            }
        }
        return temp;
    }



}
