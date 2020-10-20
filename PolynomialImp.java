import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PolynomialImp implements Polynomial {

    private ArrayList<Term> theTerms;

    public PolynomialImp(String s) {
        theTerms = new ArrayList<Term>(3);
        fString(s);
    }


    @Override
    public Polynomial add(Polynomial P2) {
        PolynomialImp r = new PolynomialImp("");
        ArrayList<Term> temp = new ArrayList<Term>();

        for(Term a : P2){
            temp.add(a);
        }

        for(int i = 0; i<this.theTerms.size(); ){
            for(int j = 0; j<temp.size(); ){

                if(this.theTerms.get(i).getExponent() == temp.get(j).getExponent()){
                    TermImp result = new TermImp(this.theTerms.get(i).getCoefficient() + temp.get(j).getCoefficient(), this.theTerms.get(i).getExponent());
                    if(!(result.getCoefficient() == 0)){
                        r.theTerms.add(result);
                    }


                    if(!(i==this.theTerms.size())){
                        i++;
                    }
                    if(!(j==temp.size())){
                        j++;
                    }
                }

                else if(this.theTerms.get(i).getExponent() > temp.get(j).getExponent()){
                    r.theTerms.add(theTerms.get(i));
                    if(!(i==this.theTerms.size())){
                        i++;
                    }
                }
                else{
                    r.theTerms.add(temp.get(j));
                    if(!(j==temp.size())){
                        j++;
                    }
                }
            }
        }

        return r;
    }

    @Override
    public Polynomial subtract(Polynomial P2) {
        return add(P2.multiply(-1));
    }

    @Override
    public Polynomial multiply(Polynomial P2) {
        PolynomialImp temp = new PolynomialImp("");

        for(Term a: this.theTerms){
            for(Term b: P2){
                TermImp mult = new TermImp(a.getCoefficient()*b.getCoefficient(), a.getExponent() + b.getExponent());
                temp.theTerms.add(mult);


            }
        }
        return temp;
    }

    @Override
    public Polynomial multiply(double c) {
        PolynomialImp temp = new PolynomialImp("");

        if(c==0){
            TermImp t = new TermImp(0,0);
            temp.theTerms.add(t);
        }
        else{
            for(Term a: this.theTerms){
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
            if(i.getExponent() > 0){
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
        TermImp constant = new TermImp(1, 0);
        temp.theTerms.add(constant);

        return temp;
    }

    @Override
    public double definiteIntegral(double a, double b) {

        double result = this.indefiniteIntegral().evaluate(b) - this.indefiniteIntegral().evaluate(a);

        return result;
    }

    @Override
    public int degree() {
        int maxDegree = this.theTerms.get(0).getExponent();
        return maxDegree;
    }

    @Override
    public double evaluate(double x) {

        double r = 0;
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
    public boolean equals(Polynomial P2){
        return this.toString().equals(P2.toString());

    }

    public String toString(){
        String result ="";

        if(this.theTerms.isEmpty()){
            return "0.00";
        }

        for(Term a: this.theTerms){
            result+=a+"+";
        }

        return result.substring(0, result.length() - 1);
    }

    public void fString(String s){
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
}
