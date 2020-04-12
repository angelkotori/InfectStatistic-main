import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * InfectStatistic TODO
 *
 * @author HHQ
 * @version 2.4
 */
class InfectStatistic {
        
    /** ����args��ֵ */
    public static String[] paramenterStrings;
    
    /** indexΪ�������ڹ�ϣ���е�λ�ã�ֵΪ��������paramenterStrings�е��±꣬�����ڲ�������Ϊ-1 */
    public static int[]  indexOfParamenterStrings = {-1, -1, -1, -1, -1, -1};
    
    /** log ��־�ļ�Ŀ¼,��Ŀ�ػḽ�� */
    public static String inputDir = "";
    
    /** ͳ�Ƶ���һ�� */
    public static String toDateString = "";
    
    /** ���·��&�ļ��� */
    public static String outputFileNameString = "";
    
    /** type�Ĳ���ֵ */
    public static String[] paramentersOfType = new String[10];
    
    /** province�Ĳ���ֵ */
    public static String[] paramentersOfProvince = new String[25]; 
    
    /** �����洢ʡ�ݵĹ�ϣ�� */
    public static Hashtable<String, Province> hashtable = new Hashtable<String, Province>(40);

    /**
     * Province��
     * @author HHQ
     */
    public class Province {

        /** ʡ������ */
        String provinceName; 
        /** ��Ⱦ���� */
        long ip; 
        /** ���ƻ��� */
        long sp;
        /** ���� */
        long cure;
        /** ���� */
        long dead;

        Province(String provinceName, int ip, int sp, int cure, int dead) {
            this.provinceName = provinceName;
            this.ip = ip;
            this.sp = sp;
            this.cure = cure;
            this.dead = dead;
        }

        /** ��Ⱦ�������� */
        public void increaseIp(int newIpNum) {
            ip += newIpNum;
        }

        /** ��Ⱦ�������� */
        public void decreaseIp(int ipNum) {
            ip -= ipNum;
        }

        /** ���ƻ������� */
        public void increaseSp(int newSpNum) {
            sp += newSpNum;
        }
        
        /** ���ƻ��߼��� */
        public void decreaseSp(int spNum) {
            sp -= spNum;
        }

        /** �������� */
        public void increaseCure(int newCureNum) {
            cure += newCureNum;
        }

        /** �������� */
        public void increaseDead(int newDeadNum) {
            dead += newDeadNum;
        }
        
        public String getProvinceName() {
            return provinceName;
        }

        public long getIp() {	
            return ip;
        }

        public long getSp() {
            return sp;
        }

        public long getCure() {
            return cure;
        }

        public long getDead() {
            return dead;
        }

        /**
         * description����ӡȫ��ͳ�Ƶ����ݽ��
         * @return resString ����ֵΪ�ַ���
         */
        public String getAllResult() {
            String resString = provinceName + ' ' + "��Ⱦ����" + ip + "��" + ' ' + "���ƻ���" + sp + "��" + ' ' + "����" + cure
                    + "��" + ' ' + "����" + dead + "��";
            return resString;
        }
        
        /**
         * description����ָ������ֵҪ��������
         * @param paramenterOf һ��������-type�Ĳ���ֵ������
         * @return resString ����ֵΪ�ַ���
         */
        public String getResultByRequest(String[] paramentersOfType) {
            String resString = provinceName + ' ';
            for(int i=0; paramentersOfType[i] != null; i++) {
                switch (paramentersOfType[i]) {
                case "ip":
                    resString += "��Ⱦ����" + ip + "��" + ' ';
                    break;
                case "sp":
                    resString += "���ƻ���" + sp + "��" + ' ';
                    break;
                case "cure":
                    resString += "����" + cure + "��" + ' ';
                    break;
                case "dead":
                    resString += "����" + dead + "��" + ' ';
                    break;
                default:
                    break;
                }
            }
            
            return resString;
        }
    }

    
    /**
     * description:���ڲ��������ַ��������ı������һ�����ݣ���һЩ����
     * @author HHQ
     */
    static class OpLineStringMethods{
        
//        /**
//         * description����һ���ַ����Կո�" "�ָ�
//         * @param string ������ַ���
//         * @return ����ֵΪ�ָ������鳤��
//         */
//        public static int numAfterSplit(String string) {
//            String[] afterSplitStrings = string.split(" ");
//            return afterSplitStrings.length;
//        }

        /**
         * description����ȡһ���ַ���ǰ������
         * @param string ������ַ���
         * @return ����ֵΪȡ�õ���ֵint
         */
        public static int getNumber(String string) {
            for (int i=0,len=string.length(); i < len; i++) {
                if (Character.isDigit(string.charAt(i))) {
                    ;
                } else {
                    string = string.substring(0, i);
                    break;
                }
            }

            return Integer.parseInt(string);
        }

        /**
         * description���õ�Ҫ�޸����ݵ�ʡ������modifyProvinceName
         * @param strings �ָ����ַ�������
         * @return ����ֵΪʡ���������飬ֻ��һ��ʡ��ʱ�ڶ���Ϊ��
         */
        public static String[] getNeedModifyProvinceNames(String[] strings) {
            int len = strings.length;
            String[] resStrings = new String[2];
            if (len == 3 || len == 4) {
                resStrings[0] = strings[0];
                resStrings[1] = "";
            } else if (len == 5) {
                resStrings[0] = strings[0];
                resStrings[1] = strings[3];
            }
            return resStrings;
        }

        /**
         * description���жϲ�������
         * @param strings �ָ����ַ�������
         * @return ����ֵ��������ID��1~8��
         */
        public static int getOperateType(String[] strings) {
            int len = strings.length;
            int res = 0;
            if (len == 3) {
                if (strings[1].equals("����")) {
                    res = 1;
                } else if (strings[1].equals("����")) {
                    res = 2;
                }
            } else if (len == 4) {
                if (strings[1].equals("����")) {
                    if (strings[2].equals("��Ⱦ����")) {
                        res = 3;
                    } else if (strings[2].equals("���ƻ���")) {
                        res = 4;
                    }
                } else if (strings[1].equals("�ų�")) {
                    res = 5;
                } else {
                    res = 6;
                }
            } else {
                if (strings[1].equals("��Ⱦ����")) {
                    res = 7;
                } else {
                    res = 8;
                }
            }
            return res;
        }

        /**
         * description�����жϸ�����ע���У����ж�ǰ�����ַ�"//"������ǿ���Ҳ����
         * @param string ����һ���ַ���
         * @return ����ֵ
         */
        public static boolean isAnnotation(String lineString) {
            if (lineString.equals("") || lineString.charAt(0) == '/' && lineString.charAt(1) == '/') {
                return true;
            } else {
                return false;
            }
        }


    }
    
    /**
     * description:��ȡ�����ļ�����ط���
     * @author HHQ
     */
    static class GetFileMethods{
        /**
         * description��ȡ������log����������
         * @param nameStrings ������ļ�������
         * @return �������ڣ����ͣ�Date
         */
        public static Date getMaxDate(String[] nameStrings) {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            String maxDateString = "2000-01-01";
            Date maxDate = null;
            try {
                maxDate = dFormat.parse(maxDateString);
                Date tmpDate = new Date();  //�����Ż���1
                for(int i=0, len=nameStrings.length; i<len; i++) {
                    tmpDate = dFormat.parse(nameStrings[i]);
                    if(tmpDate.getTime() >= maxDate.getTime()) {
                        maxDate = tmpDate;
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return maxDate;
        }

//        /** description��ȡ�ý�������� */
//        public static String getToday() {
//            Date todayDate = new Date();
//            SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String todayString = sdfDateFormat.format(todayDate);
//            return todayString;
//        }
        
        /**
         * description����ȡ�ļ�����ָ������ǰ�������ļ��ļ���
         * @param path �ļ���·��
         * @param date ָ��������
         * @param fileName ��õ��ļ����б�
         */
        public static void getBeforeDateFileName(String path, String date, ArrayList<String> fileName) {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            File file = new File(path);
            String[] nameStrings = file.list(); //ȡ�������ļ�����
            Date maxDate = getMaxDate(nameStrings); //�õ������ļ����Ƶ��������
            if (nameStrings != null) {                
                try {
                    String dateOfFileNameString = "";
                    Date dateOfFileNameDate = new Date();
                    Date limitDate = dFormat.parse(date);   //ָ������--ͳ�Ƶ���һ��
                    for (int i = 0, len=nameStrings.length; i < len; i++) {
                        dateOfFileNameString = nameStrings[i].substring(0, nameStrings[i].indexOf('.')); //ȡ���ļ����е�����****-**-**
                        dateOfFileNameDate = dFormat.parse(dateOfFileNameString);  //��string����תΪdate��ʽ
                        limitDate = dFormat.parse(date);   //ָ������--ͳ�Ƶ���һ��
                        if(limitDate.getTime() > maxDate.getTime()) {
                            System.out.println("���ڳ�����Χ");
                        }else {
                            if (dateOfFileNameDate.getTime() <= limitDate.getTime()) {
                                fileName.add(nameStrings[i]);
                            }
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }
        
        /**
         * description��ȡ��ָ��Ŀ¼����������
         * @param inputDir ָ��Ŀ¼
         * @return �������ڣ����ͣ�string
         */
        public static String getMaxDateInputDir(String inputDir) {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            File file = new File(inputDir);
            String[] nameStrings = file.list();
            Date maxDate = getMaxDate(nameStrings);
            return (dFormat.format(maxDate));
        }

    }
    
    /**
     * description:�漰��Province��һЩ����
     * @author HHQ
     */
    static class RelativeProviceMethods{
        /**
         * description��ͳ��ʡ������
         * @param lineString һ���ַ���
         * @param hashtable �������ͳ�Ƶ�ʡ��
         */
        public static void calcProvince(String lineString, Hashtable<String, Province> hashtable) {
            InfectStatistic infectStatistic = new InfectStatistic();
            String[] afterSplitStrings = lineString.split(" ");
            int numAfterSplit = afterSplitStrings.length; // �и������
            int number = OpLineStringMethods.getNumber(afterSplitStrings[numAfterSplit - 1]); // һ����Ϣ���漰������
            String[] provinceNameStrings = OpLineStringMethods.getNeedModifyProvinceNames(afterSplitStrings);   //��Ҫ�޸����ݵ�ʡ������
            int operateType = OpLineStringMethods.getOperateType(afterSplitStrings);    // ��ò�������

            if (provinceNameStrings[1].equals("")) { // ֻ��һ��ʡ
                if (!hashtable.containsKey(provinceNameStrings[0])) { // ��ϣ����û�и�ʡ
                    Province province = infectStatistic.new Province(provinceNameStrings[0], 0, 0, 0, 0);
                    RelativeProviceMethods.executeOperate(province, province, operateType, number);
                    hashtable.put(province.getProvinceName(), province);
                } else {
                    Province province = hashtable.get(provinceNameStrings[0]);
                    RelativeProviceMethods.executeOperate(province, province, operateType, number);
                }
            } else if (!provinceNameStrings[1].equals("")) { // ������ʡ
                Province province1 = null;
                Province province2 = null;
                if (hashtable.containsKey(provinceNameStrings[0])) {
                    province1 = hashtable.get(provinceNameStrings[0]);
                    if(hashtable.containsKey(provinceNameStrings[1])){
                        province2 = hashtable.get(provinceNameStrings[1]);
                    }else{
                        province2 = infectStatistic.new Province(provinceNameStrings[1], 0, 0, 0, 0);
                        hashtable.put(provinceNameStrings[1], province2);
                    }
                }else if (!hashtable.containsKey(provinceNameStrings[0])) {
                    province1 = infectStatistic.new Province(provinceNameStrings[0], 0, 0, 0, 0);
                    if(hashtable.containsKey(provinceNameStrings[1])){
                        province2 = hashtable.get(provinceNameStrings[1]);
                    }else{
                        province2 = infectStatistic.new Province(provinceNameStrings[1], 0, 0, 0, 0);
                        hashtable.put(provinceNameStrings[1], province2);
                    }
                    hashtable.put(provinceNameStrings[0], province1);
                }
                RelativeProviceMethods.executeOperate(province1, province2, operateType, number);
            }

        }

        /**
         * description��ͳ��ȫ��������
         * @param hashtable ���������в���ͳ�Ƶ�ʡ��
         */
        public static void calcWholeNation(Hashtable<String, Province> hashtable) {
            InfectStatistic infectStatistic = new InfectStatistic();
            Province wholeNation = infectStatistic.new Province("ȫ��", 0, 0, 0, 0);
            Set set = hashtable.keySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Object keyObject = iterator.next();
                wholeNation.ip += hashtable.get(keyObject).getIp();
                wholeNation.sp += hashtable.get(keyObject).getSp();
                wholeNation.cure += hashtable.get(keyObject).getCure();
                wholeNation.dead += hashtable.get(keyObject).getDead();
            }
            hashtable.put("ȫ��", wholeNation);
        }
        
        /**
         * description������ʡ�ݺͲ�������IDִ����Ӧ�Ĳ���
         * @param province1 ʡ��1
         * @param province2 ʡ��2���п���Ϊ��
         * @param operateType ��������ID��1~8��
         * @param number ִ����Ӧ�޸ĵ� ����
         */
        public static void executeOperate(Province province1, Province province2, int operateType, int number) {
            switch (operateType) {
            case 1:
                province1.increaseDead(number);
                province1.decreaseIp(number);
                break;
            case 2:
                province1.increaseCure(number);
                province1.decreaseIp(number);
                break;
            case 3:
                province1.increaseIp(number);
                break;
            case 4:
                province1.increaseSp(number);
                break;
            case 5:
                province1.decreaseSp(number);
                break;
            case 6:
                province1.decreaseSp(number);
                province1.increaseIp(number);
                break;
            case 7:
                province1.decreaseIp(number);
                province2.increaseIp(number);
                break;
            case 8:
                province1.decreaseSp(number);
                province2.increaseSp(number);
                break;
            default:
                break;
            }
        }

    }
    
    /**
     * description:����ļ�����ط���
     * @author HHQ
     */
    static class OutPutFileMethods{
        
        /**
         * description:������ϣ����ӡ������Ϣ
         */
        public static void writeInfoOfHashtale(Hashtable<String, Province> hashtable,OutputStreamWriter outputStreamWriter) {
            List<Map.Entry<String,Province>> list = OpHashTableMethods.sortByHeadAlphabet(hashtable);       //����
//          List<Map.Entry<String,Province>> list = new ArrayList<>(hashtable.entrySet());
            Province province = null;
            try {
                for (Map.Entry entry : list){
                    province = (Province) entry.getValue();
                    
                    if(paramentersOfType[0].equals("null")) {   //û��ָ���������
                        outputStreamWriter.write(province.getAllResult() + "\r\n");
                        outputStreamWriter.flush();
                    }else {
                        outputStreamWriter.write(province.getResultByRequest(paramentersOfType) + "\r\n");
                        outputStreamWriter.flush();
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        
        /**
         * description��д���ļ�
         * @param hashtable ���������в���ͳ�Ƶ�ʡ��
         * @param fileOutputStream ����ļ���
         * @param paramenterOfType���� -type�Ĳ���ֵ
         * @param paramenterOfProvice���� -province�Ĳ���ֵ
         * @param commandLineStrings���� ���������� argv
         */
        public static void writeFile(Hashtable<String, Province> hashtable, FileOutputStream fileOutputStream, 
            String[] paramentersOfType, String[] paramentersOfProvince,String[] commandLineStrings) {
            String endLineString = "// ���ĵ�������ʵ���ݣ���������ʹ��";
            String commandLineString = "// ���";
            for(int i=0, len=commandLineStrings.length; i<len; i++) {
                commandLineString = commandLineString + commandLineStrings[i] + ' ';
            }
            InfectStatistic infectStatistic = new InfectStatistic();
            try {
               
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF8");
                
                if(paramentersOfProvince[0].equals("null")) {   //û��ָ��ʡ��
                    
                    writeInfoOfHashtale(hashtable, outputStreamWriter);
                    
                    outputStreamWriter.write(endLineString + "\r\n" + commandLineString);
                    outputStreamWriter.flush();
                }else { //ָ��ʡ��
                    Hashtable<String, Province> requestProvinceHashtable = new Hashtable<String, InfectStatistic.Province>();
//                    for(int i=0; i<paramentersOfProvince.length; i++) {   // ����.length��ָ����ʡ�ĸ�����һ����������Ĵ�С
                    Province province = null;
                    for(int i=0; paramentersOfProvince[i] != null; i++) {
                        if(!hashtable.containsKey(paramentersOfProvince[i])) {  //��ϣ���в�����
                            province = infectStatistic.new Province(paramentersOfProvince[i], 0, 0, 0, 0);
                            requestProvinceHashtable.put(paramentersOfProvince[i], province);
                        }else { //��ϣ���д���
                            province = hashtable.get(paramentersOfProvince[i]);
                            requestProvinceHashtable.put(paramentersOfProvince[i], province);
                        }
                    }
                    
                    writeInfoOfHashtale(requestProvinceHashtable, outputStreamWriter);
                    
                    outputStreamWriter.write(endLineString + "\r\n" + commandLineString);
                    outputStreamWriter.flush();
                }
                
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }
    
    /**
     * description:�йع�ϣ���һЩ����
     * @author HHQ
     */
    static class OpHashTableMethods{
        /**
         * description��HashMap����value��ȡkey
         * @param map Ҫ��ת��HashMap
         * @param value map��ֵ
         * @return ���ػ�õ�keyֵ�����û�ҵ�����-1
         */
        public static int getKey(HashMap<Integer, String> map, String value) {
            int res = -1;
            for(int getKey:map.keySet()) {
                if(map.get(getKey).equals(value)) {
                    res = getKey;
                }
            }
            return res;
        }

        /**
         * description������������ĸ���򣬡�ȫ�����ö�
         * @param hashtable ���������в���ͳ�Ƶ�ʡ�ݣ���Ҫ����Ķ���
         * @return ����������lis����
         */
        public static List<Map.Entry<String,Province>> sortByHeadAlphabet(Hashtable<String, Province> hashtable) {
            Hashtable<String, String> alphabetOfProvince = new Hashtable<String, String>(35);
            alphabetOfProvince.put("ȫ��", "AAAQG");
            alphabetOfProvince.put("����", "BJ");
            alphabetOfProvince.put("���", "TJ");
            alphabetOfProvince.put("�Ϻ�", "SH");
            alphabetOfProvince.put("����", "CQ");
            alphabetOfProvince.put("�ӱ�", "HB");
            alphabetOfProvince.put("ɽ��", "SXA");
            alphabetOfProvince.put("����", "LN");
            alphabetOfProvince.put("����", "JL");
            alphabetOfProvince.put("������", "HLJ");
            alphabetOfProvince.put("����", "JS");
            alphabetOfProvince.put("�㽭", "ZJ");
            alphabetOfProvince.put("����", "AH");
            alphabetOfProvince.put("����", "FJ");
            alphabetOfProvince.put("����", "JX");
            alphabetOfProvince.put("ɽ��", "SD");
            alphabetOfProvince.put("����", "HN");
            alphabetOfProvince.put("����", "HB");
            alphabetOfProvince.put("����", "HN");
            alphabetOfProvince.put("�㶫", "GD");
            alphabetOfProvince.put("����", "HN");
            alphabetOfProvince.put("�Ĵ�", "SC");
            alphabetOfProvince.put("����", "GZ");
            alphabetOfProvince.put("����", "YN");
            alphabetOfProvince.put("����", "SXB");
            alphabetOfProvince.put("����", "GS");
            alphabetOfProvince.put("�ຣ", "QH");
            alphabetOfProvince.put("̨��", "TW");
            alphabetOfProvince.put("���ɹ�", "NMG");
            alphabetOfProvince.put("����", "GX");
            alphabetOfProvince.put("����", "XZ");
            alphabetOfProvince.put("����", "NX");
            alphabetOfProvince.put("�½�", "XZ");
            alphabetOfProvince.put("���", "XG");
            alphabetOfProvince.put("����", "AM");        
            
            List<Map.Entry<String,Province>> list = new ArrayList<>(hashtable.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Province>>() {
                @Override
                public int compare(Map.Entry<String, Province> o1, Map.Entry<String, Province> o2) {
                    return alphabetOfProvince.get(o1.getKey()).compareTo(alphabetOfProvince.get(o2.getKey()));
                }
            });
            
            return list;
        }
        
        /**
         * description����ʼ����������ϣ��
         * @return ����һ���������в������Ĺ�ϣ��
         */
        public static HashMap<Integer, String> initParamentHashMap() {
            HashMap<Integer, String> paramenterHashMap = new HashMap<Integer, String>(5);
            paramenterHashMap.put(1, "-log");
            paramenterHashMap.put(2, "-out");
            paramenterHashMap.put(3, "-date");
            paramenterHashMap.put(4, "-type");
            paramenterHashMap.put(5, "-province");
            
            return paramenterHashMap;
        }
    
    }
    
    /**
     * description:�����в�����������ʼ����ִ��ͳ�Ʋ�д��
     * @author HHQ
     */
    static class StartMethods{
        
        /**
         * description:����������Ͳ���ֵ
         * @param args ���������е�����
         */
        public static void separateNameAndValues(String[] args) {
            HashMap<Integer, String> paramenterHashMap = OpHashTableMethods.initParamentHashMap(); //һ���������в������Ĺ�ϣ��
            
            paramenterStrings = new String[args.length - 1];   //�洢����Ĳ�����������ֵ
            for(int i=1,len=args.length; i<len; i++) {
                paramenterStrings[i-1] = args[i];
            }
            
            int key;
            //�ҵ�������������¼λ��
            for(int i=0,len=paramenterStrings.length; i<len; i++) {
                key = OpHashTableMethods.getKey(paramenterHashMap, paramenterStrings[i]);
                if( key != -1) {   //�ǲ�����
                    indexOfParamenterStrings[key] = i;   //key��Ӧ�Ĳ�������patamenterStrings��i�±�λ��,ֵΪ-1������޴˲�����
                }
            }
        }
        
        
        /**
         * description:��ʼ������·�������·�����������ڡ�type����ֵ��province����ֵ
         */
        public static void initVariables() {
            HashMap<Integer, String> paramenterHashMap = OpHashTableMethods.initParamentHashMap(); //һ���������в������Ĺ�ϣ��
            paramentersOfType[0] = "null";
            paramentersOfProvince[0] = "null";
            
            //���Ŵ���ÿ����������Ӧ�Ĳ���ֵ
            for(int i=1; i<=5; i++) {
                if(indexOfParamenterStrings[i] != -1) { //�����˸ò�����
                    if(i == 1) {    // -log
                        inputDir = paramenterStrings[indexOfParamenterStrings[i] + 1];    //����log·��
                        toDateString = GetFileMethods.getMaxDateInputDir(inputDir); // �õ�����·����������ʼ��ָ��������
                    }else if(i == 2) {  //-out
                        outputFileNameString = paramenterStrings[indexOfParamenterStrings[i] + 1];      //��������ļ�·��
                    }else if(i == 3) {  //-date
                        toDateString = paramenterStrings[indexOfParamenterStrings[i] + 1];  //ͳ�Ƶ���һ��
                    }else if(i == 4) {  //-type ���ܻ��ж������
                        String[] paramenterValues = new String[20]; //��¼���в���ֵ
                        int cnt = 0;
                        //ȡ�ò���ֵ��ֱ���ҵ���һ��������ʱֹͣ��   ��ǰ������ ����ֵ1 ����ֵ2 ... ��һ��������
                         for(int j=indexOfParamenterStrings[i]+1; 
                            j<paramenterStrings.length && OpHashTableMethods.getKey(paramenterHashMap, paramenterStrings[j])==-1; j++) { 
                            paramenterValues[cnt++] = paramenterStrings[j];
                            paramentersOfType = paramenterValues;
                        }
                    }else if(i == 5) {  //-province
                        String[] paramenterValues = new String[20];
                        int cnt = 0;
                        //ȡ�ò���ֵ��ֱ���ҵ���һ��������ʱֹͣ��   ��ǰ������ ����ֵ1 ����ֵ2 ... ��һ��������
                         for(int j=indexOfParamenterStrings[i]+1; 
                            j<paramenterStrings.length && OpHashTableMethods.getKey(paramenterHashMap, paramenterStrings[j])==-1; j++) { 
                            paramenterValues[cnt++] = paramenterStrings[j];
                            paramentersOfProvince = paramenterValues;
                        }
                    }
                }
            }
        }
        
        /**
         * description:ִ��ͳ�Ʋ�д���ļ�
         * @param ���������е�����
         * */
        public static void execCalcAndWrite(String[] args) {
            InfectStatistic infectStatistic = new InfectStatistic();
            ArrayList<String> listFileNameArrayList = new ArrayList<String>();      //��������һ���ļ����µ��ļ�����������
            GetFileMethods.getBeforeDateFileName(inputDir, toDateString, listFileNameArrayList);    //��ʼ��listFileNameArrayList
            
            try {
                File file = null;
                File outputFile = new File(outputFileNameString);
                String outputDirString = outputFileNameString.substring(0,outputFileNameString.lastIndexOf("/"));
                File outputDir = new File(outputDirString);
                FileOutputStream fileOutputStream = null;
                InputStreamReader reader = null;
                String filePathString = "";
                for (int cnt=0, len=listFileNameArrayList.size(); cnt < len; cnt++) {
                    filePathString = inputDir + "/" + listFileNameArrayList.get(cnt);  //�����ļ�·��
                    file = new File(filePathString);
                    
                    if(!outputDir.exists()) {
                        outputDir.mkdir();
                    }
                    if (!outputFile.exists()) {
                        outputFile.createNewFile();
                    }
                    if (file.isFile() && file.exists()) {
                        reader = new InputStreamReader(new FileInputStream(file), "UTF8");
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        fileOutputStream = new FileOutputStream(outputFileNameString);

                        String lineString = null;
                        while ((lineString = bufferedReader.readLine()) != null) {
                            if (!OpLineStringMethods.isAnnotation(lineString)) { // ����ע����
                                RelativeProviceMethods.calcProvince(lineString, hashtable); // ����ͳ��
                            } else { // ��ע���У���ִ���κβ���
                                ;
                            }
                        }
                    }else {
                        System.out.println("�����ļ�·����"+filePathString);
                        System.out.println("�Ҳ��������ļ�");
                    }
                }
                
                RelativeProviceMethods.calcWholeNation(hashtable);
                OutPutFileMethods.writeFile(hashtable, fileOutputStream, paramentersOfType, paramentersOfProvince, args);
                reader.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        
    }
    
    
    public static void main(String[] args) {
        
        StartMethods.separateNameAndValues(args);
        StartMethods.initVariables();
        StartMethods.execCalcAndWrite(args);
    }
}
