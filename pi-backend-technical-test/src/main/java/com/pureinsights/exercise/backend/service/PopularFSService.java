package com.pureinsights.exercise.backend.service;

import com.pureinsights.exercise.backend.model.PopularFS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A search service with methods to execute queries in the movie collection
 * @author Jairo Ortega
 */
public interface PopularFSService {
  /**
   * @param query the query to execute
   * @param pageRequest the page request configuration
   * @return a page with the results of the search
   */
  Page<PopularFS> search(String rate, String genre, Pageable pageRequest);
}
