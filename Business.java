package business;

import java.util.ArrayList;
/*
 * import java.util.Collections;
 * import java.util.Comparator;
 */

/**
 * Main Business class
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 7, 2019 <v1.0>
 *
 */
public class Business {

    /* Business Program variables */

    private static PostReader postReader;
    private static PostWriter postWriter;
    private static StatFinder statFinder;

    private static ArrayList<Post> trainingPosts;
    private static ArrayList<Post> holdoutPosts;


    /* Business Program Main Method */

    /**
     * Main driver for Business Program
     * 
     * @param args
     *            Program arguments
     */
    public static void main(String[] args) {
        postReader = new PostReader();

        trainingPosts = postReader.readPostFile();
        holdoutPosts = postReader.readHoldoutFile();

        // Sorts training Post list based on highest known Engagements
        // Else, Post list is already sorted by date

        /*
         * Collections.sort(posts, new Comparator<Post>() {
         * 
         * @Override public int compare (Post p1, Post p2) {
         * return p2.getEngagements() - p1.getEngagements();
         * }
         * });
         */

        statFinder = new StatFinder(trainingPosts);

        KeywordMap keywordMap = statFinder.calculateByKeyword();

        double[] hours = statFinder.calculateByHour();
        double[] days = statFinder.calculateByDay();
        double[] months = statFinder.calculateByMonth();
        double[] types = statFinder.calculateByType();

        estimateTrainingEngagements(keywordMap, hours, days, months, types);
        estimateHoldoutEngagements(keywordMap, hours, days, months, types);

        postWriter = new PostWriter(holdoutPosts);

        postWriter.writeHoldoutFile();
    }


    /**
     * Estimate Engagements for training Posts using calculated
     * Engagement/Follower ratios
     * 
     * @param keywordMap
     *            Calculated KeywordMap
     * @param hours
     *            Array of calcualted hour ratios
     * @param days
     *            Array of calculated day of week ratios
     * @param months
     *            Array of calculated month ratios
     * @param types
     *            Array of calculated Post Type ratios
     */
    public static void estimateTrainingEngagements(
        KeywordMap keywordMap,
        double[] hours,
        double[] days,
        double[] months,
        double[] types) {

        // Estimate Engagements for training Posts
        for (Post post : trainingPosts) {
            post.estimateEngages(keywordMap, hours, days, months,
                types, "Training");
        }

        // MAPE testing
        MAPEtest();
    }
    
    /**
     * Used to test MAPE
     */
    private static void MAPEtest() {
        int numPosts = trainingPosts.size();
        
        double sum = 0;

        for (Post post : trainingPosts) {

            double a_i = post.getEngagements();
            double p_i = post.getEstimation();

            double absCheck = Math.abs((a_i - p_i) / a_i);

            sum += absCheck;

        }
        
        System.out.println("Training Set Estimation Test:");
        System.out.println("MAPE: " + ((sum / numPosts) * 100) + '\n');
    }

    /**
     * Estimate Holdout Posts Engagements based on Training SetS
     * 
     * @param keywordMap Filled KeywordMap
     * @param hours Hour ratios generated from Training set
     * @param days Day ratios generated from Training set
     * @param months Month ratios generated from Training set
     * @param types Post Type ratios generated from Training set
     */
    public static void estimateHoldoutEngagements(
        KeywordMap keywordMap,
        double[] hours,
        double[] days,
        double[] months,
        double[] types) {

        for (Post post : holdoutPosts) {
            post.estimateEngages(keywordMap, hours, days, months, types,
                "Holdout");
        }
    }
}
