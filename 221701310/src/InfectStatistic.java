import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntPredicate;

/**
 * InfectStatistic
 * TODO
 *
 * @author xxx
 * @version xxx
 * @since xxx
 */


class InfectStatistic {
	
	class MyProvince {
		
		public String provinceName;//“全国”，或省、直辖市的名称
		
		public int ip;//确诊患者数量
		public int sp;//疑似患者数量
		public int cure;//治愈患者数量
		public int dead;//死亡患者数量
		
		public boolean isMentioned;//是否在日志中被提到过
		public boolean isNeedPrint;//是否被操作员指明需要列出
		
		public MyProvince(String provinceName) {
			this.provinceName = provinceName;
			this.ip = 0;
			this.sp = 0;
			this.cure = 0;
			this.dead = 0;
			this.isMentioned = false;
			this.isNeedPrint = false;
			if(provinceName.equals("全国")) {
				this.isMentioned = true;
			}
		}
		
		//<省> 新增 感染患者 n人
		public void ipAdd(int n) {
			this.isMentioned = true;
			this.ip += n;
			provinceMap.get("全国").ip += n;
		}
		
		//<省> 新增 疑似患者 n人
		public void spAdd(int n) {
			this.isMentioned = true;
			this.sp += n;
			provinceMap.get("全国").sp +=n;
		}
		
		//<省1> 感染患者 流入 <省2> n人
		public void ipMove(String strProvinceName, int n) {
			this.isMentioned = true;
			this.ip -= n;
			provinceMap.get(strProvinceName).isMentioned = true;
			provinceMap.get(strProvinceName).ip += n;
		}
		
		//<省1> 疑似患者 流入 <省2> n人
		public void spMove(String strProvinceName, int n) {
			this.isMentioned = true;
			this.sp -= n;
			provinceMap.get(strProvinceName).isMentioned = true;
			provinceMap.get(strProvinceName).sp += n;
		
		}
		
		//<省> 死亡 n人
		public void peopleDead(int n) {
			this.isMentioned = true;
			this.ip -= n;
			this.dead += n;
			provinceMap.get("全国").ip -= n;
			provinceMap.get("全国").dead += n;
		}
		
		//<省> 治愈 n人
		public void peopleCured(int n) {
			this.isMentioned = true;
			this.ip -= n;
			this.cure += n;
			provinceMap.get("全国").ip -= n;
			provinceMap.get("全国").cure += n;
		}
		
		//<省> 疑似患者 确诊感染 n人
		public void spDiagnosed(int n) {
			this.isMentioned = true;
			this.sp -= n;
			this.ip += n;
			provinceMap.get("全国").sp -= n;
			provinceMap.get("全国").ip += n;
		}
		
		//<省> 排除 疑似患者 n人
		public void spExclude(int n) {
			this.isMentioned = true;
			this.sp -= n;
			provinceMap.get("全国").sp -= n;
		}
	}
	
	//建立省份名与省份类实例的HashMap
	HashMap<String, MyProvince>provinceMap = new HashMap<String, MyProvince>();
	
	//省份名数组，按首字母顺序排列，“全国”排最前
	String [] AllProvinceName = {
		    "全国","安徽","北京","重庆","福建","甘肃","广东","广西",
		    "贵州","海南","河北","河南","黑龙江","湖北","湖南","吉林",
		    "江苏","江西","辽宁","内蒙古","宁夏","青海","山东","山西",
		    "陕西","上海","四川","天津","西藏","新疆","云南","浙江",
	};
	
	//初始化HashMap
    public void init() {
    	provinceMap.clear();
    	for(String strProvinceName:AllProvinceName) {
    		MyProvince myProvince = new MyProvince(strProvinceName);
    		provinceMap.put(strProvinceName, myProvince);
    	}
    	
    }
    
    //处理输入的命令
    public void processCmd(String[] args) {
    	if(args.length>1 && args[0].equals("list")) {
    		
    		String logPath = new String("");
    		String outPath = new String("");
    		String date = new String("");
    		int[] type = new int[] {1,1,1,1};//默认输出四种人群
    		boolean isProvinceSpecified = false;//该项为false，输出所有日志中提到的省份，为true输出指定的省份
    		
    		for(int index = 1;index<args.length;index++) {
    			switch(args[index]) {
    				case "-log":
    					logPath = args[index+1];
    					index++;
    					break;
    				case "-out":
    					outPath = args[index+1];
    					index++;
    					break;
    				case "-date":
    					date=args[index+1];
    					index++;
    					break;
    				case "-type":
    					//若指定人群，则先把type所有元素置0，在把特定元素置1
    					for(int i=0;i<type.length;i++) {
    						type[i]=0;
    					}
    					while(index+1<args.length && args[index+1].charAt(0)!='-') {
    						if(args[index+1].equals("ip")) type[0]=1;
    						else if(args[index+1].equals("sp")) type[1]=1;
    						else if(args[index+1].equals("cure")) type[2]=1;
    						else if(args[index+1].equals("dead")) type[3]=1;
    						index++;
    					}
    					break;
    				case "-province":
    					isProvinceSpecified = true;
    					while(index+1<args.length && args[index+1].charAt(0)!='-') {
    						provinceMap.get(args[index+1]).isNeedPrint = true;
    						index++;
    					}
    					break;
    			}
    		}
    		excuteCmd(logPath,outPath,date,type,isProvinceSpecified);
    	}
    }
    
    //执行输入的命令
    public void excuteCmd(String logPath,String outPath,String date,int[] type,boolean isProvinceSpecified) {
    	
    	/*
    	System.out.println("-----In excuteCmd-----");
    	System.out.println(logPath);
    	System.out.println(outPath);
    	System.out.println(date);
    	
    	System.out.print("type.length=");
    	System.out.println(type.length);
    	for(int i=0;i<type.length;i++) {
    		System.out.print(type[i]);
    	}
    	
    	System.out.println();
    	System.out.println(isProvinceSpecified);
		*/
    	
    	
    	
    }
	
    public static void main(String[] args) {
    	InfectStatistic infectStatistic = new InfectStatistic();
    	infectStatistic.init();
    	for(String i:args) {
    		System.out.println(i);
    	}
    	System.out.println(args.length);
    	infectStatistic.processCmd(args);
    }
}