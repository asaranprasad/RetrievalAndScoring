## Implementation Report

The ./src/ folder consists of the following source code files
```
1. datastructure/IndexEntry.java      - Custom data structure to represent an index entry.
2. ranker/BM25RankingAlgorithm.java   - Manages loading queries, computation of scores and retrieval of results using BM25 Ranking algorithm.
3. ranker/FileUtility.java            - Consists of File IO utility methods.
4. ranker/LuceneIndexerAndSearch.java - Modified starter code from HW4.java
5. ranker/RankingCaller.java          - Contains the main caller method invoking the Lucene indexing, retrieval and BM25 retrieval methods.
```

1. Used version 7.3.0 of the Lucene libraries.

2. ```ranker/LuceneIndexerAndSearch.java``` - The starter code was fixed to resolve issues from deprecated methods of previous lucene version. Modified the starter code to -		
		. Use SimpleAnalyzer as the Lucene's analyzer.
		. Configured indexer and input/output paths.
		. Return only top 100 search results for each query.
		. Display results in the required format.		

3. ```ranker/BM25RankingAlgorithm.java``` - The class has the following features - 
		. search	1. Compute frequencies of each term in the query.
					2. For each term term - 
						a. fetch all inverted lists corresponding to the term.
						b. compute BM25 scores for each document in the list. Use the following formula - 
							Math.log((N - ni + 0.5) / (ni + 0.5)) * (((k1 + 1) * fi * (k2 + 1) * qfi) / ((K + fi) * (k2 + qfi)))
							where,	N    = Total number of documents in the collection
									ni   = Number of documents containing the term
									k1   = 1.2
									k2   = 100
									b    = 0.75
									fi   = frequency of the term in the document
									qfi  = frequency of the term in the query
									K    = k1 * ((1 - b) + b * (dl / avdl))
									dl   = length of the document
									avdl = average length of all documents in the collection
							Since no relevance information is available, the parameters `R` and `ri` from the original BM25 computation have been ignored.
					3. Add the computed terms document-wise and return the result.
		. writeQueryResultsToOutput
					1. Sort the documents by their BM25 scores.
					2. Write query results to individual flat-file outputs.
