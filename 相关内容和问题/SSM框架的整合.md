1. 导入相关的jar包，包括MyBatis、myBatis的依赖、myBatis-Spring、Spring中需要使用的包、Spring依赖中的核心包

2. 编写数据库，并在项目中创建相应的实体类
3. 在web.xml中声明DispatcherServlet，并声明配置文件的路径和名称，以及该servlet匹配的url
4. 创建DispatcherServlet的配置文件，在配置文件中声明Spring中封装MyBatis需要实现的类
5. 使用注解声明myBatis的mapper接口
6. 编写相关服务和控制器的代码，并使用注解声明这些类