package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.entity.registeradmin;
import com.example.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource//便于识别并实例化
    private AdminService adminService;
    @PostMapping("/login")
    public Result adminlogin(@RequestBody Admin admin) {
        Admin loginuser = adminService.login(admin);
        if (loginuser.getWhiteorblack() == 1) return Result.error("该用户已被加入黑名单");
        return Result.success(loginuser);
    }
    @PostMapping("/register")
    public Result adminregister(@RequestBody registeradmin registeradmin) {

        adminService.register(registeradmin);
        return Result.success();

    }
    //所有用户
    @GetMapping("/search")
    public Result findAll(Params params) {
        return Result.success(adminService.findAll(params));
    }

    @GetMapping("/searchblackuser")
    public Result findblackuser(Params params) {
        return Result.success(adminService.findblackuser(params));
    }

    //所有图书
    @GetMapping("/searchbook")
    public Result findAllbooks(Params params) {
        return Result.success(adminService.findAllbooks(params));
    }

    //按图书分类查询图书
    @GetMapping("/searchbookbykind")
    public Result findbookbykind(Params params) {
        return Result.success(adminService.findbookbykind(params));
    }

    //我的购物车
    @GetMapping("/searchmyshopping")
    public Result findAllmyshopping(Params phone_data) { return Result.success(adminService.findAllmyshopping(phone_data.getPhone())); }

    @GetMapping("/getmyshopping")
    public Result getmyshopping(Params params) {
        return Result.success(adminService.getmyshopping(params)); }

    //所有订单
    @GetMapping("/searchident")
    public Result findAllidents(Params params) {
        return Result.success(adminService.findAllidents(params));
    }

    //我的订单
    @GetMapping("/searchmyident")
    public Result findAllmyidents(Params params) { return Result.success(adminService.findAllmyidents(params.getPhone())); }

    //加入购物车
    @PostMapping("/addToMyShoppingCart")
    public Result addToMyShoppingCart(@RequestBody Params params){
        adminService.addToMyShoppingCart(params);
        return Result.success();
    }

    //添加订单
    @PostMapping("/insearchident")
    public Result insearchident(@RequestBody Params params){
        adminService.insearchident(params);
        return Result.success();
    }

    @PostMapping("/deleteOrder")
    public Result deleteOrder(@RequestBody Params params){
        adminService.deleteOrder(params);
        return Result.success();
    }

    @PostMapping("/deleteshopping")
    public Result deleteshopping(@RequestBody Params params){
        System.out.println(params.getBid());
        System.out.println(params.getPhone());
        adminService.deleteshopping(params);
        return Result.success();
    }

    //修改用户信息
    @PostMapping("/updatauser")
    public Result updatauser(@RequestBody Params params){
        adminService.updatauser(params);
        return Result.success();
    }

    //添加图片信息
    @PostMapping("/increasebook")
    public Result increasebook(@RequestBody Params params){
        adminService.increasebook(params);
        return Result.success();
    }

    //修改图片信息
    @PostMapping("/updatabook")
    public Result updatabook(@RequestBody Params params){
        adminService.updatabook(params);
        return Result.success();
    }

    @PostMapping("/deletebook")
    public Result deletebook(@RequestBody Params params){
        adminService.deletebook(params);
        return Result.success();
    }

    @PostMapping("/updataident")
    public Result updataident(@RequestBody Params params){
        adminService.updataident(params);
        return Result.success();
    }

    @PostMapping("/deleteident")
    public Result deleteident(@RequestBody Params params){
        adminService.deleteident(params);
        return Result.success();
    }


}
