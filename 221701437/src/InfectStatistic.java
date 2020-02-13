import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InfectStatistic
 * TODO
 *
 * @author luckyzcccc
 * @version 1.0
 * @since
 */
class InfectStatistic {
    public static void main(String[] args) throws IOException {
        /*ProvinceData pd = ProvinceData.getInstance();
        AbstractDataHandle dataHandle = AbstractDataHandle.getChainOfDataHandle();
        FileProcess fileProcess = FileProcess.getInstance();
        ArrayList<File> fileList = fileProcess.InputFileData();
        for(int i = 0; i < fileList.size(); i++){
            ArrayList<String> data = fileProcess.getFileData(fileList.get(i));
            for(int j = 0; j < data.size(); j++)
                dataHandle.dataProcessing(data.get(j));
        }
        pd.LogData("./221701437/result/output.txt");*/
        CmdArgs cmdArgs = new CmdArgs(args);
        System.out.println(cmdArgs.paramToValue.get("-province"));
    }
}
class ProvinceData{
    private static ProvinceData provinceData = new ProvinceData();

    private TreeMap<String,int[]> AllData = new TreeMap<>();
    private String[] initStr = {
            "全国","安徽","澳门","北京","重庆","福建","甘肃","广东","广西",
            "贵州", "海南","河北","河南","黑龙江","湖北","湖南","吉林","江苏",
            "江西", "辽宁","内蒙古","宁夏","青海","山东","山西","陕西","上海",
            "四川", "台湾","天津","西藏","香港","新疆","云南","浙江"
    };
    private ProvinceData(){
        for(int i = 0; i < initStr.length; i++){
            int[] initArray = {0,0,0,0};
            AllData.put(initStr[i],initArray);
        }
    }

    public static ProvinceData getInstance(){
        return provinceData;
    }

    public void AddInfectPeople(String province,int num){
        int[] temp = AllData.get(province);
        temp[0]+=num;
        AllData.put(province,temp);
    }

    public void SubInfectPeople(String province,int num){
        int[] temp = AllData.get(province);
        temp[0]-=num;
        AllData.put(province,temp);
    }

    public void AddDoubtPeople(String province,int num){
        int[] temp = AllData.get(province);
        temp[1]+=num;
        AllData.put(province,temp);
    }

    public void SubDoubtPeople(String province,int num){
        int[] temp = AllData.get(province);
        temp[1]-=num;
        AllData.put(province,temp);
    }

    public void AddCurePeople(String province,int num){
        int[] temp = AllData.get(province);
        temp[2]+=num;
        AllData.put(province,temp);
    }

    public void AddDeadPeople(String province,int num){
        int[] temp = AllData.get(province);
        temp[3]+=num;
        AllData.put(province,temp);
    }

    public void AllPrint() {
        Iterator<String> it = AllData.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            System.out.print(key+"=");
            int[] temp = AllData.get(key);
            for(int i = 0; i < temp.length; i++)
                System.out.print(temp[i]+",");
            System.out.println();
        }
    }

    public void LogData(String path) throws IOException {
        File outFile = new File(path);
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        PrintStream stream=null;
        stream=new PrintStream(path);
        String data = new String();
        for(int i = 0; i < initStr.length; i++){
            int[] datavalue = AllData.get(initStr[i]);
            data = data + initStr[i] + " 感染患者" + datavalue[0] + "人 疑似患者" + datavalue[1] + "人 治愈" + datavalue[2] + "人 死亡" + datavalue[3] + "人\n";
        }
        System.out.println(data);
        stream.print(data);
    }
}

abstract class AbstractDataHandle{
    public final static String s1 = "^[\\u4e00-\\u9fa5]*\\s(新增)\\s(感染患者)\\s(\\d+)人?";
    public final static String s2 = "^[\\u4e00-\\u9fa5]*\\s(新增)\\s(疑似患者)\\s(\\d+)人?";
    public final static String s3 = "^[\\u4e00-\\u9fa5]*\\s(死亡)\\s(\\d+)人?";
    public final static String s4 = "^[\\u4e00-\\u9fa5]*\\s(治愈)\\s(\\d+)人?";
    public final static String s5 = "^[\\u4e00-\\u9fa5]*\\s(排除)\\s(疑似患者)\\s(\\d+)人?";
    public final static String s6 = "^[\\u4e00-\\u9fa5]*\\s(疑似患者)\\s(确诊感染)\\s(\\d+)人?";
    public final static String s7 = "^[\\u4e00-\\u9fa5]*\\s(感染患者)\\s(流入)\\s[\\u4e00-\\u9fa5]*\\s(\\d+)人?";
    public final static String s8 = "^[\\u4e00-\\u9fa5]*\\s(疑似患者)\\s(流入)\\s[\\u4e00-\\u9fa5]*\\s(\\d+)人?";
    public final static String PICKUPDIGIT = "[^0-9]";

    public static int count = 0;

    protected String model;
    protected AbstractDataHandle nextDataHandle;

    public void setNextDataHandle(AbstractDataHandle nextDataHandle){
        this.nextDataHandle = nextDataHandle;
    }

    public void dataProcessing(String str){
        Pattern pattern = Pattern.compile(model);
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.matches();
        if(result){
            processing(str);
        }else if(nextDataHandle != null){
            nextDataHandle.dataProcessing(str);
        }
    }

    public static AbstractDataHandle getChainOfDataHandle(){
        AbstractDataHandle addInfect = new AddInfectPeople(AbstractDataHandle.s1);
        AbstractDataHandle addDoubt = new AddDoubtPeople(AbstractDataHandle.s2);
        AbstractDataHandle addDead = new AddDeadPeople(AbstractDataHandle.s3);
        AbstractDataHandle addCure = new AddCurePeople(AbstractDataHandle.s4);
        AbstractDataHandle subDoubt = new SubDoubtPeople(AbstractDataHandle.s5);
        AbstractDataHandle sureDoubt = new SureInfectPeople(AbstractDataHandle.s6);
        AbstractDataHandle infectInflow = new InfectInflow(AbstractDataHandle.s7);
        AbstractDataHandle doubtInflow = new DoubtInflow(AbstractDataHandle.s8);

        addInfect.setNextDataHandle(addDoubt);
        addDoubt.setNextDataHandle(addDead);
        addDead.setNextDataHandle(addCure);
        addCure.setNextDataHandle(subDoubt);
        subDoubt.setNextDataHandle(sureDoubt);
        sureDoubt.setNextDataHandle(doubtInflow);
        doubtInflow.setNextDataHandle(infectInflow);

        return addInfect;
    }

    public abstract void processing(String str);
}

class AddInfectPeople extends AbstractDataHandle{
    public AddInfectPeople(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.AddInfectPeople(province[0],num);
    }
}

class AddDoubtPeople extends AbstractDataHandle{
    public AddDoubtPeople(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.AddDoubtPeople(province[0],num);
    }
}

class SubDoubtPeople extends AbstractDataHandle{
    public SubDoubtPeople(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.SubDoubtPeople(province[0],num);
    }
}

class AddCurePeople extends AbstractDataHandle{
    public AddCurePeople(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.AddCurePeople(province[0],num);
        provinceData.SubInfectPeople(province[0],num);
    }
}

class AddDeadPeople extends AbstractDataHandle{
    public AddDeadPeople(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.AddDeadPeople(province[0],num);
        provinceData.SubInfectPeople(province[0],num);
    }
}

class DoubtInflow extends AbstractDataHandle{
    public DoubtInflow(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.SubDoubtPeople(province[0],num);
        provinceData.AddDoubtPeople(province[3],num);
    }
}

class InfectInflow extends AbstractDataHandle{
    public InfectInflow(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.SubInfectPeople(province[0],num);
        provinceData.AddInfectPeople(province[3],num);
    }
}

class SureInfectPeople extends AbstractDataHandle{
    public SureInfectPeople(String model){
        this.model = model;
    }

    @Override
    public void processing(String str) {
        ProvinceData provinceData = ProvinceData.getInstance();
        Pattern pattern = Pattern.compile(PICKUPDIGIT);
        Matcher matcher = pattern.matcher(str);

        int num = Integer.valueOf(matcher.replaceAll("").trim());
        String[] province = str.split("\\s");

        provinceData.AddInfectPeople(province[0],num);
        provinceData.SubDoubtPeople(province[0],num);
    }
}

class FileProcess{
    private final static String INPUTFILEPATH = "./221701437/log";
    private final static String OUTPUTFILEPATH = "./221701437/result";
    private final static String GETFLENAME = "(\\d{4})-(\\d{2})-(\\d{2})\\.log\\.txt";
    private static FileProcess fileProcess = new FileProcess();
    private File file = new File(INPUTFILEPATH);

    private FileProcess(){};

    public static FileProcess getInstance(){
        return fileProcess;
    }

    public ArrayList<File> InputFileData(){
        File[] tempList = file.listFiles();
        ArrayList<File> fileArrayList = new ArrayList<File>();
        for(int i = 0; i < tempList.length; i++){
            if(tempList[i].isFile()){
                Pattern pattern = Pattern.compile(GETFLENAME);
                Matcher matcher = pattern.matcher(tempList[i].getName());
                boolean result = matcher.matches();
                if(result){
                    fileArrayList.add(tempList[i]);
                }
            }
        }
        return fileArrayList;
    }

    public ArrayList<String> getFileData(File file) throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(inputReader);
        String str;
        while ((str = bf.readLine()) != null) {
            arrayList.add(str);
        }
        bf.close();
        inputReader.close();
        return arrayList;
    }
}

class CmdArgs{
    private String[] args;
    public HashMap<String, ArrayList<String>> paramToValue = new HashMap<>();
    //private static final String CMDPROCESS = "list (-data (\\d)-(\\d)-(\\d))* (-type (\\s)*)* (-province (//s)*)* -log (\\s) (-data (\\d)-(\\d)-(\\d))* (-type (\\s)*)* (-province (//s)*)* -out (\\s) (-data (\\d)-(\\d)-(\\d))* (-type (\\s)*)* (-province (//s)*)*";
    //private static final String CMDPROCESS1 = "list (-data (\\d)-(\\d)-(\\d))* (-type (\\s)*)* (-province (//s)*)* -out (\\s) (-data (\\d)-(\\d)-(\\d))* (-type (\\s)*)* (-province (//s)*)* -log (\\s) (-data (\\d)-(\\d)-(\\d))* (-type (\\s)*)* (-province (//s)*)*";


    CmdArgs(String[] args){
        this.args = args;
        argsProcess();
    }

    String getCmd(){
        return this.args[0];
    }

    public void argsProcess(){
        ArrayList<String> tempValue = new ArrayList<>();
        String tempKey = new String();
        int index = 0;
        for(int i = 1; i < args.length; i++){
            if(args[i].contains("-") && !HasDigit(args[i]) && index == 0){
                tempKey = args[i];
            }else if(args[i].contains("-") && index == 1){
                ArrayList<String> tempForValue = (ArrayList<String>) tempValue.clone();
                paramToValue.put(tempKey,tempForValue);
                tempKey = args[i];
                tempValue.clear();
            }else{
                tempValue.add(args[i]);
                index = 1;
            }
        }
        paramToValue.put(tempKey,tempValue);
    }

    public ArrayList<String> argVals(String key){
        return paramToValue.get(key);
    }

    public boolean hasParam(String key){
        if(paramToValue.containsKey(key)){
            return true;
        }else {
            return false;
        }
    }

    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}