# 动态sql
根据条件不同，执行不同的sql命令，称为动态sql。在myBatis中，动态sql就是在mapper.xml中添加逻辑判断等，使用方式类似于jstl。

## if
```xml
<select id="sel" resultType="account">
    select * from bank where 1=1
    <!-- OGNL表达式，获取参数时#{}中直接写map的key或者对象的属性名，如果使用接口绑定的方式调用，则需要在函数中使用注解 -->
    <if test="accin!=null and accin!=''">
        and accin=#{accin}
    </if>
    <if test="accout!=null and accout!=''">
        and accout=#{accout}
    </if>
</select>
```

## where
类似于sql语句中where关键字的作用。当编写where标签时，如果内容第一个是and去掉第一个and；如果<where>中有内容会生成where关键字，如果没有内容不生成where关键字
```xml
<select id="sel" resultType="account">
    select * from bank
    <where>
        <if test="accin!=null and accin!=''">
            and accin=#{accin}
        </if>
        <if test="accout!=null and accout!=''">
            and accout=#{accout}
        </if>
    </where>
</select>
```
当调用上面的sql语句时
1. 当不传入参数时不会有where语句：select * from bank
2. 传入第一个参数第一个中的and关键字会被去掉：select * from bank where accin=?

## choose when otherwise
类似于if-else结构，只要有一个成立就不继续往下执行
```xml
<choose>
    <when test="">
        //业务
    </when>
    <when test="">
        //业务
    </when>
    <otherwise>
        //业务
    </otherwise>
</choose>
```

## set
用在修改sql中从句。作用：去掉最后一个逗号，如果set标签中有内容，就会生成set
```xml
<update id="">
    update table
    <set>
        id=#{id}
        <if test="">account=#{account},</if>
        <if test="">name=#{name},</if>
    </set>
    where id=#{id}
</update>
```

## trim
对传入的内容进行添加或删除内容，先执行删除再执行添加
* prefix：在前面添加内容
* prefixOverrides：去掉前面内容
* suffix：在后面你添加内容
* suffixOverrides：删除后面内容
```xml
<update id="">
    update bank
    <trim prefix="set" suffixOverrides=",">a=a</trim>
    where id=1
</update>
```

## bind
给参数重新赋值。
```xml
<!-- 使用字符串拼接的方式给字符串赋值 -->
<bind name="name" value="'a'+name">
```

## foreach
循环参数内容，还可以在参数前后添加内容，也可以添加分隔符。适用于in查询、批量新增。属性：
* collection：要遍历的集合
* item：迭代变量名
* open：循环后左侧添加的内容
* close：循环后右侧添加的内容
* separater：每次循环元素之间的分隔符
```xml
<!-- 循环遍历 -->
<select id="selIn" parameterType="list" resultType="">
    select * from flower where id in
    <!-- open：在前面添加 close：在后面添加 separator：分隔符 -->
    <foreach collection="list" item="name" open="(" close=")" sepatator=",">
        #{name}
    </foreach>
</select>
<!-- 批量新增 使用前获取SqlSession时调用openSession方法传入ExecutorType.BATCH-->
```

## sql include
某些sql片段如果希望复用，就可以使用sql定义这个片段。使用时用include引用
```xml
<sql id="name1">
    id,name,price
</sql>
<select>
    select <include refid="name1"></infclude> from flower
</select>
```