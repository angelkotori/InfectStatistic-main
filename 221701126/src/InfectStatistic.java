import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.print.attribute.standard.OutputDeviceAssigned;








/**
 * InfectStatistic
 * TODO 
 *
 * @author 221701126_�ƾ���
 * @version 1.0
 * @since 2020/2/8
 */ 
class InfectStatistic {
	public static String inputPath = "C:\\Users\\Peter\\Documents\\GitHub\\InfectStatistic-main\\221701126\\log\\";
	public static String outputPath = "C:\\Users\\Peter\\Documents\\GitHub\\InfectStatistic-main\\221701126\\result\\out.txt";
	public static String targetDate = "";
	public static ArrayList<String> provinceArray = new ArrayList<String>();
	public static Map<String, Province> map = new HashMap<String, Province>();
	public static Province country = new Province();/*����ͳ��ȫ������*/
	public static boolean ip = false;
	public static boolean sp = false;
	public static boolean cure = false; 
	public static boolean dead = false;
	public static ArrayList<String> typeItem = new ArrayList<String>();/*��¼type����������ѡ��*/
	public static ArrayList<String> provinceItem = new ArrayList<String>();/*��¼province����������ѡ��*/
	public static String[] province = {"ȫ��", "����", "����", "����", "����", "����", "�㶫", "����", "����", "����", "�ӱ�",
			"����", "������", "����", "����", "����", "����", "����", "����", "���ɹ�", "����", "�ຣ", "ɽ��", "ɽ��", "����",
			"�Ϻ�", "�Ĵ�", "���", "����", "�½�", "����", "�㽭"
	};
	
	/*
	 * ��ȡĿ¼�����������ļ���
	 */
	public static String getMaxDate() {
		String maxDate = "0";
		File file = new File(inputPath);
		if(file.exists()) {
			String[] fileNames = file.list(); // ���Ŀ¼�µ������ļ����ļ���
			for(String fileName : fileNames) {
				fileName = fileName.substring(0, fileName.indexOf('.'));//ȥ��׺
				
				//���� �Ƚ�
				if(fileName.compareTo(maxDate) > 0) {
					maxDate = fileName;
				}
			}
		}
		
		return maxDate;
	}
	
	/*
	 * �������в���ת������Ӧ�����洢����
	 */
	public static void solveArgs(String[] args) {
		int i = 0;
		int pos = 1;
		while(pos < args.length) {
			String arg = args[pos];
		//	System.out.println(pos + "-" + arg);
			if(arg.indexOf('-') == 0) {//��������
				
	    		if(arg.equals("-log")) {//��������·��
	    			inputPath = args[pos + 1] + "\\";
	    			pos+=2;
	    		}
	    		else if(arg.equals("-out")) {//�������·��
	    			outputPath = args[pos + 1] + "\\";
	    			pos+=2;
	    		}
	    		else if(arg.equals("-date")) {//��������
	    			targetDate = args[pos + 1];
	    			pos+=2;
	    		}
	    		else if(arg.equals("-type")) {
	    			for(i = pos + 1; i < args.length; i++) {
	    				String param = args[i];
	    				if(param.indexOf('-') != 0) {//���ǲ���
	    					typeItem.add(param);
		    			}
		    			else {
		    				pos = i;
		    				break;
		    			}
	    			}
	    		}
	    		else if(arg.equals("-province")) {//����province����
	    			for(i = pos + 1; i < args.length; i++) {
	    				String param = args[i];
	    				if(param.indexOf('-') != 0) {//���ǲ���
	    					provinceItem.add(param);
		    			}
		    			else {
		    				pos = i;
		    				break;
		    			}
	    			}
	    		}
	    		
//	    		for(i = pos + 1; i < args.length; i++) {
//	    			//System.out.println("I:" + i);
//	    			String newArg = args[i];
//	    			if(newArg.indexOf('-') != 0) {//���ǲ���
//	    				oneOrder.orderParams.add(newArg);
//	    			}
//	    			else {
//	    				pos = i;
//	    				break;
//	    			}
//	    		}
//	    		orders.add(oneOrder);
	    		if(i == args.length) {
	    			break;
	    		}
	    	}
//			
		}
		
	}
	
	
	/*
	 * ��ӡһ��ʡ����Ϣ
	 */
	public static void printTheProvince(String provinceName, OutputStreamWriter osw) {
		try {
			 
			osw.write(provinceName);
			System.out.print(provinceName);
			if(map.get(provinceName) != null) {
				Province province = map.get(provinceName);
				if(typeItem.size() != 0) {
					for(String item : typeItem) {
						switch (item) {
						case "ip":
							System.out.print(" ��Ⱦ����" + province.infect + "��");
							osw.write(" ��Ⱦ����" + province.infect + "��");
							break;
						case "sp":
							System.out.print(" ���ƻ���" + province.seeming + "��");
							osw.write(" ���ƻ���" + province.seeming + "��");
							break;
						case "cure":
							System.out.print(" ����" + province.cured + "��");
							osw.write(" ����" + province.cured + "��");
							break;
						case "dead":
							System.out.print(" ����" + province.dead + "��");
							osw.write(" ����" + province.dead + "��");
							break;
						default:
							break;
						}
					}
				}
				else {
					osw.write(" ��Ⱦ����" + province.infect + "�� ���ƻ���" + province.seeming + "�� ����" + province.cured + "�� ����" + province.dead + "��");
					System.out.print(" ��Ⱦ����" + province.infect + "�� ���ƻ���" + province.seeming + "�� ����" + province.cured + "�� ����" + province.dead + "��");
				}
			}
			else {
				if(typeItem.size() != 0) {
					for(String item : typeItem) {
						switch (item) {
						case "ip":
							System.out.print(" ��Ⱦ����0��");
							osw.write(" ��Ⱦ����0��");
							break;
						case "sp":
							System.out.print(" ���ƻ���0��");
							osw.write(" ���ƻ���0��");
							break;
						case "cure":
							System.out.print(" ����0��");
							osw.write(" ����0��");
							break;
						case "dead":
							System.out.print(" ����0��");
							osw.write(" ����0��");
							break;
						default:
							break;
						}
					}
				}
				else {
					System.out.print(" ��Ⱦ����0�� ���ƻ���0�� ����0�� ����0��");
					osw.write(" ��Ⱦ����0�� ���ƻ���0�� ����0�� ����0��");
				}
			}
			System.out.println();
			osw.write("\n");
//			fileWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ���Դ�ӡ���
	 */
	public static void printResult() {
		try {
			File output = new File(outputPath);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(output));
			if(provinceItem.size() == 0) {//û��-provinceѡ��
				for(String provinceName : province) {
					if(map.get(provinceName) != null) {
						printTheProvince(provinceName, osw);
					}
				}
			}
			else {
				for (String provinceName : province) {
					if(provinceItem.contains(provinceName)) {
						printTheProvince(provinceName, osw);
					}
				}
//				for(String provinceName : provinceItem) {
//					printTheProvince(provinceName, osw);
////					if(provinceItem.contains(provinceName)) {
////						printTheProvince(provinceName, osw);
////					}
//				}
			}
			
			osw.flush();
			osw.close();
		} catch (IOException e) {
		}
	}
	
	/*
	 * ��ȡ���е�����
	 */
	public static int getNumber(String[] information) {
		//��ȡ����
		String numString = information[information.length - 1];
		int index = numString.indexOf("��");
		numString = numString.substring(0, index);
		int number = Integer.parseInt(numString);
		return number;
	}
	
	/*
	 * ����������ļ���ÿһ���ļ�
	 */
	public static void solveEveryFile(Vector<String> toHandleDate) {
		StringBuffer sb = new StringBuffer();
		for(String dateFile : toHandleDate) {
			dateFile = inputPath + dateFile + ".log.txt";
			System.out.println(dateFile);
			try {
	    		File file = new File(dateFile);
	    		InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
	    		BufferedReader bf = new BufferedReader(inputReader);
	    		String str;
	    		
	    		while ((str = bf.readLine()) != null && str.indexOf("//") != 0) {
	    			//System.out.println(str);
	    			String[] information = str.split("\\s+");
	    			//System.out.println(information[0]);
	    			String province = information[0];//��ȡ��ʡ��
	    			int number = getNumber(information);//ȡ����������
	    			
	    			if(map.get(province) != null) {//ʡ���Ѿ����ֹ�
	    				Province p = map.get(province);
	    				switch (information[1]) {
						case "����":
							if(information[2].equals("��Ⱦ����")) {
								p.infect += number;
								country.infect += number;
								//System.out.println(num);
							}
							else {//���ƻ��ߵ����
								p.seeming += number;
								country.seeming += number;
							}
							break;
						case "��Ⱦ����":
							String p2 = information[3];//ȡ�������ʡ������
							if(map.get(p2) != null) {//����ʡ���Ѿ����ֹ�
								Province anotherProvince = map.get(p2);
								anotherProvince.infect += number;
								p.infect -= number;
							}
							else {
								Province province2 = new Province();
								province2.name = p2;
								province2.infect += number;
								p.infect -= number;
								map.put(p2, province2);
							}
							break;
						case "���ƻ���":
							//�ж������뻹��ȷ��
							if(information[2].equals("����")) {
								String p3 = information[3];//ȡ�������ʡ������
								if(map.get(p3) != null) {//����ʡ���Ѿ����ֹ�
									Province anotherProvince = map.get(p3);
									anotherProvince.seeming += number;
									p.seeming -= number;
								}
								else {
									Province province3 = new Province();
									province3.name = p3;
									province3.infect += number;
									p.infect -= number;
									map.put(p3, province3);
								}
							}
							else {//ȷ��
								p.infect += number;
								p.seeming -= number;
								country.infect += number;
								country.seeming -= number;
							}
							break;
						case "����":
							p.infect -= number;
							p.dead += number;
							country.infect -= number;
							country.dead += number;
							break;
						case "����":
							p.infect -= number;
							p.cured += number;
							country.infect -= number;
							country.cured += number;
							break;
						case "�ų�":
							p.seeming -= number;
							country.seeming -= number;
							break;
						default:
							break;
						}
	    			}
	    			else {//ʡ�ݻ�δ���ֹ�
	    				Province p = new Province();
	    				p.name = province;
	    				switch (information[1]) {
						case "����":
							if(information[2].equals("��Ⱦ����")) {
								p.infect += number;
								country.infect += number;
								//System.out.println(num);
							}
							else {//���ƻ��ߵ����
								p.seeming += number;
								country.seeming += number;
							}
							break;
						case "��Ⱦ����":
							String p2 = information[3];//ȡ�������ʡ������
							if(map.get(p2) != null) {//����ʡ���Ѿ����ֹ�
								Province anotherProvince = map.get(p2);
								anotherProvince.infect += number;
								p.infect -= number;
							}
							else {
								Province province2 = new Province();
								province2.name = p2;
								province2.infect += number;
								p.infect -= number;
								map.put(p2, province2);
							}
							break;
						case "���ƻ���":
							//�ж������뻹��ȷ��
							if(information[2].equals("����")) {
								String p3 = information[3];//ȡ�������ʡ������
								if(map.get(p3) != null) {//����ʡ���Ѿ����ֹ�
									Province anotherProvince = map.get(p3);
									anotherProvince.seeming += number;
									p.seeming -= number;
								}
								else {
									Province province3 = new Province();
									province3.name = p3;
									province3.infect += number;
									p.infect -= number;
									map.put(p3, province3);
								}
							}
							else {//ȷ��
								p.infect += number;
								p.seeming -= number;
								country.infect += number;
								country.seeming -= number;
							}
							break;
						case "����":
							p.infect -= number;
							p.dead += number;
							country.infect -= number;
							country.dead += number;
							break;
						case "����":
							p.infect -= number;
							p.cured += number;
							country.infect -= number;
							country.cured += number;
							break;
						case "�ų�":
							p.seeming -= number;
							country.seeming -= number;
							break;
						default:
							break;
						}
	    				map.put(province, p);
	    			}
	    		}			
	    		bf.close();		
	    	//	bw.close();
	    		inputReader.close();
	    	
			} 
	    	catch (IOException  e) {
	    		e.printStackTrace();
			}
		}
		
	}
	
	//����date����
	public static void solveDateOrder(String targetDate) {
		String maxDate = getMaxDate();
		if(targetDate.compareTo(maxDate) > 0) {
			System.out.println("���ڳ�����Χ");
			return;
		}
		//��ȡ����·���µ������ļ�
		File file = new File(inputPath);
		if(file.isDirectory()) {
			Vector<String> toHandleDate = new Vector<String>();//��ȡ����Ҫ�������������ļ�
			String[] fileNames = file.list(); // ���Ŀ¼�µ������ļ����ļ���
			for(String fileName : fileNames) {
				fileName = fileName.substring(0, fileName.indexOf('.'));//�ضϺ�׺��
				//���ڱȽ�
				if(fileName.compareTo(targetDate) <= 0) {
					toHandleDate.add(fileName);
					System.out.println(fileName);
				}
				else {
					break;
				}
				//System.out.println(fileName);
			}

			if(toHandleDate.size() > 0) {
				solveEveryFile(toHandleDate);
			}
			map.put("ȫ��", country);
		}
		
	}
	
	public static void main(String[] args) {
	
    	country.name = "ȫ��";
  
    	solveArgs(args);
    	if(targetDate.equals("")) {
        	targetDate = getMaxDate();
    	}

    	System.out.println("�������Ϊ" + targetDate);
    	System.out.println(inputPath);
    	System.out.println(outputPath);
    	solveDateOrder(targetDate);
    	printResult();

    }
}

/*��¼һ��ʡ�ĸ�������*/
class Province{
	String name; /*ʡ��*/
	int infect; /*��Ⱦ����*/
	int seeming; /*��������*/
	int dead; /*��������*/
	int cured; /*��������*/
	
	public Province() {
		infect = seeming = dead = cured = 0;
	}
}
