jsp全程为Java Servlet Pages，jsp可以在页面中嵌套Java代码，为用户提供动态数据。
* jsp是一个综合技术，jsp=html+css+js+java+jsp标签
* jsp运行在服务端
* 主要用来做动态界面


## 运行原理
1. 第一次访问jsp文件时，web服务器会把.jsp文件翻译成一个.java文件，再将其编译成一个.class文件，然后再把.class文件加载到内存。
2. 第二次访问时就直接访问内存中的实例，因此jsp也是单例
3. 某个文件被修改后就相当于重新访问该jsp文件

## jsp中的HTML和Java代码
* html代码：jsp编译成.java文件后被jsp的内置对象out逐行输出。
* Java代码：被翻译成.java文件后jsp中的Java代码被放入一个名为jspServlet的方法中。其中多个<% %>相当于一个大的<% %>,在<% %>定义的变量相当于局部变量。

