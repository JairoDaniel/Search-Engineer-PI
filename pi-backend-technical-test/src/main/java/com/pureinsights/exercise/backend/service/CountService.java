package com.pureinsights.exercise.backend.service;

import com.pureinsights.exercise.backend.model.Count;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A search service with methods to execute queries in the movie collection
 * @author Jairo Ortega
 */
public interface CountService {
  /**
   * @param query the query to execute
   * @param pageRequest the page request configuration
   * @return a page with the results of the search
   */
  Page<Count> count(String rate, Pageable pageRequest);
}
