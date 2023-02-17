package com.pureinsights.exercise.backend.service;

import com.pureinsights.exercise.backend.model.Count;
import com.pureinsights.exercise.backend.repository.CountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link CountService}
 * @author Jairo Ortega
 */
@Service
public class CountServiceImpl implements CountService {

  @Autowired
  private CountRepository countRepository;


  @Override
  public Page<Count> count(String rate, Pageable pageRequest) {
    return countRepository.count(rate, pageRequest);
  }
}
