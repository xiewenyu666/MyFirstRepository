<template>
  <form @submit.prevent="submitImage">
    <el-upload
        ref="upload"
        class="avatar-uploader"
        name="avatar"
        :action="uploadUrl"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :auto-upload="false"
        :on-change="handleImageChange">
      <img :src="imageUrl" class="avatar" style="width: 100px;height: 100px">
    </el-upload>
    <div>
      <label>昵称:</label>
      <input v-model="userinfor.name" type="text"/>
    </div>
    <div>
      <label>密码:</label>
      <input v-model="userinfor.password" type="text"/>
    </div>
    <button type="submit">提交</button>
  </form>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      userinfor: JSON.parse(localStorage.getItem('userData')),
      imageUrl:require(`../assets/20240529151153admin.jpg`),
      uploadUrl: 'http://localhost:8080/user/avatar'
    }
  },
  created() {
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      setTimeout(() => {
        this.timeout(res);
      }, 2000);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式！');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },
    handleImageChange(file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    timeout(res) {
      this.userinfor.img = res.data;
      request.post("/admin/updatauser", this.userinfor).then(res => {
        if (res.code === '0') {
          localStorage.setItem('userData', JSON.stringify(this.userinfor));
          this.$emit('UserInfoSuccess');
          this.$message.success("修改成功");
        } else {
          this.$message.error("修改失败");
        }
      });
    },
    submitImage() {
      this.$refs.upload.submit();
    },
  }
}
</script>