import mysql.connector
from elasticsearch import Elasticsearch


es = Elasticsearch([
  {'host': '0.0.0.0'},
])
cnx = mysql.connector.connect(user='df', 
                              password='123456',
                              host='0.0.0.0',
                              database='moviemix')
cursor = cnx.cursor()
query = ("SELECT id, title, downloadurl, created, domain, pageurl, info from videolite")
cursor.execute(query)

for (id, title, downloadurl, created, domain, pageurl, info) in cursor:
  #print(u"{}, {} ".format(title, downloadurl))
  print created
  doc = {
    'id': id,
    'title': title,
    'downloadurl': downloadurl,
    'created': created,
    'domain': domain,
    'pageurl': pageurl,
    'info': info,
  }
  res = es.index(index="moviemix", doc_type='movie', body=doc)
  print res['created']

cursor.close()
cnx.close()