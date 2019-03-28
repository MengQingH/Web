JdbcRealm已经实现了从数据库中获取用户的验证信息，但是JdbcRealm灵活性太差，如果要实现一些自己的特殊应用时将不能支持，这时可以使用自定义Realm来实现身份认证功能。

# Realm
Realm是一个接口，在接口中定义了根据token获得认证信息的方法。Shiro内部实现了一系列的Realm，这些不同的Realm实现类实现了不同的功能。
<br><img src=img/Realm.png><br>
例如AuthenticationRealm实现了获取身份信息的功能，AuthorizingRealm实现了获取权限信息的功能。通常自定义Realm需要继承AuthorizingRealm，这样既可以提供身份认证的自定义方法，也可以实现授权的自定义方法。

# 自定义Realm实现认证和授权
根据传来的信息取出该用户的其他信息，如认证信息或授权信息。实现步骤：
1. 创建一个类并继承AuthorizingRealm类，并重写其中的两个方法。
```java
/**
 * 自定义Realm的实现，该Realm类提供了两个方法：
 * doGetAuthenticationInfo 获取认证信息
 * doGetAuthorizationInfo 获取权限信息
 */
public class UserRealm extends AuthorizingRealm {
    //完成认证并返回身份信息，如果失败返回null
    //此处的认证是从数据库中取出数据，真正的认证工作由shiro来做
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1. 获取用户输入的用户名
        String username = (String) token.getPrincipal();
        //2. 根据用户名到数据库中查询密码信息  假设为123
        String password = "123";
        //3. 将从数据库中查询的信息封装到SimpleAuthenticationInfo中
        AuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
        //4. 返回
        return info;
    }

    //获得授权的信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();
        //根据用户名到数据库查询用户对应的权限信息  模拟
        List<String> permission = new ArrayList<>();
        permission.add("user:add");
        permission.add("user:delete");
        permission.add("user:update");
        permission.add("user:find");
        //新建一个AuthorizationInfo对象，把权限信息添加到该对象中
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (String per :
                permission) {
            info.addStringPermission(per);
        }
        //返回
        return info;
    }
}
```
2. 在ini配置文件中声明自定义Realm
```ini
[main]
userRealm= com.mh.demo.UserRealm
securityManager.realm=$userRealm
```
使用shiro来完成权限管理，shiro并不会去维护数据。shiro中使用的数据，需要程序员根据处理业务将数据传递给shiro的相应接口。