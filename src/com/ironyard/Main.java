package com.ironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static User user;
    public static ArrayList<Message> list = new ArrayList<>();

    public static void main(String[] args) {

        Spark.init();

        //get the site
        Spark.get (
                "/",

                ((request,response) -> {
                    HashMap n = new HashMap();

                    //if there's no user, login
                    if(user == null) {
                        return new ModelAndView(n, "index.html");
                    }
                    //create new user/password/message
                    else {
                        n.put("createUser", user.name);
                        n.put("userPassword", user.password);
                        n.put("createMessage", list);
                        return new ModelAndView(n, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()

        );

        //post user and password
        Spark.post(
                "/index",

                ((request, response) -> {
                    String name = request.queryParams("createUser");
                    String password = request.queryParams("userPassword");
                    //create object
                    user = new User (name, password);
                    response.redirect("/");
                    return "";
                })
        );

        //post messages
        Spark.post(
                "/messages",

                ((request, response) -> {
                    String note = request.queryParams("createMessage");
                    //create object
                    Message x = new Message(note);
                    list.add(x);
                    response.redirect("/");
                    return "";
                })
        );
    }//end main()
}//end class Main
