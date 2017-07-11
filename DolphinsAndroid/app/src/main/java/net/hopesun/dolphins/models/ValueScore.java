package net.hopesun.dolphins.models;

/**
 * Created by ahmedabd-elbaky on 5/13/17.
 */

public class ValueScore implements Comparable<ValueScore>{
    public String value;
    public float score;

    public ValueScore() {}

    public ValueScore(String value, float score) {
        this.value = value;
        this.score = score;
    }

    @Override
    public int compareTo(ValueScore valueScore) {
        return (int)(this.score * 1000 - valueScore.score * 1000);
    }
}
