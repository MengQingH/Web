## 获取SqlSession对象
要调用myBatis的方法，首先要创建一个SqlSessionFactory对象，调用该对象的openSession方法获取一个SqlSession对象，在调用相应的方法。
```java
//导入配置文件
InputStream is = Resources.getResourceAsStream("myBatis.xml");
//前面是工厂，以Factory结尾；后面是构建者，以Builder结尾
//实例化工厂使用的是构建者设计模式，意义：简化对象实例化过程
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
SqlSession session = factory.openSession();
```
```java
//下面使用到的表和对应的实体类
class Flower {
    private int id;
    private String name;
    private double price;
    private String production;
}
```

# 查询
## selectList(id)
返回值为一个List< resultType>，泛型为映射中resultType的属性值。适用于所有结果都需要遍历的情况。
```xml
<mapper namespace="a.b">
    <select id="selAll" resultType="pojo.Flower">
        select * FROM  flower
    </select>
</mapper>
```
```java
InputStream is = org.apache.ibatis.io.Resources.getResourceAsStream("MyBatis.xml");
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
SqlSession session = factory.openSession();

List<Flower> list = session.selectList("a.b.selAll");
for (Flower flower:
        list) {
    System.out.println(flower.toString());
}
// Flower{id=1, name='矮牵牛', price=2.5, production='南美阿根廷'}
// Flower{id=2, name='百日草', price=5.0, production='墨西哥'}
// Flower{id=3, name='半枝莲', price=4.3, production='巴西'}
// Flower{id=4, name='测试花卉', price=1.0, production='测试原产地'}
```


## selectOne(id)
返回值为一个Object对象，适用于返回值只是一个对象或者一行数据时的情况
```xml
<mapper namespace="a.b">
    <select id="selByID" resultType="int">
        select count(*) from flower where id=#{id}
    </select>
</mapper>
```
```java
int i = session.selectOne("a.b.selById");
System.out.println(i);//1
```


## selectMap(id,需要取出的属性值)
返回一个Map<key,resultType>，把数据库中的某些值作为map中的key，value值仍为resultType的属性值。适用于需要在查询结果中通过某列的值取到这行数据的情况。
```xml
<mapper namespace="a.b">
    <select id="selAll" resultType="pojo.Flower">
        select * FROM  flower
    </select>
</mapper>
```
```java
Map<Object,Flower> m = session.selectMap("a.b.selAll","id");
System.out.println(m);
/**
key为id的值，value值为resultType中的属性值
{
1=Flower{id=1, name='矮牵牛', price=2.5, production='南美阿根廷'}, 
2=Flower{id=2, name='百日草', price=5.0, production='墨西哥'}, 
3=Flower{id=3, name='半枝莲', price=4.3, production='巴西'}, 
4=Flower{id=4, name='测试花卉', price=1.0, production='测试原产地'}
}
*/
```

# 增删改
在mapper映射中定义的sql语句在执行时调用的是executeUpdate()函数，返回一个int类型的值，表示受影响的行数，所以SqlSession中增删改对应的函数都只有一个，即insert()、delete()、update()。传入的参数有两种情况：
1. (String id)：当sql语句中没有传入参数时，调用该方法传入调用方法的id
2. (String id,Object Param)：当sql中需要传入参数时调用


## 有参时的函数调用
如果在mapper映射的sql语句中需要传入参数，调用时就可以使用selectOne()和selectList()的第二个参数传入参数值，在selectMap()中的第三个参数传入参数值

## 事务相关
myBatis中默认关闭了JDBC的自动提交功能，每一个SqlSession默认都是不自动提交事务
1. 可以使用session.commit()提交事务
2. 也可以在调用openSession()方法时传入true参数设置自动提交
3. 回滚：在创建SqlSession对象时，myBatis会自动创建一个相应的事务对象，该session实行的所有sql语句都包含在该事务对象中。如果要回滚事务，可以调用SqlSession对象的rollback()方法
