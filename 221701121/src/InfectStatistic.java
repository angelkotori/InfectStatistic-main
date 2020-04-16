/**
 * InfectStatistic
 * TODO
 *
 * @author shenmw
 * @version final
 * @since 2.18
 */

import java.util.List;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;


class InfectStatistic 
{
    public String date;    //����

    public String logPath;    //��־����·��

    public String outputPath;    //output�ļ�����·��

    public List<String> name;    //ʡ������    
    
    public String[] provinceName;//����ʡ������
    
    public Province country;    //ȫ�������   
    
    public String[] arg; //�����в���

    public boolean isRead;    //��ȡ������־�ļ�

    public boolean isOutput;    //���в���

    public String[] output;    //���˳��

    public boolean isOutputAll;    //���ȫ��������ʡ�����

    public List<String> provinces;    //-provinceҪ�����ʡ��

    public HashMap<String,Province> map;//ʡ������������������ӳ��

    public boolean isFinish;
        

    public InfectStatistic(String[] args)
    {        
    	arg = args;
        isRead = true;
        logPath = "G:\\example\\log\\";
        outputPath = "G:\\example\\result\\output3.txt";
        provinceName = new String[]{"����","����","����","����","����","�㶫",
            "����","����","����","�ӱ�","����","������","����","����",
            "����","����","����","����","���ɹ�","����","�ຣ","ɽ��","ɽ��","����","�Ϻ�",
            "�Ĵ�","���","����","�½�","����","�㽭"};
        name = new ArrayList<>();       
        map = new HashMap<String,Province>();
        country = new Province("ȫ��");
        isOutput = true;
        isOutputAll = true;
        provinces = new ArrayList<>();
        isFinish = false;
        output = new String[4];     

        for (int i = 0; i < provinceName.length; i ++ )
        {
            name.add(provinceName[i]);
            map.put(name.get(i), null);
        }
        
        for (int i = 0; i < 4; i ++ )
        {
            output[i] = "";
        }
        this.init();
    }
    
    //��ʼ��
    public void init()
    {
        for (int i = 0; i < arg.length; i ++ )
        {           
            switch (arg[i])
            {
                case "-date":
                    date = new String(arg[i+1]);
                    isRead = false; 
                    break;
                case "-log":
                    logPath = new String(arg[i+1]);
                    break;
                case "-out":
                    outputPath = new String(arg[i+1]);
                    break;
                case "-type":
                    isOutput = false;
                    dealType(i+1);
                    break;
                case "-province":
                    isOutputAll = false;
                    dealProvince(i+1);
                default:    
                    break;
            }           
        }
    }
    
    //-province����
    public void dealProvince(int index) 
    {
        while (index < arg.length)
        {
            switch (arg[index])
            {
                case "-date":
                    return;
                case "-log":
                    return;
                case "-out":
                    return;
                case "-type":
                    return;
                default:
                    provinces.add(arg[index]);
                    map.put(arg[index], null);
            }   
            index ++ ;
        }               
    }
    
    //-type����
    public void dealType(int index)
    {       
        for (int i = 0; index < arg.length && i < 4; i ++ )
        {
            switch (arg[index])
            {
                case "ip":
                    output[i] = arg[index];
                    break;
                case "sp":
                    output[i] = arg[index];
                    break;
                case "cure":
                    output[i] = arg[index];
                    break;
                case "dead":
                    output[i] = arg[index];
                    break;                
                default:
                    break;
            }
            index ++ ;
        }
    }
    
        
    //��־�ļ�
    public void deal() throws IOException
    {               
        String logDate;     
        String[] sArray;
        File file = new File(logPath);
        File[] tempList = file.listFiles();
        
        if (!isRead)
        {
            logDate = new String(tempList[tempList.length-1].getName());                      
            sArray = logDate.split("\\.");                    
            logDate = new String(sArray[0]);
            if ((logDate.compareTo(date)) < 0)
            {
                isFinish = true;
                System.out.println("���ڳ�����Χ!");
                return;
            }
        }

        for (int i = 0; i < tempList.length; i ++ )                   
        {                
            logDate = new String(tempList[i].getName());                      
            sArray = logDate.split("\\.");                    
            logDate = new String(sArray[0]);
                                                        
            if (isRead || (logDate.compareTo(date)) <= 0)                      
            {                 
                BufferedReader br = null;               
                String line = null;         
                br = new BufferedReader(new InputStreamReader(new FileInputStream(tempList[i].toString()), "UTF-8"));  
                
                while ((line = br.readLine()) != null)
                {
                    String[] array = line.split(" ");
                    dealOneLine(array);
                }          
                br.close();
            }                               
        }
        allStatistic();
    }
        
    private void dealOneLine(String[] array) 
    {
        if (array[0].equals("//"))
        {
            return;
        }

        if (map.get(array[0]) == null)
        {
            map.put(array[0], new Province(array[0]));  
        }
                        
        switch (array[1])
        {
            case "����":
                if (array[2].equals("���ƻ���"))
                {
                    map.get(array[0]).addSp(array[3]);
                }
                else
                {
                    map.get(array[0]).addIp(array[3]);
                }
                break;
            case "��Ⱦ����":
                map.get(array[0]).removeIp(array[4]);
                if (map.get(array[3]) == null)
                    map.put(array[3], new Province(array[3]));
                map.get(array[3]).addIp(array[4]);
                break;
            case "���ƻ���":
                if (array[2].equals("����"))
                {
                    map.get(array[0]).removeSp(array[4]);
                    if (map.get(array[3]) == null)
                        map.put(array[3], new Province(array[3]));
                    map.get(array[3]).addSp(array[4]);
                }
                else
                {
                    map.get(array[0]).addIp(array[3]);
                    map.get(array[0]).removeSp(array[3]);
                }
                break;
            case "����":
                map.get(array[0]).dead(array[2]);
                break;
            case "����":
                map.get(array[0]).cure(array[2]);
                break;
            case "�ų�":
                map.get(array[0]).removeSp(array[3]);
                break;
            default:
                break;
        }   
    }
    
    //��������ļ�
    public void output() throws IOException
    {
        if (isFinish)
        {
            return;
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));
        if (isOutputAll)
        {
            country.output(isOutput,output,bw);
            for (int i = 0; i < name.size(); i ++ )
            {   
                if (map.get(name.get(i)) != null)
                {
                    map.get(name.get(i)).output(isOutput, output, bw);
                }                
            }
        }
        else
        {
            if (provinces.contains("ȫ��"))
            {
                country.output(isOutput,output,bw);
            }
            for (int i = 0; i < provinceName.length; i ++ )
            { 
                if (provinces.contains(provinceName[i]))
                {
                    if (map.get(name.get(i)) == null)
                    {
                        map.put(provinceName[i], new Province(provinceName[i]));
                    }
                    map.get(provinceName[i]).output(isOutput, output, bw);
                }                
            }
        }
        bw.write("// ���ĵ�������ʵ���ݣ���������ʹ��");
        bw.close();
    }
    
    //ȫ�������
    public void allStatistic()
    {
        for (int i = 0; i < name.size(); i ++ )
        {
            if (map.get(name.get(i)) != null)
            {
                country.allAdd(map.get(name.get(i)));
            }            
        }
    }                    
    public static void main(String[] args) throws IOException 
    {
        if (args[0].equalsIgnoreCase("list"))
        {
            InfectStatistic l = new InfectStatistic(args);
            l.deal();   
            l.output();         
        }
        else
        {
            System.out.print("�������" + args[0]);
        }           
    }
}

//ʡ����
class Province
{ 
    private String name;    //ʡ������

    private int cure;    //��������

    private int dead;    //��������
    
    private int suspectedPatients;    //��������

    private int infectionPatients;    //��Ⱦ����  
    
    public Province(String n)
    {
        name = n;
    }
    //��Ⱦ��������
    public void addIp(String str)
    {
        str = str.substring(0, str.length()-1);
        infectionPatients += Integer.parseInt(str);
    }
    //��Ⱦ��������
    public void removeIp(String str)
    {
        str = str.substring(0, str.length()-1);
        infectionPatients -= Integer.parseInt(str);
    }
    //������������
    public void addSp(String str)
    {
        str = str.substring(0, str.length()-1);
        suspectedPatients += Integer.parseInt(str);
    }   
    //������������
    public void removeSp(String str)
    {
        str = str.substring(0, str.length()-1);
        suspectedPatients -= Integer.parseInt(str);
    }
    //������������
    public void cure(String str)
    {
        str = str.substring(0, str.length()-1);
        cure += Integer.parseInt(str);
        infectionPatients -= Integer.parseInt(str);
    }   
    //������������
    public void dead(String str)
    {
        str = str.substring(0, str.length()-1);
        dead += Integer.parseInt(str);
        infectionPatients -= Integer.parseInt(str);
    }
    //ʡ�����
    public void output(boolean isOutput, String[] output, BufferedWriter bw) throws IOException
    {
        if (isOutput)
        {
            bw.write(name + " ��Ⱦ���� " + infectionPatients + "�� "
                + "���ƻ��� " + suspectedPatients + "�� "
                + "���� " + cure + "�� "
                + "���� " + dead + "��");
            bw.newLine();
        }        
        else
        {
            bw.write(name);
            for (int i = 0; i < 4; i ++ )
            {
                switch (output[i])
                {
                    case "ip":                  
                        bw.write(" ��Ⱦ���� " + infectionPatients + "��");           
                        break;
                    case "sp":                  
                        bw.write(" ���ƻ��� " + suspectedPatients + "��");               
                        break;
                    case "cure":                    
                        bw.write(" ���� " + cure + "��");                  
                        break;
                    case "dead":                    
                        bw.write(" ���� " + dead + "��");          
                        break;
                    default:                
                        break;
                }
            }
            bw.newLine();
        }       
    }       
    //ȫ�������
    public void allAdd(Province p)
    {
        this.infectionPatients += p.infectionPatients;
        this.suspectedPatients += p.suspectedPatients;
        this.cure += p.cure;
        this.dead += p.dead;
    }
}