<template>
  <div>
     <span>
    <el-input placeholder="请输入书名" v-model="params.name" clearable style="width: 200px;margin-left:20px;margin-bottom:10px"></el-input>
    <el-button @click="findBySearch" type="primary" style="margin-left:10px">查询</el-button>
  </span>
    <i class="el-icon-circle-plus" @click="increasebook" style="color: #848484;font-size: 30px;margin-top:8px;margin-left: 650px"></i>
    <el-table :data="tableData" style="width: 100%; margin-top: 20px;" border stripe>
      <el-table-column label="封面" width="100">
        <template slot-scope="scope">
          <el-image :src="require(`@/assets/${scope.row.img}`)" fit="cover"></el-image>
        </template>
      </el-table-column>
      <!-- 其他列 -->
      <!-- 假设 tableData 中的每个对象还有 title 和 author 属性 -->
      <el-table-column label="书名" prop="name"></el-table-column>
      <el-table-column label="作者" prop="author"></el-table-column>
      <el-table-column label="类型" prop="kind"></el-table-column>
      <el-table-column label="简介" prop="describe" width="340"></el-table-column>
      <el-table-column label="价格(元)" prop="price"></el-table-column>
      <el-table-column label="数量" prop="number"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="editItem(scope.row)" type="primary" size="mini">编辑</el-button>
          <el-button @click="removeItem(scope.row)" type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
      <!-- 添加更多列以显示 tableData 中的其他属性 -->
    </el-table>
    <el-dialog :visible="showincreasebookDialogVisible" title="添加图书" @close="closeincreasebookDialog">
      <increasebook @increaseSuccess="closeincreasebookDialog"></increasebook>
    </el-dialog>
    <el-dialog :visible="showeditbookDialogVisible" title="编辑图书" @close="closeeditbookDialog">
      <Editbookform @editbookSuccess="closeeditbookDialog" :book="editingBook"></Editbookform>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";
import increasebook from "@/components/increasebook";
import Editbookform from "@/components/editbookform";

export default {
  name: "book",
  components:{
    Editbookform,
    increasebook
  },
  data() {
    return {
      params: {
        name: '',
      },
      tableData: [],
      editingBook:null,
      showincreasebookDialogVisible:false,
      showeditbookDialogVisible:false
    }
  },
  //页面加载时在created内做的操作
  created() {
    this.findBySearch();
  },
  //定义页面事件方法
  methods: {
    findBySearch() {
      request.get("/admin/searchbook", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data;
        } else {
          alert("数据还未加载完成，请刷新重试！")

        }
      })
    },
    increasebook(){
      this.showincreasebookDialog();
    },
    editItem(row){
      this.editingBook = row;
      this.showeditbookDialog();
    },
    removeItem(row){
      request.post("/admin/deletebook",row).then( res =>{
        if (res.code === '0') {
          this.findBySearch();
          this.$message.success("删除成功");
        }
        else this.$message.error("删除失败");
      });
    },
    showincreasebookDialog(){
      this.showincreasebookDialogVisible = true;
    },
    closeincreasebookDialog(){
      this.showincreasebookDialogVisible = false;
      this.findBySearch();
    },
    showeditbookDialog(){
      this.showeditbookDialogVisible = true;
    },
    closeeditbookDialog(){
      this.showeditbookDialogVisible = false;
      this.findBySearch();
    },

  },
}
</script>

<style scoped>

</style>