import java.util.ArrayList;
import java.util.List;

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
	
	
	
	
	
	
	
	
	
	
	
    public static void main(String[] args) {
    	String command = "";   	
    	//��ȡ������
        for(int i=3;i<args.length;i++){
            command += args[i] + " ";
        }
        //����Ҳ��û�ã���д��

        //������������һ��List����֮����������з�������������ʵ��
        ArrayList<String> commandline = new ArrayList<String>();
        for (String temp : args) {
            commandline.add(temp);
        }
        /*���ڲ����õ�����������
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
        */
        CommandLineAnalysis commandline_analysis = new CommandLineAnalysis();
        commandline_analysis.analysis(commandline);
    }
}
