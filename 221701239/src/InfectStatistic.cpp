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
void process_file(string log_file)
{
	int num;   //�����õ�
			   //6�����,����(��Ⱦ����,���ƻ���),��Ⱦ����,���ƻ���(����,ȷ��),����,����,�ų�(���ƻ���).
	int i;
	//���ȼ���ȫ�������
	fstream fei_yan_log(log_file);
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

//��string����ת��Ϊint��������
vector<int> transform_date(string date)
{
	int i;
	vector<int> int_temp;
	string temp=date;
	int itemp;
	for (i = 0; i < date.length(); i++)
	{
		if (date[i] == '-')
		{
			stringstream s(temp);
			s >> itemp;
			int_temp.push_back(itemp);
			temp = date.substr(++i, date.length());
		}
	}
	stringstream s(temp);
	s >> itemp;
	int_temp.push_back(itemp);
	return int_temp;
	

}

//�Ƚ��������ڴ�С
bool compare_date(vector<int> date1, vector<int> date2)
{	
	if(date1[0]<date2[0])
		return false;
	if (date1[0] == date2[0] && date1[1] < date2[1])
		return false;
	if (date1[0] == date2[0] && date1[1] == date2[1] && date1[2] < date2[2])
		return false;
	return true;
}

//�õ�·�����ļ�����Ӧ������
vector<int> get_path_name_date(string file_path)
{
	int i;
	vector<int> file_date;
	for (i = file_path.length(); file_path[i] != '\\'; i--)
	{
		continue;
	}
	file_path = file_path.substr(++i, file_path.length()-8);
	file_date = transform_date(file_path);
	return file_date;
}

//����-date����ѡ��Ӧ�ô�����ļ�
void pick_log_file(vector<string> file_list)
{
	int i;
	bool process_sign = false;   //���ڴ���û������date���������
	string temp = "";
	if (m_single.find("date") != m_single.end())
		string temp = m_single["-date"];
	else
		process_sign = true;
	vector<int> time = transform_date(temp);
	int left, right;
	for (int i = 0; i < file_list.size(); i++)
	{
		vector<int> file_date = get_path_name_date(file_list[i]);
		if (compare_date(time, file_date)||process_sign)
			process_file(file_list[i]);
		else
			break;
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
	insert_province("ȫ��");
	getFiles(m_single["-log"], file_list);
	pick_log_file(file_list);
	for (i = 0; i < file_list.size(); i++)
	{
		//cout << file_list[i] << "\n" ;
	}
	system("pause");

}