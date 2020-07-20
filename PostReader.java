package business;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Reader to turn "training_set.csv" and "holdout_set.csv" to
 * lists of Instagram Posts
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 7, 2019 <v1.0>
 *
 */
public class PostReader {

    /* PostReader Values */

    private static final String COMMA = ",";


    /* PostReader Constructor */

    /**
     * Empty Constructor for PostReader, all needed fields are known
     */
    public PostReader() {
    }


    /* PostReader Methods */

    /**
     * Read "training_set.csv" into list of Instagram Posts
     * 
     * @return List of Posts from training file
     */
    public ArrayList<Post> readPostFile() {

        ArrayList<Post> posts = new ArrayList<Post>();

        Scanner postScan = null;

        try {
            postScan = new Scanner(new File("training_set.csv"));

            String line = null;
            Scanner lineScan = null;

            int engages = -1, follows = -1;

            String date = null, zone = null, type = null, desc = null;

            while (postScan.hasNextLine()) {

                line = postScan.nextLine();

                lineScan = new Scanner(line);
                lineScan.useDelimiter(COMMA);

                if (line.contains("Followers at Posting"))
                    continue;

                if (lineScan.hasNextInt()) {
                    engages = lineScan.nextInt();
                }
                else {
                    Post fixPost = posts.remove(posts.size() - 1);
                    String fixDesc = fixPost.getDescription() + line;
                    fixPost.setDescription(fixDesc);
                    posts.add(fixPost);
                    continue;
                }

                if (lineScan.hasNextInt())
                    follows = lineScan.nextInt();
                if (lineScan.hasNext())
                    date = lineScan.next();
                if (lineScan.hasNext())
                    type = lineScan.next();
                if (lineScan.hasNext())
                    desc = lineScan.next();

                while (desc.charAt(0) == '"' && lineScan.hasNext()) {
                    desc += lineScan.next();
                }

                LocalDateTime ldt = makeDateTime(date);

                zone = date.substring(20, 23);

                PostType postType = getPostType(type);

                posts.add(new Post(engages, follows, ldt, zone, postType,
                    desc));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return posts;
    }


    /**
     * Read "holdout_set.csv" into list of Instagram Posts
     * 
     * @return List of Posts from holdout file
     */
    public ArrayList<Post> readHoldoutFile() {
        ArrayList<Post> posts = new ArrayList<Post>();

        Scanner postScan = null;

        try {
            postScan = new Scanner(new File("holdout_set.csv"));

            String line = null;

            Scanner lineScan = null;

            int follows = -1;

            String date = null, zone = null, type = null, desc = null;

            while (postScan.hasNextLine()) {

                line = postScan.nextLine();

                lineScan = new Scanner(line);
                lineScan.useDelimiter(COMMA);

                if (line.contains("Followers at Posting"))
                    continue;

                if (lineScan.hasNextInt()) {
                    follows = lineScan.nextInt();
                }
                else {
                    Post fixPost = posts.remove(posts.size() - 1);
                    String fixDesc = fixPost.getDescription() + line;
                    fixPost.setDescription(fixDesc);
                    posts.add(fixPost);
                    continue;
                }

                if (lineScan.hasNext())
                    date = lineScan.next();
                if (lineScan.hasNext())
                    type = lineScan.next();
                if (lineScan.hasNext())
                    desc = lineScan.next();

                while (desc.charAt(0) == '"' && lineScan.hasNext()) {
                    desc += lineScan.next();
                }

                LocalDateTime ldt = makeDateTime(date);

                zone = date.substring(20, 23);

                PostType postType = getPostType(type);

                posts.add(new Post(-1, follows, ldt, zone, postType, desc));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return posts;
    }


    /**
     * Get Post Type for Post
     * 
     * @param pt
     *            String value of Post Type
     * @return Post Type
     */
    private PostType getPostType(String pt) {
        switch (pt) {
            case "Photo":
                return PostType.PHOTO;
            case "Album":
                return PostType.ALBUM;
            case "Video":
                return PostType.VIDEO;
        }
        return PostType.POST_NULL;
    }


    /**
     * Turn Local Date/Time String into array of Date/Time variables
     * 
     * @param dt
     *            Date/Time String value
     * @return Array of Date/Time values
     */
    private int[] tokenizeDateTimeData(String dt) {

        int[] dateTimeTokens = new int[6];

        dateTimeTokens[0] = Integer.parseInt(dt.substring(0, 4));
        dateTimeTokens[1] = Integer.parseInt(dt.substring(5, 7));
        dateTimeTokens[2] = Integer.parseInt(dt.substring(8, 10));
        dateTimeTokens[3] = Integer.parseInt(dt.substring(11, 13));
        dateTimeTokens[4] = Integer.parseInt(dt.substring(14, 16));
        dateTimeTokens[5] = Integer.parseInt(dt.substring(17, 19));

        return dateTimeTokens;
    }


    /**
     * Make a LocalDateTime variable based off String value of Date/Time
     * 
     * @param dt
     *            Date/Time String value
     * @return LocalDateTime value
     */
    private LocalDateTime makeDateTime(String dt) {

        int[] dateTimeTokens = tokenizeDateTimeData(dt);

        int year = dateTimeTokens[0];
        int month = dateTimeTokens[1];
        int day = dateTimeTokens[2];

        int hour = dateTimeTokens[3];
        int minute = dateTimeTokens[4];
        int second = dateTimeTokens[5];

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

}
