package com.pureinsights.exercise.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Definition for a count of films-series entity
 * @author Jairo Ortega
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Count {
  private int count;
}
