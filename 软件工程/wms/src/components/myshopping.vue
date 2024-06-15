<template>
  <div class="shopping-cart">
    <el-table :data="shoppingCart" style="width: 100%; margin-bottom: 20px;"
              stripe
              highlight-current-row
              row-style="background-color: #fceded; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);"
              row-class-name="cart-item-row">
      <el-table-column label="选择" width="150">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.selected" @change="calculateTotal"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="图片" width="170">
        <template slot-scope="scope">
          <img :src="require(`@/assets/${scope.row.img}`) " style="height: 50px"/>
        </template>
      </el-table-column>
      <el-table-column label="名称" prop="name" width="320"></el-table-column>
      <el-table-column label="类别" prop="kind" width="250"></el-table-column>
      <el-table-column label="价格" prop="price" width="180"></el-table-column>
      <el-table-column label="数量" prop="nums" width="180"></el-table-column>
    </el-table>
    <div class="total">
      <span>总价: ￥{{ totalPrice }}</span>
      <el-button @click="checkout" type="primary">结算</el-button>
    </div>
    <el-dialog :visible="buybookDialogVisible" title="请仔细填写信息" @close="closebuybookDialog">
      <!-- 登录组件内容 -->
      <buybook2 @buybookSuccess="closebuybookDialog" :table-data="totalobject"></buybook2>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import buybook2 from "@/components/buybook2.vue";

export default {
  components:{
    buybook2
  },
  name: "Shopping.vue",
  data() {
    return {
      params: {
        phone: JSON.parse(localStorage.getItem('userData')).phone,
      },
      shoppingCart: [], // Array to store shopping cart items
      selectedItems: [], // Array to store selected items in the cart
      totalPrice: 0, // Total price of selected items
      buybookDialogVisible: false,
      totalobject:{
        table:this.selectedItems,
        name:"",
        imgs:[],
        price:0,
      },
    }
  },
  created() {
    this.params.phone = JSON.parse(localStorage.getItem('userData')).phone;
    this.findBySearch();
  },
  methods: {
    findBySearch() {
      request.get("/admin/searchmyshopping", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          let a = [];
          for (var i = 0;i < res.data.length;i ++){
            request.get("admin/getmyshopping",{
              params: res.data[i]
            }).then( pom => {
              if (pom.code === '0'){
                a.push(pom.data);
              }
            })
          }
          this.shoppingCart = a;
        } else {
          alert("数据还未加载完成，请刷新重试！")
        }
      })
    },
    showbuybookDialog(){this.buybookDialogVisible = true;},
    closebuybookDialog(){this.buybookDialogVisible = false;
      this.findBySearch();},
    calculateTotal() {
      this.totalPrice = this.shoppingCart.reduce((total, item) => total + (item.selected ? item.price*item.nums : 0), 0);
    },
    checkout() {
      this.selectedItems = this.shoppingCart.filter(item => item.selected);
      let name = "";
      for (var i = 0;i < this.selectedItems.length;i ++){
        this.totalobject.imgs.push(this.selectedItems[i].img);
        name = name + ",";
        name = name + this.selectedItems[i].name;
      }
      this.totalobject.name = name;
      this.totalobject.price = this.totalPrice;
      this.totalobject.table = this.selectedItems
      this.showbuybookDialog();
      this.selectedItems = [];
      this.totalPrice = 0;
    }
  },
}
</script>

<style scoped>
.shopping-cart {
  padding: 10px;
  background-color: #f8f8f8;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.cart-item-row {
  margin-bottom: 10px;
}

.total {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
</style>