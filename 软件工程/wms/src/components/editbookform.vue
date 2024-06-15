<template>
  <form @submit.prevent="submitEdit">
    <div>
      <label>书名:</label>
      <input v-model="book.name" type="text" />
    </div>
    <div>
      <label>作者:</label>
      <input v-model="book.author" type="text" />
    </div>
    <div>
      <label>价格:</label>
      <input v-model.number="book.price" type="number" step="0.01" />
    </div>
    <div>
      <label>数量:</label>
      <input v-model.number="book.number" type="number" />
    </div>
    <div>
      <label>简介:</label>
      <textarea v-model="book.describe"></textarea>
    </div>

    <button type="submit">提交</button>
  </form>
</template>

<script>
import request from "@/utils/request";

export default {
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      editedBook: { ...this.book }
    };
  },
  methods: {
    submitEdit() {
      request.post("/admin/updatabook",this.editedBook).then( res =>{
        if (res.code === '0') {
          this.$emit('editbookSuccess');
          this.$message.success("编辑成功");
        }
        else this.$message.error("编辑失败");
      });
    }
  },
  watch: {
    book: {
      handler(newVal) {
        this.editedBook = { ...newVal };
      },
      immediate: true,
      deep: true
    }
  }
};
</script>