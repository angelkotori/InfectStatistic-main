import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
public class Log {
    LocalDate date;
    File logFile;
    Country country;
    //记录该日志
    HashMap<String,DailyInfo> dailyInfos;

    public Log(String filePath){
        logFile=new File(filePath);
        String logName=logFile.getName();
        date=LocalDate.parse(logName.substring(0,10));
        //System.out.println("日志对象已创建，日期："+date);

        country=Country.getInstance();
        dailyInfos=new HashMap<>();
        for(String provinceName:Country.PROVINCES){
            DailyInfo dailyInfo=new DailyInfo(date);
            dailyInfos.put(provinceName,dailyInfo);
        }
//        date=LocalDate.parse(filePath.substring())
    }

    public void analyzeLog(){
        FileInputStream fis = null;

        //System.out.println("开始处理日志");
        try {
            fis = new FileInputStream(logFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                //注释不处理
                if(line.contains("//"))
                    continue;

                //下面是日志中八种情况的处理
                if(line.contains("新增") && line.contains("感染")) {
                    addInfected(line);
                    continue;
                }
                if(line.contains("新增") && line.contains("疑似")) {
                    addSuspected(line);
                    continue;
                }
                if(line.contains("流入") && line.contains("感染")) {
                    flowInfected(line);
                    continue;
                }
                if(line.contains("流入") && line.contains("疑似")) {
                    flowSuspected(line);
                    continue;
                }
                if(line.contains("死亡")) {
                    addDead(line);
                    continue;
                }
                if(line.contains("治愈")) {
                    addCured(line);
                    continue;
                }
                if(line.contains("确诊")){
                    suspectedToInfected(line);
                    continue;
                }
                if(line.contains("排除")) {
                    suspectedToHealthy(line);
                    continue;
                }
            }

            br.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("读取日志文件"+logFile.getName()+"时出现错误！");
            e.printStackTrace();
        }

        country.logData(dailyInfos);


    }

    public void addInfected(String line){
        String[] words=line.split(" ");
        String provinceName=words[0];
        String numberString=words[3];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(provinceName).changeInfected(number);
        country.getProvince(provinceName).hasOccured=true;
    }

    public void addSuspected(String line){
        String[] words=line.split(" ");
        String provinceName=words[0];
        String numberString=words[3];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(provinceName).changeSuspected(number);
        country.getProvince(provinceName).hasOccured=true;
    }

    public void flowInfected(String line){
        String[] words=line.split(" ");
        String province1Name=words[0];
        String province2Name=words[3];
        String numberString=words[4];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(province1Name).changeInfected(-number);
        dailyInfos.get(province2Name).changeInfected(number);
        country.getProvince(province1Name).hasOccured=true;
        country.getProvince(province2Name).hasOccured=true;
    }

    public void flowSuspected(String line){
        String[] words=line.split(" ");
        String province1Name=words[0];
        String province2Name=words[3];
        String numberString=words[4];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(province1Name).changeSuspected(-number);
        dailyInfos.get(province2Name).changeSuspected(number);
        country.getProvince(province1Name).hasOccured=true;
        country.getProvince(province2Name).hasOccured=true;
    }

    public void addDead(String line){
        String[] words=line.split(" ");
        String provinceName=words[0];
        String numberString=words[2];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(provinceName).changeDead(number);
        dailyInfos.get(provinceName).changeInfected(-number);
        country.getProvince(provinceName).hasOccured=true;
    }

    public void addCured(String line){
        String[] words=line.split(" ");
        String provinceName=words[0];
        String numberString=words[2];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(provinceName).changeInfected(-number);
        dailyInfos.get(provinceName).changeCured(number);
        country.getProvince(provinceName).hasOccured=true;
    }

    public void suspectedToInfected(String line){
        String[] words=line.split(" ");
        String provinceName=words[0];
        String numberString=words[3];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(provinceName).changeInfected(number);
        dailyInfos.get(provinceName).changeSuspected(-number);
        country.getProvince(provinceName).hasOccured=true;
    }

    public void suspectedToHealthy(String line){
        String[] words=line.split(" ");
        String provinceName=words[0];
        String numberString=words[3];
        int number=Integer.parseInt(numberString.substring(0,numberString.length()-1));

        dailyInfos.get(provinceName).changeSuspected(-number);
        country.getProvince(provinceName).hasOccured=true;
    }

}
