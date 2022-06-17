## Spring实战（第五版）学习笔记

### chapter_10

反应式编程本质上是函数式和声明式的，它描述了数据将会流经的管道或流，区别于命令式编程按顺序执行任务，反应式流可以在数据可用时立即进行处理，
反应式流旨在提供**无阻塞回压的异步**流处理标准。回压是指限制想要处理的数据数量，避免过快的被数据淹没。

即使有时我们写的反应式编程仍然保持着按步骤执行的模型，但是实际上数据在处理过程中，可能在一个线程中也可能不在一个线程中，这是我们无法判断的。

---
create\buffer\log\merge\transforming 练习以代码为准

---


### chapter_09

Spring Integration在基于Spring的应用程序中实现轻量级消息传递，并支持通过声明适配器与外部系统集成。

它就像是一个控制系统，包括过滤、分流、转换和切分等环节功能，而这每一个环节都类似一个服务，可以是MQ，Redis，MongoDB，或者是TCP/UDP，
可以是我们系统的任何一个模块，Spring Integration就是通过它的组件将这些模块连接到一起形成一个完整的服务，实现企业系统的集成的解决方案。

---

第一个Spring Integration流程图

![](images/chapter9/chapter09-1.png)

---

Spring Integration 的组件

- 通道：将消息从一个元素传递到另一个元素。默认DirectChannel: 消息只会发送至一个消费者，他会在与发送者相同的线程中调用消费者。
- 过滤器@Filter：它能够通过判断来允许或拒绝消息进入下一个流程。
- 转换器@Transformer: 它会对消息执行一些操作，可能会形成不同的消息。
- 路由器@Router: 基于判断，能够将消息进行分流。
- 切分器@Splitter: 能够切分和处理消息。
- 服务激活器@ServiceActivator: 接受来自通道的消息，并发送至一个MessageHandler进行处理，一般将其作为流程的终点。
- 网关@MessagingGateway: 它被声明为接口，通过网关应用可以提交数据到集成流中，并且`可选的`接受流的结果作为响应。相当于是接口里的一个方法，定义入参，返回值可以有也可以没有。
- 入站通道适配器@InboundChannelAdapter: 

---

maven optional标签为true时，能够避免产生重复依赖，比如A项目中有Lombok，没有添加optional标签时，B依赖A，那么B中也会有Lombok依赖

若optional为true时，此时B再依赖A，那么B中就没有Lombok可用了。optional是maven依赖jar时的一个选项，表示该依赖是可选的，不会被依赖传递。

### chapter_06

@RestController 注解会告诉Spring控制器中的所有处理器防范的返回值都要直接写入响应体中，而不是将值放到模型中并传递给一个视图以便于渲染。

@PostMapping 语义上是这对整体的更新（大规模）

@PatchMapping 语义上是对任一字段的更新

---

HATEOAS（超媒体应用状态引擎）被称为HAL超文本语言，是一种在JSON响应中嵌入超链接的简单通用格式，创建自描述API。

![img.png](images/chapter6/img.png)

可以去看看代码里的例子

---

dependencyManagement元素提供了一种管理依赖版本号的方式。在dependencyManagement元素中声明所依赖的jar包的版本号等信息，
那么所有子项目再次引入此依赖jar包时则无需显式的列出版本号。
Maven会沿着父子层级向上寻找拥有dependencyManagement 元素的项目，然后使用它指定的版本号。

dependencyManagement中定义的只是依赖的声明，并不实现引入，因此子项目需要显式的声明需要用的依赖。

### chapter_05
如下配置虽然显式设置成了0，但是服务器并不会真的在端口0上启动，相反它会任选一个可用的端口
![img_2.png](images/img_2.png)

---

要设置日志级别，我们可以创建以logging.level作为前缀的属性，随后接跟着的是我们想要设置日志级别的logger
![img_3.png](images/img_3.png)

是如何设置的JdbcTemplate的logger的呢？我们点进JdbcTemplate，发现它的logger在这
![img_4.png](images/img_4.png)

它属于它的父类

![img.png](images/img_5.png)

这个getClass()方法的值就是我们需要在log:level后配置的东西，如果不知道配置什么直接debug进来看

---

通过使用注解可以自定义自己的配置，如@ConfigurationProperties(prefix = "taco.design")，这个值可以随着我们的配置改变

![img.png](images/img_6.png)
![img_1.png](images/img_7.png)

---

profile: 可以配置不同的配置文件 application-{profile名字}.yaml

之后在默认的yaml中可以配置生效的profile

还可以根据@Profile注解条件创建bean

### chapter_04

继承WebSecurityConfigurerAdapter实现自己的SpringSecurity配置，具体查看SecurityConfig类

创建User实体类实现UserDetails接口，它提供了核心的用户信息，实现UserDetailService接口，重写方法之后，将它加入配置中能自动生效

SecurityConfig中passwordEncoder()创建密码编码器的方法会生成一个bean被Spring管理起来，
每次调用该方法时都会被Spring拦截，并返回该bean实例

对于各种请求的安全配置则看SecurityConfig注释即可

csrf: 跨站请求伪造，它会让用户在恶意的web页面上填写晰晰，然后将表单数据提交到另外一个应用上，因此可能会对用户造成损失。
SpringSecurity提供了CSRF的保护，不过我们将它关闭了。

Order中为了保存userId 写了两种方式，一是通过注解在Controller层直接在请求中拿到，另一种是通过SpringSecurity上下文进行获取。

### chapter_03

http是无状态的协议，为了给它增加状态追踪，添加了cookie和Session的机制

所谓Session，指的是客户端和服务端之间的状态信息（数据）；这个状态如何界定，生命期有多长，这是应用本身的事情，Session受控与服务端
![img_1.png](images/img_1.png)
查看请求的时候可以发现SessionID

Cookie则是存储在浏览器中，记录客户端与服务端之间的状态

@SessionAttributes只能标记在类上，会将model中指定name的对象保存在Session中，可以在请求中传参和复用对象

@ModelAttribute如果标记在方法上，每次请求的时候都会执行该方法，如果有返回值则会加入到ModelMap中

如果标记在参数上，会将客户端传递过来的参数按名称注入到指定对象中，并且会将这个对象自动加入ModelMap，供view层展示

---

### chapter_02

重定向：和重定向有关的 HTTP 状态码主要是 301、302、303、307、308，最常用的是 301 和 302，可以看看 MDN 官方对它们的解释。

301 是永久重定向（Moved Permanently）说明请求的资源已经被 永久 移动到了由 Location 头部指定的 url 上，是固定的不会再改变，搜索引擎会根据该响应修正。

而 302 是暂时性转移（Moved Temporarily，或者 Found），表明请求的资源被 暂时 移动到了由 Location 头部指定的 URL 上。浏览器会重定向到这个 URL， 但是搜索引擎不会对该资源的链接进行更新。

虽然 301 和 302 都能够将用户输入的网址 A， 改为重定向后的网址 B，但他们还是有区别的：

搜索引擎区别：301 表示原地址 A 的资源已被移除，永远无法访问，搜索引擎抓内容时会将网址 A 全部替换为 B；而 302 表示网址 A 还活着，搜索引擎会在抓取网址 B 新内容的同时，保留网址 A 的记录。

安全性：302 跳转有网站劫持的风险，导致网站被盗用。

测试：code-nav.cn 可以发现下图中是301永久重定向，浏览器永久跳转到Location指定的url上。

![img.png](images/img.png)

参考：[通俗讲解【重定向】及其实践](https://zhuanlan.zhihu.com/p/367927954)

---

### chapter_01

Spring的核心是提供了一个容器，通常成为Spring应用上下文，它们会创建和管理应用组件。 
这些组件也可以称为bean，将bean组装在一起的行为是通过一种基于依赖注入的模式实现的。

---

DevTools会监控变更，当它看到有变化的时候会自动重启应用，仅用于开发，在生产环境中会很智能的把自己禁用掉。

当它运行的时候，应用程序会被加载到Java虚拟机两个独立的类加载器中，一个加载Java代码、属性文件以及项目中"src/main"路径下几乎所有内容，
另外一个类加载器会加载依赖的库（这些不经常变化的东西）。
一旦它发现变化时，只会重新加载前者的内容，并重启Spring应用上下文，在这个过程中另一个加载器和JVM是不发生改变的。
这也就是它的不足之处，热部署无法反映依赖项的变化。