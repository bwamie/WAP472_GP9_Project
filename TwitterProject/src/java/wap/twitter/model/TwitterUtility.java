/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.twitter.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import wap.twitter.model.Tweet;

/**
 *
 * @author bwamie
 */
public class TwitterUtility {

    public static TwitterFactory config() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("fDyslJSP3mUbBXRfaJliPG1jd")
                .setOAuthConsumerSecret("KOJU7mo0Xs3s1YvheA6xBlqElGBuvUD1SpoB2HVY2b9b7iq50t")
                .setOAuthAccessToken("800805041502240768-0UqQQLOpC4WKPhdAzp6A0QCUoJKb8UP")
                .setOAuthAccessTokenSecret("dCyn31WXBDna2h98V8A4T1Rgw6uwOafXkwonDEsF4zqFr");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf;
    }

    public List<Tweet> getTweets(String news) {
        TwitterFactory tf = config();
        Twitter twitter = tf.getInstance();
        try {
            List<Tweet> tws = new ArrayList<Tweet>();
            int i = 0;
            Query query = new Query("#" + news);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    Tweet tw = new Tweet();
                    if (i == 8) {
                        break;
                    } else {
                        tw.setTitle(news);
                        tw.setUser(tweet.getUser().getScreenName());
                        tw.setUrl(tweet.getSource());
                        tw.setImage(tweet.getUser().getProfileImageURL());
                        tw.setBody(tweet.getText());
                        tw.setSource(tweet.getSource());
                        tw.setId(tweet.getId() + "");
                        i++;
                    }
                    System.out.println("******************* " + tweet.getGeoLocation());
                    tws.add(tw);
                }
            } while ((query = result.nextQuery()) != null);
            return tws;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public List<Trend> getTrends(GeoLocation loc) throws TwitterException {
        TwitterFactory tf = config();
        Twitter twitter = tf.getInstance();
        List<Trend> tds = new ArrayList<Trend>();
        ResponseList<Location> locations;
        locations = twitter.getClosestTrends(loc);
        for (Location location : locations) {
            Trends trends = twitter.getPlaceTrends(location.getWoeid());
            for (int i = 0; i < trends.getTrends().length; i++) {
                if (i == 10) {
                    break;
                }
                tds.add(trends.getTrends()[i]);
            }
        }
        return tds;
    }

    private static Integer getTrendLocationId(String locationName) {

        int idTrendLocation = 0;

        try {

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true).setOAuthConsumerKey("yourConsumerKey").setOAuthConsumerSecret("yourConsumerSecret").setOAuthAccessToken("yourOauthToken").setOAuthAccessTokenSecret("yourOauthTokenSecret");

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();

            for (Location location : locations) {
                if (location.getName().toLowerCase().equals(locationName.toLowerCase())) {
                    idTrendLocation = location.getWoeid();
                    break;
                }
            }

            if (idTrendLocation > 0) {
                return idTrendLocation;
            }

            return null;

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            return null;
        }

    }
}
