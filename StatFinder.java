package business;

import java.math.BigInteger;
import java.util.ArrayList;
// import java.text.DecimalFormat;

/**
 * Calculates Engagement/Follower ratios based off training file
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 13, 2019 <v1.0>
 * 
 */
public class StatFinder {

    /* StatFinder Values */

    private final int SUN = 0, MON = 1, TUE = 2, WED = 3, THU = 4, FRI = 5,
        SAT = 6;
    private final int DAYS_OF_WEEK = 7;

    private final int PHO = 0, ALB = 1, VID = 2;
    private final int NUM_TYPES = 3;

    /* StatFinder Variables */

    // private DecimalFormat df; // Used for to inspect ratio values
    private ArrayList<Post> posts;

    private KeywordMap keywordMap;


    /* StatFinder constructor */

    /**
     * StatFinder constructor with Post list
     * 
     * @param posts
     *            List of Posts to calculate
     */
    public StatFinder(ArrayList<Post> posts) {
        // df = new DecimalFormat("#.#######"); // Round ratios to 8 decimals
        this.posts = posts;

        keywordMap = new KeywordMap();
    }


    /* StatFinder Methods */

    /**
     * Make KeywordMap based on keywords in posts
     * 
     * @return Calculated KeywordMap
     */
    public KeywordMap calculateByKeyword() {

        for (Post post : posts) {

            ArrayList<String> keywords = post.hasKeywords();

            if (keywords == null)
                continue;

            for (String key : keywords) {
                keywordMap.handleKeyword(post, key);
            }
        }

        return keywordMap;
    }


    /**
     * Make double array of ratios based on day of week posted
     * 
     * @return Array of calculated day of week ratios
     */
    public double[] calculateByDay() {

        // Init sizes
        int[] daysCount = new int[DAYS_OF_WEEK];
        BigInteger[] daysEngages = new BigInteger[DAYS_OF_WEEK];
        BigInteger[] daysFollows = new BigInteger[DAYS_OF_WEEK];
        // Init all to 0
        for (int i = 0; i < DAYS_OF_WEEK; i++) {
            daysCount[i] = 0;
            daysEngages[i] = new BigInteger("0");
            daysFollows[i] = new BigInteger("0");
        }

        for (Post post : posts) {

            switch (post.getDateTime().getDayOfWeek().name()) {
                case "SUNDAY":
                    daysCount[SUN]++;
                    daysEngages[SUN] = daysEngages[SUN].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[SUN] = daysFollows[SUN].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case "MONDAY":
                    daysCount[MON]++;
                    daysEngages[MON] = daysEngages[MON].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[MON] = daysFollows[MON].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case "TUESDAY":
                    daysCount[TUE]++;
                    daysEngages[TUE] = daysEngages[TUE].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[TUE] = daysFollows[TUE].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case "WEDNESDAY":
                    daysCount[WED]++;
                    daysEngages[WED] = daysEngages[WED].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[WED] = daysFollows[WED].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case "THURSDAY":
                    daysCount[THU]++;
                    daysEngages[THU] = daysEngages[THU].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[THU] = daysFollows[THU].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case "FRIDAY":
                    daysCount[FRI]++;
                    daysEngages[FRI] = daysEngages[FRI].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[FRI] = daysFollows[FRI].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case "SATURDAY":
                    daysCount[SAT]++;
                    daysEngages[SAT] = daysEngages[SAT].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    daysFollows[SAT] = daysFollows[SAT].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                default:
                    break;
            }
        }

        return makeDayAvgs(daysCount, daysEngages, daysFollows);
    }


    /**
     * Make an array of averge day ratios
     * 
     * @param count
     *            Days count
     * @param engages
     *            Days engagements
     * @param follows
     *            Days followers
     * @return Array of ratios by day
     */
    private double[] makeDayAvgs(
        int[] count,
        BigInteger[] engages,
        BigInteger[] follows) {

        double[] dayAvgs = new double[7];

        double sunEngagesAvg = engages[SUN].doubleValue() / (double)count[SUN];
        double sunFollowsAvg = follows[SUN].doubleValue() / (double)count[SUN];

        double sunRatio = sunEngagesAvg / sunFollowsAvg;

        double monEngagesAvg = engages[MON].doubleValue() / (double)count[MON];
        double monFollowsAvg = follows[MON].doubleValue() / (double)count[MON];

        double monRatio = monEngagesAvg / monFollowsAvg;

        double tueEngagesAvg = engages[TUE].doubleValue() / (double)count[TUE];
        double tueFollowsAvg = follows[TUE].doubleValue() / (double)count[TUE];

        double tueRatio = tueEngagesAvg / tueFollowsAvg;

        double wedEngagesAvg = engages[WED].doubleValue() / (double)count[WED];
        double wedFollowsAvg = follows[WED].doubleValue() / (double)count[WED];

        double wedRatio = wedEngagesAvg / wedFollowsAvg;

        double thuEngagesAvg = engages[THU].doubleValue() / (double)count[THU];
        double thuFollowsAvg = follows[THU].doubleValue() / (double)count[THU];

        double thuRatio = thuEngagesAvg / thuFollowsAvg;

        double friEngagesAvg = engages[FRI].doubleValue() / (double)count[FRI];
        double friFollowsAvg = follows[FRI].doubleValue() / (double)count[FRI];

        double friRatio = friEngagesAvg / friFollowsAvg;

        double satEngagesAvg = engages[SAT].doubleValue() / (double)count[SAT];
        double satFollowsAvg = follows[SAT].doubleValue() / (double)count[SAT];

        double satRatio = satEngagesAvg / satFollowsAvg;

        dayAvgs[SUN] = sunRatio;
        dayAvgs[MON] = monRatio;
        dayAvgs[TUE] = tueRatio;
        dayAvgs[WED] = wedRatio;
        dayAvgs[THU] = thuRatio;
        dayAvgs[FRI] = friRatio;
        dayAvgs[SAT] = satRatio;

        return dayAvgs;
    }


    /**
     * Make a double array of ratios based on Post Types
     * 
     * @return Array of calculated Post Type ratios
     */
    public double[] calculateByType() {

        int[] typeCount = new int[NUM_TYPES];
        BigInteger[] typeEngages = new BigInteger[NUM_TYPES];
        BigInteger[] typeFollows = new BigInteger[NUM_TYPES];

        for (int i = 0; i < NUM_TYPES; i++) {
            typeCount[i] = 0;
            typeEngages[i] = new BigInteger("0");
            typeFollows[i] = new BigInteger("0");
        }

        for (Post post : posts) {

            switch (post.getPostType()) {
                case PHOTO:
                    typeCount[PHO]++;
                    typeEngages[PHO] = typeEngages[PHO].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    typeFollows[PHO] = typeFollows[PHO].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case ALBUM:
                    typeCount[ALB]++;
                    typeEngages[ALB] = typeEngages[ALB].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    typeFollows[ALB] = typeFollows[ALB].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                case VIDEO:
                    typeCount[VID]++;
                    typeEngages[VID] = typeEngages[VID].add(new BigInteger(
                        Integer.toString(post.getEngagements())));
                    typeFollows[VID] = typeFollows[VID].add(new BigInteger(
                        Integer.toString(post.getFollowers())));
                    break;
                default:
                    break;
            }
        }

        return makeTypeAvgs(typeCount, typeEngages, typeFollows);
    }


    private double[] makeTypeAvgs(
        int[] count,
        BigInteger[] engages,
        BigInteger[] follows) {

        double[] typeAvgs = new double[3];

        double phoEngageAvg = engages[PHO].doubleValue() / (double)count[PHO];
        double phoFollowAvg = follows[PHO].doubleValue() / (double)count[PHO];

        double phoRatio = phoEngageAvg / phoFollowAvg;

        double albEngageAvg = engages[ALB].doubleValue() / (double)count[ALB];
        double albFollowAvg = follows[ALB].doubleValue() / (double)count[ALB];

        double albRatio = albEngageAvg / albFollowAvg;

        double vidEngageAvg = engages[VID].doubleValue() / (double)count[VID];
        double vidFollowAvg = follows[VID].doubleValue() / (double)count[VID];

        double vidRatio = vidEngageAvg / vidFollowAvg;

        typeAvgs[PHO] = phoRatio;
        typeAvgs[ALB] = albRatio;
        typeAvgs[VID] = vidRatio;

        return typeAvgs;
    }


    /**
     * Make a double array of ratios based on hour posted
     * 
     * @return Array of calculated hour ratios
     */
    public double[] calculateByHour() {

        int[] hourCount = new int[24];
        BigInteger[] hourEngages = new BigInteger[24];
        BigInteger[] hourFollows = new BigInteger[24];

        for (int i = 0; i < 24; i++) {
            hourCount[i] = 0;
            hourEngages[i] = new BigInteger("0");
            hourFollows[i] = new BigInteger("0");
        }

        for (Post post : posts) {

            int hour = post.getDateTime().getHour();

            hourCount[hour]++;
            hourEngages[hour] = hourEngages[hour].add(new BigInteger(Integer
                .toString(post.getEngagements())));
            hourFollows[hour] = hourFollows[hour].add(new BigInteger(Integer
                .toString(post.getFollowers())));
        }

        double[] hourAvgs = new double[24];

        for (int i = 0; i < 24; i++) {

            double engageAvg = hourEngages[i].doubleValue()
                / (double)hourCount[i];

            double followAvg = hourFollows[i].doubleValue()
                / (double)hourCount[i];

            double ratio = engageAvg / followAvg;

            hourAvgs[i] = ratio;
        }

        return hourAvgs;
    }


    /**
     * Make a double array of ratios based on month posted
     * 
     * @return Array of calculated month ratios
     */
    public double[] calculateByMonth() {

        int[] monthCount = new int[12];
        BigInteger[] monthEngages = new BigInteger[12];
        BigInteger[] monthFollows = new BigInteger[12];

        for (int i = 0; i < 12; i++) {
            monthCount[i] = 0;
            monthEngages[i] = new BigInteger("0");
            monthFollows[i] = new BigInteger("0");
        }

        for (Post post : posts) {

            int month = post.getDateTime().getMonthValue() - 1;

            monthCount[month]++;
            monthEngages[month] = monthEngages[month].add(new BigInteger(Integer
                .toString(post.getEngagements())));
            monthFollows[month] = monthFollows[month].add(new BigInteger(Integer
                .toString(post.getFollowers())));
        }

        double[] monthAvgs = new double[12];

        for (int i = 0; i < 12; i++) {

            double engageAvg = monthEngages[i].doubleValue()
                / (double)monthCount[i];

            double followAvg = monthFollows[i].doubleValue()
                / (double)monthCount[i];

            double ratio = engageAvg / followAvg;

            monthAvgs[i] = ratio;
        }

        return monthAvgs;
    }
}
