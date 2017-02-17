# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import MySQLdb
import time
import datetime

from scrapy.conf import settings
from moviemix.items import VideoLiteItem



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
    add_newsitem = ("INSERT INTO videolite "
    "(title, downloadurl, created, domain, pageurl, info) "
    "VALUES (%s, %s, %s, %s, %s, %s)")
    
    def check_url_exist(self, url):
        if url == None:
            return True
        try:
            #check if item exist
            self.cursor.execute("SELECT COUNT(*) FROM videolite where pageurl = '%s'"%url)
            if int(self.cursor.fetchone()[0]) > 0:
                return True
            else:
                return False
        except MySQLdb.Error, e:
            print "DB Error %d: %s" % (e.args[0], e.args[1])

    def open_spider(self, spider):
        spider.mySqlPipeline = self
    
    def process_item(self, item, spider):

        if isinstance(item, VideoLiteItem):
            try:                
                #insert new data
                ts = time.time()
                timestamp = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
                data_newsitem = (item.get('title', ''), item.get('downloadurl', ''), \
                timestamp, item.get('domain', ''), item.get('pageurl', ''), item.get('info', '') )

                self.cursor.execute(self.add_newsitem, data_newsitem)
                self.conn.commit()
            except MySQLdb.Error, e:
                print "DB Error %d: %s" % (e.args[0], e.args[1])
        return item
