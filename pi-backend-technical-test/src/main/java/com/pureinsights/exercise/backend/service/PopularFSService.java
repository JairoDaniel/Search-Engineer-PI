package com.pureinsights.exercise.backend.service;

import com.pureinsights.exercise.backend.model.PopularFS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A search service with methods to execute queries for films-series in the elasticsearch index
 * @author Jairo Ortega
 */
public interface PopularFSService {
  /**
   * @param rate category of rate range
   * @param genre category of movies-series genre
   * @param pageRequest the page request configuration
   * @return a page with the results of the search
   */
  Page<PopularFS> search(String rate, String genre, Pageable pageRequest);
}
