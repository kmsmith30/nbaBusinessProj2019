package business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import business.KeywordMap.PostData;

/**
 * Represents an Instagram Post
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 7, 2019 <v1.0>
 *
 */
public class Post {
    
    /* Post Values */
    private static final double INITIAL_CRITERIA = 4.0;

    /* Post Variables */

    private int engagements;
    private int followers;
    private LocalDateTime dateTime;
    private String timeZone;
    private PostType postType;
    private String description;

    private int estEngages;


    /* Post Constructor */

    /**
     * Constructor for an Instagram Post
     * 
     * @param e
     *            Engagements
     * @param f
     *            Followers at time of Post
     * @param dt
     *            Local Date/Time
     * @param tz
     *            Time Zone
     * @param pt
     *            Post Type
     * @param desc
     *            Post Description
     */
    public Post(
        int e,
        int f,
        LocalDateTime dt,
        String tz,
        PostType pt,
        String desc) {

        engagements = e;
        followers = f;
        dateTime = dt;
        timeZone = tz;
        postType = pt;
        description = desc;

        estEngages = -1;
    }


    /* Post Methods */

    /**
     * Get Engagements for Post
     * 
     * @return Number of Engagements
     */
    public int getEngagements() {
        return engagements;
    }


    /**
     * Get Followers at time of Post
     * 
     * @return Number of Followers at time of Post
     */
    public int getFollowers() {
        return followers;
    }


    /**
     * Get estimated Engagements
     * 
     * @return Estimated Engagements
     */
    public int getEstimation() {
        return estEngages;
    }


    /**
     * Get Local Date/Time of Post
     * 
     * @return Local Date/Time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }


    /**
     * Get Time Zone of Post
     * 
     * @return Time Zone
     */
    public String getTimeZone() {
        return timeZone;
    }


    /**
     * Get Post Type
     * 
     * @return Post Type
     */
    public PostType getPostType() {
        return postType;
    }


    /**
     * Get Post Description
     * 
     * @return Post Description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Set the Post Description
     * 
     * @param desc
     *            Post Description
     */
    public void setDescription(String desc) {
        description = desc;
    }


    /**
     * Check if Post is Photo Type
     * 
     * @return Post is Photo
     */
    public boolean isPhoto() {
        return postType == PostType.PHOTO;
    }


    /**
     * Check if Post is Album Type
     * 
     * @return Post is Album
     */
    public boolean isAlbum() {
        return postType == PostType.ALBUM;
    }


    /**
     * Check if Post is Video
     * 
     * @return Post is Video
     */
    public boolean isVideo() {
        return postType == PostType.VIDEO;
    }


    /**
     * Clean username/hashtag token
     * 
     * @param cleanme
     *            Token to clean
     * @return Cleaned token
     */
    private String cleanToken(String cleanme) {

        String cleaned = "";

        for (int i = 0; i < cleanme.length(); i++) {

            char c = cleanme.charAt(i);

            if (c == '’' || c == ',' || c == ')' || c == '!' || c == '?'
                || c == ':' || c == ';' || c == '"' || c == '\'' || c == '*'
                || c == ']' || c == '•')
                break;
            else if (c == '(' || c == '[')
                continue;
            else if (c == '.' && (i + 1) < cleanme.length()) {
                if (cleanme.charAt(i + 1) == '.') {
                    break;
                }
            }
            else if (c == '.' && cleanme.charAt(0) == '#')
                break;
            else if (c == '.' && (i + 1) == cleanme.length())
                break;
            else if (c == '/')
                cleaned += ' ';
            else
                cleaned += c;
        }

        return cleaned;
    }


    /**
     * Clean description to evaluate usernames/hashtags
     * (Get rid of characters that interfere w/ @'s and #'s)
     * 
     * @param desc
     *            Description to clean
     * @return Cleaned Description
     */
    private String cleanDescription(String desc) {

        String cleaned = desc.toLowerCase();

        cleaned = cleaned.replace('/', ' ');
        cleaned = cleaned.replace('|', ' ');
        cleaned = cleaned.replace('-', ' ');
        cleaned = cleaned.replace("...", " ");

        return cleaned;
    }
    /**
     * Add hashtag to hashtag list
     * 
     * @param keywords
     *            List of keywords to add to
     * @param tags
     *            Hashtags to add
     * @return Keyword list w/ added hashtags
     */
    private ArrayList<String> addHashtags(
        ArrayList<String> keywords,
        ArrayList<String> tags) {

        for (String tag : tags) {
            tag = cleanToken(tag);
            keywords.add(tag);
        }

        return keywords;
    }

    /**
     * Get hashtags from token
     * 
     * @param token
     *            Token to evaluate
     * @return List of hashtags
     */
    private ArrayList<String> getHashtags(String token) {

        ArrayList<String> hashtags = new ArrayList<String>();

        for (int i = 0; i < token.length(); i++) {

            String currToken = "";

            char c = token.charAt(i);

            if (c == '#' && i > 0) {
                hashtags.add(currToken);
                currToken = "";
            }
            else {
                currToken += c;
            }
        }

        return hashtags;
    }

    /**
     * Add keyword to keyword list
     * 
     * @param keywords
     *            Keyword list to add to
     * @param token
     *            Keyword token to add
     * @return Keyword list with added tokens
     */
    private ArrayList<String> addKeyword(
        ArrayList<String> keywords,
        String token) {

        String cleaned = cleanToken(token);

        keywords.add(cleaned);

        return keywords;
    }

    /**
     * Check if Post Description contains usernames or hashtags
     * 
     * @return List of keywords, or null if no keywords present
     */
    public ArrayList<String> hasKeywords() {

        ArrayList<String> keywords = new ArrayList<String>();

        String cleanDesc = cleanDescription(description);

        Scanner descScan = new Scanner(cleanDesc);

        while (descScan.hasNext()) {

            String token = descScan.next();

            if (token.startsWith("@") || token.startsWith("#")) {

                int hashtagCount = token.length() - token.replace("#", "")
                    .length();

                if (hashtagCount <= 1) {

                    keywords = addKeyword(keywords, token);
                }
                else {
                    ArrayList<String> hashtags = getHashtags(token);

                    keywords = addHashtags(keywords, hashtags);
                }
            }
        }
        descScan.close();

        if (!keywords.isEmpty())
            return keywords;
        else
            return null;
    }

    /* Criteria Weights */
    // -> Video types have far more engagements than photo+album
    // -> Significant keywords such as @kingjames and @stephencurry 
    //      generate more engagements

    public final double typeWeight = 4.712; // Optimal: 4.712
    public double dayWeight = 0.0001; // Optimal: 0.00001
    public double timeWeight = 0.0005; // Optimal: 0.0005
    public double monthWeight = 0.0009; // Optimal: 0.0009
    public double keywordWeight = 0.476; // Optimal: 0.4756

    /**
     * Estimate the number of engagements Post will have based on
     * time, day of week, month, Post Type, and keywords by averaging all
     * ratios that have a specific weight
     * 
     * @param hours
     *            Array of calculated hour ratios
     * @param days
     *            Array of calculated day of week ratios
     * @param months
     *            Array of calculated month ratios
     * @param types
     *            Array of Post Type ratios
     * @param keywordMap
     *            KeywordMap with known keywords and respective ratios
     * @param mode
     *            Switch between Training and Holdout sets
     * @return Difference of estimated and actual Engagements
     */
    public void estimateEngages(
        KeywordMap keywordMap,
        double[] hours,
        double[] days,
        double[] months,
        double[] types,
        String mode) {

        // MAPE: 26.246% when all weights 1.0
        // Optimal MAPE: 14.618%

        double dayRatio = getDayRatio(days) * dayWeight;
        double typeRatio = getTypeRatio(types) * typeWeight;
        double timeRatio = hours[dateTime.getHour()] * timeWeight;
        double monthRatio = months[dateTime.getMonthValue() - 1] * monthWeight;
        
        // 4 "categories", other than keywords
        double criteriaCount = INITIAL_CRITERIA; 

        ArrayList<String> keywords = hasKeywords();

        double estRatio = -1.0;
        double totalRatio = dayRatio + typeRatio + timeRatio + monthRatio;

        if (keywords != null) {
            for (String keyword : keywords) {

                if (mode.equals("Holdout") && !keywordMap.hasKeyword(keyword))
                    continue;

                criteriaCount += 1.0;

                PostData data = keywordMap.getPostData(keyword);

                double engageAvg = data.getEngagments().doubleValue()
                    / (double)data.getCount();
                double followAvg = data.getFollowers().doubleValue()
                    / (double)data.getCount();

                double currRatio = (engageAvg / followAvg) * keywordWeight;

                totalRatio += currRatio;
            }
        }

        estRatio = totalRatio / criteriaCount;

        estEngages = (int)(estRatio * followers);

        if (mode.equals("Holdout")) {
            engagements = estEngages;
        }
    }

    /**
     * Get ratio based off Post day of week
     * 
     * @param days
     *            Array of day of week ratios
     * @return Ratio for Post day of week
     */
    private double getDayRatio(double[] days) {

        switch (dateTime.getDayOfWeek().name()) {
            case "SUNDAY":
                return days[0];
            case "MONDAY":
                return days[1];
            case "TUESDAY":
                return days[2];
            case "WEDNESDAY":
                return days[3];
            case "THURSDAY":
                return days[4];
            case "FRIDAY":
                return days[5];
            case "SATURDAY":
                return days[6];
            default:
                return -1.0;
        }
    }

    /**
     * Get ratio based off Post Type
     * 
     * @param types
     *            Array of Post Type ratios
     * @return Ratio for Post Type
     */
    private double getTypeRatio(double[] types) {

        switch (postType) {
            case PHOTO:
                return types[0];
            case ALBUM:
                return types[1];
            case VIDEO:
                return types[2];
            default:
                return -1.0;
        }
    }
}
