package com.example.dao;

import com.example.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//注明接口，以便能够使其实例化
public interface AdminDao {

    //1、基于注解的方式获取数据库的数据
    //@Select("select * from user")
    //查询所有白名单用户
    List<Admin> findAll(@Param("params") Params params);

    //查询黑名单用户
    List<Admin> findblackuser(@Param("params") Params params);

    //所有图书
    List<Books> findAllbooks(@Param("params") Params params);

    //按图书分类查询图书
    List<Books> findbookbykind(@Param("params") Params params);

    //查询所有订单
    List<Idents> findAllidents(@Param("params") Params params);

    //我的购物车
    @Select("select * from shopping where phone = #{phone}")
    List<Shopping> findAllmyshopping(@Param("phone") String phone);

    //我的订单
    @Select("select * from idents where phone = #{phone}")
    List<Idents> findAllmyidents(@Param("phone") String phone);

    @Select("select * from admin where phone = #{phone} and password = #{password} limit 1")
    Admin findByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);//查询用户名和密码是否存在

    @Select("select * from admin where phone = #{phone} limit 1")
    Admin findByPhone(@Param("phone") String phone);//查询用户主键

    //增加用户
    @Insert("INSERT INTO admin (phone, password, name, priority,balance,vip_num,balance_sums) VALUES (#{phone}, #{password}, #{name}, #{priority},#{balance},#{vip_num},#{balance_sums})")
    void register(@Param("phone") String phone, @Param("password") String password, @Param("name") String name, @Param("priority") String priority, @Param("balance") double balance, @Param("vip_num") Integer vip_num, @Param("balance_sums") double balance_sums);//注册用户

    //查询购物车中是否存在该条目
    @Select("SELECT COUNT(*) FROM shopping WHERE bid = #{bid} AND phone = #{phone}")
    int countShoppingByBidAndPhone(@Param("bid") int bid, @Param("phone") String phone);

    //在原基础上增加条目的数量
    @Update("UPDATE shopping SET nums = nums + 1 WHERE bid = #{bid} AND phone = #{phone}")
    void updateNumByBidAndPhone(@Param("bid") int bid, @Param("phone") String phone);

    //增加购物车条目
    @Insert("INSERT INTO shopping (bid, phone, nums) VALUES (#{bid}, #{phone}, #{nums})")
    void insertIntoShopping(@Param("bid") int bid, @Param("phone") String phone, @Param("nums") int i);

    //增加订单信息
    @Insert("INSERT INTO  idents (phone, recipientname, recipientphone,book_name,address,price,`condition`) VALUES (#{phone}, #{recipientname}, #{recipientphone}, #{book_name},#{address},#{price},#{condition})")
    void insearchident(@Param("phone") String phone, @Param("recipientname") String recipientname, @Param("recipientphone") String recipientphone, @Param("book_name") String book_name, @Param("address") String address, @Param("price") double price, @Param("condition") String condition);

    //修改用户信息
    @Update("UPDATE admin SET name = #{name}, password = #{password}, priority = #{priority}, balance = #{balance}, vip_num = #{vip_num}, balance_sums = #{balance_sums},whiteorblack = #{whiteorblack} ,img = #{img} WHERE phone = #{phone}")
    void updatauser(@Param("phone") String phone, @Param("name") String name, @Param("password") String password, @Param("priority") int priority, @Param("balance") double balance, @Param("vip_num") int vip_num, @Param("balance_sums") double balance_sums, @Param("whiteorblack") int whiteorblack,@Param("img") String img);

    //删除订单
    @Delete("DELETE FROM idents WHERE iid = #{iid}")
    void deleteOrder(@Param("iid") int iid);

    @Insert("INSERT INTO  books (name, author, price,`describe`,img,kind,number) VALUES (#{book_name}, #{author}, #{price}, #{describe},#{img},#{kind},#{number})")
    void increasebook(@Param("book_name") String book_name, @Param("author") String author, @Param("price") double price, @Param("describe") String describe, @Param("img") String img, @Param("kind") String kind, @Param("number") int number);

    @Update("UPDATE books SET name = #{name}, author = #{author}, `describe` = #{describe}, kind = #{kind}, price = #{price}, number = #{number} WHERE bid = #{bid}")
    void updatabook(@Param("bid") int bid, @Param("name") String name, @Param("author") String author,  @Param("price") double price,@Param("describe") String describe, @Param("kind") String kind,@Param("number") int number);

    @Delete("DELETE FROM books WHERE bid = #{bid}")
    void deletebook(@Param("bid") int bid);

    @Select("SELECT nums FROM shopping WHERE bid = #{bid} AND phone = #{phone}")
    int getnums(@Param("phone") String phone,@Param("bid") int bid);

    @Select("select * from books where bid = #{bid} limit 1")
    Books getbookbybid(@Param("bid") int bid);

    @Delete("DELETE FROM shopping WHERE bid = #{bid} AND phone = #{phone}")
    void deleteshopping(@Param("phone") String phone, @Param("bid")int bid);

    @Update("UPDATE idents SET `condition` = #{condition} WHERE iid = #{iid}")
    void updataident(@Param("condition") String condition, @Param("iid") int iid);

    @Delete("DELETE FROM idents WHERE iid = #{iid}")
    void deleteident(@Param("iid") int iid);
}
