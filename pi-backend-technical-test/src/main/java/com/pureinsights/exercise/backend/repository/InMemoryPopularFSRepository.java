package com.pureinsights.exercise.backend.repository;

import com.opencsv.CSVReader;
import com.pureinsights.exercise.backend.model.PopularFS;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

/**
 * In-memory implementation of {@link MovieRepository}
 * @author Jairo
 */
@Repository
@Slf4j
public class InMemoryPopularFSRepository implements PopularFSRepository, Closeable {
  /** Input file with the movie collection */
  private static final String MOVIE_COLLECTION = "in-memory-movies.csv";
  /** Field with the name of the movie */
  private static final String NAME_FIELD = "name";
  /** Field with the rating */
  private static final String YEAR_FIELD = "year";

  private static final String COUNT_FIELD = "count";

  /** Default analyzer for query/index */
  private static final Analyzer DEFAULT_ANALYZER = new StandardAnalyzer();
  /** Query parser for the name field */ 
  private static final QueryParser QUERY_PARSER = new QueryParser(NAME_FIELD, DEFAULT_ANALYZER);

  /** In-memory Lucene index */
  protected final Directory movieIndex = new ByteBuffersDirectory();
  /** Reader for the movie collection */
  protected DirectoryReader indexReader;
  /** Searcher for the movie collection */
  protected IndexSearcher indexSearcher;


  @PostConstruct
  void init() throws IOException {
    log.info("Initializing in-memory index with collection: {}", MOVIE_COLLECTION);
    

    log.info("In-memory index successfully initialized");
  }


  @Override
  public Page<PopularFS> search(String rate, String genre, Pageable pageRequest) {
      var count = 1;

      JSONObject jo = new JSONObject("{\"_index\":\"imdb\",\"_id\":\"y1WuS4YBT2NvTCu5O7vV\",\"_score\":1.0,\"_source\":{\"Name\":\"RockofAges\",\"Rate\":\"5.9\",\"Genre\":\"Comedy,Drama,Musical\"}},{\"_index\":\"imdb\",\"_id\":\"3FWuS4YBT2NvTCu5O7v_\",\"_score\":1.0,\"_source\":{\"Name\":\"After.Life\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Mystery,Thriller\"}},{\"_index\":\"imdb\",\"_id\":\"PFWuS4YBT2NvTCu5IrJW\",\"_score\":1.0,\"_source\":{\"Name\":\"FearofRain\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Horror,Thriller\"}},{\"_index\":\"imdb\",\"_id\":\"qFWuS4YBT2NvTCu5LrZV\",\"_score\":1.0,\"_source\":{\"Name\":\"Cursed\",\"Rate\":\"5.9\",\"Genre\":\"Adventure,Drama,Fantasy\"}},{\"_index\":\"imdb\",\"_id\":\"91WuS4YBT2NvTCu5L7Ys\",\"_score\":1.0,\"_source\":{\"Name\":\"FrenchExit\",\"Rate\":\"5.9\",\"Genre\":\"Comedy,Drama\"}},{\"_index\":\"imdb\",\"_id\":\"o1WuS4YBT2NvTCu5MLft\",\"_score\":1.0,\"_source\":{\"Name\":\"MelrosePlace\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Romance\"}},{\"_index\":\"imdb\",\"_id\":\"-1WuS4YBT2NvTCu5MbfW\",\"_score\":1.0,\"_source\":{\"Name\":\"Carrie\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Horror\"}},{\"_index\":\"imdb\",\"_id\":\"YVWuS4YBT2NvTCu5Mrjk\",\"_score\":1.0,\"_source\":{\"Name\":\"SilkRoad\",\"Rate\":\"5.9\",\"Genre\":\"Crime,Drama,Thriller\"}},{\"_index\":\"imdb\",\"_id\":\"rFWuS4YBT2NvTCu5KLTu\",\"_score\":1.0,\"_source\":{\"Name\":\"TheHauntinginConnecticut\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Horror,Mystery\"}},{\"_index\":\"imdb\",\"_id\":\"KVWuS4YBT2NvTCu5KrVF\",\"_score\":1.0,\"_source\":{\"Name\":\"IntotheWoods\",\"Rate\":\"5.9\",\"Genre\":\"Adventure,Comedy,Drama\"}}");

      var movies = Stream.of(jo)
          .map(doc -> PopularFS.builder().Name(jo.getJSONObject("_source").getString("Name"))
            .Rate(Double.parseDouble(jo.getJSONObject("_source").getString("Rate")))
            .Genre(jo.getJSONObject("_source").getString("Genre")).build())
          .collect(Collectors.toList());
        
      return new PageImpl<>(movies, pageRequest, 39);
  }


  @Override
  public void close() throws IOException {
    if (indexReader != null) {
      indexReader.close();
    }

    movieIndex.close();
  }
}
