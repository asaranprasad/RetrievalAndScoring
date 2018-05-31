# Retrieval and Scoring using BM25 - Java Implementation

```
Task1: Index, Retrieve and Score using Lucene
Task2: Building a retrieval model by implementing the BM25 ranking algorithm
```

## Setting up

Reports
```
1. Report_Implementation.md - Report describing the implementation details of the retrieval models. 
2. Report_Observation.md    - Contains a brief discussion comparing the top 5 results between the two search engines for each query.
```


The ./output/ folder consists of the following
```
1. BM25_RankedResults           - Top 100 ranked results of the BM25 ranking algorithm for each of the query inputs.
2. lucene_indexer\Indexer       - Index files generated by the Lucene libraries post parsing the crawled documents.
3. lucene_indexer\RankedResults - Top 100 ranked results of the Lucene's in-build retrieval model for each of the query inputs.
```


The ./input/ folder consists of the following
```
1. query.txt                            - Query input to the search engine.
2. custom_indexer\unigramInvIndex.txt   - Index generated by the custom indexer from previous assignment.
3. custom_indexer\unigramDocLength.txt  - Length stat of the crawled documents.
4. documents                            - Crawled documents

``` 

Kick start the program by executing ```ranker/RankingCaller.java```.


## External Libraries Referenced

The following external library need to be referenced to the build path, or via using Maven dependencies.
1. Jsoup 
2. lucene-analyzers-common-7.3.0.jar
3. lucene-core-7.3.0.jar
4. lucene-queryparser-7.3.0.jar

### Lucene
```
<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-analyzers-common</artifactId>
    <version>7.3.0</version>
</dependency>

<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-core</artifactId>
    <version>7.3.0</version>
</dependency>

<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-queryparser</artifactId>
    <version>7.3.0</version>
</dependency>

<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.9.2</version>
</dependency>
```


The above libraries have been included in the ./externalLibrary/ folder

