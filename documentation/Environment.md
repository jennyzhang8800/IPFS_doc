## 环境搭建



**目录**
* [0. 系统](#env)
* [1. JDK安装](#jdk)
* [2.配置maven](#maven)
* [3. SVN安装](#svn)
* [4. 安装proxifier](#proxi)
* [5. 安装IntelliJ IDEA](#idea)
<hr/>

<h2 id='env'>0. 系统</h2>

操作系统：

windows8 64位

需要安装的有：
1. JDK
2. maven
3. SVN
4. proxifier
5. IntelliJ IDEA

<h2 id='jdk'>1. JDK安装</h2>

1. 双击安装``jdk-7u67-windows-x64.exe``

2. 修改环境变量

控制面板-->系统和安全-->系统--> 高级系统设置-->环境变量-->系统变量

（1）新建
```
变量名：classpath
变量值：.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar 
```
```
变量名：JAVA_HOME
变量值：C:\Program Files\Java\jdk1.7.0_67
```
（2）然后，在path添加：
```
;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin
```
3. 检查jdk是否配置成功。在运行窗口输入：
```
java -version
```
可以看到版本信息，说明配置成功

<h2 id='maven'>2.配置maven</h2>

在``apache-maven-3.3.9-bin\apache-maven-3.3.9\conf\settings.xml``中

（1）找到``localRepository``标签，可以看到下面的内容（原来这部分是被注释的）：
```
 <!-- localRepository
   | The path to the local repository maven will use to store artifacts.
   |
   | Default: ${user.home}/.m2/repository
  <localRepository>/path/to/local/repo</localRepository>
  -->
```
在这段代码后添加
```
 <localRepository>F:\m2\repository</localRepository>
```
F:\m2\repository是repository的路径

（2）找到``mirrors`` ，添加下面的内容（ 使用阿里云镜像）
```
 <mirrors>
    <mirror>  
      <id>alimaven</id>  
      <name>aliyun maven</name>  
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
      <mirrorOf>central</mirrorOf>          
    </mirror>
  </mirrors>
```
（3）把修改后的settings.xml复制到``C:\Users\zhangyanni\.m2`` 目录下。（zhangyanni对应你的电脑用户名）

（4）修改环境变量：

在path中添加：
```
;E:\apache-maven-3.3.9-bin\apache-maven-3.3.9\bin
```
（5）检查maven是否配置成功。在运行窗口输入：
```
mvn -v
```
可以看到下面的版本信息，能看到就说明配置成功
```
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-11T00:41:4
7+08:00)
Maven home: E:\apache-maven-3.3.9-bin\apache-maven-3.3.9
Java version: 1.7.0_67, vendor: Oracle Corporation
Java home: C:\Program Files\Java\jdk1.7.0_67\jre
Default locale: zh_CN, platform encoding: GBK
OS name: "windows 8.1", version: "6.3", arch: "amd64", family: "windows"
```
<h2 id='svn'>3. SVN安装</h2>

1. 双击安装``TortoiseSVN_1.9.5.27581_x64.msi``
2. 修改环境变量

在path中添加：
```
;E:\SVN\bin
```
3. 启动SVN，输入```svn://XXX.XXX.XXX.XX/```

<h2 id='proxi'>4. 安装proxifier</h2>

1. 双击安装``ProxifierSetup.exe``
2. 输入注册码



<h2 id='idea'>5. 安装IntelliJ IDEA</h2>

1. 双击安装``ideaIU-2017.1.4.exe``
2. 激活：http://idea.iteblog.com/key.php
2. 设置maven
file-->settings-->maven
```

User settings file：选择settings.xml所在目录 （C:\Users\zhangyanni\.m2\settings.xml）
Local repository:选择repository所在目录（F:\m2\repository）
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
