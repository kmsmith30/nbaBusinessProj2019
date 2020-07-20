package business;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Write Engagement estimations to "holdout_set_Kevin_M_Smith.csv"
 * 
 * @author Kevin M. Smith <kmsmith3@vt.edu>
 * @version July 14, 2019 <v1.0>
 *
 */
public class PostWriter {

    /* PostWriter Variables */

    private ArrayList<Post> holdoutPosts;


    /* PostWriter Constructor */

    /**
     * Construct PostWriter for Holdout set
     * 
     * @param hp
     *            Holdout Post list
     */
    public PostWriter(ArrayList<Post> hp) {

        holdoutPosts = hp;
    }


    /**
     * Write Holdout Post data to "holdout_set_Kevin_M_Smith.csv"
     */
    public void writeHoldoutFile() {

        FileWriter holdoutWriter = null;

        try {

            holdoutWriter = new FileWriter("holdout_set_Kevin_M_Smith.csv");

            writeHeader(holdoutWriter);

            for (Post post : holdoutPosts) {
                writePostInfo(holdoutWriter, post);
            }

            holdoutWriter.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Write header onto output file
     * 
     * @param writer
     *            Output FileWriter
     */
    private void writeHeader(FileWriter writer) {

        try {
            writer.append("Engagements");
            writer.append(",");
            writer.append("Followers at Posting");
            writer.append(",");
            writer.append("Created");
            writer.append(",");
            writer.append("Type");
            writer.append(",");
            writer.append("Description");
            writer.append("\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Write post data onto output file
     * 
     * @param writer
     *            Output FileWriter
     * @param post
     *            Post to write data
     */
    private void writePostInfo(FileWriter writer, Post post) {

        try {
            writer.append(Integer.toString(post.getEngagements()));
            writer.append(",");
            writer.append(Integer.toString(post.getFollowers()));
            writer.append(",");

            String date = makeDate(post.getDateTime(), post.getTimeZone());

            writer.append(date);
            writer.append(",");

            writer.append(post.getPostType().toString());
            writer.append(",");

            if (post.getDescription().contains("\n")) {
                writer.append('"' + post.getDescription() + '"' + "\n");
            }
            else {
                writer.append(post.getDescription() + "\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Make a date String from LocalDateTime variable for file
     * 
     * @param dateTime
     *            LocalDateTime for Post
     * @param timeZone
     *            EDT or EST
     * @return Date String
     */
    private String makeDate(LocalDateTime dateTime, String timeZone) {

        String dateStr = "";

        dateStr += dateTime.getYear();
        dateStr += '-';
        if (dateTime.getMonthValue() < 10) {
            dateStr += ('0' + Integer.toString(dateTime.getMonthValue()));
        }
        else
            dateStr += Integer.toString(dateTime.getMonthValue());
        dateStr += '-';
        if (dateTime.getDayOfMonth() < 10) {
            dateStr += ('0' + Integer.toString(dateTime.getDayOfMonth()));
        }
        else
            dateStr += Integer.toString(dateTime.getDayOfMonth());
        dateStr += ' ';
        if (dateTime.getHour() < 10) {
            dateStr += ('0' + Integer.toString(dateTime.getHour()));
        }
        else
            dateStr += Integer.toString(dateTime.getHour());
        dateStr += ':';
        if (dateTime.getMinute() < 10) {
            dateStr += ('0' + Integer.toString(dateTime.getMinute()));
        }
        else
            dateStr += Integer.toString(dateTime.getMinute());
        dateStr += ':';
        if (dateTime.getSecond() < 10) {
            dateStr += ('0' + Integer.toString(dateTime.getSecond()));
        }
        else
            dateStr += (Integer.toString(dateTime.getSecond()));
        dateStr += ' ' + timeZone;

        return dateStr;
    }

}
