#!/usr/bin/python
# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class VideoItem(scrapy.Item):    
    posterurl = scrapy.Field()          # 海报地址
    title = scrapy.Field()           	# 片名
    releasedate = scrapy.Field()       # 年代
    country = scrapy.Field()            # 国家
    genre = scrapy.Field()             	# 类别
    language = scrapy.Field()           # 语言
    subtitle = scrapy.Field()           # 字幕
    imdbscore = scrapy.Field()    	    # IMDB评分
    videoformat = scrapy.Field()       # 文件格式
    videosize = scrapy.Field()		    # 视频尺寸
    filesize = scrapy.Field()		    # 文件大小
    runtime = scrapy.Field()         	# 片长
    director = scrapy.Field()           # 导演 
    cast = scrapy.Field()             	# 主演
    storyline = scrapy.Field()          # 简介
    awards = scrapy.Field()           	# 获奖情况
    downloadurl	= scrapy.Field()        # 下载地址
    #   created			---  	创建时间
    domain = scrapy.Field()             # 域名
    pageurl	= scrapy.Field()            # 网页地址

class VideoLiteItem(scrapy.Item):
    title = scrapy.Field()              # 标题
    downloadurl	= scrapy.Field()        # 下载地址
    domain = scrapy.Field()             # 域名
    pageurl	= scrapy.Field()            # 网页地址
    info = scrapy.Field()               # 详细信息