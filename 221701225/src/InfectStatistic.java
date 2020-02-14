import java.time.LocalDate;
import java.util.ArrayList;

/**
 * InfectStatistic
 * TODO
 *
 * @author xxx
 * @version xxx
 * @since xxx
 */
class InfectStatistic {
    //从命令行读取到的参数
    private String inputPath;
    private String outputPath;
    private LocalDate date=null;
    private ArrayList<String> types=null;
    private ArrayList<String> provinces=null;

    //统计用的对象
    private LogList logList=new LogList();

    public static void main(String[] args) {
        InfectStatistic infectInfoOperator=new InfectStatistic();
        Country country=Country.getInstance();
        //从命令行读取参数到该类
        infectInfoOperator.readParameter(args);
        //
        infectInfoOperator.readLogs();
        country.getProvince("福建").printAllInfo();
    }

    /**
     * 读取命令行参数到类属性
     * @param args 命令行参数
     */
    public void readParameter(String[] args){
        int inputPosition=-1;
        int outputPosition=-1;
        int datePosition=-1;
        int typesPosition=-1;
        int provincePosition=-1;

        for(int i=0;i<args.length;i++){
            if(args[i].equals("-log"))
                inputPosition=i;
            if(args[i].equals("-out"))
                outputPosition=i;
            if(args[i].equals("-date"))
                datePosition=i;
            if(args[i].equals("-type"))
                typesPosition=i;
            if(args[i].equals("-province"))
                provincePosition=i;
        }

        //读取各个参数到类属性中
        if(inputPosition!=-1) {
            inputPath = args[inputPosition + 1];
        }
        if(outputPosition!=-1) {
            outputPath = args[outputPosition + 1];
        }
        if(datePosition!=-1){
            date=LocalDate.parse(args[datePosition+1]);
        }
        if(typesPosition!=-1){
            types=new ArrayList<String>();
            int pos=typesPosition+1;
            int length=args.length;

            //第一个条件放在前面防止下标越界
            while(pos<length && !args[pos].contains("-")){
                types.add(args[pos]);
                pos++;
            }
        }
        if(provincePosition!=-1){
            provinces=new ArrayList<>();
            int pos=provincePosition+1;
            int length=args.length;

            while(pos<length && !args[pos].contains("-")){
                provinces.add(args[pos]);
                pos++;
            }
        }

        System.out.println(this.toString());

    }

    /**
     * 从输入路径读取日志
     */
    public void readLogs(){
        logList.readLogsFromPath(inputPath);
    }

    /**
     * 根据获取到的参数的情况进行输出
     */
    public void output(){
        LocalDate beginDate;
        LocalDate endDate;

        beginDate=logList.getBeginDate();
        if(date==null)
            endDate=logList.getEndDate();
        else
            endDate=date;

        if(types==null){

        }
        else{

        }

    }
    @Override
    public String toString() {
        return "InfectStatistic{" +
                "inputPath='" + inputPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", date='" + date + '\'' +
                ", types=" + types +
                ", provinces=" + provinces +
                '}';
    }
}
