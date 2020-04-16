/**
 * InfectStatistic
 * TODO
 *
 * @author yangmingwei
 * @version 1.0
 * @since 2020/2/15
 */
import java.util.Vector;
import java.io.InputStream;
import java.text.Collator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.io.*;
public class InfectStatistic {
    public static void main(String[] args) {
    	DocFormatter doc=new DocFormatter(args);  //��ȡ������ַ�����ͬ������ֵ���ࡣ
    	Excute excute=new Excute(doc);            //ִ��������࣬��װ����doc���д�ļ��ķ���
    	excute.run();							//���ö�д�ĺ�����
    }
	/* ���ڵ�Ԫ����
	 * public static Set<String> SortSet(Set<String> set){
		 Set<String> sortSet = new TreeSet<String>(new MapKeyComparator());
		 sortSet.addAll(set);
		 return sortSet;
	}
	public static  Vector<String> getFiles(String date,String source){
		Vector<String> vec =new Vector<>();
		String basePath=source;
		String[] list=new File(basePath).list();
		Vector<String> dates=new Vector<>();
		for(int i=0;i<list.length;i++) dates.add(list[i]);
		dates.add(date+".log.txt");
		Collections.sort(dates);
		int index=dates.lastIndexOf(date+".log.txt");
		if(index==dates.size() - 1&&!(dates.get(index).equals(dates.get(index -1)))) {
			return vec;
		}
		for(int i=0;i<dates.lastIndexOf(date+".log.txt");i++) {
			vec.add(source+dates.get(i));
		}
		return vec;
	}*/

}
/*���������ַ������飬ͨ����ͬ�ĺ����ӿڷ��ز�ͬ�Ĳ���ֵ�����治ͬ������������*/
class DocFormatter{
	private String[] cmd;
	private int logPathInd;
	private int outPathInd;
	private int dateInd;
	private int typeInd;
	private int provinceInd;
	private Vector<String> pros;
	DocFormatter(String[] arg){
		pros=new Vector<>();
		init_pro(pros);
		cmd=arg;
        exchange();
	}
	/*��ȡ-log��-type���ַ��������е�����λ��*/
	public void exchange() {           
		dateInd=-1;
		typeInd=-1;
		provinceInd=-1;
		for(int i=0;i<cmd.length;i++) {
			if(cmd[i].equals("-log"))  logPathInd=++i;
			if(cmd[i].equals("-out"))  outPathInd=++i;
			if(cmd[i].equals("-date"))  dateInd=++i;
			if(cmd[i].equals("-province"))  provinceInd=++i;
			if(cmd[i].equals("-type"))  typeInd=++i;
		}
	}
	private void init_pro(Vector<String> vec) {                      
		String[] pros= {"����","���","�Ϻ�","����","�ӱ�","ɽ��","����","����","������"
				,"����","�㽭","����","����","����","ɽ��","����","����","����","�㶫"
				,"����","�Ĵ�","����","����","����","����","�ຣ","̨��","���ɹ�","����"
				,"����","����","�½�","���","����","ȫ��"};
		for(int i=0;i<35;i++) {
			vec.add(pros[i]);
		}
	}
	/*��ȡlog�ļ�λ��*/
	public String getlogPath() {							
		return cmd[logPathInd];
	}
	/*��ȡ����ļ�λ��*/
	public String getoutPath() {						
		return cmd[outPathInd];
	}
	/*��ȡ����*/
	public String getDate() {               
		if(dateInd==-1) return "Latest";
		return cmd[dateInd];
	}
	/*��ȡ-type�Ĳ���ֵ*/
	public Vector<String> getType() {						
		Vector<String> type= new Vector<String>();
		Vector<String> vec =new Vector<>();
		vec.add("-log");vec.add("-out");
		vec.add("-date");vec.add("-province");
		if(typeInd!=-1) {
			int i=typeInd;
			while( i<cmd.length &&(!vec.contains(cmd[i]))) {	
				type.add(cmd[i]);		
				i++;
			}	
		}
		return type;
	}
	/*��ȡ-province�Ĳ���ֵ,������ʡ�ݵ������Ƿ�Ϸ�*/
	public Vector<String> getPro() {				
		Vector<String> vec= new Vector<String>();
		Vector<String> vecs =new Vector<>();
		vecs.add("-log");vecs.add("-out");
		vecs.add("-date");vecs.add("-type");
		if(provinceInd!=-1) {
			int i=provinceInd;
			while( i<cmd.length &&(!vecs.contains(cmd[i]))) {	
				if(!pros.contains(cmd[i]))  {
					System.out.println("ʡ���������ΪĬ�����!");
					vec.clear();
					return vec;
				}
				vec.add(cmd[i]);	
				i++;
			}	
		}
		return vec;
	}	
}
/*�������ʡ�ݵ�������Ϣ������DocFormatter�࣬��װִ�ж�д�ļ���������صĵ��ú�����*/
 class Excute{
	/*ʹ��map����������Ϣ��keΪʡ�����ƣ�valueΪStatus��¼����*/
	private Map<String,Status> map;						
	private DocFormatter Formatter;
	private String[] tyChecked;
	private boolean TyChecked;
	private boolean ProChecked;
	private Status Total;
	private Vector<String> pro;
	public Excute(DocFormatter d) {
		Formatter =d;
		ProChecked=false;
		TyChecked=false;
		tyChecked= new String[]{"","","",""};
		Total =new Status("ȫ��");		
		pro=new Vector<>();
		map=new HashMap<String,Status>();
		map.put("ȫ��",Total);
	}
	public void run() {                                    
		for(int i=0;i<Formatter.getType().size();i++) {                   //��-type������ʼ�������ʽ��������-type�����ĺϷ���
			TyChecked=true;
			if(Formatter.getType().get(i).equals("ip")) tyChecked[i]="��Ⱦ����";
			else if(Formatter.getType().get(i).equals("sp")) tyChecked[i]="���ƻ���";
			else if(Formatter.getType().get(i).equals("dead")) tyChecked[i]="����";
			else if(Formatter.getType().get(i).equals("cure")) tyChecked[i]="����";
			else {
				System.out.println("����"+Formatter.getType().get(i));
				System.out.println("-type��������������ip��sp��dead����cure��");
				return ;
			}
		
		}
		for(int i=0;i<Formatter.getPro().size();i++) {            
			ProChecked=true;
			pro.add(Formatter.getPro().get(i));
			
		}
		try{
			String date=Formatter.getDate();                                     //��ȡ�ļ�
			String source=Formatter.getlogPath();
			Vector<String> vec =getFiles(date,source);							//�������ڷ���Ҫ�򿪵��ļ�
			if(vec.size()<=0) { 
					System.out.println("���ļ������������Ƿ���ȷ��");
					return ;
				}
			for(int i=0;i<vec.size();i++) {
				openFile(vec.get(i));
			}
			
		}
		catch(IOException e) {
			System.out.println("���ļ������������Ƿ���ȷ��");
			return ;
		}
		try {
			String dest=Formatter.getoutPath();
			WriteFile(dest);                                       //д���ļ�
		}
		catch(IOException e) {
			System.out.println("д���ļ��������������Ƿ���ȷ��");
		}
	}
	private String openFile(String path) throws IOException  {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
		String line=null;
		while((line=br.readLine())!=null) {								//���ж�ȡ�ļ�
			String[] arr = line.split(" "); 							// ��  �ָ��ļ���ÿһ�У���������arr������
				if(!map.containsKey(arr[0]) ) {
					map.put(arr[0],new Status(arr[0]));					//����ÿ�п�ͷ��ʡ�ݴ���Status�ಢ����map��
				}
				Status s=map.get(arr[0]);								//��ȡarr������Ƿ�ƥ�䡰������������������������޸�Status��Ӧ����
				if(arr[1].equals("����")) {
					if(arr[2].equals("��Ⱦ����"))  addIP(s,arr[3]);
					else  addSP(s,arr[3]);
				}
				else if(arr[1].equals("����")) {
					addDead(s,arr[2]);
				}
				else if(arr[1].equals("����")) {
					addCure(s,arr[2]);
				}
				else if(arr[1].equals("�ų�")) {
					delSp(s,arr[3]);
				}
				else if(arr[1].equals("���ƻ���")) {
					if(arr[2].equals("ȷ���Ⱦ"))  spToip(s,arr[3]);
					else {
						if(!map.containsKey(arr[3]) ) {
							map.put(arr[3],new Status(arr[3]));
						}
						Status t=map.get(arr[3]);
						spRunTo(s,t,arr[4]);
					} 
				}
				else if(arr[1].equals("��Ⱦ����")){   
					if(!map.containsKey(arr[3]) ) {
						map.put(arr[3],new Status(arr[3]));
					}
					Status t=map.get(arr[3]);
					ipRunTo(s,t,arr[4]);    }
				else {}
			
		}
		MakeSumUp();
		br.close();					//�ر��ļ�
        return "";
	};
	/*��mapд���ļ�,����docformatter������д��ָ���ļ���*/
	private void WriteFile(String dest) throws IOException{			
		OutputStream os = new FileOutputStream(dest);
		PrintWriter pw=new PrintWriter(os);
		if(ProChecked) {                        			  //����Ƿ���ָ�����
			for(int i=0;i<pro.size();i++) {        			 //����ȱ©
				String s=pro.get(i);
				if(!map.containsKey(s))
					map.put(s, new Status(s));
			}
		}
		Set<String> set=SortSet(map.keySet());
		for(String key : set){
			Status status=map.get(key);
			if(key.equals("//")) continue;
			if(ProChecked) {                        			//����Ƿ���ָ�����
				if(!pro.contains(key)) continue;        		 //��ָ����ݣ�continue
			}
			
			if(TyChecked) {
				pw.print(key);
				for(int i=0;i<4;i++) {
					if(tyChecked[i].equals("��Ⱦ����")) pw.print(" ��Ⱦ����"+status.ip +"��");
					else if(tyChecked[i].equals("���ƻ���"))   pw.print(" ���ƻ���"+status.sp +"��");
					else if(tyChecked[i].equals("����"))   pw.print(" ����"+status.cure +"��");
					else if(tyChecked[i].equals("����"))   pw.print(" ����"+status.dead +"��");
					else 					{}
					
				};
				   
			    pw.print("\n");
			}
			else
		        pw.print(key +" ��Ⱦ����"+status.ip+"�� ���ƻ���"+status.sp+
		 		   "�� ����"+status.cure+"�� ����"+status.dead+"�� \n");
		}
		pw.close();
	}
	private Vector<String> getFiles(String date,String source){
		Vector<String> vec =new Vector<>();
		String basePath=source;
		String[] list=new File(basePath).list();
		Vector<String> dates=new Vector<>();
		for(int i=0;i<list.length;i++) dates.add(list[i]);
		dates.add(date+".log.txt");
		Collections.sort(dates);
		int index=dates.lastIndexOf(date+".log.txt");
		if(index==dates.size() - 1&&!(dates.get(index).equals(dates.get(index -1)))) {
			return vec;
		}
		for(int i=0;i<dates.lastIndexOf(date+".log.txt");i++) {
			vec.add(source+dates.get(i));
		}
		return vec;
	}

		
	private void addIP(Status s,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		s.ip+=num;
	}
	private void addSP(Status s,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		s.sp+=num;
	}
	private void ipRunTo(Status from,Status to,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		from.ip-=num;
		to.ip+=num;
	}
	private void spRunTo(Status from,Status to,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		from.sp-=num;
		to.sp+=num;
	}
	private void addDead(Status s,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		s.dead+=num;
		s.ip-=num;
	}
	private void addCure(Status s,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		s.cure+=num;
		s.ip-=num;
	}
	private void spToip(Status s,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		s.ip+=num;
		s.sp-=num;
	}
	private void delSp(Status s,String n) {
		int num=Integer.parseInt(n.substring(0, n.length()-1));
		s.sp-=num;
	}
	private void add(Status s,Status t) {
		s.ip+=t.ip;
		s.sp+=t.sp;
		s.dead+=t.dead;
		s.cure+=t.cure;
	}
	private void MakeSumUp() {				//ͳ��ȫ��
		map.get("ȫ��").cure=0;
		map.get("ȫ��").ip=0;
		map.get("ȫ��").sp=0;
		map.get("ȫ��").dead=0;
		
		for(String key : map.keySet()) {
			if(key.equals("ȫ��")) continue;
				add(Total,map.get(key));
		}
	}
	public Set<String> SortSet(Set<String> set){
		 Set<String> sortSet = new TreeSet<String>(new MapKeyComparator());
		 sortSet.addAll(set);
		 return sortSet;
	}
	public static Map<String,Status> sortMapByKey(Map<String,Status> map) {
			if (map == null || map.isEmpty()) {
					return null;
				}
			
			Map<String, Status> sortMap = new TreeMap<String, Status>(
			new MapKeyComparator());
			sortMap.putAll(map);
			return sortMap;
			}
	/*��װip��sp��cure��dead��ʡ������*/
	class Status{					
		private String pro;
		private int ip;
		private int sp;
		private int cure;
		private int dead;
		public Status(String s) {
			pro=s;
			ip=sp=cure=dead=0;
		}
	}
}
/*�Ƚ�������ƴ��˳�򷵻أ���ȫ����Ϊ������ֵ*/
class MapKeyComparator implements Comparator<String>{
		public int compare(String str1, String str2) {
			if(str1.equals("ȫ��")) return -99;
			if(str2.equals("ȫ��")) return 99;	
			Collator instance = Collator.getInstance(Locale.CHINA);
			return instance.compare(str1,str2);
        
          }

} 