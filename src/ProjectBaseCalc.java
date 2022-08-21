import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectBaseCalc {
    public static final String PROJECT_ID ="Project ID";
//    public static final String PROJECT_TENOR_LEFT = "Project Tenor Left";
    public static final String NILAI_PROJECT = "nilai project";
    public static final BigDecimal DANA_AWAL=new BigDecimal(1000000);
    public static final BigDecimal TOPUP_BULANAN=new BigDecimal(5000000);
    public static final BigDecimal LOT_PER_PROJECT= new BigDecimal(1000000);
    public static final String BULAN_BERJALAN = "Bulan Berjalan";
    public static final String STATUS = "Status";
    public static final String STATUS_OPEN = "Status Open";
    public static final String STATUS_CLOSE = "Status Close";
    public static final int BATAS_INVESTASI = 12; //dalam bulan
    public static final int DEFAULT_TENOR=12;
    public static final int ANNUAL_PROJECT_RETURN = 17;
    public static final BigDecimal HUNDRED= new BigDecimal(100);
    public static final int PAJAK = 15;

    public static void main(String[] args) {
        List projectList = new ArrayList();
        Map projectMap = new HashMap();
        BigDecimal rekening = new BigDecimal(0);
        BigDecimal wallet= new BigDecimal(0);
        projectMap.put(PROJECT_ID,1);
//        projectMap.put(PROJECT_TENOR_LEFT,DEFAULT_TENOR);
        projectMap.put(NILAI_PROJECT,DANA_AWAL);
        projectMap.put(STATUS,STATUS_OPEN);
        projectMap.put(BULAN_BERJALAN,0);
        projectList.add(projectMap);

        for (int bulan = 1; bulan <= BATAS_INVESTASI; bulan++) {
            for (int projectIndex = 0; projectIndex <projectList.size() ; projectIndex++) {
                Map project = (Map) projectList.get(projectIndex);
                if(project.get(STATUS).equals(STATUS_OPEN)){
                    BigDecimal nilaiProject= (BigDecimal) project.get(NILAI_PROJECT);
                    int bulanBerjalan = ((int) project.get(BULAN_BERJALAN))+1;
                    project.put(BULAN_BERJALAN,bulanBerjalan);
                    BigDecimal returnBulanan= nilaiProject.multiply(
                            new BigDecimal(bulanBerjalan==DEFAULT_TENOR ? (ANNUAL_PROJECT_RETURN-DEFAULT_TENOR)+1 : 1).divide(HUNDRED)
                    );
                    returnBulanan=returnBulanan.subtract(returnBulanan.multiply(PAJAK >0 ? new BigDecimal(PAJAK).divide(HUNDRED) : BigDecimal.ZERO));
                    rekening= rekening.add(returnBulanan);
                    if(bulanBerjalan==DEFAULT_TENOR){
                        project.put(STATUS,STATUS_CLOSE);
                    }
                }
            }
            
        }
        System.out.println(rekening);


    }



}
