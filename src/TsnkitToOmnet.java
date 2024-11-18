import java.util.Arrays;

public class TsnkitToOmnet {
    public static void main(String[] args) {
        int offset1 = 920;
        int offset2 = 828;
        String dur1 = "940us,12us,36us,12us,940us,12us,36us,12us";
        String dur2 = "940us,60us,940us,36us,12us,12us";
        String[] dur1SList = dur1.split(",");
        String[] dur2SList = dur2.split(",");
        int[] dur1IList = new int[dur1SList.length + 1];
        int[] dur2IList = new int[dur2SList.length + 1];
        for (int i = 0; i < dur1SList.length; i++)
            dur1IList[i] = Integer.parseInt(dur1SList[i].replace("us", ""));
        for (int i = 0; i < dur2SList.length; i++)
            dur2IList[i] = Integer.parseInt(dur2SList[i].replace("us", ""));
        dur1IList[dur1IList.length - 1] = offset1;
        dur2IList[dur2IList.length - 1] = offset2;
        dur1IList[0] = dur1IList[0] - offset1;
        dur2IList[0] = dur2IList[0] - offset2;
        int period = Arrays.stream(dur1IList).sum();
        char[] dur1CList = new char[period];
        char[] dur2CList = new char[period];
        int pt = 0;
        char status = 0;
        for (int i = 0; i < dur1IList.length; i++) {
            for (int j = 0; j < dur1IList[i]; j++) {
                dur1CList[pt] = status;
                pt ++;
            }
            status = status == (char)0 ? (char)1 : (char)0;
        }
        pt = 0;
        status = 0;
        for (int i = 0; i < dur2IList.length; i++) {
            for (int j = 0; j < dur2IList[i]; j++) {
                dur2CList[pt] = status;
                pt ++;
            }
            status = status == (char)0 ? (char)1 : (char)0;
        }
        char[] finals = new char[period];
        for(int i = 0; i < period; ++i) if (dur1CList[i] == (char)0 && dur2CList[i] == (char)0) finals[i] = 1;
        status = 1;
        int count = 0;
        int test = 0;
        StringBuffer fin = new StringBuffer();
        for (int i = 0; i < finals.length; i++) {
            if (finals[i] == status) {
                count++;
                continue;
            }
            fin.append(count).append("us,");
            test++;
            status = finals[i];
            count = 1;
        }
        fin.append(count).append("us");
        System.out.println(fin);
        test++;
        System.out.println(test);
        for (int i = 0; i < dur1CList.length - 1; i++) {
            System.out.printf((int)dur1CList[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < dur2CList.length - 1; i++) {
            System.out.printf((int)dur2CList[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < dur1CList.length - 1; i++) {
            System.out.printf((int)finals[i] + " ");
        }
    }
}