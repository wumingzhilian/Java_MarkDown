package com.MarkDown;


import com.MarkDown.Url.Urls;
import com.MarkDown.dao.UrlDao;

import java.sql.SQLException;

public class testsql {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UrlDao urlDao = new UrlDao();
        Urls url = new Urls("123.html");
        urlDao.insertUrl(url);
    }
}
