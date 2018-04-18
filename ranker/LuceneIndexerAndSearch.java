package ranker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;

/**
 * To create Apache Lucene index in a folder and add files into this index based on the input of the
 * user.
 */
public class LuceneIndexerAndSearch {
  private static Analyzer sAnalyzer;
  private static int NUM_OF_RESULTS_TO_RETURN = 100;
  private static String INDEX_LOCATION = "./output/lucene_indexer/Indexer/";
  private static String DOCS_LOCATION = "./input/documents/";
  private static String QUERY_FILE_PATH = "./input/query.txt";
  private static String RANKING_RESULTS_PATH = "./output/lucene_indexer/RankedResults/";


  private IndexWriter writer;
  private ArrayList<File> queue;

  public void indexRankAndQuery() {
    String indexLocation = INDEX_LOCATION;

    try {
      // clean up old index files and create a new one
      File dir = new File(INDEX_LOCATION);
      for (File file : dir.listFiles())
        file.delete();
      createIndex(INDEX_LOCATION);
    } catch (Exception ex) {
      System.out.println("Cannot create index..." + ex.getMessage());
      System.exit(-1);
    }

    // ===================================================
    // read and parse raw documents from raw docs location
    // ===================================================
    try {
      indexFileOrDirectory(DOCS_LOCATION);
    } catch (Exception e) {
      System.out.println("Error indexing " + DOCS_LOCATION + " : " + e.getMessage());
      System.exit(-1);
    }

    // ===================================================
    // after adding, we always have to call the
    // closeIndex, otherwise the index is not created
    // ===================================================
    try {
      closeIndex();
    } catch (Exception e) {
      System.out.println("Error closing index");
      System.exit(-1);
    }

    // =========================================================
    // Now search
    // =========================================================
    IndexReader reader = null;
    try {
      reader = DirectoryReader.open(FSDirectory.open(FileSystems.getDefault().getPath(
          indexLocation)));
    } catch (IOException e1) {
      System.out.println("Error reading index");
      System.exit(-1);
    }

    // =========================================================
    // Write query results to output
    // =========================================================
    FileUtility fu = new FileUtility();
    List<String> queryList = fu.textFileToList(QUERY_FILE_PATH);
    for (int queryID = 0; queryID < queryList.size(); queryID++) {
      String query = queryList.get(queryID);
      try {
        Query q = new QueryParser("contents", sAnalyzer).parse(query);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector =
            TopScoreDocCollector.create(NUM_OF_RESULTS_TO_RETURN);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        String outputFilePath =
            RANKING_RESULTS_PATH + "queryID_" + (queryID + 1) + ".txt";
        StringBuilder toOutput = new StringBuilder();
        // display results in console
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
          int docId = hits[i].doc;
          Document d = searcher.doc(docId);
          String resultLine = (queryID + 1) + " Q0 " + getDocIDFromDocName(d.get("path"))
              + " " + (i + 1) + " " + hits[i].score + " Lucene_Query_Parser";
          toOutput.append(resultLine);
          toOutput.append(System.getProperty("line.separator"));
          System.out.println(resultLine);
        }
        fu.writeStringToFile(toOutput.toString(), outputFilePath);

      } catch (Exception e) {
        System.out.println("Error searching " + query + " : " + e.getMessage());
        System.exit(-1);
      }
    }
  }

  private void createIndex(String indexDir) throws IOException {
    FSDirectory dir = FSDirectory.open(FileSystems.getDefault().getPath(indexDir));
    IndexWriterConfig config = new IndexWriterConfig(sAnalyzer);
    writer = new IndexWriter(dir, config);
  }

  /**
   * Returns docID sans extension
   */
  private String getDocIDFromDocName(String name) {
    name = name.substring(name.lastIndexOf('\\') + 1);
    return name.substring(0, name.lastIndexOf('.'));
  }


  /**
   * Constructor
   * 
   * @param indexDir the name of the folder in which the index should be created
   * @throws java.io.IOException when exception creating index.
   */
  LuceneIndexerAndSearch(String indexDir) {
    // init
    sAnalyzer = new SimpleAnalyzer();
    queue = new ArrayList<File>();
  }

  /** default constructor */
  LuceneIndexerAndSearch() {
    this(INDEX_LOCATION);
  }

  /**
   * Indexes a file or directory
   * 
   * @param fileName the name of a text file or a folder we wish to add to the index
   * @throws java.io.IOException when exception
   */
  public void indexFileOrDirectory(String fileName) throws IOException {
    // ===================================================
    // gets the list of files in a folder (if user has submitted
    // the name of a folder) or gets a single file name (is user
    // has submitted only the file name)
    // ===================================================
    addFiles(new File(fileName));

    int originalNumDocs = writer.numDocs();
    for (File f : queue) {
      FileReader fr = null;
      try {
        Document doc = new Document();

        // ===================================================
        // add contents of file
        // ===================================================
        fr = new FileReader(f);
        doc.add(new TextField("contents", fr));
        doc.add(new StringField("path", f.getPath(), Field.Store.YES));
        doc.add(new StringField("filename", f.getName(),
            Field.Store.YES));

        writer.addDocument(doc);
      } catch (Exception e) {
        System.out.println("Could not add: " + f);
      } finally {
        fr.close();
      }
    }

    int newNumDocs = writer.numDocs();
    System.out.println("");
    System.out.println("************************");
    System.out
        .println((newNumDocs - originalNumDocs) + " documents added.");
    System.out.println("************************");

    queue.clear();
  }

  private void addFiles(File file) {

    if (!file.exists()) {
      System.out.println(file + " does not exist.");
    }
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        addFiles(f);
      }
    } else {
      String filename = file.getName().toLowerCase();
      // ===================================================
      // Only index text files
      // ===================================================
      if (filename.endsWith(".htm") || filename.endsWith(".html")
          || filename.endsWith(".xml") || filename.endsWith(".txt")) {
        queue.add(file);
      } else {
        System.out.println("Skipped " + filename);
      }
    }
  }

  /**
   * Close the index.
   * 
   * @throws java.io.IOException when exception closing
   */
  public void closeIndex() throws IOException {
    writer.close();
  }
}
