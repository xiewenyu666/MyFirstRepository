<template>
  <el-container>
    <el-aside>
      <el-form :model="buybookForm" :rules="buybookRules" class="buybook-form">
        <el-form-item prop="phone">
          <el-input v-model="buybookForm.phone" placeholder="请输入收货人电话" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input v-model="buybookForm.name" placeholder="请输入收货人姓名" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item prop="address">
          <el-input v-model="buybookForm.address" placeholder="请输入收货地址" style="width: 300px;"></el-input>
        </el-form-item>
        <div></div>
        <el-form-item>
          <el-button type="primary" @click="buybook">购买</el-button>
        </el-form-item>
      </el-form>
    </el-aside>
    <el-main>
      <div>
        <div class="header"
             style="padding: 0; flex-shrink: 0; display: flex; justify-content: center; align-items: center;">
          <el-image :title="tableData.describe" :src="require(`@/assets/${tableData.img}`)"
                    style="height: 100px"></el-image>
        </div>
        <div class="content"
             style="display: flex; flex-direction: column; justify-content: space-between; padding: 10px; flex-grow: 1;">
          <div class="book-name" style="font-size: 14px; color: #4084ff; padding-left: 30px">{{ tableData.name }}</div>
          <div class="author" style="font-size: 14px; color: #9c9c9c; padding-left: 30px">{{ tableData.author }}</div>
          <div class="price" style="font-size: 14px; color: #db0000; padding-left: 30px">￥ {{ tableData.price }}</div>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "buybook",
  props: {
    tableData: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      buybookForm: {
        name: '',
        phone: '',
        address: ''
      },
      buybookRules: {
        // 定义登录表单的验证规则
        phone: [{required: true, message: '请输入收货人电话', trigger: 'blur'}],
        name: [{required: true, message: '请输入收货人姓名', trigger: 'blur'}],
        address: [{required: true, message: '请输入收货地址', trigger: 'blur'}]
      }

    }
  },
  methods: {
    buybook() {
      if (this.tableData.price > JSON.parse(localStorage.getItem('userData')).balance) {
        this.$message.warning('余额不足，请先充值');
      } else {
        let param = {
          phone: JSON.parse(localStorage.getItem('userData')).phone,
          recipientname: this.buybookForm.name,
          recipientphone: this.buybookForm.phone,
          address: this.buybookForm.address,
          book_name: this.tableData.name,
          price: this.tableData.price,
          condition: '未发货',
        };
        request.post("admin/insearchident", param).then(res => {
          if (res.code === '0') {
            let user={
              phone: JSON.parse(localStorage.getItem('userData')).phone,
              name:JSON.parse(localStorage.getItem('userData')).name,
              password:JSON.parse(localStorage.getItem('userData')).password,
              priority:JSON.parse(localStorage.getItem('userData')).priority,
              balance: JSON.parse(localStorage.getItem('userData')).balance - param.price,
              vip_num: JSON.parse(localStorage.getItem('userData')).vip_num,
              balance_sums: JSON.parse(localStorage.getItem('userData')).balance_sums
            }
            request.post("/admin/updatauser", user).then(res =>{
              if (res.code === '0'){
                //更新本地数据
                localStorage.setItem('userData', JSON.stringify(user));
                //关闭
                this.$emit('buybookSuccess');
                this.$message({
                  message: "购买成功，可前往订单查看状态",
                  type: "success"
                });
              } else {
                this.$message({
                  message: res.msg,
                  type: "error"
                });
              }
            });
            this.$message({
              message: "购买成功，可前往订单查看状态",
              type: "success"
            });
          } else {
            this.$message({
              message: res.msg,
              type: "error"
            });
          }
        });
      }

    }
  }
}
</script>

<style scoped>

</style>