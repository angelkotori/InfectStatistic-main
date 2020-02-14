import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

class Provin{
	String name;//省名
	int ip;//确诊人数
	int sp;//疑似人数
	int cure;//治愈人数
	int death;//死亡人数
	public Provin(String name) {
		this.name=name;
		ip=0;
		sp=0;
		cure=0;
		death=0;
	}
}
class Dates{
	int month;
	int day;
	public Dates(int mon,int da) {
		month=mon;
		day=da;
	}
    
}
public class InfectStatistic {
	public static void arraySort(String[] input){               
		for (int i=0;i<input.length-1;i++){            
			for (int j=0;j<input.length-i-1;j++) {            	
				if(input[j].compareTo(input[j+1])<0){                    
					String temp=input[j];                    
					input[j]=input[j+1];                    
					input[j+1]=temp;                
					}            
				}        
			
			}        
		}
	public static void main(String[] args) throws IOException {
		// System.out.println(args[0]);
		String path = null, date = null, output = null;
		String request = args[0];
		String[] provins=new String[] {"北京","上海","天津","重庆","黑龙江","辽宁","吉林","河北","河南","湖北","湖南",
				"山东","山西","陕西","安徽","浙江","江苏","福建","广东","海南","四川","云南","贵州","青海","甘肃","江西","台湾",
				"内蒙古","宁夏","新疆","西藏","广西","香港","澳门"};
		Provin[] provinces=new Provin[34];
		for(int i=0;i<34;i++) {
			provinces[i]=new Provin(provins[i]);
		}
		int num = 0;
		if (request.equals("list")) {
			// System.out.println(args[0]);
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-date"))
					date = args[i + 1];
				else if (args[i].equals("-log"))
					path = args[i + 1];
				else if (args[i].equals("-out"))
					output = args[i + 1];

			}
		} 
		else {
			System.out.println("input the ritght request");
			System.exit(1);
		}
		if (output == null || path == null) {
			System.out.println("input the ritght request");
			System.exit(1);
		}
		File f = new File(path);
		// File[] files=f.listFiles();
		String[] ss = f.list();
		arraySort(ss);
		Dates[] Dates= new Dates[ss.length];
		for(int j=0;j<ss.length;j++) {
			String s=ss[j].substring(5,7);
			String s2=ss[j].substring(8,10);
			int j1 = Integer.parseInt(s);
			int j2 = Integer.parseInt(s2);
			Dates[j]=new Dates(j1,j2);
		}
		if (date != null) {
			FileInputStream f1 = new FileInputStream(path + date + ".log.txt");
			InputStreamReader reader = new InputStreamReader(f1, "UTF-8");
			BufferedReader bf = new BufferedReader(reader);
			BufferedReader bf2 = new BufferedReader(reader);
			String str = null;
			String[] line = new String[8];
			num = 0;
			while ((str = bf.readLine()) != null) {
				line[num] = str;
				num++;
			}
			for (int k = 0; k < num; k++)
				System.out.println(line[k]);
			System.out.println();
		}
		else {
			num=0;
			String[] line = new String[2000];
			Dates begindate = Dates[ss.length - 1];
			for (int i = 0; i <ss.length; i++) {
				FileInputStream f1 = new FileInputStream(path + ss[ss.length-1-i]);
				InputStreamReader reader = new InputStreamReader(f1, "UTF-8");
				BufferedReader bf = new BufferedReader(reader);
				BufferedReader bf2 = new BufferedReader(reader);
				String str = null;
				//String[] line = new String[8];
				//num = 0;
				while ((str = bf.readLine()) != null) {
					line[num] = str;
					num++;
				}
			}
			for (int k = 0; k < num; k++)
			{
				String[] as = line[k].split(" ");
				//String mes=as[0];
				if(as[1].equals("新增")) {
					String mes=as[0];
					if(as[2].equals("感染患者")) {
						int ki=0;
						String coun=as[3].substring(0,as[3].length()-1);
						int count = Integer.parseInt(coun);
						for(ki=0;ki<34;ki++) {
							if(provinces[ki].name.equals(mes)){
								provinces[ki].ip +=count;
								break;
							}
						}
					}
					else if(as[2].equals("疑似患者")) {
						int ki=0;
						String coun=as[3].substring(0,as[3].length()-1);
						int count = Integer.parseInt(coun);
						for(ki=0;ki<34;ki++) {
							if(provinces[ki].name.equals(mes)){
								provinces[ki].sp +=count;
								break;
							}
						}
					}
				}
				else if(as[1].equals("死亡")) {
					String mes=as[0];
					int ki=0;
					String coun=as[2].substring(0,as[2].length()-1);
					int count = Integer.parseInt(coun);
					for(ki=0;ki<34;ki++) {
						if(provinces[ki].name.equals(mes)){
							provinces[ki].death +=count;
							break;
						}
					}
				}
				else if(as[1].equals("治愈")) {
					String mes=as[0];
					int ki=0;
					String coun=as[2].substring(0,as[2].length()-1);
					int count = Integer.parseInt(coun);
					for(ki=0;ki<34;ki++) {
						if(provinces[ki].name.equals(mes)){
							provinces[ki].cure +=count;
							break;
						}
					}
				}
				else if(as[1].equals("排除")) {
					String mes=as[0];
					int ki=0;
					String coun=as[3].substring(0,as[3].length()-1);
					int count = Integer.parseInt(coun);
					for(ki=0;ki<34;ki++) {
						if(provinces[ki].name.equals(mes)){
							provinces[ki].ip -=count;
							break;
						}
					}
				}
				else if(as[1].equals("感染患者")) {
					String mes=as[0];
					String mes1=as[3];
					int ki=0,num1=0;
					String coun=as[4].substring(0,as[4].length()-1);
					int count = Integer.parseInt(coun);
					for(ki=0;ki<34;ki++) {
						if(provinces[ki].name.equals(mes)){
							provinces[ki].ip -=count;
							num1++;
						}
						if(provinces[ki].name.equals(mes1)){
							provinces[ki].ip +=count;
							num1++;
						}
						if(num1==2) break;
					}
				}
				else if(as[1].equals("疑似患者")) {
					if(as[2].equals("流入")) {
					    String mes=as[0];
					    String mes1=as[3];
					    int ki=0,num1=0;
					    String coun=as[4].substring(0,as[4].length()-1);
					    int count = Integer.parseInt(coun);
					    for(ki=0;ki<34;ki++) {
							if(provinces[ki].name.equals(mes)){
								provinces[ki].sp -=count;
								num1++;
							}
							if(provinces[ki].name.equals(mes1)){
								provinces[ki].sp +=count;
								num1++;
							}
							if(num1==2) break;
						}
					}
					else if (as[2].equals("确诊感染")) {
						String mes = as[0];
						int ki = 0, num1 = 0;
						String coun = as[3].substring(0, as[3].length() - 1);
						int count = Integer.parseInt(coun);
						for (ki = 0; ki < 34; ki++) {
							if (provinces[ki].name.equals(mes)) {
								provinces[ki].ip += count;
								provinces[ki].sp -= count;
								break;
							}
						}
					}
				}
				System.out.println(line[k]);
			}
			for(int j=0;j<34;j++)
			    System.out.println(provinces[j].name+" 感染患者"+provinces[j].ip+"人 "
			    		+ " 疑似患者"+provinces[j].sp+"人 "+ "治愈"+provinces[j].cure+"人 "+ "死亡"+provinces[j].death+"人");
		}
	}
}