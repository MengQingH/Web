1. new project选择Web Application，并配置好服务器(Application server)
2. 输入项目名，finished
3. 在WEB-INF目录下新建classes(存放编译后的class文件)和lib(存放第三方包)文件夹
4. 配置classes文件夹路径 File -> 选择 Project Structure -> 选择 Module -> 选择Paths -> 选择 “Use module compile output path” -> 将Output path和Test output path都选择刚刚创建的classes文件夹。
5. 配置lib文件夹路径 点击Paths旁边的Dependencies 点击右边的”+”号 -> 选择”Jars or Directories” -> 选择刚刚创建的lib文件夹 -> 然后一路OK就行了

6. 配置Tomcat服务器 点击Run -> 选择 Edit Configurations -> 击“+”号 -> 选择“Tomcat Server” -> 选择“Local” -> 输入Name → 点击Configure…选择本地的Tomcat安装目录


存疑
6. 在Project Structure中选择Artifacts -> 选择+号 -> 选择Web Application:Exploded -> 选择from Modules -> 选择该项目
7. 点击Run -> 选择 Edit Configurations -> 击“+”号 -> 选择“Tomcat Server” -> 选择“Local” -> 输入Name -> 点击configurations选择服务器路径 -> 点击deployment -> 点击+号 -> 选择Artifacts -> 右边application context中输入项目名 -> 点ok
