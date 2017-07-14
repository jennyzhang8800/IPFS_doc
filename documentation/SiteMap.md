# 新闻资讯站点地图

**目录**
* [0.需求](#demand)
* [1.流程图](#flowChart)
* [2.方法列表](#function)
* [3.SiteMap格式](#sitemap)
* [4.结果展示](#result)

<hr/>

<h2 id='demand'>0.需求</h2>

>Sitemap 可方便网站管理员通知搜索引擎他们网站上有哪些可供抓取的网页。最简单的 Sitemap 形式，就是XML 文件，在其中列出网站中的网址以及关于每个网址的其他元数据（上次更新的时间、更改的频率以及相对于网站上其他网址的重要程度为何等），以便搜索引擎可以更加智能地抓取网站

本例实现了新闻资讯版块的Sitemap，通新闻资讯版块下的5个子版块的所有详情页的URL构成新闻资讯SiteMap,结果保存为sitemap_news.xml。sitemap_news.xml文件最大设定为10M,如果内容超过10M会新建XML。
<h2 id='flowChart'>1.流程图</h2>

<h2 id='function'>2.方法列表</h2>

<h2 id='sitemap'>3.SiteMap格式</h2>
sitemap_news.xml格式如下：
```
  <?xml version="1.0" encoding="UTF-8" ?> 
 <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
   <url>
     <loc>https://mall.gtja.com/news/stocknews/18566810_000407.html</loc> 
     <lastmod>2017-6-23</lastmod> 
     <priority>1.0</priority> 
   </url>
   ...
 </urlset>
```

<h2 id='result'>4.result</h2>
