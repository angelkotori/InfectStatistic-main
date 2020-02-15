import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InfectStatistic
 * 利用CmdArgs类对命令行进行剖析
 * 利用ListCommand类和Command类的命令模式保证程序可拓展性
 * 使用Map对list命令的参数和值进行储存并处理
 *
 * @author Benjamin_Gnep
 * @version 1.0
 * @since 2020/2/11
 */
class InfectStatistic {
    public static void main(String[] args) {
    	String[] test = {"list","-date","2020-01-22","2020-02-14","-log","D:/log/","-out","D:/output.txt"};
    	CmdArgs cmdArgs = new CmdArgs(test,"benjamin");
    	Map<String,List<String>> map = new HashMap<String,List<String>>();
    	
    	/*
    	 * 这里可能省略一些命令的选择，可以添加list之外的命令进行筛选
    	 */

    	ListCommand listCommand = new ListCommand();//目前默认只有list命令
    	cmdArgs.setCommand(listCommand);
    	cmdArgs.fillMap(map);
    	cmdArgs.command.execute(map);
    	
    	System.out.println(map);
    	
    	TxtTool txtTool = new TxtTool();
    	
    	File file = new File("C:\\Users\\jhuy\\Documents\\GitHub\\InfectStatistic-main\\example\\log\\2020-01-22.log.txt");
    	String test1 = TxtTool.txt2String(file);
    	System.out.println(test1);
    	File file2 = new File("C:\\Users\\jhuy\\Documents\\GitHub\\InfectStatistic-main\\example\\log\\output.txt");
    	TxtTool.string2Txt(file2);
    }
    
}
