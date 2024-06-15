package com.example.service;

import com.example.dao.AdminDao;
import com.example.entity.*;
import com.example.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {

    @Resource//便于识别并实例化
    private AdminDao adminDao;


    //查询所有白名单用户
    public List<Admin> findAll(Params params) { return adminDao.findAll(params); }

    //查询黑名单用户
    public List<Admin> findblackuser(Params params) { return adminDao.findblackuser(params); }

    //查询所有图书
    public List<Books> findAllbooks(Params params) {
        return adminDao.findAllbooks(params);
    }

    //按图书分类查询图书
    public List<Books> findbookbykind(Params params) { return adminDao.findbookbykind(params); }

    //所有订单
    public List<Idents> findAllidents(Params params) {
        return adminDao.findAllidents(params);
    }

    //我的购物车
    public List<Shopping> findAllmyshopping(String phone) {
        return adminDao.findAllmyshopping(phone);
    }

    //登陆验证
    public Admin login(Admin admin) {
        if (admin.getPhone() == null || "".equals(admin.getPhone())) { throw new CustomException("用户名不能为空"); }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) throw new CustomException("密码不能为空");
        Admin user = adminDao.findByPhoneAndPassword(admin.getPhone(),admin.getPassword());
        if (user == null) throw new CustomException("用户名或密码输入错误");
        return user;
    }
    //注册验证
    public void register(registeradmin registeradmin) {
        if (registeradmin.getName() == null || "".equals(registeradmin.getName())) { throw new CustomException("昵称不能为空"); }
        if (registeradmin.getPhone() == null || "".equals(registeradmin.getPhone())) { throw new CustomException("用户名不能为空"); }
        if (registeradmin.getPassword() == null || "".equals(registeradmin.getPassword())) throw new CustomException("密码不能为空");
        if (!registeradmin.getPassword().equals(registeradmin.getConfirmPassword())) throw new CustomException("两次密码输入不一致");
        Admin user = adminDao.findByPhone(registeradmin.getPhone());
        if (user != null) throw new CustomException("用户名已存在");
        adminDao.register(registeradmin.getPhone(),registeradmin.getPassword(),registeradmin.getName(),"0",0,0,0);
    }

    //我的订单
    public List<Idents> findAllmyidents(String phone) { return adminDao.findAllmyidents(phone); }

    //加入购物车
    public void addToMyShoppingCart(Params params) {
        // 检查记录是否存在
        int count = adminDao.countShoppingByBidAndPhone(params.getBid(), params.getPhone());
        if (count > 0) { adminDao.updateNumByBidAndPhone(params.getBid(), params.getPhone()); } else { adminDao.insertIntoShopping(params.getBid(), params.getPhone(), 1); }
    }
    //增加订单
    public void insearchident(Params params) {adminDao.insearchident(params.getPhone(),params.getRecipientname(),params.getRecipientphone(),params.getBook_name(),params.getAddress(),params.getPrice(),params.getCondition()); }

    //修改用户信息
    public void updatauser(Params params) { adminDao.updatauser(params.getPhone(),params.getName(),params.getPassword(),params.getPriority(),params.getBalance(),params.getVip_num(),params.getBalance_sums(),params.getWhiteorblack(),params.getImg()); }

    //删除购物车信息
    public void deleteOrder(Params params) { adminDao.deleteOrder(params.getIid()); }

    //添加图书信息
    public void increasebook(Params params) { adminDao.increasebook(params.getBook_name(),params.getAuthor(),params.getPrice(),params.getDescribe(),params.getImg(),params.getKind(),params.getNumber()); }

    public void updatabook(Params params) { adminDao.updatabook(params.getBid(),params.getName(),params.getAuthor(),params.getPrice(),params.getDescribe(),params.getKind(),params.getNumber()); }

    public void deletebook(Params params) { adminDao.deletebook(params.getBid()); }

    public Object getmyshopping(Params params) {
        shoppingbookitem item = new shoppingbookitem();
        Books bookinfor = adminDao.getbookbybid(params.getBid());
        int nums = adminDao.getnums(params.getPhone(),params.getBid());
        item.setImg(bookinfor.getImg());
        item.setName(bookinfor.getName());
        item.setKind(bookinfor.getKind());
        item.setPrice(bookinfor.getPrice());
        item.setNums(nums);
        item.setBid(params.getBid());
        item.setPhone(params.getPhone());

        return item;
    }

    public void deleteshopping(Params params) { adminDao.deleteshopping(params.getPhone(),params.getBid());
    }

    public void updataident(Params params) { adminDao.updataident(params.getCondition(),params.getIid());
    }

    public void deleteident(Params params) {adminDao.deleteident(params.getIid());
    }
}
