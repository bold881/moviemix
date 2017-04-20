# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import MySQLdb
import time
import datetime

from scrapy.conf import settings
from moviemix.items import VideoItem
from elasticsearch import Elasticsearch



class MysqlDBPipeline(object):
    def __init__(self):
        self.conn = MySQLdb.connect(
            settings['MYSQL_HOST'],
            settings['MYSQL_USER'],
            settings['MYSQL_PSWD'],
            settings['MYSQL_DBNAME'],
            charset=settings['MYSQL_CHARSET'],
            use_unicode=settings['MYSQL_UNICODE']
        )
        self.cursor = self.conn.cursor()
        # connect esclient
        self.es = Elasticsearch([
            {'host': settings['ES_HOST']}
        ])

    add_newsitem = ("INSERT INTO videos (posterurl, title, releasedate, country, genre, language, \
    subtitle, imdbscore, videoformat, videosize, filesize, runtime, director, cast, storyline, awards, \
    downloadurl, created, domain, pageurl) VALUES \
    (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)")
    
    def check_url_exist(self, url):
        if url == None:
            return True
        try:
            #check if item exist
            self.cursor.execute("SELECT COUNT(*) FROM videos where pageurl = '%s'"%url)
            if int(self.cursor.fetchone()[0]) > 0:
                return True
            else:
                return False
        except MySQLdb.Error, e:
            print "DB Error %d: %s" % (e.args[0], e.args[1])

    def open_spider(self, spider):
        spider.mySqlPipeline = self
    
    def process_item(self, item, spider):

        if isinstance(item, VideoItem):
            try:                
                #insert new data
                ts = time.time()
                timestamp = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
                data_newsitem = (
                    item.get('posterurl', ''), \
                    item.get('title', ''), \
                    item.get('releasedate', ''), \
                    item.get('country', ''), \
                    item.get('genre', ''), \
                    item.get('language', ''), \
                    item.get('subtitle', ''), \
                    item.get('imdbscore', ''), \
                    item.get('videoformat', ''), \
                    item.get('videosize', ''), \
                    item.get('filesize', ''), \
                    item.get('runtime', ''), \
                    item.get('director', ''), \
                    item.get('cast', ''), \
                    item.get('storyline', ''), \
                    item.get('awards', ''), \
                    item.get('downloadurl', ''), \
                    timestamp, \
                    item.get('domain', ''), \
                    item.get('pageurl', ''))

                self.cursor.execute(self.add_newsitem, data_newsitem)
                self.conn.commit()

            except MySQLdb.Error, e:
                print "DB Error %d: %s" % (e.args[0], e.args[1])

            # index document
            #tses = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%dT%H:%M:%SZ')
            doc = {
                'posterurl': item.get('posterurl', ''),
                'title': item.get('title', ''),
                'releasedate': item.get('releasedate', ''),
                'country': item.get('country', ''),
                'genre': item.get('genre', ''),
                'language': item.get('language', ''),
                'subtitle': item.get('subtitle', ''),
                'imdbscore': item.get('imdbscore', ''),
                'videoformat': item.get('videoformat', ''),
                'videosize': item.get('videosize', ''),
                'filesize': item.get('filesize', ''),
                'runtime': item.get('runtime', ''),
                'director': item.get('director', ''),
                'cast': item.get('cast', ''),
                'storyline': item.get('storyline', ''),
                'awards': item.get('awards', ''),
                'downloadurl': item.get('downloadurl', ''),
                'created': timestamp, # date datatype can be formatted when index time
                'domain': item.get('domain', ''),
                'pageurl': item.get('pageurl', '')
            }
            self.es.index(index="moviemix", doc_type='movie', id=self.cursor.lastrowid, body=doc)
        return item
