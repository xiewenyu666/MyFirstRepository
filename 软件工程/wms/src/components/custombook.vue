<template>
  <div class="book-item" style="width: 150px; height: 200px; display: inline-flex; flex-direction: column; justify-content: space-between; padding: 10px; border: 1px solid #ccc; border-radius: 5px;">
    <div class="header" style="padding: 0; flex-shrink: 0; display: flex; justify-content: center; align-items: center;">
      <el-image :title="tableData[0].describe" :src="require(`@/assets/${tableData[0].img}`)" style="height: 100px" @mouseover="showDescription" @mouseout="hideDescription"></el-image>
    </div>
    <div class="content" style="display: flex; flex-direction: column; justify-content: space-between; padding: 10px; flex-grow: 1;">
      <div class="book-name" style="font-size: 14px; color: #4084ff; text-align: center">{{ tableData[0].name }}</div>
      <div class="author" style="font-size: 14px; color: #9c9c9c; padding-left: 30px">{{ tableData[0].author }}</div>
      <div class="price" style="font-size: 14px; color: #db0000; padding-left: 30px">￥ {{ tableData[0].price }}</div>
    </div>
    <div class="oprate" style="display: flex; flex-direction: row; justify-content: space-between; ">
      <el-button @click="insertIntoShopping" type="danger" round size="mini">购物车</el-button>
      <el-button @click="buyBook" type="danger" round size="mini">购买</el-button>
    </div>
  <el-dialog :visible="buybookDialogVisible" title="请仔细填写信息" @close="closebuybookDialog">
    <!-- 登录组件内容 -->
    <buybook @buybookSuccess="closebuybookDialog" :table-data="tableData[0]"></buybook>
  </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import buybook from "./buybook";

export default {
  components: {
    buybook
  },

  props: {
    tableData: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      showDesc: false,
      timer: null,
      buybookDialogVisible: false,
    };
  },
  methods: {
    showDescription() {
      this.timer = setTimeout(() => {
        this.showDesc = true;
      }, 3000);
    },
    hideDescription() {
      clearTimeout(this.timer);
      this.showDesc = false;
    },
    showbuybookDialog(){this.buybookDialogVisible = true;},
    closebuybookDialog(){this.buybookDialogVisible = false;},
    insertIntoShopping() {
      if (!JSON.parse(localStorage.getItem('boollogin'))) this.$message.warning('未登录请先登录');
      else {
        let param = {
          bid: this.tableData[0].bid,
          phone: JSON.parse(localStorage.getItem('userData')).phone,
        }
        request.post("/admin/addToMyShoppingCart", param).then(res => {
          if (res.code === '0') {
            this.$message({
              message: "加入购物车成功",
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
    },
    buyBook() {
      if (!JSON.parse(localStorage.getItem('boollogin'))) this.$message.warning('未登录请先登录');
      else {
        this.showbuybookDialog();
      }
    }
  }
}
</script>

<style scoped>
.book-item {
  position: relative;
}
.description {
  display: none;
}
</style>