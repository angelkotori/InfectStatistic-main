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
import javax.annotation.Resource;

class InfectStatistic {
	
	
	private static final HashMap<String, Integer> TypeToNumMap = null;
	private static String commandDate;
	//��ȡ��ǰ��ϵͳʱ�䲢��ʽ�����
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static Date date1 = new Date(System.currentTimeMillis());
    static String currentDate = dateFormat.format(date1);
	 	//����һ��˫��Ƕ�׵Ĺ�ϣ��
	static HashMap <String,HashMap<String,Integer>> ProvinceToNumMap;

	static boolean isEmptyCommandProvince = true;
	 static String provinceList[] = {"ȫ��","����","����","����","����","����","�㶫","����","����","����","�ӱ�","����",
	    		"������","����","����","����","����","����","����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
	    		"�Ĵ�","���","����","�½�","����","�㽭"};              
  public InfectStatistic() {
	  
    	ProvinceToNumMap
        = new HashMap<String,HashMap<String,Integer>>();
    	
      /*  String provinceList[] = {"ȫ��","����","����","����","����","����","�㶫","����","����","����","�ӱ�","����",
    		"������","����","����","����","����","����","����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
    		"�Ĵ�","���","����","�½�","����","�㽭"};
	 	 */
          for(int i=0;i<provinceList.length;i++) {
        	  HashMap<String,Integer> TypeToNumMap = new HashMap<String,Integer>();
    	//��ʼ��TypeToNum��ϣ��
    	TypeToNumMap.put("��Ⱦ����",0);
        TypeToNumMap.put("���ƻ���",0);
        TypeToNumMap.put("����",0);
        TypeToNumMap.put("����",0);
        
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
		int commandOrder=0;
		if(!args[0].equals("list")) {
			System.out.println("�����еĸ�ʽ����");
		}
		else {
		while(commandOrder<args.length) {
				if(args[commandOrder].equals("-date")) {
				//commandDate = changeToValidDate(args[++commandOrder]);
					commandDate = args[++commandOrder];
				}
				else if(args[commandOrder].equals("-log")) {
					inputAddress = args[++commandOrder];
					if(!isValidInputAddress(inputAddress)) {
						System.out.println("log·������");
						//return;
					}
				}
				else if(args[commandOrder].equals("-out")) {
					outputAddress = args[++commandOrder];
					if(!isValidOutputAddress(outputAddress)) {
						System.out.println("out·������");
						//return;
					}
				}
				else if(args[commandOrder].equals("-type")) {
					
					type = args[++commandOrder];
					
					//�������ǲ���-��ͷ�ģ��򲻶���ӵ������б���
					while(!type.startsWith("-")) {
						switch(type) {
						case "ip":typeList.add("��Ⱦ����");
						break;
						case "sp":typeList.add("���ƻ���");
						break;
						case "cure":typeList.add("����");
						break;
						case "dead":typeList.add("����");
						break;
						}
						
						if(commandOrder==args.length-1) 
							break;
						type = args[++commandOrder];
					}
				}
				else if(args[commandOrder].equals("-province")) {
					isEmptyCommandProvince = false;
					province = args[++commandOrder];
					while(!province.startsWith("-")) {
						commandProvinceList.add(province);
						if(commandOrder==args.length-1) 
							break;
						province = args[++commandOrder];
					}
				}
				else commandOrder++;
		}
	}
		if(isEmptyCommandProvince == true) {
		commandProvinceList.add("ȫ��");	
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
    public static boolean isValidInputAddress(String address) {
    	//��������ʽ�ж������·���Ƿ���ȷ
    	if (address.matches("^[A-z]:\\\\(.+?\\\\)*$")) {
    	return true;
    	}
    	else
    		return false;
    }
    /*
     *�������ܣ��ж��Ƿ��ǺϷ���·��
     *���������string
     *���������false,true
     **/
    public static boolean isValidOutputAddress(String address) {
    	//��������ʽ�ж������·���Ƿ���ȷ
    	if (address.matches("^[A-z]:\\\\(\\S+)+(\\.txt)$")) {
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
	public void searchFile(String address) throws ParseException, IOException {
	File file = new File(inputAddress);
	String fileName;
	
	//��ȡinputAddress·���µ������ļ����ļ�Ŀ¼
	File[] tempList ;
	tempList = file.listFiles();
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	String latestDate = tempList[0].getName();
		Date latestDay = format.parse(latestDate);
	if(commandDate.isEmpty()) {
	for(int i=0;i<tempList.length;i++) {
		fileName = tempList[i].getName();
		Date fileDay = format.parse(fileName);
		if(fileDay.after(latestDay)) {
			latestDay = fileDay;
		}
	}
	commandDate = latestDay.toString();
	}
	
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
		
		
		
		if(fileDay.before(commandDay)||fileDay.equals(commandDay)) {
			
				readFile(inputAddress+fileName);
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
	        
	                //���ж�ȡ�ı�����
	        while ((lineTxt = bfr.readLine()) != null) { 
	        	
	        	//������//������ȡ
	            if(!lineTxt.startsWith("//")) {
	            	 
	            		//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
	            	if(isEmptyCommandProvince == true) {
	            		
	            	String[] linePart = lineTxt.split(" ");
	            	String province = linePart[0];	
	            	if(!commandProvinceList.contains(province)) {
	            	commandProvinceList.add(province);
	            	}
	            	}
	             handleInformation(lineTxt);
	            // System.out.println("x");
	           
	        }
	        
	    
	    }bfr.close();
	        }catch (Exception e) {
	        e.printStackTrace();
	}
	}
	
	/*
	 *�������ܣ�ͳ��ʡ����������
	 *���������
	 *���������
	 **/
	public void handleInformation(String lineInformation) {
		String lineTypeOne = "(\\S+) ���� ��Ⱦ���� (\\d+)��";
		String lineTypeTwo = "(\\S+) ���� ���ƻ��� (\\d+)��";
		String lineTypeThree = "(\\S+) ���� (\\d+)��";
		String lineTypeFour = "(\\S+) ���� (\\d+)��";
		String lineTypeFive = "(\\S+) ��Ⱦ���� ���� (\\S+) (\\d+)��";
		String lineTypeSix = "(\\S+) ���ƻ��� ���� (\\S+) (\\d+)��";
		String lineTypeSeven = "(\\S+) ���ƻ��� ȷ���Ⱦ (\\d+)��";
		String lineTypeEight = "(\\S+) �ų� ���ƻ��� (\\d+)��";
		
		
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
		//��ȡʡ�ݶ�Ӧ�����µĻ�������
		int previousNum=0;
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String str:thisSet) {
					if(str.equals(province)) {
						HashMap<String, Integer> thisMap = ProvinceToNumMap.get(province);
						Set<String> typeKeys = thisMap.keySet();
						for(String strTwo:typeKeys) {		
							if(strTwo.equals(type)) {
								
								previousNum = thisMap.get(type);
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
		
	    ProvinceToNumMap.get(province).replace("��Ⱦ����",currentNum);
	    ProvinceToNumMap.get("ȫ��").replace("��Ⱦ����",countryCurrentNum);
	    
	    
	    
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
 		
		
 	    ProvinceToNumMap.get(province).replace("���ƻ���",currentNum);
 	    ProvinceToNumMap.get("ȫ��").replace("���ƻ���",countryCurrentNum);
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
 		
 	    ProvinceToNumMap.get(flowOutProvince).replace("��Ⱦ����",flowOutCurrentNum);
 	   ProvinceToNumMap.get(flowInProvince).replace("��Ⱦ����",flowInCurrentNum);
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
  		
  		//�������ƻ�������
  		int flowNum;
  		
  		//ȥ�����ֺ���ġ��ˡ���ȡ������������
  		flowNum = Integer.valueOf(linePart[4].replaceAll("��",""));
  		
  		int flowOutPreviousNum,flowInPreviousNum,flowOutCurrentNum,flowInCurrentNum;
  		
  		flowOutPreviousNum = searchProvinceToTypeNum(flowOutProvince,"���ƻ���");
  		flowInPreviousNum = searchProvinceToTypeNum(flowInProvince,"���ƻ���");
  		
  		flowOutCurrentNum = flowOutPreviousNum-flowNum;
  		flowInCurrentNum = flowInPreviousNum+flowNum;
  		
  		
  	    ProvinceToNumMap.get(flowOutProvince).replace("���ƻ���",flowOutCurrentNum);
  	   ProvinceToNumMap.get(flowInProvince).replace("���ƻ���",flowInCurrentNum);
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
  		int previousInfectionNum,countryPreviousInfectionNum,currentInfectionNum,countryCurrentInfectionNum;
  		
  		previousNum = searchProvinceToTypeNum(province,"��������");
  		previousInfectionNum = searchProvinceToTypeNum(province,"��Ⱦ����");
  		countryPreviousNum = searchProvinceToTypeNum("ȫ��","����");
  		countryPreviousInfectionNum = searchProvinceToTypeNum("ȫ��","��Ⱦ����");
			
  		currentNum = num + previousNum;
  		countryCurrentNum = num+countryPreviousNum;
  		currentInfectionNum = previousInfectionNum-num;
   		countryCurrentInfectionNum = countryPreviousInfectionNum-num;
  		
  		System.out.println("**********5");
 		System.out.println(num);
 		System.out.println(previousNum);
		System.out.println(countryPreviousNum);
		System.out.println(currentNum);
		System.out.println(countryCurrentNum);
		System.out.println("**********");
		
  	    ProvinceToNumMap.get(province).replace("����",currentNum);
  	    ProvinceToNumMap.get("ȫ��").replace("����",countryCurrentNum);
  	  ProvinceToNumMap.get(province).replace("��Ⱦ����",currentInfectionNum);
	    ProvinceToNumMap.get("ȫ��").replace("��Ⱦ����",countryCurrentInfectionNum);
       	}
       	
     /*�������ܣ�ͳ����������
          *���������
         *���������
          **/
     public void addCurePatients(String lineInformation) {
    	//�Ƚ�ÿһ�е��ַ����ָ����ַ�������
   		String[] linePart = lineInformation.split(" ");
   		String province = linePart[0];
   				
   		//�����������ߵ�����
   		int num;
   		
   		//ȥ�����ֺ���ġ��ˡ���ȡ������������
   		num = Integer.valueOf(linePart[2].replaceAll("��",""));
   		
   		int previousNum,countryPreviousNum,currentNum,countryCurrentNum;
   		int previousInfectionNum,countryPreviousInfectionNum,currentInfectionNum,countryCurrentInfectionNum;
   		
   		previousNum = searchProvinceToTypeNum(province,"����");
   		previousInfectionNum = searchProvinceToTypeNum(province,"��Ⱦ����");
   		
   		countryPreviousNum = searchProvinceToTypeNum("ȫ��","����");
   		countryPreviousInfectionNum = searchProvinceToTypeNum("ȫ��","��Ⱦ����");
   			
   		currentNum = num + previousNum;
   		countryCurrentNum = num+countryPreviousNum;
   		currentInfectionNum = previousInfectionNum-num;
   		countryCurrentInfectionNum = countryPreviousInfectionNum-num;
   		
   	    ProvinceToNumMap.get(province).replace("����",currentNum);
   	    ProvinceToNumMap.get("ȫ��").replace("����",countryCurrentNum);
   	 ProvinceToNumMap.get(province).replace("��Ⱦ����",currentInfectionNum);
	    ProvinceToNumMap.get("ȫ��").replace("��Ⱦ����",countryCurrentInfectionNum);
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
			
			if(typeList.isEmpty()) {
					typeList.add("��Ⱦ����");
					typeList.add("���ƻ���");
	                typeList.add("����");
					typeList.add("����");
					
				}
			//��-province����ȫ�����������в�����-province
		/*	if(isEmptyCommandProvince==true) {
				for(int i=0;i<provinceList.length;i++) {
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String strKey:thisSet) {	
					if(strKey.equals(provinceList[i])) {	
						writer.write(strKey+" ");
					HashMap<String,Integer> TypeToNumValue = ProvinceToNumMap.get(strKey);
					for(int j=0;j<typeList.size();j++) {
					Set<String> set = TypeToNumValue.keySet();
					for(String integerKey:set) {
							
							if(integerKey.equals(typeList.get(j))) {			
							switch(typeList.get(j)) {
							case "��Ⱦ����":Integer value = TypeToNumValue.get("��Ⱦ����");
							writer.write("��Ⱦ����"+value+"��"+" ");
							break;
							case "���ƻ���":Integer value1 = TypeToNumValue.get("���ƻ���");
							writer.write("���ƻ���"+value1+"��"+" ");
							break;
							case "����":Integer value2 = TypeToNumValue.get("����");	
							writer.write("����"+value2+"��"+" ");
							break;
							case "����":Integer value3 = TypeToNumValue.get("����");
							writer.write("����"+value3+"��"+" ");
							break;
							}
						}	
							}
						}
					writer.write("\n");
					}
				}
				}
				}
			else {*/
				for(int i=0;i<commandProvinceList.size();i++) {
				Set<String> thisSet = ProvinceToNumMap.keySet();
				for(String strKey:thisSet) {			
						if(strKey.equals(commandProvinceList.get(i))) {
							writer.write(strKey);
							HashMap<String,Integer> TypeToNumValue = ProvinceToNumMap.get(strKey);
							for(int j=0;j<typeList.size();j++) {
						Set<String> set = TypeToNumValue.keySet();
						for(String integerKey:set) {
							
								if(integerKey.equals(typeList.get(j))) {
								switch(typeList.get(j)) {
								case "��Ⱦ����":Integer value = TypeToNumValue.get("��Ⱦ����");
								writer.write("��Ⱦ����"+value+"��"+" ");
								break;
								case "���ƻ���":Integer value1 = TypeToNumValue.get("���ƻ���");
								writer.write("���ƻ���"+value1+"��"+" ");
								break;
								case "����":Integer value2 = TypeToNumValue.get("����");	
								writer.write("����"+value2+"��"+" ");
								break;
								case "����":Integer value3 = TypeToNumValue.get("����");
								writer.write("����"+value3+"��"+" ");
								break;
								}
						}}
							}writer.write("\n");
						}
						
						
					}
				}
		//	} 
			writer.write("// ���ĵ�������ʵ���ݣ���������ʹ��");
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] args) throws ParseException, IOException, InterruptedException {
     InfectStatistic infectStatistic = new InfectStatistic();
    
       String[] str3= {"list","-log","D:\\221701136\\log\\","-out",
				"D:\\ListOut1.txt","-date","2020-01-23","-province","ȫ��","����","�㽭","-type","cure","dead","ip"};
       String[] str1= {"list","-log","D:\\221701136\\log\\","-out",
				"D:\\ListOut1.txt","-date","2020-01-22"};
       String[] str2= {"list","-log","D:\\221701136\\log\\","-out",
				"D:\\ListOut1.txt","-date","2020-01-22","-province","����","�ӱ�"};
       infectStatistic.analyseCommandLine(str3);
      // System.out.println("���"+"111");
       System.out.println(inputAddress);
       System.out.println(outputAddress);
       
     // System.out.println(commandDate);
       infectStatistic.searchFile(inputAddress);
       infectStatistic.outputData(outputAddress);
       
     //  String lineTypeOne = "(\\S+) ���� ��Ⱦ���� (\\d+)��";
     //  System.out.println(Pattern.matches(lineTypeOne, "���� ���� ��Ⱦ���� 5��"));
    //  System.out.println(commandProvinceList.size());
      
    /*   for(int i=0;i<commandProvinceList.size();i++) {
       System.out.println(commandProvinceList.get(i));
       }
       
       for(int i=0;i<typeList.size();i++) {
           System.out.println(typeList.get(i));
           }
           */
	}
 
   
}
