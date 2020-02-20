/**
 * InfectStatistic
 * TODO
 *
 * @author 杨鑫杰
 * @version 1.0
 * @since 2020.02.17
 */

import java.io.*;
import java.util.regex.*;
import java.text.SimpleDateFormat;
import java.util.Date;


class InfectStatistic{
	
	public String path_in;//读取日志目录
	public String path_out;//输出日志目录
	
	//获取格式化日期
	Date dt = new Date(System.currentTimeMillis());
    String strDateFormat = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
    String date=sdf.format(dt);
	
	public String[] province = {
			"全国", "安徽", "澳门" ,"北京", "重庆", "福建","甘肃","广东", "广西",
			"贵州", "海南", "河北", "河南", "黑龙江", "湖北", "湖南", "吉林",
			"江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", 
			"上海","四川", "台湾", "天津", "西藏", "香港", "新疆", "云南", "浙江"};
	/*二维数组
	 * 一维表示省份
	 * 二维分别表示{是否需要列出,ip,sp,cure,dead}*/
	public int[][] situation=new int[35][5];
	
	//类型输出顺序，默认ip,sp,cure,dead
	public int type_order[]= {1,2,3,4};
	public String[] type= {"感染患者", "疑似患者", "治愈", "死亡"};

	//处理文件
	class ProcessFile{
		
		//读取日志
		public void readLog() {
			File file=new File(path_in);
			File[] listFiles=file.listFiles();
			String path;
			
			int i=0;
			while(i<listFiles.length){
				path=listFiles[i].getName();
				if(path.compareTo(date)<=0) {
					try {
						BufferedReader b=new BufferedReader(new InputStreamReader(
								new FileInputStream(new File(path_in+path)), "UTF-8"));
						String line;
						while((line=b.readLine())!=null) {
							if(!line.startsWith("//")) {
								String p1 = "(\\S+) 新增 感染患者 (\\d+)人";
						    	String p2 = "(\\S+) 新增 疑似患者 (\\d+)人";
						    	String p3 = "(\\S+) 治愈 (\\d+)人";
						    	String p4 = "(\\S+) 死亡 (\\d+)人";
						    	String p5 = "(\\S+) 感染患者 流入 (\\S+) (\\d+)人";
						    	String p6 = "(\\S+) 疑似患者 流入 (\\S+) (\\d+)人";
						    	String p7 = "(\\S+) 疑似患者 确诊感染 (\\d+)人";
						    	String p8 = "(\\S+) 排除 疑似患者 (\\d+)人";
						    	if(Pattern.matches(p1, line)) 
						    		IP(line);
						    	if(Pattern.matches(p2, line)) 
						    		SP(line);
						    	if(Pattern.matches(p3, line)) 
						    		Cure(line);
						    	if(Pattern.matches(p4, line)) 
						    		Dead(line);
						    	if(Pattern.matches(p5, line)) 
						    		IPgo(line);
						    	if(Pattern.matches(p6, line))
						    		SPgo(line);
						    	if(Pattern.matches(p7, line))
						    		SPtoIP(line);
						    	if(Pattern.matches(p8, line))
						    		notSP(line);
							}
						}
						b.close();
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				i++;
			}			
		}
		public void IP(String line) {
			String[] str = line.split(" ");    	
	    	int n = Integer.valueOf(str[3].replace("人", ""));
	    	int i;
	    	for(i = 1; i < 35; i++) {
	    		if(str[0].equals(province[i])) { 
	    			situation[0][1] += n;
	    			situation[i][1] += n; 
	    			if(situation[0][0]==-1)
	    				situation[i][0] = 1;
	    			break;
	    		}
	    	}
		}
		public void SP(String line) {
			String[] str = line.split(" ");	    	
	    	int n = Integer.valueOf(str[3].replace("人", ""));
	    	int i;
	    	for(i = 1; i < 35; i++) {
	    		if(str[0].equals(province[i])) { 
	    			situation[0][2] += n;
	    			situation[i][2] += n; 
	    			if(situation[0][0]==-1)
	    				situation[i][0] = 1;
	    			break;
	    		}
	    	}
			
		}
		public void Cure(String line) {
			String[] str = line.split(" ");	    	
	    	int n = Integer.valueOf(str[2].replace("人", ""));
	    	int i;
	    	for(i = 1; i < 35; i++) {
	    		if(str[0].equals(province[i])) { 
	    			situation[0][3] += n;
	    			situation[0][1] -= n;
	    			situation[i][3] += n;
	    			situation[i][1] -= n;
	    			if(situation[0][0]==-1)
	    				situation[i][0] = 1;
	    			break;
	    		}
	    	}
		}
		public void Dead(String line) {
			String[] str = line.split(" ");	    	
	    	int n = Integer.valueOf(str[2].replace("人", ""));
	    	int i;
	    	for(i = 1; i < 35; i++) {
	    		if(str[0].equals(province[i])) { 
	    			situation[0][4] += n;
	    			situation[0][1] -= n;
	    			situation[i][4] += n;
	    			situation[i][1] -= n;
	    			if(situation[0][0]==-1)
	    				situation[i][0] = 1;
	    			break;
	    		}
	    	}
		}
		public void IPgo(String line) {
	    	String[] str = line.split(" ");	    	
	    	int n = Integer.valueOf(str[4].replace("人", ""));
	    	int i;
	    	for(i = 1; i < province.length; i++) {
	    		if(str[0].equals(province[i])) {
	    			situation[i][1] -= n;
	    			if(situation[0][0]==-1)
	    				situation[i][0]=1;
	    		}
	    		if(str[3].equals(province[i])) {
	    			situation[i][1] += n;
	    			if(situation[0][0]==-1)
	    				situation[i][0]=1;
	    		}
	    	}		
		}
		public void SPgo(String line) {
	    	String[] str = line.split(" ");	    	
	    	int n = Integer.valueOf(str[4].replace("人", ""));
	    	int i;
	    	for(i = 1; i < province.length; i++) {
	    		if(str[0].equals(province[i])) {
	    			situation[i][2] -= n;
	    			if(situation[0][0]==-1)
	    				situation[i][0]=1;
	    		}
	    		if(str[3].equals(province[i])) {
	    			situation[i][2] += n;
	    			if(situation[0][0]==-1)
	    				situation[i][0]=1;
	    		}
	    	}			
		}
		public void SPtoIP(String line) {
	    	String[] str = line.split(" ");     	
	    	int n = Integer.valueOf(str[3].replace("人", ""));
	    	int i;
	    	for(i = 1; i < province.length; i++) {
	    		if(str[0].equals(province[i])) { 
	    			situation[0][2] -= n; 
	    			situation[0][1] += n; 
	    			situation[i][2] -= n; 
	    			situation[i][1] += n; 
	    			if(situation[0][0]==-1)
	    				situation[i][0]=1;
	    			break;
	    		}
	    	}
		}
		public void notSP(String line) {
	    	String[] str = line.split(" ");     	
	    	int n = Integer.valueOf(str[3].replace("人", ""));
	    	int i;
	    	for(i = 1; i < province.length; i++) {
	    		if(str[0].equals(province[i])) { 
	    			situation[0][2] -= n; 	    			
	    			situation[i][2] -= n; 
	    			if(situation[0][0]==-1)
	    				situation[i][0]=1;
	    			break;
	    		}
	    	}			
		}
		
		//输出日志
		public void writeLog() throws IOException {
			FileWriter fw=null;
			fw=new FileWriter(path_out);
			if(situation[0][0]==-1)
				situation[0][0]=1;
			int i=0;
			while(i<35) {
				if(situation[i][0]==1) {
					fw.write(province[i]+" ");
					for(int j=0,k=1;j<type_order.length;j++) {
						if(type_order[j]==k) {
							fw.write(type[j]+situation[i][j+1]+"人");
							k++;
						}
					}			
				}
				fw.write("\n");
				i++;
			}
			fw.write("// 该文档并非真实数据，仅供测试使用");
			fw.close();
		}
		
	}	
	
	 
	
	
	//处理命令行
	class ProcessCmd{
		String[] args;//存储命令行
		
		ProcessCmd(String[] args){
			this.args=args;
		}
		
		//处理参数
		public boolean ProcessPara(){
			
			//判断命令是否正确	
			if(!args[0].equals("list")){
				System.out.println("命令错误");
				return false;
			}
		
			int i;
			for(i = 1;i<args.length;i++){
				//读取-log
				if(args[i].equals("-log")){
					path_in=args[++i];
					
				} 
				//读取-out
				else if(args[i].equals("-out")){ 
					path_out=args[++i];
					
				} 
				//读取-date
				else if(args[i].equals("-date")){
					date=args[++i]+".log.txt";
	
				} 
				//设置-type输出顺序
				else if(args[i].equals("-type")){ 
					i=setType(++i); 
					
				} 
				//读取-province
				else if(args[i].equals("-province")){ 
					i=getProvince(++i);
	
				}
			}
			return true;
		}	
	
//		public void getPathIn(int i){
//
//		}
//		public void getPathOut(int i){
//			
//		}
//		public void getDate(int i){
//			
//		}
		
		public int setType(int i){
			int k;
			int flag=i;
			for(k=0;k<4;k++)
				type_order[k]=0;
			k=1;
			while(i<args.length) {
				if(args[i].equals("ip")) {
					type_order[0] = k;
					i++;
					k++;
				} 
				else if(args[i].equals("sp")) {
					type_order[1] = k;
					i++;
					k++;
				} 
				else if(args[i].equals("cure")) {
					type_order[2] = k;
					i++;
					k++;
				} 
				else if(args[i].equals("dead")) {
					type_order[3] = k;
					i++;
					k++;
				} 
				else
					break;
			}				
			if(flag==i) {
				type_order[0] = 1;
				type_order[1] = 2;
				type_order[2] = 3;
				type_order[3] = 4;
				return i--;
			}
			return i;
		}
		
		public int getProvince(int i){
			int flag=i;
			for(int t=0;t<35;t++)
				situation[t][0]=0;
			while(i<args.length) {
				for(int j=0;j<35;j++) {
					if(args[i].equals(province[j])) {
						situation[j][0]=1;
						i++;
						break;
					}
				}
			}
			if(flag==i) {
				situation[0][0]=-1;
				return i--;
			}
			return i;
		}
		
	}
	
	
	

    public static void main(String[] args) {
    	
    }	
}
