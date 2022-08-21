//import com.sun.org.apache.xpath.internal.operations.String;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

public class DanaSyariahCalc {
    public static void main(String[] args) {
        BigDecimal perLotProject= new BigDecimal(1000000);
        BigDecimal saldoWallet=new BigDecimal(31000000);
        BigDecimal topupRutin = new BigDecimal(5000000);
        BigDecimal targetAset=new BigDecimal(30000000);//dikalikan 10
        targetAset= targetAset.multiply(BigDecimal.TEN);
        int annualProjectReturn=17;
        boolean timeBaseInvestment=true;// true: investement will calc by time, false: investment calc by asset target, see @targetAset
        NumberFormat nf=NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        System.out.println("Investasi Awal "+ nf.format(saldoWallet));
        boolean putarReturn=true;
        int lamaInvestasi=4;//dalam tahun
        int pajak=0;
        final BigDecimal HUNDRED= new BigDecimal(100);
        BigDecimal saldoRekening = BigDecimal.ZERO;
        BigDecimal totalTopup = saldoWallet;

//        boolean lanjutInvestasi=true;
//        while (lanjutInvestasi) {
        int bulan;
        List records = new ArrayList();

//        for (bulan = 1; saldoRekening.add(saldoWallet).compareTo(targetAset)<=0; bulan++) {
        for (bulan = 1; timeBaseInvestment==true ? bulan<=(lamaInvestasi*12)+12 : saldoRekening.add(saldoWallet).compareTo(targetAset)<=0; bulan++) {
            Map record = new HashMap();
            record.put("bulan",bulan);
            if(bulan >1 && bulan<=lamaInvestasi*12){
                saldoRekening=saldoRekening.add(topupRutin);
                totalTopup=totalTopup.add(topupRutin);
            }

            BigDecimal returnBulanan=saldoWallet.multiply(
                    new BigDecimal(bulan%12==0 && bulan!=0 ? (annualProjectReturn-12)+1 : 1).divide(HUNDRED)
            );
//            System.out.println("return bulanan "+nf.format(returnBulanan) + "bulan "+bulan);
            returnBulanan=returnBulanan.subtract(returnBulanan.multiply(pajak >0 ? new BigDecimal(pajak).divide(HUNDRED) : BigDecimal.ZERO));
            record.put("Return Bulanan", nf.format(returnBulanan));
            saldoRekening=saldoRekening.add(returnBulanan);
//            record.put("Saldo Rekening", saldoRekening);
//            if(bulan%12==0){
//                saldoRekening=saldoRekening.add(
//                        saldoWallet.multiply(
//                                new BigDecimal(annualProjectReturn-12).divide(HUNDRED)
//                        )
//                );
//            }
            if(saldoRekening.compareTo(perLotProject)>=0  && saldoRekening.subtract(perLotProject).compareTo(BigDecimal.ZERO)>=0){
                BigDecimal danaTopup=putarReturn && bulan<=lamaInvestasi*12 ? saldoRekening.subtract(saldoRekening.remainder(perLotProject)) : topupRutin.subtract(topupRutin.remainder(perLotProject));
//                System.out.println("topup "+nf.format(danaTopup));
                record.put("Dana Topup", nf.format(danaTopup));
                saldoWallet= saldoWallet.add(danaTopup);
//                System.out.println("saldo wallet "+nf.format(saldoWallet));
//                System.out.println("saldo rekening "+nf.format(saldoRekening));
                saldoRekening= saldoRekening.subtract(danaTopup);

            }
            record.put("Saldo Rekening", nf.format(saldoRekening));
            record.put("Saldo Wallet", nf.format(saldoWallet));
            records.add(record);
//                System.out.println("Bulan ke "+(bulan)+" saldo rekening: "+nf.format(saldoRekening)+" saldo wallet "+nf.format(saldoWallet)+", aset total: "+nf.format(saldoRekening.add(saldoWallet)));

        }
//        }

        BigDecimal cuan = (saldoRekening.add(saldoWallet)).subtract(totalTopup);

        System.out.println(records);
        System.out.println("total aset "+ nf.format(totalTopup.add(cuan)) +" total investasi: "+nf.format(totalTopup)+" cuan(RP) "+ nf.format(cuan) + " cuan(%) "+ cuan.divide(totalTopup, 2, RoundingMode.HALF_UP).multiply(HUNDRED)+" selama "+bulan +" Bulan atau "+new BigDecimal(bulan).divide(new BigDecimal(12),2, RoundingMode.HALF_UP) + " Tahun" );
    }
}