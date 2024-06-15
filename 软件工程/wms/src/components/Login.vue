<template>
  <div class="login" style="width: 400px">
  <el-form :model="loginForm" ref="loginForm" :rules="loginRules" class="login-form">
    <el-form-item prop="username">
      <el-input v-model="loginForm.phone" placeholder="请输入用户名" style="width: 300px;"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" style="width: 300px;"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login">登录</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  inject: ['loginState','user'],
  data() {
    return {
      loginForm: {
        phone: '',
        password: ''
      },
      loginRules: {
        // 定义登录表单的验证规则
        phone: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    };
  },
  methods: {
    login() {
      request.post("/admin/login", this.loginForm).then(res => {
        if (res.code === '0') {
          //更新登录状态
          this.loginState.set(true);
          // this.user.setuser();
          //关闭登录视图框
          this.$emit('loginSuccess');
          // 将用户数据存储到本地
          localStorage.setItem('userData', JSON.stringify(res.data));
          localStorage.setItem('boollogin', JSON.stringify(true));
          this.$message({
            message: "登录成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.loginForm.phone = '';
        this.loginForm.password = '';
      });
    },
  }
};
</script>
<style>
</style>