/**
 * InfectStatistic
 * TODO
 *
 * @author ��С��
 * @version 1.0
 * @since 2.13
 * @function ͳ����������
 */

import java.io.*;
import java.text.SimpleDateFormat;

import com.sun.org.apache.xpath.internal.operations.String;
import java.util.*;

class InfectStatistic {
	
	string date;
	
	//��ȡ��ǰ��ϵͳʱ�䲢��ʽ�����
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String currentDate = dateFormat.format(date);
	
	
    
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
					date=args[++commandOrder];
				}
				if(args[commandOrder].equals("-log")) {
					inputAddress=args[++commandOrder];
				}
				if(args[commandOrder].equals("-out")) {
					outputAdderss=args[++commandOrder];
				}
				
				if(args[commandOrder].equals("-type")) {
					type=args[++commandOrder];
					
					//�������ǲ���-��ͷ�ģ��򲻶���ӵ������б���
					while(!type.startsWith("-")&&commandOrder<args.length-1) {
						typeList.add(type);
						type=args[++commandOrder];
					}
				}
				if(args[commandOrder].equals("-province")) {
					province=args[commandOrder++];
					while(province.startwith("-")&&commandOrder<args.length-1) {
						provinceList.add(province);
						province=args[commandList++];
					}
				}
		}
		}
		}	
	}
	
	/*
	 *�������ܣ���ȡ�ļ�����
	 *���������-log·��
	 *����������ļ�����
	 **/
	public string getFileName(string inputAddress) {
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
	
	//��ǰʱ��
	Date commandDate = format.parse(date);
	
	//���ṩ�����ڴ��ڵ�ǰʱ�䣬�򱨴�
	if(commandDate.after(currentDate)) {
		System.out.println("���ڳ�����Χ")��
	}
	
	//��commandDate�������в����ڣ���������鲻�����仯
	//
	if()
	
	
	
	for(int i=0;i<tempList.length();i++) {
		fileName = tempList[i].getName();
		
		SimpleDateFormat format = new SimpleDataFormat("yyyy-MM-dd");
		Date commandDate = format.parse(date)
		Date fileDate = format.parse(fileName)
		Date latestDate = format.parse(currentDate);
		
		
		
		//����ṩ�����ڴ��ڵ�ǰ���ڣ��򱨴�
		if(commandDate.after(Date)) {
			System.out.println("���ڳ�����Χ");
		}
		
		//����ҵ������ж�Ӧ����־���򷵻���־��
		else if(commandDate.equals(fileDate)) {
			return commandDate;
		}
		else {
		
			
		}
		
	}
	
		
	}
	}
	
	
	/*
	 *�������ܣ���ȡ�ļ�����
	 *����������ļ�·��
	 *���������
	 **/
	public void readFile(string inputAddress) {
		string filePath=inputAddress+getFileName(inputAddress);
		
		
		
	}
	
	
	
	
	/*
	 *�������ܣ�ͳ��ʡ����������
	 *���������
	 *���������
	 **/
	public void countData() {
		
		
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
