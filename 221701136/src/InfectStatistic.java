/**
 * InfectStatistic
 * TODO
 *
 * @author ��С��
 * @version 1.1
 * @since 2.13
 * @function ͳ����������
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.lang.*;
import com.sun.org.apache.xpath.internal.operations.String;
import java.util.*;
import java.util.HashMap;;
import java.util.Map;

class InfectStatistic {
	
	string commandDate;
	
	//��ȡ��ǰ��ϵͳʱ�䲢��ʽ�����
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String currentDate = dateFormat.format(date);
	
    //������ϣ��
	HashMap<String,Integer> TypeToNumMap = new HashMap<String,Integer>();
	HashMap<String,TypeToNumMap> ProvinceToNumMap = new HashMap<String,TypeToNumMap>();
	
	//��ʼ��
	TypeToNumMap.put("��Ⱦ����",0);
    TypeTnNumMap.put("���ƻ���",0);
    TypeToNumMap.put("����",0);
    TypeToNumMap.put("����",0);
    
    ProvinceToNumMap("ȫ��",TypeToNumMap);
    
    
	/*
	 *�������ܣ�����������
	 *����������������ַ���
	 *���������-data��-type��-province��-log��-out
	 **/
	public void analyseCommandLine(String args[])throws IOException {
		//ʹ��BufferedReader�ӿ���̨��ȡ�ַ�
	/*	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		string str;
		str=br.readLine();
*/   
		String date,inputAddress,outputAddress,type,province;
		//type��province�����Ϳ��ܲ�ֹһ�֣��ʴ������ַ�������
		List<string> typeList=new List<string>();
		List<string> provinceList=new List<string>();
		
		
		if(!args[0].equals("list")) {
			System.out.println("�����еĸ�ʽ����");
		}
		else if{
		for(int commandOrder=1;commandOrder<args.length;commandOrder++) {
				if(args[commandOrder].equals("-data")) {
					currentDate = changeToValidDate(args[++commandOrder]);
				}
				if(args[commandOrder].equals("-log")) {
					inputAddress = args[++commandOrder];
					if(!isValidAddress(inputAddress)) {
						System.out.println("log·������");
						return;
					}
				}
				if(args[commandOrder].equals("-out")) {
					outputAdderss = args[++commandOrder];
					if(!isValidAddress(outputAddress)) {
						System.out.println("out·������");
						return;
					}
				}
				if(args[commandOrder].equals("-type")) {
					type = args[++commandOrder];
					
					//�������ǲ���-��ͷ�ģ��򲻶���ӵ������б���
					while(!type.startsWith("-")&&commandOrder<args.length-1) {
						typeList.add(type);
						type = args[++commandOrder];
					}
				}
				if(args[commandOrder].equals("-province")) {
					province = args[commandOrder++];
					while(!province.startwith("-")&&commandOrder<args.length-1) {
						provinceList.add(province);
						province = args[commandList++];
					}
				}
		}
		}
		}	
	}
	
/*
 *�������ܣ��ж������и�ʽ�Ƿ��д���
 *���������args[]
 *���������true,false
 **/
    public boolean isValidCommand(String args[]) {
	    if(!args[0].equals("list")) {
		    System.out.println("�����еĸ�ʽ����");
		    return false;
	    }
    }

    /*
     *�������ܣ���ȡ�Ϸ�����־����
     *���������args[]
     *���������Date
     **/
    public Date changeToValidDate(String date) {
    	SimpleDateFormat format = new SimpleDataFormat("yyyy-MM-dd");
    	Date validDate = format.parse(date);
    	return validDate;
    }
    
    /*
     *�������ܣ��ж��Ƿ��ǺϷ���·��
     *���������string
     *���������false,true
     **/
    public boolean isValidAddress(String address) {
    	//��������ʽ�ж������·���Ƿ���ȷ
    	if(address.matches("^[A-z]:\\\\(.+?\\\\)*$")) {
    	ruturn true;
    	}
    	else
    		return false;
    }
   
    
	/*
	 *�������ܣ���ȡ�ļ�
	 *���������-log·��
	 *����������ļ�����
	 **/
	public void getFile(string inputAddress) {
	File file = new File(inputAddress);
	string fileName;
	
	//��ȡinputAddress·���µ������ļ����ļ�Ŀ¼
	File[] tempList = file.listFiles();
	
	//��ȡ��ǰ��־�������������
	SimpleDateFormat format = new SimpleDataFormat("yyyy-MM-dd");
	
	//������־�������ʱ��ΪtempList[0]
	string latestFileName = tempList[0].getName();
	Date latestDate = format.parse(latetFileName);
	for(int i=1;i<tempList.length();i++) {
		fileName = tempList[i].getName();
		Date fileDate = format.parse(fileName);
		if(fileDate.after(latestDate)) {
			latestDate = fileDate;
		}
	}
	
	Date commandDate = format.parse(date);
	
	//���ṩ�����ڴ��ڵ�ǰʱ�䣬�򱨴�
	if(commandDate.after(currentDate)) {
		System.out.println("���ڳ�����Χ")��
	}
	
	//��ȡ����С��commandDate����־,����ȡ����
	for(int j=0;j<tempList.length;j++) {
		fileName = tempList[i].getName();
		Date fileDate = format.parse(fileName);
		if(fileDate.before(commandDate)) {
			readFile(inputAddress+fileName+".log.txt");
		}	
	}
	}
	
	
	/*
	 *�������ܣ���ȡ�ļ�����
	 *����������ļ�·��
	 *�����������
	 **/
	public void readFile(String address) throws IOException {
	FileInputStream fiStream = new FileInputStream(address);
	InputStreamReader isReader = new InputStreamReader(fiStream,"UTF-8");
	BufferedReader bufferedReader = new BufferedReader(isReader);
	String line = null;
	while((line=bufferedReader.readLine())! = null) {
		if(!line.startWith("//")) {
			handleInformation(line);
		}	
	}
	}
	
	
	
	
	/*
	 *�������ܣ�ͳ��ʡ����������
	 *���������
	 *���������
	 **/
	public void handleInformation(String lineInformation) {
		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
		String[] linePart = lineInformation.split(" ");
		String province = linePart[0];
		
		if(lineInformation.contains("���� ��Ⱦ����"))
	
		
		
			
	}
		
	
	
	
	
	/*
	 *�������ܣ����ͳ�ƽ�����ļ���
	 *���������
	 *���������
	 **/
	public void outputData() {
		
		
	}
	
	
    public static void main(String[] args) {
        System.out.println("helloworld");
    }
}
