package com.example.sayakat_travel.helpers;

import jakarta.servlet.http.HttpServletRequest;

public class Helpers {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

}
