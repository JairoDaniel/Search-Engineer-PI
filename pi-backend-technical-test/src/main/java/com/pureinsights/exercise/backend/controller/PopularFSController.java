package com.pureinsights.exercise.backend.controller;

import com.pureinsights.exercise.backend.model.PopularFS;
import com.pureinsights.exercise.backend.service.PopularFSService;
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
 * REST Controller for the search endpoints
 * @author Jairo Ortega
 */
@Tag(name = "searchFS")
@RestController("/searchFS")
public class PopularFSController {

  @Autowired
  private PopularFSService popularFSService;


  @Operation(summary = "Search films-series by rate and genre", description = "Executes a search for films-series by rate range (i, ii, iii, iv, v) and genre.")
  @GetMapping(value = "/searchFS", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<PopularFS>> search(@RequestParam("rate") String rate, @RequestParam("genre") String genre, @ParameterObject Pageable pageRequest) {
    return ResponseEntity.ok(popularFSService.search(rate, genre, pageRequest));
  }
}
