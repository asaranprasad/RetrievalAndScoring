package ranker;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import datastructure.IndexEntry;

public class BM25RankingAlgorithm {
  private static String UNIGRAM_INDEX_PATH = "./input/custom_indexer/unigramInvIndex.txt";
  private static String UNIGRAM_DOC_STAT_PATH =
      "./input/custom_indexer/unigramDocLength.txt";
  private static String QUERY_FILE_PATH = "./input/query.txt";
  private static String RANKING_RESULTS_PATH = "./output/BM25_RankedResults/";
  //inverted index
  private HashMap<String, HashMap<String, IndexEntry>> invIndex;
  private HashMap<String, Integer> docStat;
  private static int NUM_OF_RESULTS_TO_RETURN = 100;
  private static int N = 1000; // number of documents in the collection
  private int avdl; // average length of documents in the collection;
  FileUtility fu;

  public BM25RankingAlgorithm() {
    fu = new FileUtility();
    loadInvertedIndexFromFile(UNIGRAM_INDEX_PATH);
    loadDocStatFromFile(UNIGRAM_DOC_STAT_PATH);
  }

  /**
   * Loads documentId and its doclength infomation from the indexer
   */
  private void loadDocStatFromFile(String path) {
    docStat = new HashMap<String, Integer>();
    List<String> stats = fu.textFileToList(path);
    int totLength = 0;
    for (String stat : stats) {
      String docID = stat.split(" ")[0];
      int docLen = Integer.parseInt(stat.split(" ")[1]);
      totLength += docLen;
      docStat.put(docID, docLen);
    }
    avdl = totLength / N;
  }


  /**
   * Performs search of the query terms in the custom index, computes bm25 score and returns a mapped
   * set of the documentId and its bm25 score.
   * 
   * @param query
   */
  public HashMap<String, Double> search(String query) {
    // ===================================================
    // 1. Fetch all inverted lists corresponding to terms
    //    in the query.
    // ===================================================
    String[] terms = query.split(" ");
    HashMap<String, Integer> qf = new HashMap<String, Integer>();
    // Calculate term frequencies in the query
    for (String term : terms) {
      if (qf.containsKey(term))
        qf.put(term, qf.get(term) + 1);
      else
        qf.put(term, 1);
    }
    HashMap<String, Double> docScore = new HashMap<String, Double>();
    for (Entry<String, Integer> termEntry : qf.entrySet()) {
      String term = termEntry.getKey();
      int qfi = termEntry.getValue();

      // ===================================================
      // 2. Compute BM25 scores for documents in the lists.
      //    Make a score list for documents in the inverted
      //    lists. Assume that no relevance information is 
      //    available. For parameters, use k1=1.2, b=0.75, 
      //    k2=100.
      // ===================================================
      double k1 = 1.2;
      double b = 0.75;
      double k2 = 100;
      int ni = invIndex.get(term).size();


      for (Entry<String, IndexEntry> invListEntry : invIndex.get(term).entrySet()) {
        String docID = invListEntry.getKey();
        double bm25Score;
        if (docScore.containsKey(docID))
          bm25Score = docScore.get(docID);
        else
          bm25Score = 0;

        // length of the document
        int dl = docStat.get(docID);
        // frequency of the term in the document
        int fi = invListEntry.getValue().getTf();
        double K = k1 * ((1 - b) + b * ((double) dl / avdl));

        // ===================================================
        // 3. Accumulate scores for each term in a query
        //    on the score list.
        // ===================================================
        bm25Score += Math.log((N - ni + 0.5) / (ni + 0.5))
            * (((k1 + 1) * fi * (k2 + 1) * qfi) / ((K + fi) * (k2 + qfi)));
        docScore.put(docID, bm25Score);
      }
    }

    return docScore;
  }


  /**
   * Loads query list from input, performs search and writes back the search results in ranked order
   * of the documents' bm25 scores.
   */
  public void searchAndWriteQueryResultsToOutput() {

    List<String> queryList = fu.textFileToList(QUERY_FILE_PATH);

    for (int queryID = 0; queryID < queryList.size(); queryID++) {
      String query = queryList.get(queryID);
      // ===================================================
      // 4. Sort the documents by the BM25 scores.
      // ===================================================
      HashMap<String, Double> docScore = search(query);
      List<Map.Entry<String, Double>> rankedDocList =
          new LinkedList<Map.Entry<String, Double>>(docScore.entrySet());
      Collections.sort(rankedDocList, new MapComparatorByValues());

      // ===================================================
      // 5. Write Query Results to output
      // =================================================== 
      String outputFilePath =
          RANKING_RESULTS_PATH + "queryID_" + (queryID + 1) + ".txt";
      StringBuilder toOutput = new StringBuilder();
      // display results in console
      System.out.println("Found " + docScore.size() + " hits.");
      int i = 0;
      for (Entry<String, Double> scoreEntry : rankedDocList) {
        if (i >= NUM_OF_RESULTS_TO_RETURN)
          break;
        String docId = scoreEntry.getKey();
        Double score = scoreEntry.getValue();
        String resultLine =
            (queryID + 1) + " Q0 " + docId + " " + (i + 1) + " " + score + " BM25";
        toOutput.append(resultLine);
        toOutput.append(System.getProperty("line.separator"));
        System.out.println(resultLine);
        i++;
      }
      fu.writeStringToFile(toOutput.toString(), outputFilePath);
    }
  }

  /**
   * Parses flat file containing the inverted index and reads it into the indexer datastructure in
   * memory
   * 
   * @param indexPath - path to the input flat file
   */
  private void loadInvertedIndexFromFile(String indexPath) {
    try {
      invIndex = new HashMap<String, HashMap<String, IndexEntry>>();
      Scanner sc = new Scanner(new File(indexPath));
      int lineNum = 0;
      while (sc.hasNextLine()) {
        String entry = sc.nextLine().trim();
        if (!entry.isEmpty()) {
          String[] termValue = entry.trim().split(" -> ");
          String[] docEntries = termValue[1].split("\\) \\(");
          // clean up the opening and closing paranthesis post split
          if (docEntries[0].charAt(0) == '(')
            docEntries[0] = docEntries[0].substring(1);
          String lastDocEntry = docEntries[docEntries.length - 1];
          if (lastDocEntry.charAt(lastDocEntry.length() - 1) == ')')
            docEntries[docEntries.length - 1] =
                lastDocEntry.substring(0, lastDocEntry.length() - 1);
          HashMap<String, IndexEntry> invEntryVal = new HashMap<String, IndexEntry>();
          for (String docEntry : docEntries) {
            String[] components = docEntry.split(", ");
            String docID = components[0];
            int tf = Integer.parseInt(components[1]);

            // if term positions information present
            List<Integer> termPos = new LinkedList<Integer>();
            if (components.length > 2) {
              String[] pos = components[2].replace("[", "").replace(" ]", "").split(" ");
              for (String eachPos : pos)
                termPos.add(Integer.parseInt(eachPos));
            }
            IndexEntry ie = new IndexEntry(docID, termPos, tf);
            invEntryVal.put(docID, ie);
          }
          invIndex.put(termValue[0], invEntryVal);
          System.out.println((++lineNum) + " | " + termValue[0]);
        }
      }
      sc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}


/**
 * Custom comparator to sort an HashMap by its values
 */
class MapComparatorByValues implements Comparator<Map.Entry<String, Double>> {
  @Override
  public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
    return (o2.getValue()).compareTo(o1.getValue());
  }
}
