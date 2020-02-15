import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import javafx.beans.binding.When;




/**
 * InfectStatistic
 * TODO
 *
 * @author xxx
 * @version xxx
 * @since xxx
 */
class InfectStatistic {
	//һ������������Ӧ��ʵ����
	//һ�������а���һ��������ڶ����
	static class CommandLine{
		public Command command;
		public Arguments arguments;
		
		//�����ã�֮��ǵ�ɾ��������
		public void test() {
			System.out.println("command:" + command.type );
			System.out.println("arguments.log:" + arguments.log + "," + arguments.log_value);	
			System.out.println("arguments.date:" + arguments.date + "," + arguments.date_value);	
			System.out.println("arguments.out:" + arguments.out + "," + arguments.out_value);
			System.out.println("arguments.type:" + arguments.type + "," + arguments.type_value);
			System.out.println("arguments.province:" + arguments.province + "," + arguments.province_value);
		}
		
		//һ����������Ӧ��ʵ���࣬������ҵֻ��һ������list
		static class Command{
			//��������ͣ�Ŀǰֻ��list
			String type;
			
			//�ж��Ƿ�Ϊlist����
			public boolean is_list() {
				if(this.type.equals("list")) {
					return true;
				}
				else {
					return false;
				}	
			}
		}
		
		//һ�������Ӧ��ʵ���࣬�������֣��ֱ�Ϊ��
		//-log��-date��-out��-type��-province
		static class Arguments{
			//���ֲ�����������������ĳ�������Ӧ������Ϊtrue��ʾ����
			public boolean log;
			public String log_value;
			public boolean out;
			public String out_value;
			public boolean date;
			public String date_value;
			public boolean type;
			public ArrayList<String> type_value;
			public boolean province;
			public ArrayList<String> province_value;
			
			//���ֺ��������ж����ֲ����Ƿ񼤻����ֵΪ��ӦBoolean�Ͳ���
			public boolean is_log() {
				return log;
			}
			
			public boolean is_out() {
				return out;
			}
			
			public boolean is_date() {
				return date;
			}
			
			public boolean is_type() {
				return type;
			}
			
			public boolean is_province() {
				return province;
			}
		}
	
	
	}
	
	//�����н����࣬����ʹ�ô�����������н����γ������ж��󣬼�һ��CommandLine����
	static class CommandLineAnalysis{
		//�����н����������������н���Ϊһ����Ӧ��CommandLine����
		public CommandLine analysis(ArrayList<String> commandline) {
			//��ʵ����һ��commandline����
			CommandLine command_line = new CommandLine();
			command_line.command = new CommandLine.Command();
			command_line.arguments = new CommandLine.Arguments();
			command_line.arguments.type_value = new ArrayList<String>();
			command_line.arguments.province_value = new ArrayList<String>();
			
			//���������н���
			for(int i = 0;i < commandline.size();i++) {
				String temp = commandline.get(i);
				
				switch (temp) {
				case "list":
					command_line.command.type = "list";					
					break;
				case "-log":
					command_line.arguments.log = true;
					command_line.arguments.log_value = commandline.get(i + 1);
					break;
				case "-date":
					command_line.arguments.date = true;
					command_line.arguments.date_value = commandline.get(i + 1);
					break;
				case "-out":
					command_line.arguments.out = true;
					command_line.arguments.out_value = commandline.get(i + 1);
					break;
				case "-type":
					//����type��province���п����ж�������������⴦��
					command_line.arguments.type = true;
					for(int j = i + 1;j < commandline.size();j++) {
						char temp_char = commandline.get(j).charAt(0);
						//�����ͷ����-��������ǲ���ֵ;����-����˵���Ѿ�������һ������
						if(temp_char != '-') {
							command_line.arguments.type_value.add(commandline.get(j));
						}else {
							//���ڴ�ʱcommandline.get(j)�Ѿ�����һ��������
							//���԰�i��Ϊj-1����ô��һ��switch��temp������һ������
							i = j - 1;
							break;
						}
					}
					break;
				case "-province":
					command_line.arguments.province = true;
					for(int j = i + 1;j < commandline.size();j++) {
						char temp_char = commandline.get(j).charAt(0);
						//�����ͷ����-��������ǲ���ֵ;����-����˵���Ѿ�������һ������
						if(temp_char != '-') {
							command_line.arguments.province_value.add(commandline.get(j));
						}else {
							//���ڴ�ʱcommandline.get(j)�Ѿ�����һ��������
							//���԰�i��Ϊj-1����ô��һ��switch��temp������һ������
							i = j - 1;
							break;
						}
					}
					break;
				default:
					break;
				}
			}
			
			//���Կ��������Ƿ�ɹ�
			command_line.test();
			return command_line;
		}
	}
	
	//������������,ʹ�ô�����������е�����,���Կ��Ǽ�һ��ʡ��ֱ���ʡ���Լ���������
	static class CommandLineRun{
		public CommandLine commandline;
		public File file_test;
		public int ip;
		public int sp;
		public int cure;
		public int dead;
		
		public CommandLineRun(CommandLine cmdline) throws IOException {
			commandline = new CommandLine();
			commandline = cmdline;
			//����ǵ�Ҫ�ĳɲ���ֵ��������������������
			file_test = new File("D:\\InfectStatistic-main\\221701430\\log\\2020-01-22.log.txt");
			get_data(file_test);
		}
		public void get_data(File f) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
			String temp;
			
			//����������ʽƥ��
			String type_1 = "(\\W+) (���� ��Ⱦ����) (\\d+)(��)";
	        String type_2 = "(\\W+) (���� ���ƻ���) (\\d+)(��)";
	        String type_3 = "(\\W+) (��Ⱦ���� ����) (\\W+) (\\d+)(��)";
	        String type_4 = "(\\W+) (���ƻ��� ����) (\\W+) (\\d+)(��)";
	        String type_5 = "(\\W+) (����) (\\d+)(��)";
	        String type_6 = "(\\W+) (����) (\\d+)(��)";
	        String type_7 = "(\\W+) (���ƻ��� ȷ���Ⱦ) (\\d+)(��)";
	        String type_8 = "(\\W+) (�ų� ���ƻ���) (\\d+)(��)";
	        
	        ArrayList<String> type_list = new ArrayList<String>();
	        type_list.add(type_1);
	        type_list.add(type_2);
	        type_list.add(type_3);
	        type_list.add(type_4);
	        type_list.add(type_5);
	        type_list.add(type_6);
	        type_list.add(type_7);
	        type_list.add(type_8);
	        
	        
	        while((temp = reader.readLine()) != null) {
	        	if(temp.charAt(0) == '/') {
	        		continue;
	        	}
	        	
	        	char flag = '0';
	        	Pattern pattern;
		        Matcher matcher;
	        	for(int i = 0;i < 8;i++) {
	        		pattern = Pattern.compile(type_list.get(i));
	        		matcher = pattern.matcher(temp);
	        		if(matcher.find()) {
	        			flag = (char) (48 + (i + 1));
	        			break;
	        		}else {
						continue;
					}
	        	}
	        	System.out.println(temp);
	        	System.out.println("this is p" + flag);
	        	
	        }
	        
	        /*
	        if (m1.find( )) {
	            System.out.println("Found value: " + m1.group(0) );
	            System.out.println("Found value: " + m1.group(1) );
	            System.out.println("Found value: " + m1.group(2) );
	            System.out.println("Found value: " + m1.group(3) ); 
	         } else {
	            System.out.println("NO MATCH");
	         }*/
		}
	}
	
	
	
	
	
	
	
	
	
    public static void main(String[] args) throws IOException {
    	String command = "";   	
    	//��ȡ������
        for(int i=3;i<args.length;i++){
            command += args[i] + " ";
        }
        //����Ҳ��û�ã���д��
        
        //������
        CommandLine commandline = new CommandLine();
         
        //������������һ��List����֮����������з�������������ʵ��
        ArrayList<String> cmd_line = new ArrayList<String>();
        for (String temp : args) {
        	cmd_line.add(temp);
        }
        //���ڲ����õ����������У��ǵ�ע�͵��ٰ�commandline_analysis.analysis(commandline_test);
        //�ĳ�commandline_analysis.analysis(cmdline);
        ArrayList<String> commandline_test = new ArrayList<String>();
        commandline_test.add("list");
        commandline_test.add("-log");
        commandline_test.add("123");
        commandline_test.add("-type");
        commandline_test.add("444");
        commandline_test.add("555");
        commandline_test.add("666");
        commandline_test.add("-province");
        commandline_test.add("7");
        commandline_test.add("8");
        
        CommandLineAnalysis commandline_analysis = new CommandLineAnalysis();
        commandline = commandline_analysis.analysis(commandline_test);
        
        //������
        CommandLineRun cmd_run = new CommandLineRun(commandline);
    }
}
