#!/usr/bin/python
# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class VideoItem(scrapy.Item):    
    posterurl = scrapy.Field()          # 海报地址
    pianming = scrapy.Field()           # 片名
    niandai = scrapy.Field()            # 年代
    guojia = scrapy.Field()             # 国家
    leibie = scrapy.Field()             # 类别
    yuyan = scrapy.Field()              # 语言
    zimu = scrapy.Field()               # 字幕
    imdbpingfen = scrapy.Field()    	# IMDB评分
    wjgs = scrapy.Field()       		# 文件格式
    spcc = scrapy.Field()		        # 视频尺寸
    wjdx = scrapy.Field()		        # 文件大小
    pc = scrapy.Field()         		# 片长
    daoyan = scrapy.Field()             # 导演 
    zhuyan = scrapy.Field()             # 主演
    jianjie	= scrapy.Field()    	    # 简介
    hjqk = scrapy.Field()           	# 获奖情况
    downloadurl	= scrapy.Field()        # 下载地址
    #   created			---  创建时间
    domain = scrapy.Field()             # 域名
    pageurl	= scrapy.Field()            # 网页地址

class VideoLiteItem(scrapy.Item):
    title = scrapy.Field()              # 标题
    downloadurl	= scrapy.Field()        # 下载地址
    domain = scrapy.Field()             # 域名
    pageurl	= scrapy.Field()            # 网页地址
    info = scrapy.Field()               # 详细信息
