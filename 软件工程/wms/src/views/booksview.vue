<template>
  <el-container style="height: 100%">
    <el-aside>
      <el-carousel class="custom-carousel" :interval="interval" arrow="always" indicator-position="outside">
        <el-carousel-item v-for="(item, index) in items" :key="index">
          <div class="carousel-item-container">
            <img :src="item.image" alt="" class="carousel-item-image">
          </div>
        </el-carousel-item>
      </el-carousel>
    </el-aside>
    <el-main>
     <span>
    <el-input placeholder="请输入书名" v-model="params.name" clearable style="width: 200px;padding-left: 20px"></el-input>
    <el-button @click="findBySearch" type="primary" style="margin-left: 10px">查询</el-button>
  </span>
      <div class="book_item" style="display: flex; flex-wrap: wrap;">
        <div v-for="book in tableData">
          <custombook v-if="book.number > 0" :table-data="[book]"></custombook>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import request from "@/utils/request";
import custombook from "@/components/custombook";

export default {
  name: "booksview",
  components: {
    custombook
  },
  data() {
    return {
      params: {
        name: '',
      },
      tableData: [],
      currentIndex: 0,
      items: [
        { image: require(`../../public/img.png`) },
        { image: require(`../../public/img_1.png`) },
        { image: require(`../../public/img_2.png`) },
      ],
      interval: 3000,
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
    next() {
      this.currentIndex = (this.currentIndex + 1) % 3;
    },
  },
  mounted() {
    setInterval(() => {
      this.next();
    }, 3000);
  },
};
</script>

<style scoped>
* {
  margin-top: 0;
  margin-bottom: 0;
}

.book-item {
  margin: 10px;
}

.custom-carousel {
  margin-top: 80px;
  width: 300px;
  height: 400px;
  overflow: hidden;
}

.el-carousel-item {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  overflow: hidden;
}

.carousel-item-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.carousel-item-image {
  max-width: 100%;
  max-height: 300px; /* 根据需要调整图片的最大高度 */
  object-fit: cover;
}

.carousel-item-text {
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
  padding: 16px;
  text-align: center;
}

.carousel-item-text h3 {
  margin: 0;
}

.carousel-item-text p {
  margin: 8px 0 0 0;
}
</style>