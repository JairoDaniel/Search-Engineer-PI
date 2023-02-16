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


/**
 * In-memory implementation of {@link CountRepository}
 * @author Jairo Ortega
 */
@Repository
@Slf4j
public class InMemoryCountRepository implements CountRepository, Closeable {

  private double lowLimit = 0.0;
  private double highLimit = 0.0;

  private httpHelper requester = new httpHelper();

  
  private static final String esQueryEndPoint = "imdb/_count";



  @PostConstruct
  void init() throws IOException {
    log.info("Initializing in-memory index with collection");

    log.info("In-memory index successfully initialized");
  }


  @Override
  public Page<Count> count(String rate, Pageable pageRequest){
    
    switch (rate) {
      case "i":
        lowLimit = 8.0;
        highLimit = 10.0;
        break;
      case "ii":
        lowLimit = 6.0;
        highLimit = 8.0;
        break;
      case "iii":
        lowLimit = 4.0;
        highLimit = 6.0;
        break;
      case "iv":
        lowLimit = 2.0;
        highLimit = 4.0;
        break;
      default:
        lowLimit = 0.0;
        highLimit = 2.0;
        break;
    }
    try{
      String jsonInputString = "{\"query\":{\"range\":{\"Rate\":{\"gte\":" + lowLimit + ",\"lt\": "+ highLimit +"}}}}";
      JSONObject jo = new JSONObject(requester.POST(esQueryEndPoint, jsonInputString));
    
      var movies_count = Stream.of(jo)
        .map(doc -> Count.builder().count(jo.getInt("count")).build())
        .collect(Collectors.toList());
      
      return new PageImpl<>(movies_count, pageRequest, 1);
    } catch (IOException ex) {
        throw new IllegalStateException(ex);
    }
  }

  @Override
  public void close() throws IOException {
    
  }
}
