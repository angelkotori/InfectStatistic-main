## 代码风格

1.缩进：缩进采用4个空格，不使用tab字符
> 若使用tab进行缩进需将tab设置为4个空格

2.变量命名：
* 不能以下划线或美元符号开始，也不能以下划线或美元符号结束。
* 代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式。
* 成员变量、局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式。

3.每行最多字符数：单行字符数限制不超过100个，超出需要换行，换行时遵循如下原则：
* 第二行相对第一行缩进8个空格，从第三行开始，不再继续缩进。
* 运算符与下文一起换行。
* 方法调用的点符号与下文一起换行。
* 在多个参数超长，逗号后进行换行。
* 在括号前不要换行。

4.方法最大行数：规模尽量限制在 100 行以内。
> 不包括注释和空行。

5.方法、类命名：
* 方法名、参数名统一使用lowerCamelCase风格，必须遵从驼峰形式。
* 类命名统一采用UpperCamelCase风格，必须遵从驼峰形式。
* 方法名应反映方法执行什么操作。

6.常量：
* 常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚。

7.空行规则：相对独立的程序块之间必须加空行。如下情况需加空行：
* 方法之间需加空行。
* 每个类声明之后应该加入空格同其他代码分开。
* 用空行将代码按照逻辑片断划分。

8.注释规则
* 单行注释采用 //内容 形式，多行注释采用 /* 内容 */ 形式。
* 注释应该和代码同时更新：注释应该和代码同时更新，不再有用的注释要删除。
* 注释的代码段需加上说明注释。

9.操作符前后空格
* 左括号和后一个字符之间不出现空格；同样，右括号和前一个字符之间也不出现空格。
* if/for/while/switch/do等保留字与左右括号之间都必须加空格。
* 方法参数在定义和传入时，多个参数逗号后边必须加空格。
* 任何运算符左右必须加一个空格。
* 一元操作符如“ !”、“ ~”、“ ++”、“ --”、“ &”（ 地址运算符） 等前后不加空格。像“［ ］”、“ .”、“ ->” 这类操作符前后不加空格。

10.其他规则
* 中括号是数组类型的一部分，数组定义如下：String[] args;
* if、for、do、while、case、switch、default等语句自占一行，且if、for、do、while等语句的执行语句部分无论多少都要加括号{}。
* 在一个switch块内，每个case要么通过break/return等来终止，要么注释说明程序将继续执行到哪一个case为止；在一个switch块内，都必须包含一个default语句并且放在最后，即使它什么代码也没有。