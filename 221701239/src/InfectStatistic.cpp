/*
�ô��빦��:ʵ��ȫ���������������ͳ��
����:���� ѧ��:221701239
*/

#include<stdio.h>
#include<iostream>
#include<fstream>
#include<filesystem>
#include <string>  
#include <io.h>  
#include <vector>  

using namespace std;
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
		cout << file_list[i] << "\n";
	}
	system("pause");

}