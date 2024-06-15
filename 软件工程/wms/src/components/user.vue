<template>
  <div>
    <span>
      <el-input placeholder="请输入姓名" v-model="params.name" clearable style="width: 200px;margin-left:20px;margin-bottom:10px"></el-input>
      <el-button @click="findBySearch" type="primary" style="margin-left:10px">查询</el-button>
    </span>
    <el-table :data="tableData" style="width: 100%" border stripe>
      <el-table-column prop="phone" label="phone" width="180"></el-table-column>
      <el-table-column prop="name" label="name"></el-table-column>
      <el-table-column prop="priority" label="priority"></el-table-column>
      <el-table-column prop="balance" label="balance"></el-table-column>
      <el-table-column prop="balance_sums" label="balance_sums"></el-table-column>
      <el-table-column prop="vip_num" label="vip_num"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="addpriorityItem(scope.row)" type="primary" size="mini" style="margin-bottom: 5px;">增加权限</el-button>
          <el-button @click="droppriorityItem(scope.row)" type="primary" size="mini" style="margin-bottom: 5px;">降低权限</el-button>
          <el-button @click="toblackuser(scope.row)" type="danger" size="mini" style="margin-bottom: 5px;">拉黑用户</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "user.vue",
  data() {
    return {
      params: {
        name: '',
      },
      tableData: [],
    }
  },
  //页面加载时在created内做的操作
  created() {
    this.findBySearch();
  },
  //定义页面事件方法
  methods: {
    findBySearch() {
      request.get("/admin/search", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data;
        } else {
          alert("数据还未加载完成，请刷新重试！")

        }
      })
    },
    addpriorityItem(row){
      if (JSON.parse(localStorage.getItem('userData')).priority <= row.priority) this.$message.warning('权限不足');
      else {
        row.priority = row.priority + 1;
        request.post("admin/updatauser", row).then(res => {
          if (res.code === '0') {
            this.findBySearch();
          } else {
            this.$message.success('修改失败');
          }
        });
      }
    },
    droppriorityItem(row){
      if (JSON.parse(localStorage.getItem('userData')).priority <= row.priority) this.$message.warning('权限不足');
      else {
        row.priority = row.priority - 1;
        request.post("admin/updatauser", row).then(res => {
          if (res.code === '0') {
            this.findBySearch();
          } else {
            this.$message.error('修改失败');
          }
        });
      }
    },
    toblackuser(row){
      if (JSON.parse(localStorage.getItem('userData')).priority <= row.priority) this.$message.warning('权限不足');
      else {
        row.whiteorblack = 1;
        request.post("admin/updatauser", row).then(res => {
          if (res.code === '0') {
            this.findBySearch();
          } else {
            this.$message.success('修改失败');
          }
        });
      }
    }
  },
}
</script>

<style scoped>
.el-table {
  font-size: 14px;
  color: #333;
  border-collapse: separate;
  border-spacing: 0;
}

.el-table .el-button {
  padding: 10px 15px;
  font-size: 13px;
}

.el-table .el-button:last-child {
  margin-bottom: 0;
}

.el-table tr {
  transition: background-color 0.3s ease;
  background-color: #f9f9f9;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.el-table tr:hover {
  background-color: #f4f4f4;
}

.el-table tr:not(:last-child) {
  margin-bottom: 10px;
}
</style>