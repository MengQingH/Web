# myBatis接口绑定方案及多参数传递
1. 作用：实现创建一个接口后把mapper.xml由myBatis生成接口的实现类，通过调用接口对象就可以获取mapper.xml中编写的sql语句
2. myBatis框架和其他框架整合时使用的就是这个方案
```java
//下面使用到的表和对应的实体类
class Flower {
    private int id;
    private String name;
    private double price;
    private String production;
}
```

## 步骤：
1. 创建一个接口，要求接口的
    * 包名和接口名要与mapper文件中的namespace属性的值相同
    * 接口中的方法需要和mapper文件的id相同
2. 在myBatis.xml文件中使用<.package>标签进行扫描接口和mapper.xml

## 代码步骤：
1. 新建一个mapper映射文件，映射文件中的namespace属性使用该文件的全限定路径（包名+文件名）
    ```xml
    <!-- 新建FlowerMapper.xml文件 -->
    <mapper namespace="mapper.FlowerMapper">
        <select id="selAll" resultType="pojo.Flower">
            select * FROM  flower
        </select>
    </mapper>
    ```
2. 在mapper文件的路径下新建一个接口，接口名与mapper文件相同，接口中的方法与mapper文件中注册的方法的id相同
    ```java
    //在上面的路径下新建一个接口，接口中的方法与mapper文件中的定义的sql标签的id属性相同
    public interface FlowerMapper {
        List<Flower> selAll();
    }
    ```
3. 在全局配置文件中使用mappers标签中的package子标签注册mapper映射文件所在的文件夹
    ```xml
    <!-- 全局文件中注册该路径 -->
    <mappers>
        <package name="mapper"/>
    </mappers>
    ```
4. 使用接口的动态代理设计模式给接口实例化并调用相应的方法
    ```java
    InputStream is = Resources.getResourceAsStream("myBatis.xml");
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
    SqlSession session = factory.openSession();

    //使用动态代理模式新建接口的实现类
    FlowerMapper mapper = session.getMapper(FlowerMapper.class);
    //调用接口中的方法
    List<Flower> list = mapper.selAll();
    for (Flower flower:
            list) {
        System.out.println(flower);
    }
    ```

## 实现多参数传递
当sql语句需要多个参数时，可以用接口绑定的方式实现。
1. 在声明sql语句时，如果需要多个参数，不需要parameterType属性。
    * #{}中使用参数次序0 1 2或者param1 param2
    * #{}中使用参数名，可以传入一个map或对象，但如果参数传入基本类型还想使用属性名的方式就可以在参数中使用注解（原理：把参数转为mapper）
    ```xml
    <!-- 新建FlowerMapper.xml文件 -->
    <mapper namespace="mapper.FlowerMapper">
        <!-- 使用#{}的第一种方式 -->
        <select id="sel" resultType="pojo.Flower">
            select * FROM  flower where id=#{0} and name=#{1}
        </select>
        <!-- 使用#{}的第二种方式，接口中参数的定义方式看2 -->
        <select id="sel" resultType="pojo.Flower">
            select * FROM  flower where id=#{id} and name=#{name}
        </select>
    </mapper>
    ```
2. 然后在相应的接口中定义相应的方法
    ```java
    //在上面的路径下新建一个接口，接口中的方法与mapper文件中的定义的sql标签的id属性相同
    public interface FlowerMapper {
        //第一种方法
        List<Flower> sel(int i,String s);
        //第二种方法
        /**
        *使用时需要使用@Param对参数进行注解，注解的名称为在mapper文件中定义的sql语句中传入参数的名称
        *原理：使用该方法时，myBatis会把参数转为map，其中@Param("")中的内容转为key，参数内容就是map的value
        */
        List<Flower> sel(@Paramint("id") int i, @Param("name") String s);
    }
    ```