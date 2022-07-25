package com.example.demo2.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo2.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RestHelloController {

    @Autowired
    DataSource dataSource;
    @Autowired
    private DruidDataSource dataSourcepool;
    //  根据类型依赖注入
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    @GetMapping("/GetUserMeaasge")
    public Object GetUserMeaasge(String name) throws Exception {
        Connection connect = dataSource.getConnection();
        PreparedStatement pre = connect.prepareStatement("select * from user where username =" +name);
        ResultSet result = pre.executeQuery();
        List<Map<String,Object>> list = new ArrayList<>();
        while (result.next()) {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id", result.getObject("id"));
            map.put("username", result.getObject("username"));
            map.put("password", result.getObject("password"));
            map.put("email", result.getObject("email"));
            list.add(map);
        }
        if(result!= null ) result.close();
        if(pre!= null ) pre.close();
        if(connect!= null ) connect.close();
        return list;
    }

    @GetMapping("/GetUserMeaasge2")
    public Object GetUserMeaasge2(String name) throws Exception {
        Connection connect = dataSourcepool.getConnection();
        PreparedStatement pre = connect.prepareStatement("select * from user where username =" +name);
        ResultSet result = pre.executeQuery();
        List<Map<String,Object>> list = new ArrayList<>();
        while (result.next()) {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id", result.getObject("id"));
            map.put("username", result.getObject("username"));
            map.put("password", result.getObject("password"));
            map.put("email", result.getObject("email"));
            list.add(map);
        }
        if(result!= null ) result.close();
        if(pre!= null ) pre.close();
        if(connect!= null ) connect.close();
        return list;
    }

    @GetMapping("/GetUserMeaasge3")
    public Object GetUserMeaasge3(String name) throws Exception {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession=sqlSessionFactory.openSession();
        User findNameById = sqlSession.selectOne("findNameByName", name);
        sqlSession.commit();
        sqlSession.close();
        return findNameById;
    }
    @GetMapping("/GetUserMeaasge4")
    public Object GetUserMessage4(String name) throws Exception {
        List<User> query = jdbcTemplate.query("select * from user where username = ?", new BeanPropertyRowMapper<>(User.class), name);
        return query;
    }

}
