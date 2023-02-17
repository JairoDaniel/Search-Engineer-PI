package com.pureinsights.exercise.backend.repository;

import com.pureinsights.exercise.backend.model.Count;
import lombok.extern.slf4j.Slf4j;
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
import java.util.*;

/**
 * In-memory implementation of {@link CountRepository}
 * @author Jairo Ortega
 */
@Repository
@Slf4j
public class InMemoryCountRepository implements CountRepository, Closeable {
  /** Variable for the low limit of the range rate to search */
  private double lowLimit = 0.0;
  /** Variable for the high limit of the range rate to search */
  private double highLimit = 0.0;
  /** Field for query the index on Elasticsearch for counting films-series  */
  private static final String esQueryEndPoint = "imdb/_count";
  /** Instance of the http handler for processing http requests */
  private HttpHandler requester = new HttpHandler();
  /** Instance of the adapter to set the range of the elasticsearch query limits */
  private AdapterRangeRate adapterLimits = new AdapterRangeRate();

  @PostConstruct
  void init() throws IOException {
    log.info("Initializing index.");

    log.info("Index successfully initialized.");
  }


  @Override
  public Page<Count> count(String rate, Pageable pageRequest){
    double[] limits =  adapterLimits.translateRange(rate);
    lowLimit = limits[0];
    highLimit = limits[1];
    try{
      String esQueryCount = "{\"query\":{\"range\":{\"Rate\":{\"gte\":" + lowLimit + ",\"lt\": "+ highLimit +"}}}}";
      JSONObject esResponse = new JSONObject(requester.POST(esQueryEndPoint, esQueryCount));
      var movies_count = Stream.of(esResponse)
        .map(doc -> Count.builder().count(esResponse.getInt("count")).build())
        .collect(Collectors.toList());
      return new PageImpl<>(movies_count);
    } catch (IOException ex) {
        throw new IllegalStateException(ex);
    }
  }

  @Override
  public void close() throws IOException {
    log.info("Close dependencies.");    
  }
}
