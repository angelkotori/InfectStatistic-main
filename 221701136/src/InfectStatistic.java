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
import java.util.regex.Pattern;


class InfectStatistic {
	
	String commandDate;
	
	//��ȡ��ǰ��ϵͳʱ�䲢��ʽ�����
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String currentDate = dateFormat.format(date);
	
	//����һ��˫��Ƕ�׵Ĺ�ϣ��
	HashMap<String,HashMap<String,Integer>> ProvinceToNumMap 
	                               = new HashMap<String,HashMap<String,Integer>>();
	HashMap<String,Integer> TypeToNumMap = new HashMap<String,Integer>();
	
	//��ʼ��TypeToNum��ϣ��
	TypeToNumMap.put("��Ⱦ����",0);
    TypeToNumMap.put("���ƻ���",0);
    TypeToNumMap.put("����",0);
    TypeToNumMap.put("����",0);
    
    String provinceList[] = {"ȫ��","����","����","����","����","����","�㶫","����","����","����","�ӱ�","����",
    		"������","����","����","����","����","����","����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
    		"�Ĵ�","���","����","�½�","����","�㽭"
}
    
    for(int i=0;i<provinceList.length;i++) {
    	ProvinceToNumMap.put(provinceList[i], TypeToNumMap);
    }
    
    
    String date,inputAddress,outputAddress;
    
	//type��province�����Ϳ��ܲ�ֹһ�֣��ʴ������ַ�������
	List<String> typeList=new List<String>();
			
	List<String> commandProvinceList=new List<String>();
	
    
	/*
	 *�������ܣ�����������
	 *����������������ַ���
	 *�����������
	 **/
	public static void analyseCommandLine(String args[]) {
		String province,type;
		
		if(!args[0].equals("list")) {
			System.out.println("�����еĸ�ʽ����");
		}
		
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
						commandProvinceList.add(province);
						province = args[commandList++];
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
	 *�������ܣ���ѯ·���ļ�
	 *���������-log·��
	 *����������ļ�����
	 **/
	public void searchFile(String inputAddress) {
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
		
		
		String lineTypeOne = "(\\s+) ���� ��Ⱦ���� (\\d+)��";
		String lineTypeTwo = "(\\s+) ���� ���ƻ��� (\\d+)��";
		String lineTypeThree = "(\\s+) ���� (\\d+)��";
		String lineTypeFour = "(\\s+) ���� (\\d+)��";
		String lineTypeFive = "(\\s+) ��Ⱦ���� ���� (\\s+) (\\d+)��";
		String lineTypeSix = "(\\s+) ���ƻ��� ���� (\\s+) (\\d+)��";
		String lineTypeSeven = "(\\s+) ���ƻ��� ȷ�ϸ�Ⱦ (\\d+)��";
		String lineTypeEight = "(\\s+) �ų� ���ƻ��� (\\d+)��";
		
		if(Pattern.matches(lineTypeOne, lineInformation)) {
			addInfectionPatients(linePart);
		}
		if(Pattern.matches(lineTypeTwo, lineInformation)) {
			addSuspectedPatients(linePart);
		}
		if(Pattern.matches(lineTypeThree, lineInformation)) {
			addCurePatients(linePart);
		}
		if(Pattern.matches(lineTypeFour, lineInformation)) {
			addDeadPatients(linePart);
		}
		if(Pattern.matches(lineTypeFive, lineInformation)) {
			infectionPatientsMove(linePart);
		}
		if(Pattern.matches(lineTypeSix, lineInformation)) {
			suspectedPatientsMove(linePart);
		}
		if(Pattern.matches(lineTypeSeven, lineInformation)) {
			suspectedToInfection(linePart);
		}
		if(Pattern.matches(lineTypeEight, lineInformation)) {
			suspectedToNormal(linePart);
		}
		
		
	}
		
	/*
	 *�������ܣ���ȡ��Ӧʡ�ݶ�Ӧ���͵Ļ���previousNum
	 *���������
	 *���������
	 **/
	public int searchProvinceToTypeNum(String procince,String type) {
		//��ȡʡ�ݶ�Ӧ�µĸ�Ⱦ��������
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String str:thisSet) {
					if(str.equals(province)) {
						HashMap<String,String> thisMap = ProvinceToNumMap.get(str);
						Set<String> typeKeys = thisMap.keySet();
						for(String strTwo:typeKeys) {
							if(strTwo.equals(type)) {
								previousNum = thisMap.get(strTwo);
							}
						}
					}
					}
				return previousNum;
	}
	
	
	
	/*
	 *�������ܣ����Ӹ�Ⱦ����
	 *���������
	 *���������
	 **/
	public void addInfectionPatients(String linePart[]) {
		
		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
		String[] linePart = lineInformation.split(" ");
		String province = linePart[0];
				
		//������Ⱦ���ߵ�����
		int num;
		
		//ȥ�����ֺ���ġ��ˡ���ȡ������������
		num = Integer.valueOf(linePart[3].replaceAll("��",""));
		
		int previousNum,countryPreviousNum,currentNum,countryCurrentNum;
		
		previousNum = searchProvinceToTypeNum(province,"��Ⱦ����");
		
		countryPreviousNum = searchProvinceToTypeNum("ȫ��","��Ⱦ����");
			
		currentNum = num + previousNum;
		countryCurrentNum = num+countryPreviousNum;
		
	    ProvinceToNumMap.get(province).replace(linePart[2],currentNum);
	    ProvinceToNumMap.get("ȫ��").replace(linePart[2],countryCurrentNum);
	}
	
	
	/*
	 *�������ܣ��������ƻ���
	 *���������
	 *���������
	 **/
     public void addSuspectedPatients(String linePart[]) {
 		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
 		String[] linePart = lineInformation.split(" ");
 		String province = linePart[0];
 				
 		//������Ⱦ���ߵ�����
 		int num;
 		
 		//ȥ�����ֺ���ġ��ˡ���ȡ������������
 		num = Integer.valueOf(linePart[3].replaceAll("��",""));
 		
 		int previousNum,countryPreviousNum,currentNum,countryCurrentNum;
 		
 		previousNum = searchProvinceToTypeNum(province,"���ƻ���");
 		
 		countryPreviousNum = searchProvinceToTypeNum("ȫ��","���ƻ���");
 			
 		currentNum = num + previousNum;
 		countryCurrentNum = num+countryPreviousNum;
 		
 	    ProvinceToNumMap.get(province).replace(linePart[2],currentNum);
 	    ProvinceToNumMap.get("ȫ��").replace(linePart[2],countryCurrentNum);
		}
		
		
		
	
     /*
 	 *�������ܣ���Ⱦ��������
 	 *���������
 	 *���������
 	 **/
      public void infectionPatientsMove(String linePart[]) {
 		
 		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
 		String[] linePart = lineInformation.split(" ");
 		String flowOutProvince = linePart[0];
 		String flowInProvince = linePart[3];
 		
 		
 		//������������
 		int flowNum;
 		
 		//ȥ�����ֺ���ġ��ˡ���ȡ������������
 		flowNum = Integer.valueOf(linePart[4].replaceAll("��",""));
 		
 		int flowOutPreviousNum,flowInPreviousNum,flowOutCurrentNum,flowInCurrentNum;
 		
 		flowOutPreviousNum = searchProvinceToTypeNum(flowOutProvince,"��Ⱦ����");
 		flowInPreviousNum = searchProvinceToTypeNum(flowInPreviousNum,"��Ⱦ����");
 		
 		flowOutCurrentNum = flowOutPreviousNum-flowNum;
 		flowInCurrentNum = flowInPreviousNum+flowNum;
 		
 	    ProvinceToNumMap.get(flowOutProvince).replace(linePart[1],flowOutCurrentNum);
 	   ProvinceToNumMap.get(flowInProvince).replace(linePart[1],flowInCurrentNum);
 	}
 	
      
      /*
   	 *�������ܣ����ƻ�������
   	 *���������
   	 *���������
   	 **/
     public void suspectedPatientsMove(String linePart[]) {
   		

  		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
  		String[] linePart = lineInformation.split(" ");
  		String flowOutProvince = linePart[0];
  		String flowInProvince = linePart[3];
  		
  		
  		//������������
  		int flowNum;
  		
  		//ȥ�����ֺ���ġ��ˡ���ȡ������������
  		flowNum = Integer.valueOf(linePart[4].replaceAll("��",""));
  		
  		int flowOutPreviousNum,flowInPreviousNum,flowOutCurrentNum,flowInCurrentNum;
  		
  		flowOutPreviousNum = searchProvinceToTypeNum(flowOutProvince,"���ƻ���");
  		flowInPreviousNum = searchProvinceToTypeNum(flowInPreviousNum,"���ƻ���");
  		
  		flowOutCurrentNum = flowOutPreviousNum-flowNum;
  		flowInCurrentNum = flowInPreviousNum+flowNum;
  		
  	    ProvinceToNumMap.get(flowOutProvince).replace(linePart[1],flowOutCurrentNum);
  	   ProvinceToNumMap.get(flowInProvince).replace(linePart[1],flowInCurrentNum);
   	}
   	

        /*
        *�������ܣ�ͳ����������
       	 *���������
       	 *���������
       	 **/
     public void addDeadPatients(String linePart[]) {
    	//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
  		String[] linePart = lineInformation.split(" ");
  		String province = linePart[0];
  				
  		//������Ⱦ���ߵ�����
  		int num;
  		
  		//ȥ�����ֺ���ġ��ˡ���ȡ������������
  		num = Integer.valueOf(linePart[2].replaceAll("��",""));
  		
  		int previousNum,countryPreviousNum,currentNum,countryCurrentNum;
  		
  		previousNum = searchProvinceToTypeNum(province,"��������");
  		
  		countryPreviousNum = searchProvinceToTypeNum("ȫ��","����");
  			
  		currentNum = num + previousNum;
  		countryCurrentNum = num+countryPreviousNum;
  		
  	    ProvinceToNumMap.get(province).replace(linePart[2],currentNum);
  	    ProvinceToNumMap.get("ȫ��").replace(linePart[2],countryCurrentNum);
       	}
       	
     /*�������ܣ�ͳ����������
          *���������
         *���������
          **/
     public void addCurePatients(String linePart[]) {
    	//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
   		String[] linePart = lineInformation.split(" ");
   		String province = linePart[0];
   				
   		//������Ⱦ���ߵ�����
   		int num;
   		
   		//ȥ�����ֺ���ġ��ˡ���ȡ������������
   		num = Integer.valueOf(linePart[2].replaceAll("��",""));
   		
   		int previousNum,countryPreviousNum,currentNum,countryCurrentNum;
   		
   		previousNum = searchProvinceToTypeNum(province,"����");
   		
   		countryPreviousNum = searchProvinceToTypeNum("ȫ��","����");
   			
   		currentNum = num + previousNum;
   		countryCurrentNum = num+countryPreviousNum;
   		
   	    ProvinceToNumMap.get(province).replace(linePart[2],currentNum);
   	    ProvinceToNumMap.get("ȫ��").replace(linePart[2],countryCurrentNum);
           	}
           	
                
     /*�������ܣ����ƻ���ȷ�ϸ�Ⱦ
               	 *���������
               	 *���������
               	 **/
      public void suspectedToInfection(String linePart[]) {
               		
               		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
               		String[] linePart = lineInformation.split(" ");
               		String province = linePart[0];
               		
               		
               		
               		//���ƻ���ȷ�ϸ�Ⱦ����
               		int num;
               		
               		//ȥ�����ֺ���ġ��ˡ���ȡ������������
               		num = Integer.valueOf(linePart[3].replaceAll("��",""));
               		
               		int previousSuspectedNum,previousInfectionNum;
               		int currentSuspectedNum,currentInfectionNum;
               		int countryPreviousSuspectedNum,countryPreviousInfectionNum;
               		int countryCurrentSuspectedNum,countryCurrentInfectionNum;
               		
               		previousSuspectedNum = searchProvinceToTypeNum(province,"���ƻ���");
               		previousInfectionNum = searchProvinceToTypeNum(province,"��Ⱦ����");
               		
               		countryPreviousSuspectedNum = searchProvinceToTypeNum("ȫ��","���ƻ���");
               		countryPreviousInfectionNum = searchProvinceToTypeNum("ȫ��","��Ⱦ����");		
               	
               		currentSuspectedNum = previousSuspectedNum-num;
               		currentInfectionNum = previousInfectionNum+num;
               		
               		countryCurrentSuspectedNum = countryPreviousSuspectedNum-num;
               		countryCurrentInfectionNum = countryPreviousInfectionNum+num;
               		
               		
               	    ProvinceToNumMap.get(province).replace("���ƻ���",currentSuspectedNum);
               	 ProvinceToNumMap.get(province).replace("��Ⱦ����",currentInfectionNum);
               	 ProvinceToNumMap.get("ȫ��").replace("��Ⱦ����",countryCurrentSuspectedNum);
               	 ProvinceToNumMap.get("ȫ��").replace("���ƻ���",countryCurrentInfectionNum);
               	}
                    
     
      /*�������ܣ��ų����Ƹ�Ⱦ����
    	 *���������
    	 *���������
    	 **/
         public void suspectedToNormal(String linePart[]) {

        		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
        		String[] linePart = lineInformation.split(" ");
        		String province = linePart[0];
        	
        		//���ƻ���ȷ�ϸ�Ⱦ����
        		int num;
        		
        		//ȥ�����ֺ���ġ��ˡ���ȡ������������
        		num = Integer.valueOf(linePart[3].replaceAll("��",""));
        		
        		int previousSuspectedNum,currentSuspectedNum;
        	
        		int countryPreviousSuspectedNum,countryCurrentSuspectedNum;
        		
        		previousSuspectedNum = searchProvinceToTypeNum(province,"���ƻ���");
        		countryPreviousSuspectedNum = searchProvinceToTypeNum("ȫ��","���ƻ���");
        		
        		currentSuspectedNum = previousSuspectedNum-num;
        		countryCurrentSuspectedNum = countryPreviousSuspectedNum-num;
        				
        	    ProvinceToNumMap.get(province).replace("���ƻ���",currentSuspectedNum);
        	 ProvinceToNumMap.get("ȫ��").replace("���ƻ���",countryCurrentInfectionNum);
    	}

                   		
                  	 
                        
                    
	/*
	 *�������ܣ����ͳ�ƽ�����ļ���
	 *���������
	 *���������
	 **/
	public void outputData(String path) {
		FileWriter fileWriter = null;
		File file = new File(path);
		try {
			if(!file.exists()) {
				file.createNewFile(path);
			}
			fileWriter = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fileWriter);
			
			//��-province����ȫ�����������в�����-province
			if(commandProvinceList.contains("ȫ��")||commandProvinceList.isEmpty()) {
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String strKey:thisSet) {		
					HashMap<String,Integer> TypeToNumValue = ProvinceToNumMap.get(strKey);
					Set<String> set = TypeToNumValue.keySet();
					for(String integerKey:set) {
						for(int j=0;j<typeList.size();i++) {						
							if(typeList.isEmpty()) {
								typeList.add("ip","sp","cure","dead");
							}
							switch(typeList[i]) {
							case "ip":Integer value = TypeToNumValue.get("��Ⱦ����");
							out.write(strKey+" "+"��Ⱦ����"+" "+value+"\n");
							case "sp":Integer value = TypeToNumValue.get("���ƻ���");
							out.write(strKey+" "+"���ƻ���"+" "+value+"\n");
							case "cure":Integer value = TypeToNumValue.get("����");
							out.write(strKey+" "+"����"+" "+value+"\n");
							case "dead":Integer value = TypeToNumValue.get("����");
							out.write(strKey+" "+"����"+" "+value+"\n");
							}
						}	
						}
					}
				}
			}
			else {
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String strKey:thisSet) {		
					for(int i=0;i<commandProvinceList.size();i++) {
						if(strKey.equals(commandProvinceList[i])) {
							HashMap<String,Integer> TypeToNumValue = ProvinceToNumMap.get(strKey);
						Set<String> set = TypeToNumValue.keySet();
						for(String integerKey:set) {
							for(int j=0;j<typeList.size();i++) {
							switch(typeList[i]) {
							if(typeList.isEmpty()) {
								typeList.add("ip","sp","cure","dead");
							}
							case "ip":Integer value = TypeToNumValue.get("��Ⱦ����");
							out.write(strKey+" "+"��Ⱦ����"+" "+value+"\n");
							case "sp":Integer value = TypeToNumValue.get("���ƻ���");
							out.write(strKey+" "+"���ƻ���"+" "+value+"\n");
							case "cure":Integer value = TypeToNumValue.get("����");
							out.write(strKey+" "+"����"+" "+value+"\n");
							case "dead":Integer value = TypeToNumValue.get("����");
							out.write(strKey+" "+"����"+" "+value+"\n");
							}
						}
							}
						}
					}
				}
			} 
			out.write("// ���ĵ�������ʵ���ݣ���������ʹ��");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
    public static void main(String[] args) {
       InfectStatistic infectStatistic = new InfectStatistic();
       infectStatistic.analyseCommandLine(args);
       infectStatistic.searchFile(inputAddress);
       infectStatistic.outputData(outPutAddesss);
    }
}
