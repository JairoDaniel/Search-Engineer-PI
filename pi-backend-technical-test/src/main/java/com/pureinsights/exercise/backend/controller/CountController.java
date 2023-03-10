package com.pureinsights.exercise.backend.controller;

import com.pureinsights.exercise.backend.model.Count;
import com.pureinsights.exercise.backend.service.CountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for counting by range of rate endpoints
 * @author Jairo Ortega
 */
@Tag(name = "Count")
@RestController("/count")
public class CountController {

  @Autowired
  private CountService countService;


  @Operation(summary = "Count the amount of movies-series of one particular rate range", description = "Executes a search for counting the elements of any group of rate (i, ii, iii, iv, v).")
  @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Count>> search(@RequestParam("rate") String rate, @ParameterObject Pageable pageRequest) {
    return ResponseEntity.ok(countService.count(rate, pageRequest));
  }
}
