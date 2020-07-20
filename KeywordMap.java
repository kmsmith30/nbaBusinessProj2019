package business;

import java.util.HashMap;
import java.util.ArrayList;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Holds data for keywords that appear in multiple Posts
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 13, 2019 <v1.0>
 *
 */
public class KeywordMap {

    /* KeywordMap Variables */

    private HashMap<String, PostData> keywordMap;


    /* KeywordMap Constructor */

    /**
     * Construct a HashMap with String keys and PostData values to use for
     * KeywordMap
     */
    public KeywordMap() {
        keywordMap = new HashMap<String, PostData>();
    }


    /* KeywordMap Methods */

    /**
     * Get KeywordMap HashMap
     * 
     * @return HashMap in KeywordMap
     */
    public HashMap<String, PostData> getMap() {
        return keywordMap;
    }


    /**
     * Handle a keyword within KeywordMap
     * 
     * @param post
     *            Post to evaluate
     * @param key
     *            Keyword with Post Description
     */
    public void handleKeyword(Post post, String key) {
        PostData data = new PostData();

        if (!hasKeyword(key))
            keywordMap.put(key, data);

        handlePost(post, key);
    }


    /**
     * Check if KeywordMap alreader contains keyword
     * 
     * @param key
     *            Keyword to check
     * @return If key is already in KeywordMap
     */
    public boolean hasKeyword(String key) {
        return keywordMap.containsKey(key);
    }


    /**
     * Get PostData for keyword
     * 
     * @param key
     *            Keyword to get PostData for
     * @return PostData for key in KeywordMap
     */
    public PostData getPostData(String key) {
        return keywordMap.get(key);
    }


    /**
     * Handle data for Post in KeywordMap
     * 
     * @param post
     *            Post to evaluate
     * @param key
     *            Keyword mentioned in Post
     */
    private void handlePost(Post post, String key) {

        PostData data = keywordMap.remove(key);

        data.incrementCount();

        data.addEngagements(post.getEngagements());
        data.addFollowers(post.getFollowers());

        data.addDate(post.getDateTime());

        keywordMap.put(key, data);
    }


    /**
     * Get count of keyword mentions
     * 
     * @param key
     *            Keyword to get count
     * @return Count of key mentions
     */
    public int getKeywordCount(String key) {
        return keywordMap.get(key).count;
    }


    /**
     * Get total Engagements for keyword
     * 
     * @param key
     *            Keyword to get Engagements
     * @return Total Engagements for key
     */
    public BigInteger getEngagements(String key) {
        return keywordMap.get(key).engagements;
    }


    /**
     * Get total Followres for keyword
     * 
     * @param key
     *            Keyword to get Followers
     * @return Total Followers for key
     */
    public BigInteger getFollowers(String key) {
        return keywordMap.get(key).followers;
    }


    /**
     * Get Engagement/Follower ratio for keyword
     * 
     * @param key
     *            Keyword to get ratio
     * @return Engagement/Follower ratio for key
     */
    public double getRatio(String key) {
        return keywordMap.get(key).getRatio();
    }


    /**
     * Internal class to hold Post data in KeywordMap
     * 
     * @author Kevin M. Smith <kmsmith3@vt.edu>
     * @version July 13, 2019 <v1.0>
     *
     */
    public class PostData {

        /* PostData Variables */

        private int count;

        private BigInteger engagements;
        private BigInteger followers;

        private ArrayList<LocalDateTime> dates;


        /* PostData Constructor */

        /**
         * Construct PostData to be used in KeywordMap
         */
        public PostData() {
            count = 0;

            engagements = new BigInteger("0");
            followers = new BigInteger("0");

            dates = new ArrayList<LocalDateTime>();
        }


        /* PostData Methods */

        /**
         * Get count of keyword mentions in posts
         * 
         * @return Count of keyword mentions
         */
        public int getCount() {
            return count;
        }


        /**
         * Get PostData calculated Engagement/Follower ratio
         * 
         * @return Calculated Engagement/Follower ratio
         */
        public double getRatio() {

            double engageAvg = engagements.doubleValue() / (double)count;
            double followAvg = engagements.doubleValue() / (double)count;

            return (double)(engageAvg / followAvg);
        }


        /**
         * Get PostData Engagements
         * 
         * @return Engagements
         */
        public BigInteger getEngagments() {
            return engagements;
        }


        /**
         * Get PostData Followers
         * 
         * @return Followers
         */
        public BigInteger getFollowers() {
            return followers;
        }


        /**
         * Get list of LocalDateTime associated with keyword PostData
         * 
         * @return List of LocalDateTimes
         */
        public ArrayList<LocalDateTime> getDates() {
            return dates;
        }


        /**
         * Increment keyword count in PostData
         */
        public void incrementCount() {
            count++;
        }


        /**
         * Add Engagements to PostData total
         * 
         * @param engages
         *            Engagements to add
         */
        public void addEngagements(int engages) {
            engagements = engagements.add(new BigInteger(Integer.toString(
                engages)));
        }


        /**
         * Add Followers to PostData total
         * 
         * @param follows
         *            Followers to add
         */
        public void addFollowers(int follows) {
            followers = followers.add(new BigInteger(Integer.toString(
                follows)));
        }


        /**
         * Add LocalDateTime to DateTime list
         * 
         * @param date
         *            LocalDateTime to add
         */
        public void addDate(LocalDateTime date) {
            dates.add(date);
        }
    }
}
