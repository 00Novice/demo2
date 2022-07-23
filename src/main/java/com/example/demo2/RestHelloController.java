package com.example.demo2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RestHelloController {

    @Autowired
    DataSource dataSource;

    @GetMapping("/restHello")
    public Object restHello(String a) throws Exception {
        Connection connect = dataSource.getConnection();
        PreparedStatement pre = connect.prepareStatement("select * from user where username =" +a);
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
}
