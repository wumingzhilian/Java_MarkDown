package com.MarkDown.dao;

import com.MarkDown.Url.Urls;

import java.sql.*;

public class UrlDao {
    Connection connection=null;
    PreparedStatement preparedStatement = null;
    /**
     * 完成添加url方法
     */
    public void insertUrl(Urls url) throws ClassNotFoundException, SQLException {
        /**
         * 加载mysql驱动
         */
        Class.forName("com.mysql.jdbc.Driver");
        /**
         * 获取连接对象，连接到数据库
         * 1-数据库连接路径，协议名称，所需要连接的库
         * 2-user
         * 3-password
         */
        Connection connection= DriverManager.getConnection("jdbc:mysql:///sql_java_mark_do","s","");
        String sql = "insert into MarkDown(url) values(?)";
        /**
         * 定义sql容器，并装载sql语句
         */
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        /**
         * set数据类型，同时调用对应的方法,参数1是为了替换第一个问号
         */
        preparedStatement.setString(1,url.getUrls());
        /**
         * 执行sql
         */
        preparedStatement.execute();
        /**
         * 关闭链接
         */
        if(preparedStatement!=null){
            preparedStatement.close();
        }
        if (connection!=null){
            connection.close();
        }
    }

}
