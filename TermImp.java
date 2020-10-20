import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TermImp implements Term {

    private double coefficient;
    private int exponent;

    public TermImp(double coefficient, int exponent){
        this.coefficient = coefficient;
        this.exponent = exponent;
    }


    @Override
    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public int getExponent() {
        return exponent;
    }

    @Override
    public double evaluate(double x) {
        return coefficient * Math.pow(x, exponent);
    }

    @Override
    public String toString(){
        if(this.exponent == 0){
            return String.format("%.2f", this.coefficient);
        }
        else if(this.exponent == 1){
            return String.format("%.2fx", this.coefficient);
        }
        else
            return String.format("%.2fx^%d", this.coefficient, this.exponent);

    }


    public static Term stringToPoly(String f){
        String z = new String(f);
        TermImp r = null;

        if(z.contains("x^")){
            StringTokenizer stringTok = new StringTokenizer(z, "x^");
            List<String> lista = new ArrayList<String>(2);

            while(stringTok.hasMoreElements()){
                lista.add((String) stringTok.nextElement());
            }

            if(lista.size() == 0){
                throw new IllegalArgumentException("Illegal argument");
            }

            if(lista.size() == 1){
                Integer e = Integer.parseInt(lista.get(0));
                r = new TermImp(1, e);
            }

            else{
                Double c = Double.parseDouble(lista.get(0));
                Integer e = Integer.parseInt(lista.get(1));
                r = new TermImp(c, e);

            }

        }

        else if(z.contains("x")){
            StringTokenizer stringTok = new StringTokenizer(z, "x");
            List<String> lista = new ArrayList<String>(2);

            while(stringTok.hasMoreElements()){
                lista.add((String) stringTok.nextElement());
            }

            if(lista.size() == 0){
                r = new TermImp(1.00, 1);
            }

            else{
                Double c = Double.parseDouble(lista.get(0));
                r = new TermImp(c, 1);
            }
        }

        else{
            r = new TermImp(Double.parseDouble(z), 0);
        }

        return r;


    }

}
