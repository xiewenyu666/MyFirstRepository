<template>
  <div>
    <el-table :data="tableData" style="width: 100%" border stripe>
      <el-table-column prop="recipientphone" label="收货人电话" width="180"></el-table-column>
      <el-table-column prop="recipientname" label="收货人"></el-table-column>
      <el-table-column prop="book_name" label="书名"></el-table-column>
      <el-table-column prop="address" label="收获地址"></el-table-column>
      <el-table-column prop="price" label="付款金额"></el-table-column>
      <el-table-column prop="condition" label="状态"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="deleteItem(scope.row)" type="danger" size="mini">取消订单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "myident",
  props:[
    'user'
  ],
  data() {
    return {
      params: {
        phone: JSON.parse(localStorage.getItem('userData')).phone,
      },
      tableData: [],
    }
  },
  //页面加载时在created内做的操作
  created() {
    this.params.phone = JSON.parse(localStorage.getItem('userData')).phone;
    this.findBySearch();
  },
  //定义页面事件方法
  methods: {
    findBySearch() {
      request.get("/admin/searchmyident", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data;
        } else {
          alert("数据还未加载完成，请刷新重试！")

        }
      })
    },
    deleteItem(row) {
      if (row.condition !== '未发货'){this.$message.warning('商品已发货，无法取消');}
      else {
        request.post("/admin/deleteOrder",row).then(res => {
          if (res.code === '0') {
            // 删除成功后，可以重新加载数据或更新当前数据
            this.findBySearch();
            this.$message.success('订单已成功取消');
            let user={
              phone: JSON.parse(localStorage.getItem('userData')).phone,
              name:JSON.parse(localStorage.getItem('userData')).name,
              password:JSON.parse(localStorage.getItem('userData')).password,
              priority:JSON.parse(localStorage.getItem('userData')).priority,
              balance: JSON.parse(localStorage.getItem('userData')).balance + row.price,
              vip_num: JSON.parse(localStorage.getItem('userData')).vip_num,
              balance_sums: JSON.parse(localStorage.getItem('userData')).balance_sums
            }
            request.post("admin/updatauser", user).then(res => {
              if (res.code === '0') {
                //更新本地数据
                localStorage.setItem('userData', JSON.stringify(user));
              } else {
                this.$message.error('取消订单失败，请重试');
              }
            });
          } else {
            this.$message.error('取消订单失败，请重试');
          }
        })
      }
    },
  },
}
</script>

<style scoped>

</style>