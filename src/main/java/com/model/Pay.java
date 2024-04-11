package com.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Pay {
    private Map<String, Double> conversionRate;

    public Pay() {
        conversionRate = new HashMap<>();
        conversionRate.put("ISK", 1.0);
        conversionRate.put("EUR", 149.10);
        conversionRate.put("GBP", 174.58);
        conversionRate.put("USD", 137.42);
    }

    public long getISK(Double value, String currency) {
        double conversionFactor = getConversionRate(currency);
        return Math.round(value * conversionFactor);
    }

    public double getConversionRate(String currency) {
        return conversionRate.getOrDefault(currency, 0.0);
    }
}
