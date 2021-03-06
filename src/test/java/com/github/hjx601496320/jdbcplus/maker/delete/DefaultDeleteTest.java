package com.github.hjx601496320.jdbcplus.maker.delete;

import com.github.hjx601496320.jdbcplus.maker.Wheres;
import org.junit.Test;
import com.github.hjx601496320.jdbcplus.User;

import java.util.Arrays;
import java.util.Date;

public class DefaultDeleteTest {

    @Test
    public void makeSql() {
        User user = new User();
        user.setId(12);
        user.setCreateDate(new Date());
        user.setAge(22);
        user.setName("heiheihei3");
        DefaultDelete delete = new DefaultDelete();
        delete.target(User.class);

        System.out.println(delete.makeSql());
        System.out.println(Arrays.toString(delete.makeSqlValue().toArray()));

    }

    @Test
    public void where() {
        User user = new User();
        user.setId(13);
        user.setCreateDate(new Date());
        user.setAge(23);
        user.setName("heiheihei4");
        DefaultDelete delete = new DefaultDelete();
        delete.target(User.class);
        delete.where(
                Wheres.equal("age", 123),
                Wheres.in("age", new Object[]{123, 123234}),
                Wheres.equal("user_name", "hebaibai")
        );

        System.out.println(delete.makeSql());
        System.out.println(Arrays.toString(delete.makeSqlValue().toArray()));

    }
}