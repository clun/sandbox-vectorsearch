package com.datastax.sandbox;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Bean used to map Vector type from Cassandra.
 */
public class VectorSearch {

    private Float[] vector;

    /**
     * Default Constructor
     */
    public VectorSearch() {
    }

    /**
     * Init with a byte buffer
     * @param input
     */
    public VectorSearch(ByteBuffer input) {
        vector = new Float[768];
        for (int i = 0; i < 768; i++) {
            vector[i] = input.getFloat(i*4);
        }
    }

    /**
     * Init with a list of string.
     *
     * @param input
     *      input
     */
    public VectorSearch(List<String> input) {
        vector = new Float[768];
        for (int i = 0; i < 768; i++) {
            vector[i] = Float.parseFloat(input.get(i));
        }
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        return Arrays.asList(vector).stream().map(f -> df.format(f))
                .collect(Collectors.toList()).toString()
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
    }

    /**
     * Gets vector
     *
     * @return value of vector
     */
    public Float[] getVector() {
        return vector;
    }

    /**
     * Set value for vector
     *
     * @param vector
     *         new value for vector
     */
    public void setVector(Float[] vector) {
        this.vector = vector;
    }
}
