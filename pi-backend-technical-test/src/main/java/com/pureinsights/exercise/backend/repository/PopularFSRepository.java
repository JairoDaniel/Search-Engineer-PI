package com.pureinsights.exercise.backend.repository;

import com.pureinsights.exercise.backend.model.PopularFS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository for {@link film-serie} entities
 * @author Jairo
 */
public interface PopularFSRepository {
  /**
   * @param rate category of rate range
   * @param genre category of movies-series genre
   * @param pageRequest the page request configuration
   * @return a page with the results of the search
   */
  Page<PopularFS> search(String rate, String genre, Pageable pageRequest);
}
