<template>
  <el-container style="height: 100vh">
    <el-header style="padding-top:20px;height: 100px;background-color: #ffccaa">
      <div class="header-content">
        <div class="logo">
          <img src="../assets/logo.png" alt="书店logo">
          <span>网上售书系统</span>
        </div>
        <div class="user-info">
          <div v-if="!boollogin">
            <el-button type="text" @click="showLoginDialog">登录</el-button>
            <el-button type="text" @click="showRegisterDialog">注册</el-button>
          </div>
          <div v-if="boollogin">
            <span>欢迎 {{ user.name }}的登录  </span>
            <span>余额：{{ user.balance }} </span>
            <span v-if="user.vip_num > 0" style="color:#fff2a1">VIP {{ user.vip_num }} </span>
          </div>
          <el-dropdown>
            <el-avatar :src="require(`@/userassets/${user.img ? user.img : 'admin.jpg'}`)" :size="55" style="margin-left: 10px;margin-right: 30px"></el-avatar>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item><span @click="showUserInfoDialog">编辑信息</span></el-dropdown-item>
              <el-dropdown-item v-if="user.priority > 0"><span @click="backedmanage">后台管理</span></el-dropdown-item>
              <el-dropdown-item v-if="boollogin"><span @click="rechargeclilck">充值</span></el-dropdown-item>
              <el-dropdown-item><span @click="quit">退出登录</span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <el-menu v-if="showhomenav" :default-active="this.$route.path" router class="el-menu-demo" mode="horizontal"
               active-text-color="red" style="padding-left:200px;height: 47px;background-color: #ffccaa">
        <el-menu-item index="/home/booksview" style="height: 45px;">首页</el-menu-item>
        <el-menu-item index="/vuebookbykind/小说" style="height: 45px;">小说</el-menu-item>
        <el-menu-item index="/vuebookbykind/科幻" style="height: 45px;">科幻</el-menu-item>
        <el-menu-item index="/vuebookbykind/历史" style="height: 45px;">历史</el-menu-item>
        <el-menu-item index="/vuebookbykind/传记" style="height: 45px;">传记</el-menu-item>
        <el-menu-item index="/vuebookbykind/教育" style="height: 45px;">教育</el-menu-item>
        <el-menu-item index="/vuebookbykind/艺术" style="height: 45px;">艺术</el-menu-item>
        <el-menu-item index="/vuebookbykind/儿童" style="height: 45px;">儿童</el-menu-item>
        <el-menu-item index="/vuebookbykind/科技" style="height: 45px;">科技</el-menu-item>
        <el-menu-item index="/vuebookbykind/经济" style="height: 45px;">经济</el-menu-item>
        <el-menu-item index="/vuebookbykind/心理" style="height: 45px;">心理</el-menu-item>
        <el-button size="mini" style="margin-left: 75px;margin-top: 15px" @click="handleShowMyShopping">我的购物车
        </el-button>
        <el-button size="mini" @click="handleShowMyIdent">我的订单</el-button>

      </el-menu>
    </el-header>
    <el-main style="padding: 0">
      <hr style="border-top: 3px solid #ff0066;">
      <router-view></router-view>
      <!-- 弹窗组件 -->
      <el-dialog :visible="loginDialogVisible" title="登录" @close="closeLoginDialog">
        <!-- 登录组件内容 -->
        <Login @loginSuccess="closeLoginDialog"></Login>
      </el-dialog>
      <el-dialog :visible="registerDialogVisible" title="注册" @close="closeRegisterDialog">
        <!-- 注册组件内容 -->
        <Register @registerSuccess="closeRegisterDialog"></Register>
      </el-dialog>
      <el-dialog :visible="userInfoDialogVisible" title="个人信息" @close="closeUserInfoDialog">
        <UserInfo @UserInfoSuccess="closeUserInfoDialog"></UserInfo>
      </el-dialog>
      <el-dialog :visible="rechargeDialogVisible" title="充值" @close="closerechargeDialog">
        <recharge @rechargeSuccess="closerechargeDialog"></recharge>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import Login from "../components/Login"
import Register from "@/components/Register";
import UserInfo from "@/components/UserInfo";
import vuebookbykind from "@/components/vuebookbykind";
import Recharge from "@/components/recharge";

export default {
  components: {
    Login,
    Register,
    UserInfo,
    vuebookbykind,
    Recharge,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('userData')),
      boollogin: JSON.parse(localStorage.getItem('boollogin')),
      showhomenav: true,
      loginDialogVisible: false,
      registerDialogVisible: false,
      userInfoDialogVisible: false,
      rechargeDialogVisible:false,
    };
  },
  created() {
    // 定期检查本地缓存的变化
    setInterval(this.checkLocalStorageChange, 1000);
  },
  provide() {
    return {
      loginState: {
        value: this.boollogin,
        set: (newValue) => {
          this.boollogin = newValue;
        }
      },
    };
  },
  methods: {
    checkLocalStorageChange() {
      const newLocalStorageData = JSON.parse(localStorage.getItem('userData'));
      if (JSON.stringify(newLocalStorageData) !== JSON.stringify(this.user)) {
        // 更新本地缓存数据
        this.user = newLocalStorageData;
        this.boollogin = JSON.parse(localStorage.getItem('boollogin'));
      }
      if (JSON.stringify(newLocalStorageData) !== JSON.stringify(this.showhomenav)) {
        this.showhomenav = JSON.parse(localStorage.getItem('showhomenav'));
      }
    },
    handleShowMyShopping() {
      if (!this.boollogin) {
        // 如果未登录，弹出提示
        this.$message.warning('未登录请先登录');
      } else {
        // 如果已登录，执行 showmyshopping 方法
        this.$router.push({name: "myshopping"});
      }
    },
    handleShowMyIdent() {
      if (!this.boollogin) {
        // 如果未登录，弹出提示
        this.$message.warning('未登录请先登录');
      } else {
        // 如果已登录，执行 showmyshopping 方法
        this.$router.push({name: "myident"});
      }
    },
    backedmanage() {
      localStorage.setItem('showhomenav', JSON.stringify(false));
      this.$router.push({name: 'backedmanage'})
    },
    showLoginDialog() {
      this.loginDialogVisible = true;
    },
    closeLoginDialog() {
      this.loginDialogVisible = false;
    },
    showRegisterDialog() {
      this.registerDialogVisible = true;
    },
    closeRegisterDialog() {
      this.registerDialogVisible = false;
    },
    showUserInfoDialog() {
      if (!this.boollogin) this.$message.warning('未登录请先登录');
      else this.userInfoDialogVisible = true;
    },
    closeUserInfoDialog() {
      this.userInfoDialogVisible = false;
    },
    showrechargeDialog(){
      this.rechargeDialogVisible=true;
    },
    closerechargeDialog(){
      this.rechargeDialogVisible=false;
    },
    rechargeclilck(){
      this.showrechargeDialog();
    },
    quit() {
      if (!this.boollogin) this.$message.warning('未登录请先登录');
      else {
        // 从本地删除用户数据
        localStorage.setItem('userData', JSON.stringify({
          name: "",
          phone: "",
          priority: 0,
          vip_num: 0,
          balance: 0,
          balance_sums: 0
        }));
        //更新登陆状态
        localStorage.setItem('boollogin', JSON.stringify(false));
        this.$forceUpdate();
        this.$router.push({name: 'Home'})
      }
    }
  },
};
</script>

<style>
* {
  margin-bottom: 0;
  margin-top: 0;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 32px;
}

.logo {
  display: flex;
  align-items: center;

}

.logo img {
  width: 50px;
  margin-right: 20px;
}

.logo span {
  font-family: Arial, Sans-serif, sans-serif;
  font-size: 20px
}

.user-info {
  display: flex;
  align-items: center;
}

.el-menu-demo .el-menu-item:hover,
.el-menu-demo .el-menu-item.is-active,
.el-menu-demo .el-menu-item:focus {
  color: red !important; /* 悬停、激活、点击时变为红色 */
  background-color: initial !important; /* 保持背景色不变 */
  text-decoration: none !important;
}
</style>