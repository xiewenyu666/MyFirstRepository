<template>
  <el-container style="height: 100%; border: 1px solid #eee;width: 100%;">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-active="this.$route.path" router>
        <el-menu-item index="/backedmanage/users">用户管理</el-menu-item>
        <el-menu-item index="/backedmanage/books">图书管理</el-menu-item>
        <el-menu-item index="/backedmanage/idents">订单管理</el-menu-item>
        <el-menu-item index="/" @click="back">回到首页</el-menu-item>
      </el-menu>
    </el-aside>
    <el-main style="padding-left: 0">
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
import user from "@/components/user";

export default {
  components: {
    user
  },
  data() {
    return {
      params: {
        name: '',
      },
      tableData: [],
    }
  },
  methods:{
    back(){
      localStorage.setItem('showhomenav', JSON.stringify(true));
    }
  }

}
</script>

<style>

</style>