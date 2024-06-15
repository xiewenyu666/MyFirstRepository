<template>
  <form @submit.prevent="submitEdit">
    <div>
      <label>名字:</label>
      <input v-model.number="ident.recipientname" type="number" step="0.01" />
    </div>
    <div>
      <label>电话:</label>
      <input v-model.number="ident.recipientphone" type="number" />
    </div>
    <div>
      <label>状态：</label>
      <select v-model="ident.condition">
        <option value="未发货">未发货</option>
        <option value="已发货">已发货</option>
        <option value="已送达">已送达</option>
      </select>
    </div>


    <button type="submit">提交</button>
  </form>
</template>

<script>
import request from "@/utils/request";

export default {
  props: {
    ident: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      editedident: { ...this.ident }
    };
  },
  methods: {
    submitEdit() {
      request.post("/admin/updataident",this.editedident).then( res =>{
        if (res.code === '0') {
          this.$emit('editidentSuccess');
          this.$message.success("编辑成功");
        }
        else this.$message.error("编辑失败");
      });
    }
  },
  watch: {
    ident: {
      handler(newVal) {
        this.editedident = { ...newVal };
      },
      immediate: true,
      deep: true
    }
  }
};
</script>