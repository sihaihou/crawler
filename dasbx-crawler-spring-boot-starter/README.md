# 有什么作用?
  可以非常友好实现网页爬虫：依赖浏览器和浏览器Driver一致！
  
# 安装
  下载或编译
  可选 1 - 从这里下载预构建包：https://github.com/sihaihou/dasbx-crawler-spring-boot-starter/releases
  
# 快速开始
  开始您的第一个项目非常容易。

### 第一步: 引入依赖
      
```xml
<dependency>
    <groupId>com.housihai</groupId>
    <artifactId>dasbx-crawler-spring-boot-starter</artifactId>
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

### 第三步: 在类中注入CrawlerPage对象，调用CrawlerPage的crawler方法完成爬虫：
  -  下面用一个简单的示例完成爬虫的功能
```java
@Component
public class Kugou {

	@Autowired
	private CrawlerPage crawlerPage;
	
	@Crawler
	public List<Map<String,Object>> crawler() throws CrawlerPageException{
		String baseUrl = "https://www.kugou.com/yy/rank/home/1-6666.html?from=rank";
		DefaultCrawlerPageLocater nameCrawlerPageLocater = new DefaultCrawlerPageLocater();
		nameCrawlerPageLocater.setName("name");
		nameCrawlerPageLocater.setExpression("//*[@id=\"rankWrap\"]/div[2]/ul/li/a");
		nameCrawlerPageLocater.setAttribute("substring-before('text','-')");
		
		DefaultCrawlerPageLocater tagCrawlerPageLocater = new DefaultCrawlerPageLocater();
		tagCrawlerPageLocater.setName("tag");
		tagCrawlerPageLocater.setExpression("//*[@id=\"rankWrap\"]/div[2]/ul/li/a");
		tagCrawlerPageLocater.setAttribute("substring-after('text','-')");
		
		DefaultCrawlerPageLocater foverCrawlerLocater = new DefaultCrawlerPageLocater();
		foverCrawlerLocater.setName("url");
		foverCrawlerLocater.setExpression("//*[@id=\"rankWrap\"]/div[2]/ul/li/a");
		foverCrawlerLocater.setAttribute("href");
		
		List<CrawlerPageLocater> crawlerPageLocaters = new ArrayList<>();
		crawlerPageLocaters.add(nameCrawlerPageLocater);
		crawlerPageLocaters.add(tagCrawlerPageLocater);
		crawlerPageLocaters.add(foverCrawlerLocater);
		
		DefaultCrawlerPageLocation crawlerPageLocation = new DefaultCrawlerPageLocation();
		crawlerPageLocation.setDetail(false);
		crawlerPageLocation.setCrawlerPageLocaters(crawlerPageLocaters);
		
		DefaultCrawlerPageParameter crawlerParameter = new DefaultCrawlerPageParameter();
		crawlerParameter.setCrawlerUrl(baseUrl);
		crawlerParameter.setLoadDelayTime(2000L);
		crawlerParameter.setCrawlerPageLocation(crawlerPageLocation);
		List<Map<String, Object>> list = crawlerPage.crawler(crawlerParameter);
		return list;
	}
}
```
# 文档
所有最新和长期的通知也可以从Github 通知问题这里找到。

# 贡献
欢迎贡献者加入 Crawler 项目。请联系18307200213@163.com 以了解如何为此项目做出贡献。



