# **Technical Test - Search Engineer**

## Environment setup
1. Install docker following the documentation from the [docker site](https://www.docker.com/get-started). 
2. Install Elasticsearch following the official [documentation](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html). 
3. After pull and create the network I ran the following command:  `$docker run --name es01 --net elastic -e ELASTIC_PASSWORD=elastic -p 127.0.0.1:9200:9200 -p 127.0.0.1:9300:9300 -e "discovery.type=single-node" -e xpack.security.enabled=false -it docker.elastic.co/elasticsearch/elasticsearch:8.6.1` This will create an Elasticsearch container on a single-node configuration with no security validation
4. In case you want to install kibana you can run: `docker run --name kibana --net elastic -p 5601:5601 docker.elastic.co/kibana/kibana:8.6.1`

## Elasticsearch index and population

### Web Scrapping

The idea behind a search engine is to firstly execute a process called scrapping in which the web page information is extracted and then it is indexed.

So, in order to simulate this step, I created the *webScrapper* project. This is a python script to procesing the popular films-series list (a csv download from the [kaggle site](https://www.kaggle.com/datasets/mazenramadan/imdb-most-popular-films-and-series)), create the Elasticsearch index and populate it.

### Elasticsearch documents structure

```
{
    "mappings": {
        "properties": {
            "Name": {"type": "text"},
            "Rate": {"type": "float"},
            "Genre": {"type": "text"}
        }
    }
}
``` 

### Web Scrapping execution
To populate the Elasticsearch node you have to run the following commands:

`$cd {projectDir}/webScraper/src`

`$python3 Indexer.py`


## Java search engine 

### Search engine start
The modifications added by me doesn't affects the previous configuration to running the project, so you must run the following commands to start the server.

`$cd {projectDir}/pi-backend-technical-test/`

`$./gradlew bootRun`

Open the swagger ui: http://localhost:8080/swagger-ui/index.html.

### REST queries 

- Endpoint /count:

This get method retrieves the total of documents that matches with the rating range selected (i, ii, iii, iv, v) requested by the "rate" parameter. 

The range are determine by:
i. All above ([8-10[)
ii. [6-8[
iii. [4-6[
iv. [2-4[
v. All below ([0-2[)

- Endpoint /searchFS:

This get method retrieves the documents that matches with the genre specified in the "genre" parameter and the rating range selected (i, ii, iii, iv, v) requested by the "rate" parameter.

### Implemented Elasticsearch query body

The search by rate and genre is implemented with the following Elasticsearch query.

```
{
   "query": {
      "bool" : {
         "must" : {
            "range":{
                "Rate":{
                    "gte":<low limit>,
                    "lt":<high limit>
                }
             }
         },
         "filter": {
            "match" : {
                "Genre":"*<genre>*"
            }
         }
      }
   }
}
```

The count by rate range is implemented with the following Elasticsearch query.
```
{
   "query":{
      "range":{
         "Rate":{
            "gte":<low limit>,
            "lt":<high limit>
         }
      }
   }
}
```


## Pending tasks
Since this is my first time working with Elasticsearch and Spring, I had to overcome the learning curve by spending most of the time reading the documentation and reviewing examples. Once I achieved a better understanding of the technologies I applyed reverse engineering for the source code provided and unfortunately I wasn't able to fully implementing the project requirements, so I'm attaching the list of pending task:
- [ ] Integrate the pagination for the search with range rate and genre.
- [ ] Unit testing 


## Author
Jairo Ortega Calderón

jairo.orca23@gmail.com

Computer Engineer




