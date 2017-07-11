package net.hopesun.dolphins.models.classifier;

/**
 * Created by ahmedabd-elbaky on 5/13/17.
 */

public class WTSNVRClass implements Comparable<WTSNVRClass>{
    public String class_;
    public float score;
    public String type_hierarchy;

    @Override
    public int compareTo(WTSNVRClass wtsnvrClass) {
        return (int)(this.score * 1000 - wtsnvrClass.score * 1000);
    }
}