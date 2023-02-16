package com.pureinsights.exercise.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Definition for a Movie entity
 * @author Jairo Ortega
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularFS {
  private String Name;
  private double Rate;
  private String Genre;
}
