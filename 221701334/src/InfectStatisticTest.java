import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class InfectStatisticTest {
	@Test
	void test1() throws FileNotFoundException, IOException { //�޲������
		String args[]= {"list",  "-log", "D:/log/", "-out", "D:/output1.txt"};
		InfectStatistic.main(args);
	}
	@Test
	void test2() throws FileNotFoundException, IOException { //ʡ�ݲ����޼�¼���
		String args[]= {"list",  "-log", "D:/log/", "-out", "D:/output2.txt","-province","����"};
		InfectStatistic.main(args);
	}
	@Test
	void test3() throws FileNotFoundException, IOException { //�����ڲ���
		String args[]= {"list",  "-log", "D:/log/", "-out", "D:/output3.txt","-date", "2020-01-21"};
		InfectStatistic.main(args);
	}
	@Test
	void test4() throws FileNotFoundException, IOException { //�����Ͳ���
		String args[]= {"list",  "-log", "D:/log/", "-out", "D:/output4.txt","-type","����"};
		InfectStatistic.main(args);
	}
	@Test
	void test5() throws FileNotFoundException, IOException { //�����ʡ�ݲ���
		String args[]= {"list",  "-log", "D:/log/", "-out", "D:/output5.txt","-province","����","ȫ��"};
		InfectStatistic.main(args);
	}
	@Test
	void test6() throws FileNotFoundException, IOException { //ͬʱ�����ں����Ͳ���
		String args[]= {"list", "-date", "2020-01-25", "-log", "D:/log/", "-out", "D:/output6.txt","-type","����"};
		InfectStatistic.main(args);
	}
	@Test
	void test7() throws FileNotFoundException, IOException { //ͬʱ�����ں�ʡ�ݲ���
		String args[]= {"list", "-date", "2020-01-22", "-log", "D:/log/", "-out", "D:/output7.txt","-province","����"};
		InfectStatistic.main(args);
	}
	@Test
	void test8() throws FileNotFoundException, IOException { //ͬʱ�����ڡ��޼�¼ʡ�ݡ����Ͳ���
		String args[]= {"list",  "-log", "D:/log/", "-out", "D:/output8.txt","-type","����","-province","����","����"};
		InfectStatistic.main(args);
	}
	@Test
	void test9() throws FileNotFoundException, IOException { //ͬʱ������ʡ�����Ͳ���
		String args[]= {"list", "-date", "2020-01-22", "-log", "D:/log/", "-out", "D:/output9.txt","-type","����","-province","����"};
		InfectStatistic.main(args);
	}
	@Test
	void test10() throws FileNotFoundException, IOException { //ͬʱ�����ڶ�����Ͷ��ʡ�ݲ���
		String args[]= {"list", "-date", "2020-01-22", "-log", "D:/log/", "-out", "D:/output10.txt","-type","��Ⱦ","����","-province","����","ȫ��"};
		InfectStatistic.main(args);
	}
}
