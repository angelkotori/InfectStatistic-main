/*
 * InfectStatistic
 * TODO
 *
 * @author ��С��
 * @version 1.1
 * @since 2.13
 * @function ͳ����������
 */


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.util.*;
import java.util.HashMap;
import java.util.regex.Pattern;


class InfectStatistic {
	
	private static String commandDate;
	
	//��ȡ��ǰ��ϵͳʱ�䲢��ʽ�����
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static Date date1 = new Date(System.currentTimeMillis());
    static String currentDate = dateFormat.format(date1);
	
	//����һ��˫��Ƕ�׵Ĺ�ϣ��
	private HashMap<String,HashMap<String,Integer>> ProvinceToNumMap;
	                              
	
	
    public InfectStatistic() {
    	ProvinceToNumMap 
        = new HashMap<String,HashMap<String,Integer>>();
    	HashMap<String,Integer> TypeToNumMap = new HashMap<String,Integer>();
    	//��ʼ��TypeToNum��ϣ��
    	TypeToNumMap.put("��Ⱦ����",0);
        TypeToNumMap.put("���ƻ���",0);
        TypeToNumMap.put("����",0);
        TypeToNumMap.put("����",0);
         String provinceList[] = {"ȫ��","����","����","����","����","����","�㶫","����","����","����","�ӱ�","����",
    		"������","����","����","����","����","����","����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
    		"�Ĵ�","���","����","�½�","����","�㽭"};
          for(int i=0;i<provinceList.length;i++) {
    	ProvinceToNumMap.put(provinceList[i], TypeToNumMap);
    }
    }
	
	static String inputAddress;
	static String outputAddress;
    
	//type��province�����Ϳ��ܲ�ֹһ�֣��ʴ������ַ�������
	static List<String> typeList=new LinkedList<>();
			
	static List<String> commandProvinceList=new LinkedList<String>();
	
	/*
	 *�������ܣ�����������
	 *����������������ַ���
	 *�����������
	 **/
	public void analyseCommandLine(String args[]) {
		String province,type;
		
		if(!args[0].equals("list")) {
			System.out.println("�����еĸ�ʽ����");
		}
		
		for(int commandOrder=1;commandOrder<args.length;commandOrder++) {
				if(args[commandOrder].equals("-data")) {
				//commandDate = changeToValidDate(args[++commandOrder]);
					commandDate = args[++commandOrder];
				}
				if(args[commandOrder].equals("-log")) {
					inputAddress = args[++commandOrder];
					if(!isValidAddress(inputAddress)) {
						System.out.println("log·������");
						return;
					}
				}
				if(args[commandOrder].equals("-out")) {
					outputAddress = args[++commandOrder];
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
					while(!province.startsWith("-")&&commandOrder<args.length-1) {
						commandProvinceList.add(province);
						province = args[commandOrder++];
					}
				}
		}
	}
	
	
/*
 *�������ܣ��ж������и�ʽ�Ƿ��д���
 *���������args[]
 *���������true,false
 **/
  /*  public boolean isValidCommand(String args[]) {
	    if(!args[0].equals("list")) {
		    System.out.println("�����еĸ�ʽ����");
		    return false;
	    }
    }
    */

    /*
     *�������ܣ���ȡ�Ϸ�����־����
     *���������args[]
     *���������Date
     **/
   /* public String changeToValidDate(String date) {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date validDate = format.parse(date);
    	return validDate;
    }
    */
    /*
     *�������ܣ��ж��Ƿ��ǺϷ���·��
     *���������string
     *���������false,true
     **/
    public static boolean isValidAddress(String address) {
    	//��������ʽ�ж������·���Ƿ���ȷ
    	if (address.matches("^[A-z]:\\\\(.+?\\\\)*$")) {
    	return true;
    	}
    	else
    		return false;
    }
   
    
	/*
	 *�������ܣ���ѯ·���ļ�
	 *���������-log·��
	 *����������ļ�����
	 **/
	public void searchFile(String inputAddress) throws ParseException, IOException {
	File file = new File(inputAddress);
	String fileName;
	
	//��ȡinputAddress·���µ������ļ����ļ�Ŀ¼
	File[] tempList = file.listFiles();
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	Date commandDay = format.parse(commandDate);
	Date currentDay = format.parse(currentDate);
	
	//���ṩ�����ڴ��ڵ�ǰʱ�䣬�򱨴�
	if(commandDay.after(currentDay)) {
		System.out.println("���ڳ�����Χ");
	}
	
	//��ȡ����С��commandDate����־,����ȡ����
	for(int j=0;j<tempList.length;j++) {
		fileName = tempList[j].getName();
		Date fileDay = format.parse(fileName);
		if(fileDay.before(commandDay)) {
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
		try {
	        BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(address)),"UTF-8"));
	        String lineTxt = null;
	                
	        while ((lineTxt = bfr.readLine()) != null) { //���ж�ȡ�ı�����
	            if(!lineTxt.startsWith("//")) //������//������ȡ
	               handleInformation(lineTxt);
	        }
	        bfr.close();
	    } catch (Exception e) {
	        e.printStackTrace();
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
			addInfectionPatients(lineInformation);
		}
		if(Pattern.matches(lineTypeTwo, lineInformation)) {
			addSuspectedPatients(lineInformation);
		}
		if(Pattern.matches(lineTypeThree, lineInformation)) {
			addCurePatients(lineInformation);
		}
		if(Pattern.matches(lineTypeFour, lineInformation)) {
			addDeadPatients(lineInformation);
		}
		if(Pattern.matches(lineTypeFive, lineInformation)) {
			infectionPatientsMove(lineInformation);
		}
		if(Pattern.matches(lineTypeSix, lineInformation)) {
			suspectedPatientsMove(lineInformation);
		}
		if(Pattern.matches(lineTypeSeven, lineInformation)) {
			suspectedToInfection(lineInformation);
		}
		if(Pattern.matches(lineTypeEight, lineInformation)) {
			suspectedToNormal(lineInformation);
		}
		
		
	}
		
	/*
	 *�������ܣ���ȡ��Ӧʡ�ݶ�Ӧ���͵Ļ���previousNum
	 *���������
	 *���������
	 **/
	public int searchProvinceToTypeNum(String province,String type) {
		//��ȡʡ�ݶ�Ӧ�µĸ�Ⱦ��������
		int previousNum = 0;
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String str:thisSet) {
					if(str.equals(province)) {
						HashMap<String, Integer> thisMap = ProvinceToNumMap.get(str);
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
	public void addInfectionPatients(String lineInformation) {
		
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
     public void addSuspectedPatients(String lineInformation) {
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
      public void infectionPatientsMove(String lineInformation) {
 		
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
 		flowInPreviousNum = searchProvinceToTypeNum(flowInProvince,"��Ⱦ����");
 		
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
     public void suspectedPatientsMove(String lineInformation) {
   		
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
  		flowInPreviousNum = searchProvinceToTypeNum(flowInProvince,"���ƻ���");
  		
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
     public void addDeadPatients(String lineInformation) {
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
     public void addCurePatients(String lineInformation) {
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
      public void suspectedToInfection(String lineInformation) {
               		
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
         public void suspectedToNormal(String lineInformation) {

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
        	 ProvinceToNumMap.get("ȫ��").replace("���ƻ���",countryCurrentSuspectedNum);
    	}

                   		
                  	 
                        
                    
	/*
	 *�������ܣ����ͳ�ƽ�����ļ���
	 *���������
	 *���������
	 **/

	public void outputData(String path) {
		
		
		try {
			File file = new File(path);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream foStream = new FileOutputStream(file);
			OutputStreamWriter opStream = new OutputStreamWriter(foStream);
			BufferedWriter writer = new BufferedWriter(opStream);
			
			
			
			
			//��-province����ȫ�����������в�����-province
			if(commandProvinceList.contains("ȫ��")||commandProvinceList.isEmpty()) {
				if(typeList.isEmpty()) {
					typeList.add("ip");
					typeList.add("sp");
					typeList.add("cure");
					typeList.add("dead");
				}
				
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String strKey:thisSet) {	
					HashMap<String,Integer> TypeToNumValue = ProvinceToNumMap.get(strKey);
					Set<String> set = TypeToNumValue.keySet();
					for(String integerKey:set) {
						for(int j=0;j<typeList.size();j++) {	
							if(integerKey.equals(typeList.get(j))) {
							switch(typeList.get(j)) {
							case "ip":Integer value = TypeToNumValue.get(integerKey);
							writer.write(strKey+" "+"��Ⱦ����"+" "+value+"\n");
							case "sp":Integer value1 = TypeToNumValue.get(integerKey);
							writer.write(strKey+" "+"���ƻ���"+" "+value1+"\n");
							case "cure":Integer value2 = TypeToNumValue.get(integerKey);
							writer.write(strKey+" "+"����"+" "+value2+"\n");
							case "dead":Integer value3 = TypeToNumValue.get(integerKey);
							writer.write(strKey+" "+"����"+" "+value3+"\n");
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
						if(strKey.equals(commandProvinceList.get(i))) {
							HashMap<String,Integer> TypeToNumValue = ProvinceToNumMap.get(strKey);
						Set<String> set = TypeToNumValue.keySet();
						for(String integerKey:set) {
							for(int j=0;j<typeList.size();i++) {
								if(typeList.isEmpty()) {
									typeList.add("ip");
									typeList.add("sp");
									typeList.add("cure");
									typeList.add("dead");
								}
								if(integerKey.equals(typeList.get(j))) {
								switch(typeList.get(j)) {
								case "ip":Integer value = TypeToNumValue.get("��Ⱦ����");
								writer.write(strKey+" "+"��Ⱦ����"+" "+value+"\n");
								case "sp":Integer value1 = TypeToNumValue.get("���ƻ���");
								writer.write(strKey+" "+"���ƻ���"+" "+value1+"\n");
								case "cure":Integer value2 = TypeToNumValue.get("����");
								writer.write(strKey+" "+"����"+" "+value2+"\n");
								case "dead":Integer value3 = TypeToNumValue.get("����");
								writer.write(strKey+" "+"����"+" "+value3+"\n");
								}
						}}
							}
						}
					}
				}
			} 
			writer.write("// ���ĵ�������ʵ���ݣ���������ʹ��");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ParseException, IOException {
       InfectStatistic infectStatistic = new InfectStatistic();
       infectStatistic.analyseCommandLine(args);
       infectStatistic.searchFile(inputAddress);
       infectStatistic.outputData(outputAddress);
    }
   
    
}
 