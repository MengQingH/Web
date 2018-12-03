# Ant
Ant是一种基于Java和XML的项目构建工具。java项目的编译运行打包等过程都需要依赖于Ant构建工具。

# 分布式
1. 传统项目部署：
    * 新建一个项目，通过不同的包区分不同的模块，再把这个项目发布到服务器中
    * 当访问量过大时，容易出现效率降低的问题。
2. 分布式项目部署
    * 把一个完整的项目拆分成多个项目，把拆分后的项目分别部署到对象的服务器中的过程就是分布式部署
    * 把传统项目的包转换成一个单独的项目
    * 适用于高负载的情况



# Maven项目
基于Ant的构建工具，在Ant的基础上添加了许多功能。

## 运行原理：
1. maven项目运行时，会在本地仓库寻找项目运行需要的运行环境
2. 如果第一次运行时找不到需要的运行环境，就会去中央仓库中下载需要的运行环境，然后放在本地仓库中
    * 中央仓库就是一个maven官方存放jar包的网站，地址为：https://repo1.maven.org/maven2/
    * 如果下载速度慢可以使用国内镜像。

## 设置本地maven版本
一些编译器如idea、eclipse都集成了maven运行环境，但如果想要使用其他版本的maven，就可以在编译器中导入本地maven的文件夹，编译器就会使用本地的maven。设置方法：
* setting --> Maven --> Maven home derectory 导入本地maven的文件夹 --> user setting file选择maven路径下的配置文件。

## maven配置文件配置(jdk版本必须配置)
* 配置文件的分类：
    1. 全局配置：安装目录下的配置文件
    2. 用户配置：用户目录下的配置文件
* 配置步骤
    1. 配置本地仓库的位置
        ```xml
        <localRepository>D:/Program Files (x86)/apache-maven-3.6.0/repository</localRepository>
        ```
    2. 配置中央仓库镜像的url
        ```xml
        <mirrors>
            <mirror>
                <id>alimaven</id>
                <mirrorOf>repositoryId</mirrorOf>
                <name>Human Readable Name for this Mirror.</name>
                <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
            </mirror>
        </mirrors>
        ```
    3. 配置jdk版本和开发环境版本一致，必须配置，默认为1.4或1.5，可能出现下载的环境和开发环境不匹配的错误
        ```xml
        <profiles>
            <profile>
                <id>jdk-1.8</id>
                <activation>
                    <activeByDefault>true</activeByDefault>
                    <jdk>1.8</jdk>
                </activation>
                <properties>
                    <!-- 源码版本 -->
                    <maven.compiler.source>1.8</maven.compiler.source>
                    <!-- 目标jre版本 -->
                    <maven.compiler.target>1.8</maven.compiler.target>
                    <!-- 编译器版本 -->
                    <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
                </properties>
            </profile>
        </profiles>
        ```