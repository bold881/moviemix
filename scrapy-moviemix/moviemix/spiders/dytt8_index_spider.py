# -*- coding: utf-8 -*-

# http://www.ygdy8.net/
# @Dev 
# 2017/02/20

import scrapy
import w3lib.html


from scrapy.selector import Selector
from moviemix.items import VideoItem

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
        item = VideoItem()

        # 海报地址
        item['posterurl'] = response.selector.xpath(u'//img[@border="0" and @onclick]/@src')\
        .extract_first()

        # 片名
        item['title'] = response.selector.xpath(u'//meta[@name="keywords"]/@content')\
            .extract_first()
        if item['title'].find(u'下载') != -1:
            length = len(item['title'])
            item['title'] = item['title'][:length-2]
        
        details = Selector(response).xpath(u'//p/text()')
        castFound = False
        storylineFound = False
        awardsFound = False
        for detail in details:
            txt = detail.extract()
            # 年代
            if txt.find(u'◎年　　代　') != -1:
                item['releasedate'] = txt.replace(u'◎年　　代　', '')
            
            # 国家
            if txt.find(u'◎国　　家　') != -1:
                item['country'] = txt.replace(u'◎国　　家　', '')
               
            # 类别
            if txt.find(u'◎类　　别　') != -1:
                item['genre'] = txt.replace(u'◎类　　别　', '')
              
            # 语言
            if txt.find(u'◎语　　言　') != -1:
                item['language'] = txt.replace(u'◎语　　言　', '')
              
            # 字幕
            if txt.find(u'◎字　　幕　') != -1:
                item['subtitle'] = txt.replace(u'◎字　　幕　', '')
               
            # IMDB评分
            if txt.find(u'◎IMDb评分') != -1:
                item['imdbscore'] = txt.replace(u'◎IMDb评分', '').strip()
              
            # 文件格式
            if txt.find(u'◎文件格式　') != -1:
                item['videoformat'] = txt.replace(u'◎文件格式　', '')
            
            # 视频尺寸
            if txt.find(u'◎视频尺寸　') != -1:
                item['videosize'] = txt.replace(u'◎视频尺寸　', '')
            
            # 文件大小
            if txt.find(u'◎文件大小　') != -1:
                item['filesize'] = txt.replace(u'◎文件大小　', '')
             
            # 片长
            if txt.find(u'◎片　　长　') != -1:
                item['runtime'] = txt.replace(u'◎片　　长　', '')
           
            # 导演
            if txt.find(u'◎导　　演　') != -1:
                item['director'] = txt.replace(u'◎导　　演　', '')

            if castFound and (not storylineFound):
                item['cast'] += txt
            
            if storylineFound and (not awardsFound):
                item['storyline'] += txt

            if awardsFound and (storylineFound or castFound):
                item['awards'] += txt
           
            # 主演
            if txt.find(u'◎主　　演　') != -1:
                item['cast'] = txt.replace(u'◎主　　演　', '')
                castFound = True
            
            # 简介
            if txt.find(u'◎简　　介') != -1:
                storylineFound = True
                item['storyline'] = ''
            
            # 获奖情况
            if txt.find(u'◎获奖情况') != -1:
                awardsFound = True
                item['awards'] = ''
        # 下载地址
        item['downloadurl'] = response.selector\
        .xpath(u'//td[@style="WORD-WRAP: break-word" and @bgcolor="#fdfddf"]/a/@href').extract_first()       # 下载地址
        
        # 域名
        item['domain'] = response.meta['baseurl']                                   # 域名
        
        # 网页地址
        item['pageurl']	= response.url                                              # 网页地址
        
        if item['downloadurl'] != None:
            yield item