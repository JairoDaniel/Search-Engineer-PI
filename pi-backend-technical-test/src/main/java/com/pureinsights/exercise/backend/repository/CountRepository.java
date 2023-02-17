package com.pureinsights.exercise.backend.repository;

import com.pureinsights.exercise.backend.model.Count;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository for {@link Count} entities
 * @author Jairo Ortega
 */
public interface CountRepository {
  /**
   * @param rate the rate range to query
   * @param pageRequest the page request configuration
   * @return a page with the results of the search
   */
  Page<Count> count(String rate, Pageable pageRequest);
}
