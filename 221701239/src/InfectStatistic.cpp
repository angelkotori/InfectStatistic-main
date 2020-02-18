/*
�ô��빦��:ʵ��ȫ���������������ͳ��
����:���� ѧ��:221701239
*/

#include<stdio.h>
#include<iostream>
#include<fstream>
#include<filesystem>
#include <string>
#include<string.h>
#include <io.h>  
#include <vector>  
#include<map>
#include <windows.h>
#include<sstream>

using namespace std;
map<string, string> m_single;      //�洢������ֻ��һ��ֵ��Ԫ�ص���Ϣ
map<string, vector<string>> m_many;   //�洢�����п����ж��ֵ��Ԫ�ص���Ϣ
									  //ÿ��ʡ����ȫ���ķ�����Ϣ��
map<string, vector<int>> m_province;  //�洢ÿ��ʡ�ķ��������Ϣ,(��Ⱦ����,���ƻ���,������,������)


									  //���ַ�תutf-8
string UTF8ToGB(const char* str)
{
	string result;
	WCHAR *strSrc;
	LPSTR szRes;

	//�����ʱ�����Ĵ�С
	int i = MultiByteToWideChar(CP_UTF8, 0, str, -1, NULL, 0);
	strSrc = new WCHAR[i + 1];
	MultiByteToWideChar(CP_UTF8, 0, str, -1, strSrc, i);

	//�����ʱ�����Ĵ�С
	i = WideCharToMultiByte(CP_ACP, 0, strSrc, -1, NULL, 0, NULL, NULL);
	szRes = new CHAR[i + 1];
	WideCharToMultiByte(CP_ACP, 0, strSrc, -1, szRes, i, NULL, NULL);

	result = szRes;
	delete[]strSrc;
	delete[]szRes;

	return result;
}


//��map�в���һ��pair
void insert_province(string province_name)
{
	int i;
	vector<int> list;
	for (i = 0; i < 4; i++)
		list.push_back(0);
	m_province.insert(make_pair(province_name, list));
}
//���ַ�������ȡ����
int draw_number(string temp)
{
	stringstream s1(temp);
	int itemp;
	s1 >> itemp;
	return itemp;
}
//�������������
void process_xin_zeng(fstream& file, string province_name)
{
	string temp;
	file >> temp;
	temp = UTF8ToGB(temp.c_str()).c_str();
	if (temp.compare("��Ⱦ����") == 0)
	{
		file >> temp;
		int num = draw_number(temp);
		(m_province["ȫ��"])[0] += num;
		(m_province[province_name])[0] += num;
	}
	else
	{
		file >> temp;
		int num = draw_number(temp);
		(m_province["ȫ��"])[1] += num;
		(m_province[province_name])[1] += num;
	}
}
//�������������
void process_zhi_yu(fstream& file, string province_name)
{
	string temp;
	file >> temp;
	int num = draw_number(temp);
	(m_province["ȫ��"])[2] += num;
	(m_province[province_name])[2] += num;
	(m_province["ȫ��"])[0] -= num;
	(m_province[province_name])[0] -= num;
}
//�������������
void process_pass_away(fstream& file, string province_name)
{
	string temp;
	file >> temp;
	int num = draw_number(temp);
	(m_province["ȫ��"])[3] += num;
	(m_province[province_name])[3] += num;
	(m_province["ȫ��"])[0] -= num;
	(m_province[province_name])[0] -= num;
}
//������������
void process_liu_ru(fstream& file, string province_name, string type_name)
{
	string liu_ru_province;
	file >> liu_ru_province;				//��������ʡ��
	liu_ru_province = UTF8ToGB(liu_ru_province.c_str()).c_str();
	string temp;
	file >> temp;
	int num = draw_number(temp);
	if (type_name.compare("��Ⱦ����") == 0)
	{
		(m_province[province_name])[0] -= num;
		(m_province[liu_ru_province])[0] += num;
	}
	else
	{
		(m_province[province_name])[1] -= num;
		(m_province[liu_ru_province])[1] += num;
	}
}
//����ȷ���Ⱦ�����
void process_que_zhen(fstream& file, string province_name)
{
	string temp;
	file >> temp;
	int num = draw_number(temp);
	(m_province["ȫ��"])[0] += num;
	(m_province["ȫ��"])[1] -= num;
	(m_province[province_name])[0] += num;
	(m_province[province_name])[1] -= num;
}
//�����ų������
void process_pai_chu(fstream& file, string province_name)
{
	string temp;
	file >> temp;
	file >> temp;
	int num = draw_number(temp);
	(m_province["ȫ��"])[1] -= num;
	(m_province[province_name])[1] -= num;

}
//���ַ�����ȡ�������ļ�
void process_file(vector<string> file_list)
{
	int num;   //�����õ�
			   //6�����,����(��Ⱦ����,���ƻ���),��Ⱦ����,���ƻ���(����,ȷ��),����,����,�ų�(���ƻ���).
	int i;
	insert_province("ȫ��");
	//���ȼ���ȫ�������
	fstream fei_yan_log(file_list[0]);
	string temp;
	string temp_past;   //���ڴ洢֮ǰһ�εĶ�ȡ
	string province_name;
	bool is_province = true; //�Ƿ����ʡ�ݵı�־λ
	while (!fei_yan_log.eof())
	{
		temp_past = temp;
		fei_yan_log >> temp;
		num = draw_number(temp);
		temp = UTF8ToGB(temp.c_str()).c_str();
		if (temp.substr(0, 1).compare("/") == 0)
			break;
		if (is_province)
		{
			if (m_province.find(temp) == m_province.end())
				insert_province(temp);
			is_province = false;
			province_name = temp;
		}
		if (temp.compare("����") == 0)
		{
			process_xin_zeng(fei_yan_log, province_name);
			is_province = true;
		}
		if (temp.compare("����") == 0)
		{
			process_pass_away(fei_yan_log, province_name);
			is_province = true;
		}
		if (temp.compare("����") == 0)
		{
			process_zhi_yu(fei_yan_log, province_name);
			is_province = true;
		}
		if (temp.compare("����") == 0)
		{
			process_liu_ru(fei_yan_log, province_name, temp_past);
			is_province = true;
		}
		if (temp.compare("ȷ���Ⱦ") == 0)
		{
			process_que_zhen(fei_yan_log, province_name);
			is_province = true;
		}
		if (temp.compare("�ų�") == 0)
		{
			process_pai_chu(fei_yan_log, province_name);
			is_province = true;
		}

	}



}


//�ú���ʵ���˶���������Ϣ�Ĵ���
void process_cmd(int num, char* cmd_i[])
{
	int i;
	string temp;   //��ת�ַ���
	for (i = 3; i < num; i++)
	{
		if (strcmp(cmd_i[i], "-province") == 0 || strcmp(cmd_i[i], "-type") == 0)
		{
			vector<string> list_temp;
			string m_name = cmd_i[i];//��¼�����в�������
			temp = cmd_i[i];
			while (temp.compare("-") != 0)
			{
				temp = cmd_i[++i];
				list_temp.push_back(temp);
				cout << cmd_i[i] << "\n";
				if (i >= num - 1)
					break;
				temp = temp.substr(0, 1);
			}
			m_many.insert(make_pair(m_name, list_temp));
		}
		else
		{
			string temp1, temp2;
			temp1 = cmd_i[i];
			temp2 = cmd_i[++i];
			m_single.insert(make_pair(temp1, temp2));
		}
	}
}

//�ú���ʵ���˽�path·���ļ����µ������ļ���ȡ��files����
void getFiles(const std::string & path, std::vector<std::string> & files)
{
	//�ļ����  
	long long hFile = 0;
	//�ļ���Ϣ��_finddata_t��Ҫio.hͷ�ļ�  
	struct _finddata_t fileinfo;
	std::string p;
	int i = 0;
	if ((hFile = _findfirst(p.assign(path).append("\\*").c_str(), &fileinfo)) != -1)
	{
		do
		{
			//�����Ŀ¼,����֮  
			//�������,�����б�  
			if ((fileinfo.attrib & _A_SUBDIR))
			{
				//if (strcmp(fileinfo.name, ".") != 0 && strcmp(fileinfo.name, "..") != 0)
				//getFiles(p.assign(path).append("\\").append(fileinfo.name), files);
			}
			else
			{
				files.push_back(p.assign(path).append("\\").append(fileinfo.name));
			}
		} while (_findnext(hFile, &fileinfo) == 0);
		_findclose(hFile);
	}
}

int main(int argc, char* argv[])
{


	process_cmd(argc, argv);
	int i;
	cout << argc;
	for (i = 0; i < argc; i++)
	{
		cout << argv[i] << "\n";      //�����в���
	}
	vector<string> file_list;
	getFiles("D:\\���״���\\InfectStatistic-main\\221701239\\log", file_list);
	for (i = 0; i < file_list.size(); i++)
	{
		//cout << file_list[i] << "\n" ;
	}
	process_file(file_list);
	system("pause");

}