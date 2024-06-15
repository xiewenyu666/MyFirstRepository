<template>
  <div class="book_item" style="display: flex; flex-wrap: wrap;">
    <custombook v-for="book in tableData" :table-data="[book]"></custombook>
  </div>
</template>

<script>
import request from "@/utils/request";
import custombook from "@/components/custombook";

export default {
  components: {
    custombook
  },
  data() {
    return {
      tableData: [],
      params: {
        kind: ''
      }
    }
  },
  created() {
    this.updateData();
  },
  watch: {
    '$route' (to, from) {
      this.updateData();
    }
  },
  methods: {
    updateData() {
      this.params.kind = this.$route.params.type;
      request.get("/admin/searchbookbykind", {
        params: this.params
      }).then(res => {
        if (res.code === '0') {
          this.tableData = res.data;
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
</script>

<style scoped>
* {
  margin-top: 0;
  margin-bottom: 0;
}
.book-item{
  margin: 10px;
}
</style>