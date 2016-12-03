/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.twitter.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.twitter.model.Tweet;
import wap.twitter.model.TwitterUtility;

/**
 *
 * @author bwamie
 */
@WebServlet(name = "TwitterTweetsServlet", urlPatterns = {"/tweets"})
public class TwitterTweetsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tag = request.getParameter("tag");
        if (tag != null) {
            TwitterUtility tu = new TwitterUtility();
            List<Tweet> tweets = tu.getTweets(tag);
            response.setContentType("text/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                String sjson = (new Gson()).toJson(tweets);
                System.out.println("=============\n" + sjson + "\n============");
                out.write(sjson);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Fetch Tweets by Category";
    }
}
