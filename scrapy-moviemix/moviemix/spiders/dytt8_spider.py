# -*- coding: utf-8 -*-

# http://www.ygdy8.net/html/gndy/dyzz/index.html
# http://dytt8.net/html/gndy/dyzz/index.html
# @Dev 
# 2017/02/11

import scrapy
import w3lib.html


from scrapy.selector import Selector
from moviemix.items import VideoLiteItem

class Dytt8Spider(scrapy.Spider):
    name = 'dytt8'
    start_urls = [
        'http://dytt8.net/html/gndy/dyzz/index.html',
        'http://www.ygdy8.net/html/gndy/dyzz/index.html',
    ]

    def __init__(self, *args, **kwargs):
        super(Dytt8Spider, self).__init__(*args, **kwargs)
        self.mySqlPipeline = None

    def parse(self, response):
        itemexist = False
        # 0) get base url
        r1 = response.url.find('/', 8)
        baseurl = response.url[:r1]
        # 1) get all the READMORE pages
        readmores = Selector(response).xpath(u'//a[@class="ulink"]')
        for readmore in readmores:
            readmoreurl =  readmore.xpath(u'@href').extract_first()
            readmoreurl = baseurl + readmoreurl
            if self.mySqlPipeline.check_url_exist(url=readmoreurl) == False:
                request = scrapy.http.Request(url=readmoreurl, callback=self.parse_readmore)
                yield request
            else:
                break
            #    itemexist = True
        
        # 2) goto the next page if exist and read more
        if itemexist == False:
            nextpageurl = response.url
            r2 = nextpageurl.rfind('/')
            nextpageurl = nextpageurl[:r2+1]
            nexturl = response.selector.xpath(u'//a[text()="下一页"]/@href').extract_first()
            if nexturl != None:
                nexturl = nextpageurl + nexturl
                request = scrapy.http.Request(url=nexturl, callback=self.parse)
                yield request

    def parse_readmore(self, response):
        item = VideoLiteItem()

        item['title'] = response.selector.xpath(u'//title/text()')\
            .extract_first()                                                        # 标题
       
        item['downloadurl'] = response.selector\
        .xpath(u'//td[@style="WORD-WRAP: break-word" and @bgcolor="#fdfddf"]/a/@href').extract_first()       # 下载地址

        item['domain'] = 'http://www.ygdy8.net/'                                    # 域名
        item['pageurl']	= response.url                                              # 网页地址
        
        context = response.selector.xpath(u'//div[@id="Zoom"]').extract_first()
        if context != None:
            item['info'] = w3lib.html.remove_tags(context).strip()                  # 详细信息
        if item['downloadurl'] != None:
            yield item