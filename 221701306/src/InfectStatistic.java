import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.lang.String;

/**
 * InfectStatistic TODO
 *
 * @author 221701306������
 * @version 1.0
 * @since 2020-02-12
 */
public class InfectStatistic {
    public static void main(String[] args) {
        // ���������ʽ���
        if (!ListCommand.checkInput(args)) {
            System.out.println("���������ʽ�����˳�����");
            System.exit(1);
        }
        // ��ʼ����������ݱ�
        ListCommand aListCommand = new ListCommand(args);
        Statistic aStatistic = new Statistic();
        // ��������Ƿ����Ҫ��
        aListCommand.checkCommand(aStatistic);
        // ��ʼ����־Ŀ¼
        LogFiles aLogFiles = new LogFiles(aListCommand.arguments[0].value);
        // ��ȡ��־
        aLogFiles.readFiles(aListCommand.arguments[2].value, aStatistic);
        // ���ͳ�ƽ��
        aStatistic.outPutFile(aListCommand.arguments[1].value, (TypeArgument) aListCommand.arguments[3],
            (ProvinceArgument) aListCommand.arguments[4]);
        // ���
        System.out.println("���гɹ���ͳ�����ݴ����" + aListCommand.arguments[1].value);
    }

}

class ListCommand {
    String name;
    BaseArgument[] arguments;

    ListCommand(String[] strs) {
        name = strs[0];
        arguments = new BaseArgument[5];
        arguments[0] = new LogArgument("-log");
        arguments[1] = new OutArgument("-out");
        arguments[2] = new DateArgument("-date");
        arguments[3] = new TypeArgument("-type");
        arguments[4] = new ProvinceArgument("-province");

        // ���������� ��ֵarguments
        for (int i = 1, k = 0; i < strs.length && k < 5; ++i) {
            int n = 1;
            // �����strs[1]һ���ǲ���
            ++i;
            while (!strs[i].startsWith("-")) {
                ++n;
                ++i;
                if (i == strs.length) {
                    break;
                }
            }
            --i;
            // ������ֵBaseArgument��string����
            String[] argStrings = new String[n];
            for (int j = 0; j < n; ++j) {
                argStrings[j] = strs[i - n + 1 + j];
            }

            // ��ֵBaseArgument[]
            switch (argStrings[0]) {
            case "-log":
                arguments[0] = new LogArgument(argStrings);
                break;
            case "-out":
                arguments[1] = new OutArgument(argStrings);
                break;
            case "-date":
                arguments[2] = new DateArgument(argStrings);
                break;
            case "-type":
                arguments[3] = new TypeArgument(argStrings);
                break;
            case "-province":
                arguments[4] = new ProvinceArgument(argStrings);
                break;
            default:
                ;
            }
        }
    }

    static boolean checkInput(String[] strs) {
        // �������list �򷵻�false
        if (!strs[0].equals("list")) {
            return false;
        }
        // list���������
        if (!strs[1].startsWith("-")) {
            return false;
        }
        // ��������ȷ������������������ �򷵻�false
        for (int i = 1; i < strs.length; ++i) {
            if (strs[i].startsWith("-")) {
                if (!(strs[i].equals("-log") || strs[i].equals("-out") || strs[i].equals("-type")
                    || strs[i].equals("-date") || strs[i].equals("-province"))) {
                    return false;
                }
            }
        }
        // ������������-log��-out �򷵻ش��� //��-logֻ����һ��
        for (int i = 1; i < strs.length; ++i) {
            if (strs[i].equals("-log")) {
                for (int j = i + 1; j < strs.length; ++j) {
                    // -log���ֶ��
                    if (strs[j].equals("-log")) {
                        return false;
                    }
                }
                break;
            }
            // û�ҵ�-log
            if (i == strs.length - 1) {
                return false;
            }
        }
        for (int i = 1; i < strs.length; ++i) {
            if (strs[i].equals("-out")) {
                for (int j = i + 1; j < strs.length; ++j) {
                    // -out���ֶ��
                    if (strs[j].equals("-log")) {
                        return false;
                    }
                }
                break;
            }
            // û�ҵ�-out
            if (i == strs.length - 1) {
                return false;
            }
        }
        // ÿ�ֲ���-date -type -province������һ��
        for (int i = 1; i < strs.length; ++i) {
            // ����-date
            if (strs[i].equals("-date")) {
                for (int j = i + 1; j < strs.length; ++j) {
                    // -date���ֶ��
                    if (strs[j].equals("-date")) {
                        return false;
                    }
                }
                break;
            }
        }
        for (int i = 1; i < strs.length; ++i) {
            // ����-type
            if (strs[i].equals("-type")) {
                for (int j = i + 1; j < strs.length; ++j) {
                    // -type���ֶ��
                    if (strs[j].equals("-type")) {
                        return false;
                    }
                }
                break;
            }
        }
        for (int i = 1; i < strs.length; ++i) {
            // ����-province
            if (strs[i].equals("-province")) {
                for (int j = i + 1; j < strs.length; ++j) {
                    // -province���ֶ��
                    if (strs[j].equals("-province")) {
                        return false;
                    }
                }
                break;
            }
        }
        return true;
    }

    void checkCommand(Statistic sta) {
        for (int i = 0; i < 4; ++i) {
            if (!arguments[i].checkError()) {
                System.exit(1);
            }
        }
        ((ProvinceArgument) arguments[4]).checkError(sta);
    }

}

abstract class BaseArgument {
    String name;
    String value;

    BaseArgument(String name) {
        this.name = name;
        value = "";
    }

    BaseArgument(String[] strs) {
        name = strs[0];
        value = "";
    }

    abstract boolean checkError();
}

class LogArgument extends BaseArgument {

    LogArgument(String name) {
        super(name);
    }

    LogArgument(String[] strs) {
        super(strs);
        if (strs.length > 2) {
            value = "*";
        }
        value += strs[1];
    }

    boolean checkError() {
        // -logֻ��һ������ֵ
        if (value.startsWith("*")) {
            System.out.println("-log����ֵ�����˳�����");
            return false;
        }
        // -log�Ĳ���ֵ��һ��Ŀ¼��·��
        File alog = new File(value);
        if (!alog.isDirectory()) {
            return false;
        }
        return true;
    }
}

class OutArgument extends BaseArgument {

    OutArgument(String name) {
        super(name);
    }

    OutArgument(String[] strs) {
        super(strs);
        if (strs.length > 2) {
            value = "*";
        }
        value += strs[1];
    }

    boolean checkError() {
        // -outֻ��һ������ֵ
        if (value.startsWith("*")) {
            System.out.println("-out����ֵ�����˳�����");
            return false;
        }
        // ������������ļ�·��û�к�׺ ���׺��Ϊ".txt" ���޸�ΪĬ��
        if (!value.endsWith(".txt")) {
            int x = value.lastIndexOf('.');
            if (x >= 0) {
                value = value.substring(0, x);
            }
            value += ".txt";
            System.out.println("����ļ���ʽ���󣬸�Ϊ" + value);
        }
        return true;
    }
}

class DateArgument extends BaseArgument {

    DateArgument(String name) {
        super(name);
        value = "lastdate";
    }

    DateArgument(String[] strs) {
        super(strs);
        if (strs.length > 2) {
            value = "*";
        }
        value += strs[1];
    }

    boolean checkError() {
        // -dateֻ��һ������ֵ
        if (value.startsWith("*")) {
            System.out.println("-date����ֵ�����˳�����");
            return false;
        }
        // ���ڸ�ʽ���,����ƽ�������YYYY-MM-DD��ʽ
        if (!value.matches(
            "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]"
                + "|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468]"
                + "[048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)   \r\n(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]"
                + "{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))"
                + "|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))"
                + "-02-29) \r\n")) {
            value = "lastdate";
            System.out.println("���ڸ�ʽӦΪYYYY-MM-DD����ƽ��Ĭ����������");
        }
        return true;
    }
}

class TypeArgument extends BaseArgument {

    String[] valueList;

    TypeArgument(String name) {
        super(name);
    }

    TypeArgument(String[] strs) {
        super(strs);
        value = "valuelist";
        int n = strs.length - 1;
        // ����ֵֻ��Ϊip,sp,cure,dead,�����Ǿͺ��Ըô�������
        for (int i = 1; i < strs.length; ++i) {
            if (!(strs[i].equals("ip") || strs[i].equals("sp") || strs[i].equals("cure") || strs[i].equals("dead"))) {
                strs[i] = "";
                --n;
                System.out.println("-type����ֵֻ��Ϊip,sp,cure,dead�����Ըô������ֵ");
            }
        }
        // ��ȫ���������,����Ϊ����ȱʡ
        if (n == 0) {
            valueList = new String[] { "ip", "sp", "cure", "dead" };
            return;
        }
        valueList = new String[n];
        for (int i = 0, j = 1; i < n; ++i) {
            while (strs[j].equals("")) {
                ++j;
            }
            valueList[i] = strs[j];
        }
    }

    boolean checkError() {
        if (value == "") {
            return true;
        }
        // ����ֵ�����ظ�,���ظ���ɾȥ
        int n = valueList.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (valueList[i].equals(valueList[j])) {
                    valueList[j] = valueList[--n];
                }
            }
        }
        String[] temp = new String[n];
        for (int i = 0; i < n; ++i) {
            temp[i] = valueList[i];
        }
        valueList = temp;
        return true;
    }
}

class ProvinceArgument extends BaseArgument {

    String[] valueList;

    ProvinceArgument(String name) {
        super(name);
    }

    ProvinceArgument(String[] strs) {
        super(strs);
        value = "valuelist";
        valueList = new String[strs.length - 1];
        for (int i = 1; i < strs.length; ++i) {
            valueList[i - 1] = strs[i];
        }
    }

    boolean checkError() {
        return true;
    }

    boolean checkError(Statistic sta) {
        if (value == "") {
            return true;
        }
        // ����ֵ�����ظ�,���ظ���ɾȥ
        int n = valueList.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (valueList[i].equals(valueList[j])) {
                    valueList[j] = valueList[--n];
                }
            }
        }
        String[] temp = new String[n];
        for (int i = 0; i < n; ++i) {
            temp[i] = valueList[i];
        }
        valueList = temp;

        // �����ʡ�ݴ���,�����������г�����
        for (int i = 0; i < valueList.length; ++i) {
            if (!sta.data.containsKey(valueList[i])) {
                value = "";
                valueList = new String[0];
                System.out.println("-province������������Ĭ���г�����ʡ������");
            }
        }
        return true;
    }
}

class LogFiles {
    String lastDate;
    TreeSet<File> files;

    LogFiles(String path) {
        File logFile = new File(path);
        File[] temp = logFile.listFiles();
        files = new TreeSet<File>(new Comparator<File>() {
            @Override
            // �ļ����������� ��д�����ڲ���Comparator��compare()
            public int compare(File f0, File f1) {
                String name0 = f0.getName();
                String name1 = f1.getName();
                // ������·��������αȽ�ʱ��ǰ��
                if (Integer.parseInt(name0.substring(0, 4)) < Integer.parseInt(name1.substring(0, 4))) {
                    return -1;
                }
                if (Integer.parseInt(name0.substring(0, 4)) > Integer.parseInt(name1.substring(0, 4))) {
                    return 1;
                }
                if (Integer.parseInt(name0.substring(5, 7)) < Integer.parseInt(name1.substring(5, 7))) {
                    return -1;
                }
                if (Integer.parseInt(name0.substring(5, 7)) > Integer.parseInt(name1.substring(5, 7))) {
                    return 1;
                }
                if (Integer.parseInt(name0.substring(8, 10)) < Integer.parseInt(name1.substring(8, 10))) {
                    return -1;
                }
                if (Integer.parseInt(name0.substring(8, 10)) > Integer.parseInt(name1.substring(8, 10))) {
                    return 1;
                }
                return 0;
            }

        });
        for (int i = 0; i < temp.length; ++i) {
            files.add(temp[i]);
        }

        lastDate = files.last().getName().substring(0, 10);
    }

    void readFiles(String date, Statistic sta) {
        // Ĭ����������
        if (date.equals("lastdate")) {
            for (File f : files) {
                LogFiles.statisFile(f, sta);
            }
            return;
        }
        // ��Ĭ������
        for (File f : files) {
            if (LogFiles.dateCompare(date, f.getName().substring(0, 10))) {
                LogFiles.statisFile(f, sta);
                continue;
            }
            break;
        }

        // ��ʡ����ͳ�Ƶ�ȫ��
        int[] all = { 0, 0, 0, 0 };
        for (String keytemp : sta.data.keySet()) {
            int[] valuetemp = sta.data.get(keytemp);
            for (int i = 0; i < 4; ++i) {
                all[i] += valuetemp[i];
            }
        }
        sta.data.put("ȫ��", all);
    }

    // ͳ��ĳ����־�ļ�������
    static void statisFile(File f, Statistic sta) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                // ����'/'��ͷ����
                if (line.startsWith("/")) {
                    continue;
                }
                LogFiles.statisLine(line, sta);
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // ������־�ļ��е�һ��
    static void statisLine(String line, Statistic sta) {
        String[] strs = line.split(" ");
        // ʡ�ݴ���
        if (!sta.data.containsKey(strs[0])) {
            return;
        }
        // 8����־��
        switch (strs[1]) {
        case "����":
            if (strs[2].equals("��Ⱦ����")) {
                sta.data.get(strs[0])[0] += Integer.parseInt(strs[3].replace("��", ""));
                break;
            }
            if (strs[2].equals("���ƻ���")) {
                sta.data.get(strs[0])[1] += Integer.parseInt(strs[3].replace("��", ""));
            }
            break;
        case "����":
            sta.data.get(strs[0])[0] -= Integer.parseInt(strs[2].replace("��", ""));
            sta.data.get(strs[0])[3] += Integer.parseInt(strs[2].replace("��", ""));
            break;
        case "����":
            sta.data.get(strs[0])[0] -= Integer.parseInt(strs[2].replace("��", ""));
            sta.data.get(strs[0])[2] += Integer.parseInt(strs[2].replace("��", ""));
            break;
        case "�ų�":
            sta.data.get(strs[0])[1] -= Integer.parseInt(strs[3].replace("��", ""));
            break;
        case "���ƻ���":
            if (strs[2].equals("����")) {
                sta.data.get(strs[0])[1] -= Integer.parseInt(strs[4].replace("��", ""));
                sta.data.get(strs[3])[1] += Integer.parseInt(strs[4].replace("��", ""));
                break;
            }
            if (strs[2].equals("ȷ���Ⱦ")) {
                sta.data.get(strs[0])[1] -= Integer.parseInt(strs[3].replace("��", ""));
                sta.data.get(strs[0])[0] += Integer.parseInt(strs[3].replace("��", ""));
            }
            break;
        case "��Ⱦ����":
            if (strs[2].equals("����")) {
                sta.data.get(strs[0])[0] -= Integer.parseInt(strs[4].replace("��", ""));
                sta.data.get(strs[3])[0] += Integer.parseInt(strs[4].replace("��", ""));
            }
            break;
        default:
            break;
        }
    }

    // �Ƚ�����YYYY-MM-DD�����ַ��� ��date0���ڵ���date1 �򷵻�true
    static boolean dateCompare(String date0, String date1) {
        // ������·��������αȽ�ʱ��ǰ��
        if (Integer.parseInt(date0.substring(0, 4)) < Integer.parseInt(date0.substring(0, 4))) {
            return false;
        }
        if (Integer.parseInt(date0.substring(0, 4)) > Integer.parseInt(date1.substring(0, 4))) {
            return true;
        }
        if (Integer.parseInt(date0.substring(5, 7)) < Integer.parseInt(date1.substring(5, 7))) {
            return false;
        }
        if (Integer.parseInt(date0.substring(5, 7)) > Integer.parseInt(date1.substring(5, 7))) {
            return true;
        }
        if (Integer.parseInt(date0.substring(8, 10)) < Integer.parseInt(date1.substring(8, 10))) {
            return false;
        }
        if (Integer.parseInt(date0.substring(8, 10)) > Integer.parseInt(date1.substring(8, 10))) {
            return true;
        }
        return true;
    }
}

class Statistic {
    Map<String, int[]> data;

    public Statistic() {
        data = new HashMap<String, int[]>();
        data.put("ȫ��", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("�㶫", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("�ӱ�", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("������", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("���ɹ�", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("�ຣ", new int[] { 0, 0, 0, 0 });
        data.put("ɽ��", new int[] { 0, 0, 0, 0 });
        data.put("ɽ��", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("�Ϻ�", new int[] { 0, 0, 0, 0 });
        data.put("�Ĵ�", new int[] { 0, 0, 0, 0 });
        data.put("���", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("�½�", new int[] { 0, 0, 0, 0 });
        data.put("����", new int[] { 0, 0, 0, 0 });
        data.put("�㽭", new int[] { 0, 0, 0, 0 });
    }

    void outPutFile(String path, TypeArgument types, ProvinceArgument provinces) {
        try {
            // ��out�ļ�
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            String outline;
            // ����type����ֵ ��boolean[]�ֱ��ʾ�Ƿ������������
            boolean[] typetemp = { false, false, false, false };
            if (types.value.equals("valuelist")) {
                for (String temp : types.valueList) {
                    switch (temp) {
                    case "ip":
                        typetemp[0] = true;
                        break;
                    case "sp":
                        typetemp[1] = true;
                        break;
                    case "cure":
                        typetemp[2] = true;
                        break;
                    case "dead":
                        typetemp[3] = true;
                        break;
                    default:
                        break;
                    }
                }
            }
            if (types.value.equals("")) {
                typetemp[0] = true;
                typetemp[1] = true;
                typetemp[2] = true;
                typetemp[3] = true;
            }
            // ���������ʡ������ ��������,����ʡ��ƴ������д���ļ�
            if (provinces.value.equals("")) {
                String[] provincesort = { "ȫ��", "����", "����", "����", "����", "����", "�㶫", "����", "����", "����", "�ӱ�", "����", "������",
                    "����", "����", "����", "����", "����", "����", "���ɹ�", "����", "�ຣ", "ɽ��", "ɽ��", "����", "�Ϻ�", "�Ĵ�", "���", "����",
                    "�½�", "����", "�㽭" };
                for (int i = 0; i < provincesort.length; ++i) {
                    String keytemp = provincesort[i];
                    int[] valuetemp = data.get(keytemp);
                    outline = keytemp;
                    if (typetemp[0]) {
                        outline = outline + " ��Ⱦ����" + valuetemp[0] + "��";
                    }
                    if (typetemp[1]) {
                        outline = outline + " ���ƻ���" + valuetemp[1] + "��";
                    }
                    if (typetemp[2]) {
                        outline = outline + " ����" + valuetemp[2] + "��";
                    }
                    if (typetemp[3]) {
                        outline = outline + " ����" + valuetemp[3] + "��";
                    }
                    bw.write(outline);
                    bw.newLine();
                }
            }
            // ��ֻ���-province����ֵʡ������
            if (provinces.value.equals("valuelist")) {
                for (String provincetemp : provinces.valueList) {
                    int[] valuetemp = data.get(provincetemp);
                    outline = provincetemp;
                    if (typetemp[0]) {
                        outline = outline + " ��Ⱦ����" + valuetemp[0] + "��";
                    }
                    if (typetemp[1]) {
                        outline = outline + " ���ƻ���" + valuetemp[1] + "��";
                    }
                    if (typetemp[2]) {
                        outline = outline + " ����" + valuetemp[2] + "��";
                    }
                    if (typetemp[3]) {
                        outline = outline + " ����" + valuetemp[3] + "��";
                    }
                    bw.write(outline);
                    bw.newLine();
                }
            }
            bw.write("// ���ĵ�������ʵ���ݣ���������ʹ��");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}