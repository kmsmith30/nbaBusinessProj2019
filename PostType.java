package business;

/**
 * Enum for Instagram Post Types:
 * Photo, Album, and Video
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 7, 2019 <v1.0>
 *
 */
public enum PostType {
    PHOTO("Photo"), ALBUM("Album"), VIDEO("Video"), POST_NULL("");

    private String type;


    private PostType(String t) {
        type = t;
    }


    @Override
    public String toString() {
        return type;
    }
}
