 # 1������
+ ������������ӵڶ��п�ʼ����1��Tab����Ϊ4���ո�
+ ����������һ��д
+ ��������
```
public static int GetInteger(String s)  
	{
		String ss = s.substring(0,s.length()-1);
		int ans=Integer.valueOf(ss); 
		return ans;
	}
```

# 2����������
+ ������һ������Сд�����浥�ʴ�дͷ��ĸ��д����wordWord����
+ ��������
```
static String pathString = "./log";
```

# 3��ÿ������ַ���
+ ÿ������ַ���Ϊ40��
+ ��������
```
static String[] pString = {"ȫ��","����","����","����","����","����","�㶫","����","����","����",
    		"�ӱ�","����","������","����","����","����","����","����","����","���ɹ�","����",
    		"�ຣ","ɽ��","ɽ��","����","�Ϻ�","�Ĵ�","���","����","�½�","����","�㽭"
    };  
```

# 4�������������
+ ÿ�������������Ϊ100��
@ -16,6 +36,18 @@
# 5��������������
+ ��������ÿ�����ʵ�ͷ��ĸ��д
+ ����ÿ�����ʵ�ͷ��ĸ��д
+ ��������
```
class InfectStatistic 
```
```
public static int GetInteger(String s)  
	{
		String ss = s.substring(0,s.length()-1);
		int ans=Integer.valueOf(ss); 
		return ans;
	}
```

# 6������
+ ����Ϊȫ����д
@ -23,11 +55,63 @@
# 7�����й���
+ �����뺯�������1��
+ �������ݳ�Ա������Ҳ�ǿ���1��
+ ��������
```
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
```
```
//��ʼ��
	public InfectStatistic()   
	{
		for(int i = 0;i<32;i++)
			ip[i] =sp[i] = cure[i] = dead[i] = 0;
		
		pathString = "./log";
		
		outPathString = "./result/testOutput.txt";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
		dateString = dateFormat.format(date); //���ڲ���ֵ����ʼֵ��Ϊ��ǰ����
		
		//��key��valueһ��ŵ�hashmap��
	    map.put("-log",0);
	   	map.put("-out",1);
	   	map.put("-date",2);
	   	map.put("-type",3);
	   	map.put("-province",4);
		
	   	for(int i = 0;i<pString.length;i++)
		    provinceMap.put(pString[i],i);
	}
	
	//���ַ�������ȡ����
	public static int GetInteger(String s)  
	{
		String ss = s.substring(0,s.length() - 1);
		int ans = Integer.valueOf(ss); 
		return ans;
	}
```

# 8��ע�͹���
+ һ�㵥��ע��Ϊ//
+ �������ע�Ͳ������һ�д��������/*zxcvbnm*/
+ ��������
```
//���ַ�������ȡ����
	public static int GetInteger(String s)  
	{
		String ss = s.substring(0,s.length()-1);
		int ans=Integer.valueOf(ss); 
		return ans;
	}
```

# 9��������ǰ��ո�
+ ���в�����ǰ��һ�񣬺��һ��
+��������
```
String ss = s.substring(0,s.length()-1);
```
