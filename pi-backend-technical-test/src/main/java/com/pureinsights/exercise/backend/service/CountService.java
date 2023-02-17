package com.pureinsights.exercise.backend.service;

import com.pureinsights.exercise.backend.model.Count;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A search service with method to execute a query for count films-series by rate.
 * @author Jairo Ortega
 */
public interface CountService {
  
  /**
   * @param rate the rate range to query
   * @param pageRequest the page request configuration
   * @return a page with the results of the search
   */
  Page<Count> count(String rate, Pageable pageRequest);
}
