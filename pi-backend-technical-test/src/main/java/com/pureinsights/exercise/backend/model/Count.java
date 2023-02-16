package com.pureinsights.exercise.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Definition for a Count of movies entity
 * @author Jairo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Count {
  private int count;
}
