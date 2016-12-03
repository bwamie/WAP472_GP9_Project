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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.GeoLocation;
import twitter4j.TwitterException;
import wap.twitter.model.Tweet;
import wap.twitter.model.TwitterUtility;

/**
 *
 * @author bwamie
 */
@WebServlet(name = "TrendsServlet", urlPatterns = {"/trends"})
public class TrendsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TwitterException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("trends.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Trending====================Post");
        response.setContentType("text/json;charset=UTF-8");
        TwitterUtility tu = new TwitterUtility();
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");
        if (lat != null && lng != null) {
            try {
                String jsn = (new Gson()).toJson(tu.getTrends(new GeoLocation(Double.parseDouble(lat), Double.parseDouble(lng))));
                System.out.println(jsn);
                response.getWriter().write(jsn);
            } catch (TwitterException ex) {
                Logger.getLogger(TrendsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
