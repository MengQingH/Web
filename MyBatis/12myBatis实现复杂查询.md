## 多表查询：
实现方式：
1. 业务装配：对两个表编写单表查询语句，在service中把查询的两个结果进行关联：在一个实体类中定义另一个实体类的对象来传入另一个表的信息，使用时在service中把内部对象添加到外部对象
2. 使用AutoMapping特性，在实现两表联合查询时通过别名完成映射
3. 使用MyBatis的resultMap进行实现

多表查询时，类中包含另一个类的对象的分类：
1. 单个对象：类中只包含另一个类的一个对象
2. 集合对象：类中包含一个类的对象list


# 使用resultMap标签实现
在mapper中，用来控制sql查询结果与实体类之间的映射关系
## 使用resultMap标签实现关联单个对象（n+1方式）
N+1查询方式：先查询出某个表的全部信息，根据这个表的信息，再查询另一个表的信息。使用方式：
1. 在实体类中创建一个其他类的对象（在Student对象中创建一个Teacher类的对象）
```java
class Student{
    int id;
    int tid;
    private Teacher teacher;
}
```
2. 在TeacherMapper中创建一个查询（查询一条信息并返回一个Teacher对象）
```xml
<select id="selById" resultMap="teacher" parameterType="int">
    select * from teacher where id = #{0}
</select>
```
3. 在StudentMapper中使用resultMap标签创建一个Student类的映射
```xml
<resultMap type="Student" id="stuMap">
    <id property="id" column="id"/>
    <result property="tid" column="tid"/>
    <!-- association标签关联一个其他实体类的对象 -->
    <!-- property表示对象名；select表示查询出该对象的select标签的id；column表示传入select中作为参数的属性 -->
    <association property="teacher" select="TeacherMapper.selById" column="tid">
</resultMap>
```
4. 创建student对象的查询语句，使用上面定义的resultMap的id

## 使用resultMap标签实现关联单个对象（多表联合查询方式）
使用多表联合查询、别名、resultMap映射关联
1. 在实体类中创建一个其他类的对象（在Student对象中创建一个Teacher类的对象）
```java
class Student{
    int id;
    int tid;
    private Teacher teacher;
}
```
2. 在select标签中使用多表联合查询
<select id="sel" resultMap="stuMap">
    select s.*,t.id t_id,t.name t=_name from student s left join teacher t on s.tid=t.id
</select>
3. 在mapper文件中建立一个resultMap映射，使用association标签定义别名和该类中的属性的对应关系
```xml
<resultMap type="Student" id="stuMap">
    <id property="id" column="id"/>
    <result property="tid" column="tid"/>
    <!-- association标签关联一个其他实体类的对象 -->
    <!-- 使用多表联合查询方式进行关联时，该标签相当于一个resultMap标签，在其中定义列和属性的对应关系 -->
    <!-- property属性表示对象在外类中的属性名，JavaType表示该对象的类型 -->
    <!-- 该标签中使用id和result标签，column属性为查询出的列的别名，property属性为对象中属性名 -->
    <association property="teacher" javaType="Teacher">
        <id column="" property="">
        <result column="" property="">
    </association>
</resultMap>
```

## 使用resultMap标签实现关联多个对象（n+1方式）
1. 在teacher类中添加一个Student类的list
```java
class teacher{
    int id;
    String name;
    List<Student> list;
}
```
2. 在StudentMapper中创建一个查询
```xml
<select id="selByTid" parameterType="int" resultType="student">
    select * from student where tid=#{0}
</select>
```
3. 在TeacherMapper中使用resultMap标签关联
```xml
<resultMap type="teacher" id="map">
    <id property="id" column="id">
    <result property="name" column="name">
    <collection property="list" javaType="student" column="id" select="StudentMapper.selByTid">
</resultMap>
```
4. 在标签中使用该resultMap


# 使用AutoMapping结合别名实现多表查询
只能使用多表联合查询的方式
1. 在实体类中定义对象
```java
class Student{
    private int id;
    private int tid;
    private Teacher teacher;
}
class Teacher{
    private int id;
    private String name;
}
```
2. 使用联合查询和别名实现多表查询
```sql
select s.id,s.tid,t.id `teacher.id`,t.name `teacher.name` from student s left join teacher t where 条件
```