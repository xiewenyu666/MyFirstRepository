<template>
  <div>
    <div class="increasebook" style="width: 400px">
      <el-form :model="increasebookForm" ref="loginForm" :rules="increasebookRules" class="login-form">
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
          <img v-if="imageUrl" :src="imageUrl" class="avatar" style="width: 100px;height: 100px">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item prop="book_name">
          <el-input v-model="increasebookForm.book_name" placeholder="请输入书名" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item prop="author">
          <el-input v-model="increasebookForm.author" placeholder="请输入作者" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="increasebookForm.price" placeholder="请输入价格" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="increasebookForm.describe" placeholder="请输入简介" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="increasebookForm.kind" placeholder="请输入类型" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="increasebookForm.number" placeholder="请输入数量" style="width: 300px;"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="submitImage">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      imageUrl: '',
      uploadUrl: 'http://localhost:8080/book/avatar',
      increasebookForm: {
        book_name: '',
        author: '',
        price: null,
        describe: '',
        img: '',
        kind: '',
        number: null,
      },
      increasebookRules: {
        // 定义登录表单的验证规则
        book_name: [{required: true, message: '请输入书名', trigger: 'blur'}],
        author: [{required: true, message: '请输入作者', trigger: 'blur'}],
        price: [{required: true, message: '请输入价格', trigger: 'blur'}],
        describe: [{required: true, message: '请输入简介', trigger: 'blur'}],
        kind: [{required: true, message: '请输入类型', trigger: 'blur'}],
        number: [{required: true, message: '请输入数量', trigger: 'blur'}],
      }
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      setTimeout(() => {
        // 在这里执行你想要延迟执行的函数或代码
        this.timeout(res); // 调用你的函数
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
      // 当文件发生变化时，更新 imageUrl
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    timeout(res) {
      this.increasebookForm.img = res.data;
      request.post("/admin/increasebook", this.increasebookForm).then(res => {
        if (res.code === '0') {
          this.$emit('increaseSuccess');
          this.$message.success("添加成功");
        } else {
          this.$message.error("添加失败");
        }
      });
    },
    submitImage() {
      this.$refs.upload.submit(); // 点击提交按钮后触发上传

    }
  }
}
</script>