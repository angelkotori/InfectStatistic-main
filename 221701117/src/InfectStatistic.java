/**
 * InfectStatistic
 * TODO
 *
 * @author yjchen
 * @version 1.0
 * @since 2020-02-08
 */
import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;  
import infectstatistic_yjchen.J_Province;

public class InfectStatistic
{
	static J_Province allCountry = new J_Province();
	static Vector<J_Province> provinces = new Vector<J_Province>();
	static String[] arrayProvinces={"安徽","澳门","北京","重庆","福建","甘肃","广东","广西","贵州","海南",
			"河北","河南","黑龙江","湖北","湖南","吉林","江苏","江西","辽宁","内蒙古","宁夏",
			"青海","山东","山西","陕西","上海","四川","台湾","天津","西藏","香港","新疆","云南","浙江"};
			//已排好序
	static int[] listType = {0,0,0,0};
	static String inputEndDate;
	static String currentStringDate;
	static String inputAddress;
	static String outputFileAddress;
	static Vector<Integer> posVec = new Vector<Integer>();
	static Vector<Integer> interval = new Vector<Integer>();
	static Vector<String> temp=new Vector<String>();
	static Vector<String> vector=new Vector<String>();
	static boolean isOutCountry = false;//是否输出全国感染信息
	static int countryPos = 0;//”全国“所处的下标
	static int countList = 0;//记录输出列个个数
	static boolean IsOutputDefautList = true;//参数选项是否有-type
	static boolean IsOutputDefautProvince = true;//参数选项是否有-province
	static boolean IsOutputDefautDate = true;//参数选项是否有-date
	
	
    public static void main(String args[ ]) throws IOException
    {
    	//接收命令行参数
    	for(String temp : args)vector.add(temp);
    	//测试函数dealParameter
    	
    	{
    		vector.add("list");
    		vector.add("-log");
    		vector.add("G:\\java\\eclipse\\eclipse-workspace\\hw2_2\\src\\infectstatistic_yjchen\\");
    		vector.add("-out");
    		vector.add("G:\\java\\eclipse\\eclipse-workspace\\hw2_2\\src\\output.txt");
    		
    		vector.add("-date");
    		vector.add("2020-01-31");
    		
    		/*
    		vector.add("-type");
    		vector.add("sp");
    		vector.add("ip");
    		vector.add("dead");
    		*/
    		/*
    		vector.add("-province");
    		vector.add("重庆");
    		vector.add("福建");
    		vector.add("全国");
    		vector.add("湖南");
    		vector.add("广东");
    		vector.add("安徽"); 
    		*/
    	}
    	
    	//保存全国的感染情况
    	allCountry.setName("全国");
    	//保存各省市的感染情况
    	//Vector<J_Province> provinces = new Vector<J_Province>();
    	//省份按拼音首字母顺序排序
    	//Comparator<Object> com=Collator.getInstance(java.util.Locale.CHINA);
    	//Arrays.sort(arrayProvinces,com);
    	//输出排序结果
    	/*
    	for(String i:arrayProvinces){
    		System.out.print(i+" ");
    	}
    	*/
    	//先前用Vector实现，发现排序麻烦    	
    	//将各省份感染信息加入到Vector中，方便管理
    	for(int i = 0;i < 34;i++) {
    		J_Province temp = new J_Province();
    		temp.setName(arrayProvinces[i]);
    		provinces.add(temp);    		
    	}
    	//初始化vector
    	initialVector();
		//判断参数类型
    	dealParameter(vector);
    	//处理log文件
    	processLogFile();
    	//输出
		ListProvince(temp);
    }
    
    
    /*******************
     * 作用:初始化向量
     * 参数:null
     *******************/
    private static void initialVector() {
		// TODO Auto-generated method stub    	
    	for(int i = 0;i < vector.size();i ++ )
    	{
    		if(vector.get(i).charAt(0) == '-')
    		{
    			posVec.add(i);
    		}
    		if(vector.get(i).equals("全国")) //如果有全国，则省份个数减一个
    		{
    			isOutCountry = true;
    			countryPos = i;
    		}
       	}
    	if(isOutCountry) //如果有全国，则省份个数减一个
		{
    		posVec.add(vector.size()-1);
		}
    	else
    	{
    		posVec.add(vector.size());
    	}
    	
    	for(int i = 1;i < posVec.size();i ++)
    	{
    		interval.add(posVec.elementAt(i)-posVec.elementAt(i-1)-1);
    		//System.out.println(posVec.elementAt(i)-posVec.elementAt(i-1)-1);
    	}
	}
    
    
    /*******************
     * 作用:判断参数选项
     * 参数:存放命令行参数的Vector数据结构
     *******************/
    private static void dealParameter(Vector<String> vector) {
		// TODO Auto-generated method stub
    	int pos=0;
    	if(vector.get(0).equals("list")) 
    	{
    		for(int i = 0;i < vector.size();i ++ )
        	{
        		if(vector.get(i).charAt(0) == '-') {
        			if(vector.get(i).equals("-log"))
        			{
        				for(int j = 0;j < interval.elementAt(pos);j ++ )
        				{
        					inputAddress = vector.get(i+j+1);
        				}
        				i += interval.elementAt(pos);
        				pos ++ ;
        				continue;
        			}
        			if(vector.get(i).equals("-out"))
        			{
        				for(int j = 0;j < interval.elementAt(pos);j ++ )
        				{
        					outputFileAddress = vector.get(i+j+1);
        				}
        				i += interval.elementAt(pos);
        				pos ++ ;
        				continue;
        			}
        			if(vector.get(i).equals("-date"))
        			{
        				IsOutputDefautDate = false;
        				for(int j = 0;j < interval.elementAt(pos);j ++ )
        				{
        					inputEndDate = vector.get(i+j+1);
        					//获取当前日期的年月日
        					//Calendar now = Calendar.getInstance();
        			        Date d = new Date();
        			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        			        String dateNowStr = sdf.format(d);  
        			        currentStringDate = dateNowStr;
        			        //System.out.println("格式化后的日期：" + dateNowStr);  
        			        //System.out.println(dateNowStr.compareTo(inputEndDate));
        					if(!checkDate(inputEndDate)||dateNowStr.compareTo(inputEndDate) < 0)
        					{
        						System.out.println("日期有误!");
        						System.exit(0);
        					}
        				}
        				i += interval.elementAt(pos);
        				pos ++ ;
        				continue;
        			}
        			if(vector.get(i).equals("-type"))
        			{
        				IsOutputDefautList = false;
        				for(int j = 0;j < interval.elementAt(pos);j ++ )
        				{
        					if(vector.get(i+j+1).equals("ip")) {
        						listType[0] = j+1;//向listType传入参数顺序，从1开始以免和0混淆
        						countList++;
        					}
        					if(vector.get(i+j+1).equals("sp")) {
        						listType[1] = j+1;
        						countList++;
        					}
        					if(vector.get(i+j+1).equals("cure")) {
        						listType[2] = j+1;
        						countList++;
        					}
        					if(vector.get(i+j+1).equals("dead")) {
        						listType[3] = j+1;
        						countList++;
        					}
        				}
        				i += interval.elementAt(pos);
        				pos ++ ;
        				//判断输出的列
        				continue;
        			}
        			if(vector.get(i).equals("-province"))
        			{
        				IsOutputDefautProvince = false;
        				for(int j = 0;j < interval.elementAt(pos);j ++ )
        				{
        					if(vector.get(i+j+1).equals("全国"))
        					{
        						isOutCountry = true;
        					}
        					if(i+j+1 >= countryPos)
        					{
        						temp.add(vector.get(i+j+2));//跳过“全国”
        					}
        					else
        					{
        						temp.add(vector.get(i+j+1));
        					}
        				}
        				i += interval.elementAt(pos);
        				pos ++ ;
        				continue;
        			}
        			i += interval.elementAt(pos);
    				pos ++ ;        			
        		}
        	}  
    	}    	
	}
    
    
    /*****************************
     * 作用:处理日志文件
     * 参数:null
     *****************************/
    private static void processLogFile()
    {
    	//判断是否默认-date
    	if(IsOutputDefautDate)
    	{
    		Date d = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        String dateNowStr = sdf.format(d);  
	        currentStringDate = dateNowStr;
    		inputEndDate = currentStringDate;
    	}
    	//System.out.println(currentStringDate);
    	//读取文件，读取从2019-12-31到指定日期的所有文件。没有则continue。
    	//定义变量
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null,dEnd = null;
        String fileName = new String();
        ArrayList<String> dataFile;
        File file;
		try {
			dBegin = sdf.parse("2019-12-31");
			dEnd = sdf.parse(inputEndDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //设置Calendar时间
    	Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		//在选择的日期内循环。
		while(dEnd.after(calBegin.getTime()))
		{
			 //加一天
			 calBegin.add(Calendar.DAY_OF_MONTH, 1);
			 //记得加上前缀，不是在当前文件的目录
			 fileName = inputAddress +  sdf.format(calBegin.getTime()) + ".log.txt";
			 file = new File(fileName);
			 if(!file.exists()) 
			 {
				 continue;
			 }
			 else 
			 {
				 String[] ss ;
				 String st;
				 dataFile = FileReadLine(fileName);
				 for(int i = 0; i < dataFile.size(); i++) {
					 //获取文件行
			         //System.out.println(dataFile.get(i));
			         //按空格分割
					 ss = dataFile.get(i).split("\\s+");
					 //获取对应省份下标
					 int pos = 0;
					 for(int k = 0;k < provinces.size();k++) 
					 {
						 st = provinces.elementAt(k).getName();	
						 //判断是否默认输出所有涉及的省份代码
						 boolean isInTemp = false;
						 for(String s:temp)
						 {
							 if(s.equals(ss[0]))
							 {
								 isInTemp = true;
								 break;
							 }
						 }
						 boolean notProvince = true;//以免例如“//”记录进去
						 if(ss[0].equals("全国"))
						 {
							 notProvince = false;
						 }
						 for(String s:arrayProvinces)
						 {
							 if(ss[0].equals(s))
							 {
								 notProvince = false;
							 }
						 }
				    	 if(IsOutputDefautProvince && !isInTemp && !notProvince)
				    	 {
				    		 temp.add(ss[0]);
				    	 }								    	 
						 //System.out.println(st+" "+ss[0]);
						 if(st.contentEquals(ss[0]))
						 {
							 pos = k;
							 break;
						 }
					 }
					 //行分三份
					 if(ss.length == 3) 
					 {
						 String sTemp = ss[2].substring(0,ss[2].length() - 1);
						 if(ss[1].equals("死亡"))
						 {
							 //死亡数+num
							 provinces.elementAt(pos).AddDied(Integer.parseInt(sTemp));
							 //感染数-num
							 provinces.elementAt(pos).DecInfected(Integer.parseInt(sTemp));
							 //全国死亡数+num
							 allCountry.AddDied(Integer.parseInt(sTemp));
							 //全国感染数-num
							 allCountry.DecInfected(Integer.parseInt(sTemp));
						 }
						 if(ss[1].equals("治愈"))
						 {
							 //治愈数+num
							 provinces.elementAt(pos).AddCured(Integer.parseInt(sTemp));
							 //感染数-num
							 provinces.elementAt(pos).DecInfected(Integer.parseInt(sTemp));
							 //全国治愈数+num
							 allCountry.AddCured(Integer.parseInt(sTemp));
							 //全国感染数-num
							 allCountry.DecInfected(Integer.parseInt(sTemp));
						 }
					 }
					//行分四份
					 if(ss.length == 4)
					 {
						 //获取人数
						 String sTemp = ss[3].substring(0,ss[3].length() - 1);
						 if(ss[1].equals("新增"))
						 {
							 if(ss[2].equals("感染患者"))
							 {
								 //感染数+num
								 provinces.elementAt(pos).AddInfected(Integer.parseInt(sTemp));
								 //全国感染数+num
								 allCountry.AddInfected(Integer.parseInt(sTemp));
							 }
							 if(ss[2].equals("疑似患者"))
							 {
								 //疑似数+num
								 provinces.elementAt(pos).AddSuspected(Integer.parseInt(sTemp));
								 //全国疑似数+num
								 allCountry.AddSuspected(Integer.parseInt(sTemp));
							 }
						 }
						 if(ss[1].equals("疑似患者"))
						 {
							 //感染数+num
							 provinces.elementAt(pos).AddInfected(Integer.parseInt(sTemp));
							 //疑似数-num
							 provinces.elementAt(pos).DecSuspected(Integer.parseInt(sTemp));
							 //全国感染数+num
							 allCountry.AddInfected(Integer.parseInt(sTemp));
							 //全国疑似数-num
							 allCountry.DecSuspected(Integer.parseInt(sTemp));
						 }
						 if(ss[1].equals("排除"))
						 {
							 //疑似数-num
							 provinces.elementAt(pos).DecSuspected(Integer.parseInt(sTemp));
							 //全国疑似数-num
							 allCountry.DecSuspected(Integer.parseInt(sTemp));
						 }
					 }
					//行分五份
					 if(ss.length == 5)
					 {
						 String sTemp = ss[4].substring(0,ss[4].length() - 1);
						 //流入的城市的下标
						 int pos_2 = 0;
						 for(int k = 0; k < provinces.size(); k++) 
						 {
							 st = provinces.elementAt(k).getName();
							 if(st.contentEquals(ss[3]))
							 {
								 pos_2 = k;
								 break;
							 }
						 }
						 if(ss[1].equals("感染患者"))
						 {
							 //感染数+num
							 provinces.elementAt(pos_2).AddInfected(Integer.parseInt(sTemp));
							 //疑似数-num
							 provinces.elementAt(pos).DecInfected(Integer.parseInt(sTemp));
						 }
						 if(ss[1].equals("疑似患者"))
						 {
							 //疑似数+num
							 provinces.elementAt(pos_2).AddSuspected(Integer.parseInt(sTemp));
							 //疑似数-num
							 provinces.elementAt(pos).DecSuspected(Integer.parseInt(sTemp));
						 }
					 }
			     }
			 }	  
		}
    }
    
    
    /*********************************
     * 作用:输出结果到指定文件
     * 参数:存放需要输出的省份
     ********************************/
    private static void ListProvince(Vector<String> temp) throws IOException {
		// TODO Auto-generated method stub
		File outFile = new File(outputFileAddress);
		Writer out = new FileWriter(outFile);
		Vector<J_Province> limitProvince = new Vector<J_Province>();	
		int arrayPos[] = new int [temp.size()];
		int provincePos = 0;//好神奇，如果变量在函数内，则循环体内赋值失败。
		for(String s:temp) //需要列出的省份的数组下标组成新数组
		{			
			//System.out.println(s);
			for(int i = 0;i < arrayProvinces.length;i ++ )
			{
				if(s.equals(arrayProvinces[i]))
				{
					arrayPos[provincePos++] = i;
					break;
				}
			}			
		}	
		//System.out.println("arrayPos=" + arrayPos.length);
		Arrays.sort(arrayPos);
		for(int i = 0;i < arrayPos.length;i++) 
		{
			limitProvince.add(provinces.elementAt(arrayPos[i]));
		}
		//无设定-type选项，默认全部输出，输出顺序为1,2,3,4
		if(IsOutputDefautList)
		{
			for(int i = 0;i < listType.length;i++)
			{
				listType[i] = i + 1;
			}			
			countList = 4;
		}
		//是否输出默认省份疫情信息		
		//是否输出全国感染信息		
		if(isOutCountry || IsOutputDefautProvince) 
		{
			J_Province s = allCountry;
			out.write(s.getName() + "\t" );
			System.out.print(s.getName() + "\t" );
			for(int j = 1;j < countList + 1;j ++ )
			{
				for(int i = 0;i < 4;i ++ )
				{
					if(listType[i] == 0)
					{
						continue;
					}
					if(listType[i] == j)
					{
						if(i == 0)
						{
							out.write("感染患者" + s.Infected() + "人" + "\t");
							System.out.print("感染患者" + s.Infected() + "人" + "\t");
						}
						if(i == 1)
						{
							out.write("疑似患者" +  s.Suspected() + "人" + "\t" );
							System.out.print("疑似患者" +  s.Suspected() + "人" + "\t" );
						}
						if(i == 2)
						{
							out.write("治愈" + s.Cured() + "人" + "\t");
							System.out.print("治愈" + s.Cured() + "人" + "\t");
						}
						if(i == 3)
						{
							out.write("死亡" + s.Died() + "人" + "\t");
							System.out.print( "死亡" + s.Died() + "人" + "\t");
						}
					}
				}
			}			
			out.write("\n");
			System.out.println();
		}
		for(J_Province s:limitProvince) 
		{
			out.write(s.getName() + "\t" );
			System.out.print(s.getName() + "\t" );
			for(int j = 1;j < countList + 1;j ++ )
			{
				for(int i = 0;i < 4;i ++ )
				{
					if(listType[i] == 0)
					{
						continue;
					}
					if(listType[i] == j)
					{
						if(i == 0)
						{
							out.write("感染患者" + s.Infected() + "人" + "\t");
							System.out.print("感染患者" + s.Infected() + "人" + "\t");
						}
						if(i == 1)
						{
							out.write("疑似患者" +  s.Suspected() + "人" + "\t" );
							System.out.print("疑似患者" +  s.Suspected() + "人" + "\t" );
						}
						if(i == 2)
						{
							out.write("治愈" + s.Cured() + "人" + "\t");
							System.out.print("治愈" + s.Cured() + "人" + "\t");
						}
						if(i == 3)
						{
							out.write("死亡" + s.Died() + "人" + "\t");
							System.out.print( "死亡" + s.Died() + "人" + "\t");
						}
					}
				}
			}
			out.write("\n");
			System.out.println();
		}
		out.close();
	}
    
    
    /**********************************************
     * 作用:txt文件按行分割
     * 参数:文件名
     * 返回结果:分割结果放入ArrayList
     **********************************************/
    public static ArrayList<String> FileReadLine(String name)
    {
    	//System.out.println(name);
    	ArrayList<String> arrayList = new ArrayList<>();
		try {
			File file = new File(name);
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
			BufferedReader bf = new BufferedReader(inputReader);
			// 按行读取字符串
			String str;
			while ((str = bf.readLine()) != null) {
				arrayList.add(str);
			}
			bf.close();
			inputReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return arrayList;
    } 
    
    
    /**************************************************
     * 作用:判断日期合法
     * 参数:String
     * 1返回类型:boolean
     ***************************************************/
    private static boolean checkDate(String date)
    {
    	//判断日期合法
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");//括号内为日期格式，y代表年份，
		//M代表年份中的月份（为避免与小时中的分钟数m冲突，此处用M），d代表月份中的天数
		try {
			sd.setLenient(false);//此处指定日期/时间解析是否不严格，在true是不严格，false时为严格
			sd.parse(inputEndDate);//从给定字符串的开始解析文本，以生成一个日期
		}
		catch (Exception e) {
			return false;
		}
		return true;
    }
}
