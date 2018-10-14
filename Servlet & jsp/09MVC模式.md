# MVC模式：
* view：界面/视图，web层的界面相关内容
* controller：控制器，接收用户的请求(数据)，然后调用model的service类，然后根据返回的结果决定跳转达到哪个界面。
    控制器添加原则：同一类的业务逻辑对应一个控制器(发出请求时，可以附带一个type类型的值以区分不同类型的请求)
* model：模型，后台相关的内容，主要提供对业务的操作和业务的数据，比如项目中与数据库相关的内容
```
    pro-|-web-|-view：jsp
        |     |
        |     |                     |-接收前一个页面的数据
        |     |-controller：servlet-|-处理数据
        |                           |-为下一个页面准备数据
        |                           |-跳转到下一个界面
        |       |-业务层：service+domain
        |-model-|-dao层：data access object数据访问对象，即专门对数据库操作的类
                |-数据持久层
```
### dao层和业务层也可以再分为dao+daoImp层和service+serviceImp层，其中dao和service是接口，而daoImp和serviceImp是其实现类