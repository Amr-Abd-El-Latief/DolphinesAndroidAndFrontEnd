package net.hopesun.dolphins.models.faces;

/**
 * Created by ahmedabd-elbaky on 5/13/17.
 */

public class WTSNVRFace {
    public Age age;
    public FaceLocation face_location;
    public Gender gender;
    public Identity identity;

    public static class Age {
        public int max;
        public int min;
        public double score;
    }

    public static class FaceLocation {
        public int height;
        public int left;
        public int top;
        public int width;
    }

    public static class Gender {
        public String gender;
        public double score;
    }

    public static class Identity {
        public String name;
        public double score;
        public String type_hierarchy;
    }
}
