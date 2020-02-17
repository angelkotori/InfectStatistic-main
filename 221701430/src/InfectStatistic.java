import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





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
		//����Ϊ��Ÿ�ʡ���ݵ��б�
		public ArrayList<Province> province_list;
		//����Ϊȫ������
		public int ip;
		public int sp;
		public int cure;
		public int dead;
		//����Ϊ��Ҫ������ļ����б�
		public ArrayList<String> filename_list;
		
		public CommandLineRun(CommandLine cmdline) throws IOException {
			commandline = new CommandLine();
			commandline = cmdline;
			//��ʼ��ȫ�������ݺ͸�ʡ������
			ip = 0;
			sp = 0;
			cure = 0;
			dead = 0;
			creat_provinces_list();
			creat_filename_list("D:\\InfectStatistic-main\\221701430\\log\\", "2020-01-25");
			//����ǵ�Ҫ�ĳɲ���ֵ��������������������
			for(int i = 0;i < filename_list.size();i++) {
				file_test = new File("D:\\InfectStatistic-main\\221701430\\log\\" + filename_list.get(i));
				if(file_test != null) {
					process_data(file_test);
				}
			}
			country_total();
			//���������������������������������������������
	        for(int i = 0;i<province_list.size();i++) {
	        	System.out.println("ʡ����" + province_list.get(i).name + 
	        			" ip:" + province_list.get(i).ip + 
	        			" sp:" + province_list.get(i).sp + 
	        			" cure:" + province_list.get(i).cure + 
	        			" dead:" + province_list.get(i).dead);
	        }
		}
		
		//���ڴ������ļ���
		public void process_data(File f) throws IOException {
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
	        
	        //�������ݶ�ȡ��ƥ�䣬������ͳ��
	        while((temp = reader.readLine()) != null) {
	        	if(temp.charAt(0) == '/') {
	        		continue;
	        	}
	        	//���ڼ�¼�ļ�ĳ��ƥ�������һģʽ
	        	char flag = '0';
	        	Pattern pattern;
		        Matcher matcher;
		        
		        //����ƥ��
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
	        	//������++++++++++++++++++++++++++++++++++++++++++++++++++
	        	System.out.println(temp);
	        	
	        	//���ݴ���
	        	pattern = Pattern.compile(type_list.get((int)(flag-48)-1));
        		matcher = pattern.matcher(temp);
        		Province p,p1,p2;
        		if(matcher.find()) {
        			switch (flag) {
        			//(\\W+) (���� ��Ⱦ����) (\\d+)(��)
    				case '1':
    					p = get_province(matcher.group(1));
    					p.ip += Integer.parseInt(matcher.group(3));
    					break;
    				//(\\W+) (���� ���ƻ���) (\\d+)(��)	
    				case '2':
    					p = get_province(matcher.group(1));
    					p.sp += Integer.parseInt(matcher.group(3));
    					break;
    				//(\\W+) (��Ⱦ���� ����) (\\W+) (\\d+)(��)
    				case '3':
    					p1 = get_province(matcher.group(1));
    					p2 = get_province(matcher.group(3));
    					p1.ip -= Integer.parseInt(matcher.group(4));
    					p2.ip += Integer.parseInt(matcher.group(4));
    					break;
    				//(\\W+) (���ƻ��� ����) (\\W+) (\\d+)(��)
    				case '4':
    					p1 = get_province(matcher.group(1));
    					p2 = get_province(matcher.group(3));
    					p1.sp -= Integer.parseInt(matcher.group(4));
    					p2.sp += Integer.parseInt(matcher.group(4));
    					break;
    				//(\\W+) (����) (\\d+)(��)
    				case '5':
    					p = get_province(matcher.group(1));
    					p.dead += Integer.parseInt(matcher.group(3));
    					p.ip -= Integer.parseInt(matcher.group(3));
    					break;
    				//(\\W+) (����) (\\d+)(��)
    				case '6':
    					p = get_province(matcher.group(1));
    					p.cure += Integer.parseInt(matcher.group(3));
    					p.ip -= Integer.parseInt(matcher.group(3));
    					break;
    				//(\\W+) (���ƻ��� ȷ���Ⱦ) (\\d+)(��)
    				case '7':
    					p = get_province(matcher.group(1));
    					p.ip += Integer.parseInt(matcher.group(3));
    					p.sp -= Integer.parseInt(matcher.group(3));
    					break;
    				//(\\W+) (�ų� ���ƻ���) (\\d+)(��)
    				case '8':
    					p = get_province(matcher.group(1));
    					p.sp -= Integer.parseInt(matcher.group(3));
    					break;
    				default:
    					break;
    				}
        		}else {
        			System.out.println("NO MATCH");
				}
	        	
	        }
	        
		}
		
		//��������õ�ʡ���б�
		public void creat_provinces_list(){
			province_list = new ArrayList<InfectStatistic.CommandLineRun.Province>();
			String[] provinces = {"ȫ��","����","����","����","����","����","����","�㶫","����",
		            "����", "����","�ӱ�","����","������","����","����","����","����",
		            "����", "����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
		            "�Ĵ�", "̨��","���","����","���","�½�","����","�㽭"};
			for(int i = 0;i < provinces.length;i++) {
				Province province = new Province();
				province.name = provinces[i];
				province.ip = 0;
				province.sp = 0;
				province.cure = 0;
				province.dead = 0;
				province_list.add(province);
			}
		}
		
		//ʹ��ʡ����ʡ�б��л�ȡʡ����
		public Province get_province(String pname) {
			for(int i = 0;i<province_list.size();i++) {
	        	if(pname.equals(province_list.get(i).name)) {
	        		return province_list.get(i);
	        	}
	        }
			System.out.println("��ʡ������");
			return null;
		}
		
		//ȫ��������ͳ��
		public void country_total() {
			//�ӵ�һ��ʡ��ʼ�ۼ�
			for(int i = 1;i<province_list.size();i++) {
	        	province_list.get(0).ip += province_list.get(i).ip;
	        	province_list.get(0).sp += province_list.get(i).sp;
	        	province_list.get(0).cure += province_list.get(i).cure;
	        	province_list.get(0).dead += province_list.get(i).dead;
	        }
		}
		
		//ʡ��
		static class Province{
			public String name;
			public int ip;
			public int sp;
			public int cure;
			public int dead;
		}
		
		//������Ҫ��ȡ���ļ����б�
		public void creat_filename_list(String path,String date) {
			List<String> temp_list = new ArrayList<String>();
			filename_list = new ArrayList<String>();
			String []temp;
			int index = -1;
			//path ��Ϊlog�Ĳ���ֵ
			File name = new File(path);
			temp = name.list();
			//��date��Ҫ����Ϊlog�ļ����������ļ�
			if(date == null) {
				if(name.isDirectory()) {		
					for(int i = 0;i < temp.length;i++) {
						filename_list.add(temp[i]);
					}
				}
			}else {//��date��Ҫ����Ϊdateǰ�������ļ�
				if(name.isDirectory()) {
					//����list()�ķ���ֵ���ź����
					for(int i = 0;i < temp.length;i++) {
						//����б���Ϊ22 24 26 ��dateΪ25�����
						if((temp[i].compareTo((date + ".log.txt")) > 0)) {
							index = i - 1;
							break;
						}
						//����б���Ϊ22 24 26 ��dateΪ26�����
						else if((temp[i].compareTo((date + ".log.txt")) == 0)){
							index = i;
							break;
						}
					}
					if(index == -1) {
						System.err.println("���ڳ�����Χ");
						System.exit(0);
					}
					for(int i = 0;i <= index;i++) {
						filename_list.add(temp[i]);
					}
				}
			}
			Collections.sort(filename_list);
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
        commandline_test.add("D:\\InfectStatistic-main\\221701430\\log\\");
        commandline_test.add("-out");
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
