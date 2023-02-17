package com.pureinsights.exercise.backend.repository;

import com.opencsv.CSVReader;
import com.pureinsights.exercise.backend.model.PopularFS;
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
 * In-memory implementation of {@link films-seriesRepository}
 * @author Jairo Ortega
 */
@Repository
@Slf4j
public class InMemoryPopularFSRepository implements PopularFSRepository, Closeable {
  /** Variable for the low limit of the range rate to search */
  private double lowLimit = 0.0;
  /** Variable for the high limit of the range rate to search */
  private double highLimit = 0.0;
  /** Field for query the index on Elasticsearch for searching films-series  */
  private static final String esQueryEndPoint = "imdb/_search";
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
  public Page<PopularFS> search(String rate, String genre, Pageable pageRequest) {
    double[] limits =  adapterLimits.translateRange(rate);
    lowLimit = limits[0];
    highLimit = limits[1];
    int countPage = 1;
    try{
      String esQueryCount = "{\"query\":{\"bool\":{\"must\":{\"range\":{\"Rate\":{\"gte\":" + lowLimit + ",\"lt\": "+ highLimit +"}}},\"filter\":{\"match\":{\"Genre\":\"*" + genre + "*\"}}}}}";
      JSONObject esResponse = new JSONObject(requester.POST(esQueryEndPoint, esQueryCount));
      System.out.println("ElasticSearch response: " + esResponse.toString());

      JSONObject fakeESResponse = new JSONObject("{\"_index\":\"imdb\",\"_id\":\"y1WuS4YBT2NvTCu5O7vV\",\"_score\":1.0,\"_source\":{\"Name\":\"RockofAges\",\"Rate\":\"5.9\",\"Genre\":\"Comedy,Drama,Musical\"}},{\"_index\":\"imdb\",\"_id\":\"3FWuS4YBT2NvTCu5O7v_\",\"_score\":1.0,\"_source\":{\"Name\":\"After.Life\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Mystery,Thriller\"}},{\"_index\":\"imdb\",\"_id\":\"PFWuS4YBT2NvTCu5IrJW\",\"_score\":1.0,\"_source\":{\"Name\":\"FearofRain\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Horror,Thriller\"}},{\"_index\":\"imdb\",\"_id\":\"qFWuS4YBT2NvTCu5LrZV\",\"_score\":1.0,\"_source\":{\"Name\":\"Cursed\",\"Rate\":\"5.9\",\"Genre\":\"Adventure,Drama,Fantasy\"}},{\"_index\":\"imdb\",\"_id\":\"91WuS4YBT2NvTCu5L7Ys\",\"_score\":1.0,\"_source\":{\"Name\":\"FrenchExit\",\"Rate\":\"5.9\",\"Genre\":\"Comedy,Drama\"}},{\"_index\":\"imdb\",\"_id\":\"o1WuS4YBT2NvTCu5MLft\",\"_score\":1.0,\"_source\":{\"Name\":\"MelrosePlace\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Romance\"}},{\"_index\":\"imdb\",\"_id\":\"-1WuS4YBT2NvTCu5MbfW\",\"_score\":1.0,\"_source\":{\"Name\":\"Carrie\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Horror\"}},{\"_index\":\"imdb\",\"_id\":\"YVWuS4YBT2NvTCu5Mrjk\",\"_score\":1.0,\"_source\":{\"Name\":\"SilkRoad\",\"Rate\":\"5.9\",\"Genre\":\"Crime,Drama,Thriller\"}},{\"_index\":\"imdb\",\"_id\":\"rFWuS4YBT2NvTCu5KLTu\",\"_score\":1.0,\"_source\":{\"Name\":\"TheHauntinginConnecticut\",\"Rate\":\"5.9\",\"Genre\":\"Drama,Horror,Mystery\"}},{\"_index\":\"imdb\",\"_id\":\"KVWuS4YBT2NvTCu5KrVF\",\"_score\":1.0,\"_source\":{\"Name\":\"IntotheWoods\",\"Rate\":\"5.9\",\"Genre\":\"Adventure,Comedy,Drama\"}}");
    
      var movies = Stream.of(fakeESResponse)
        .map(doc -> PopularFS.builder().Name(fakeESResponse.getJSONObject("_source").getString("Name"))
          .Rate(Double.parseDouble(fakeESResponse.getJSONObject("_source").getString("Rate")))
          .Genre(fakeESResponse.getJSONObject("_source").getString("Genre")).build())
        .collect(Collectors.toList());
      return new PageImpl<>(movies, pageRequest, countPage);
    } catch (IOException ex) {
        throw new IllegalStateException(ex);
    }      
  }

  @Override
  public void close() throws IOException {
    log.info("Close dependencies.");    
  }
}
