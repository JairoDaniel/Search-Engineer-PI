package com.pureinsights.exercise.backend.repository;

/**
 * Class to convert the category to limits
 * @author Jairo Ortega
 */
public class AdapterRangeRate {
    /**
     * @param range category of the rate limits (i, ii, iii, iv, v)
     * @return the low and high limits for searching films-series by rate
     */
    public double[] translateRange(String range){
        double lowLimit = 0.0;
        double highLimit = 0.0;
        double[] response = new double[2];
        switch (range) {
            case "i":
                lowLimit = 8.0;
                highLimit = 10.0;
                break;
            case "ii":
                lowLimit = 6.0;
                highLimit = 8.0;
                break;
            case "iii":
                lowLimit = 4.0;
                highLimit = 6.0;
                break;
            case "iv":
                lowLimit = 2.0;
                highLimit = 4.0;
                break;
            default:
                lowLimit = 0.0;
                highLimit = 2.0;
                break;
        }
        response[0]=lowLimit;
        response[1]=highLimit;
        return response;
    }
}