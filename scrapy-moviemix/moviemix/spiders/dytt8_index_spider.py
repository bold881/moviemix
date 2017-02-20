# -*- coding: utf-8 -*-

# http://www.ygdy8.net/
# @Dev 
# 2017/02/20

import scrapy
import w3lib.html


from scrapy.selector import Selector
from moviemix.items import VideoLiteItem

class Dytt8IndexSpider(scrapy.Spider):
    name = 'dytt8_index'
    start_urls = [
        'http://www.ygdy8.net/',
    ]

    def __init__(self, *args, **kwargs):
        super(Dytt8IndexSpider, self).__init__(*args, **kwargs)
        self.mySqlPipeline = None

    def parse(self, response):
        itemexist = False
        # 0) get base url
        r1 = response.url.find('/', 8)
        baseurl = response.url[:r1]
        # 1) get all the READMORE pages and get only <a> tag content
        readmores = Selector(response).xpath(u'//a[.="最新电影下载"]/following-sibling::*[1][starts-with(name(),"a")]')
        for readmore in readmores:
            readmoreurl =  readmore.xpath(u'@href').extract_first()
            readmoreurl = baseurl + readmoreurl
            if self.mySqlPipeline.check_url_exist(url=readmoreurl) == False:
                request = scrapy.http.Request(url=readmoreurl, callback=self.parse_readmore)
                request.meta['baseurl'] = baseurl
                yield request

    def parse_readmore(self, response):
        item = VideoLiteItem()

        item['title'] = response.selector.xpath(u'//title/text()')\
            .extract_first()                                                        # 标题
       
        item['downloadurl'] = response.selector\
        .xpath(u'//td[@style="WORD-WRAP: break-word" and @bgcolor="#fdfddf"]/a/@href').extract_first()       # 下载地址

        item['domain'] = response.meta['baseurl']                                   # 域名
        item['pageurl']	= response.url                                              # 网页地址
        
        context = response.selector.xpath(u'//div[@id="Zoom"]').extract_first()
        if context != None:
            item['info'] = w3lib.html.remove_tags(context).strip()                  # 详细信息
        if item['downloadurl'] != None:
            yield item