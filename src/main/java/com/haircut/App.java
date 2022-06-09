package com.haircut;

import com.haircut.dao.UserDOMapper;
import com.haircut.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.haircut"})
@RestController
@MapperScan("com.haircut.dao")
public class App 
{
    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String Home() {
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if (userDO == null) {
            return "user not exist";
        } else {
            return userDO.getTelephone();
        }
//        return "haircut reservation";
    }

    public static void main( String[] args )
    {
        System.out.println( "haircut reservation" );
        SpringApplication.run(App.class, args);
    }
}
