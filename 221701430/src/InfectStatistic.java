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
	class CommandLine{
		public Command command;
		public Arguments arguments;
		
		//һ����������Ӧ��ʵ���࣬������ҵֻ��һ������list
		class Command{
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
		class Arguments{
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
	class CommandLineAnalysis{
		//�����н����������������н���Ϊһ����Ӧ��CommandLine����
		public CommandLine analysis() {
			CommandLine command_line = null;
			return command_line;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
    public static void main(String[] args) {
    	String commandline = "";
    	
    	//��ȡ������
        for(int i=3;i<args.length;i++){
            commandline += args[i] + " ";
        }

        //������������һ��List����֮����������з�������������ʵ��
        List<String> line = new ArrayList<String>();

        for (String temp : args) {
            line.add(temp);
        }
        
        System.out.println(line.get(0));
    }
}
