# 有什么作用?
  可以非常友好的管理WebDriver.
  
# 安装
  下载或编译
  可选 1 - 从这里下载预构建包：https://github.com/sihaihou/crawler-spring-boot-starter/releases
  
# 快速开始
  开始您的第一个项目非常容易。

### 第一步: 引入依赖
      
```xml
<dependency>
    <groupId>com.housihai</groupId>
    <artifactId>crawler-spring-boot-starter</artifactId>
    <version>0.0.1-releases</version>
</dependency>
```

### 第二步: 在application.yaml配置文件中配置浏览器Driver地址
```yaml
reyco:
  dasbx:
    crawler: 
      name: edge
      driver: D:\\download\\edgedriver_win64\\msedgedriver.exe
```

### 第三步: 在启动类加上@EnableCrawlerManagement注解开启Crawler
```java
@EnableCrawlerManagement
@SpringBootApplication
public class TestApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
}
```

### 第四步: 在需要使用Crawler的方法上加上@Crawler就可以了      
```java
@Service
public class TestService {
    @Crawler
    public void crawler() throws CrawlerPageException {
        //xxx
    }
}
```
# 文档
所有最新和长期的通知也可以从Github 通知问题这里找到。

# 贡献
欢迎贡献者加入 Crawler 项目。请联系18307200213@163.com 以了解如何为此项目做出贡献。



