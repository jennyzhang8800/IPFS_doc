### 环境搭建

操作系统：

windows8 64位

需要安装的有：
1. JDK
2. SVN
3. proxifier
4. maven
5. IntelliJ IDEA

### 1. JDK安装

1. 双击安装``jdk-7u67-windows-x64.exe``

2. 修改环境变量

### 2. SVN安装

1. 双击安装``TortoiseSVN_1.9.5.27581_x64.msi``
2. 输入```svn://XXX.XXX.XXX.XX/```

### 3. 安装proxifier

1. 双击安装``ProxifierSetup.exe``
2. 输入注册码

### 4.配置maven

在``apache-maven-3.3.9-bin\apache-maven-3.3.9\conf\settings.xml``中添加
```
<localRepository>E:/repository</localRepository>
```
E:/repository是repository的路径

### 5. 安装IntelliJ IDEA

1. 双击安装``ideaIU-2017.1.4.exe``
2. 设置maven
file-->settings-->maven
```
Maven home directory:选择maven所在目录（``E:/apache-maven-3.3.9-bin/apache-maven-3.3.9``）
User settings file：选择settings.xml所在目录 （``E:\apache-maven-3.3.9-bin\apache-maven-3.3.9\conf\settings.xml``）
```
3.设置jdk
file-->Project Settins
```
Project SDK: 选择java jdk所在目录 （C:\Program Files\Java\jdk1.7.0_67）
```
4.从SVN导入文件
```
VCS-->Browse VCS Repository-->Browse Subversion Repository
在下方出现的SVN Repositories框中，点+号，然后输入SVN地址。即可看到SVN目录。
找到其中一个项目。选中，点右键-->checkout
```
