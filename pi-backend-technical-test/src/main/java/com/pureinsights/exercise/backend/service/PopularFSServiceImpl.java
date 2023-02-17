package com.pureinsights.exercise.backend.service;

import com.pureinsights.exercise.backend.model.PopularFS;
import com.pureinsights.exercise.backend.repository.PopularFSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link Film-SerieService}
 * @author Jairo Ortega
 */
@Service
public class PopularFSServiceImpl implements PopularFSService {

  @Autowired
  private PopularFSRepository popularFSRepository;


  @Override
  public Page<PopularFS> search(String rate, String genre,Pageable pageRequest) {
    return popularFSRepository.search(rate, genre, pageRequest);
  }
}
