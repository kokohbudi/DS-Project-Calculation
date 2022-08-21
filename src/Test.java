import java.math.BigDecimal;

public class Test {
    public static void main(String[] args){
        BigDecimal saldoRekening= new BigDecimal(2000000);
        System.out.println(saldoRekening.remainder(new BigDecimal(2000000)));
    }
}
