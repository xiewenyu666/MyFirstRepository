<template>
  <div>
  <span>
    <el-input placeholder="请输入姓名" v-model="params.name" clearable style="width: 200px;margin-left:20px;margin-bottom:10px"></el-input>
    <el-button @click="findBySearch" type="primary" style="margin-left:10px">查询</el-button>
  </span>
    <el-table :data="tableData" style="width: 100%" border stripe>
      <el-table-column prop="recipientphone" label="电话" width="180"></el-table-column>
      <el-table-column prop="recipientname" label="收货人"></el-table-column>
      <el-table-column prop="book_name" label="书名"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="price" label="价格"></el-table-column>
      <el-table-column prop="condition" label="状态"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="editItem(scope.row)" type="primary" size="mini">编辑</el-button>
          <el-button @click="deleteItem(scope.row)" type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible="showeditidentDialogVisible" title="编辑订单" @close="closeeditidentDialog">
      <Editidentform @editidentSuccess="closeeditidentDialog" :ident="editingident"></Editidentform>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import Editidentform from "@/components/editidentform"

export default {
  components:{
    Editidentform
  },
  data() {
    return {
      editingident:'',
      params: {
        name: '',
      },
      tableData: [],
      showeditidentDialogVisible:false
    }
  },
  //页面加载时在created内做的操作
  created() {
    this.findBySearch();
  },
  //定义页面事件方法
  methods: {
    findBySearch() {
      request.get("/admin/searchident", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data;
        } else {
          alert("数据还未加载完成，请刷新重试！")

        }
      })
    },
    showeditidentDialog(){
      this.showeditidentDialogVisible = true;
    },
    closeeditidentDialog(){
      this.showeditidentDialogVisible = false;
      this.findBySearch();
    },
    editItem(row){
      this.editingident = row;
      this.showeditidentDialog();
    },
    deleteItem(row){
      request.post("/admin/deleteident",row).then(res=>{
        if (res.code==='0'){
          this.$message.success("删除成功")
          this.findBySearch();
        }
        else this.$message.error("删除失败")
      });
    }
  },
}
</script>

<style scoped>

</style>