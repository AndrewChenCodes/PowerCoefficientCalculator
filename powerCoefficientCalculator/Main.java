

package powerCoefficientCalculator;

public class Main {
    public static void main(String[] args){
        powerCoefficient pc = new powerCoefficient();
        try{
            System.out.println(pc.results());
        } catch (Exception e) {
            System.out.println("error");
        }

    }
}
