package ranker;

public class RankingCaller {
  /**
   * Calls utility methods from Lucene as well as custom written BM25 retrieval model in order to draw
   * comparison between the two retrieval systems
   * 
   * @param args
   */
  public static void main(String[] args) {

    // =========================================================
    // Perform Indexing and Retrieval - Lucene
    // =========================================================
    LuceneIndexerAndSearch lucene = new LuceneIndexerAndSearch();
    lucene.indexRankAndQuery();

    // =========================================================
    // Perform Retrieval - BM25
    // =========================================================
    BM25RankingAlgorithm bm25 = new BM25RankingAlgorithm();
    bm25.searchAndWriteQueryResultsToOutput();
  }
}
