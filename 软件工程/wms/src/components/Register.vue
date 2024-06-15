<template>
  <el-form :model="registerForm" ref="registerForm" :rules="registerRules" class="register-form">
    <h3 class="register-title">用户注册</h3>
    <el-form-item prop="name">
      <el-input v-model="registerForm.name" placeholder="请输入昵称"></el-input>
    </el-form-item>
    <el-form-item prop="phone">
      <el-input v-model="registerForm.phone" placeholder="请输入用户名"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
    </el-form-item>
    <el-form-item prop="confirmPassword">
      <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码"></el-input>
    </el-form-item>
    <el-form-item prop="email">
      <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="register">注册</el-button>
    </el-form-item>
  </el-form>
</template>


<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      registerForm: {
        name: '',
        phone: '',
        password: '',
        confirmPassword: '',
        email: ''
      },
      registerRules: {
        name:[{required: true,message: '请输入昵称',trigger:'blur'}],
        phone: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        confirmPassword: [
          {required: true, message: '请确认密码', trigger: 'blur'},
          {validator: this.checkPassword, trigger: 'blur'}
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] }
        ]
      },
    }
  },
  methods: {
    checkPassword(rule, value, callback) {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    },
    register() {
      request.post("admin/register",this.registerForm).then(res =>{
        if (res.code === '0'){
          this.$emit('registerSuccess');
          this.$message({
            message:"注册成功,请前往登录",
            type:"success"
          })
        }else {
          this.$message({
            message:res.msg,
            type:"error"
          })
        }

      })
    },
  }
};
</script>