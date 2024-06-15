<template>
  <el-carousel class="custom-carousel" :interval="interval" arrow="always" indicator-position="outside">
    <el-carousel-item v-for="(item, index) in items" :key="index">
      <div class="carousel-item-container">
        <img :src="item.image" alt="" class="carousel-item-image">
      </div>
      <p style="color:#ff0066">woshicehsi</p>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
export default {
  name: 'Carousel',
  data() {
    return {
      items: [
        { image: require(`../../public/img.png`) },
        { image: require(`../../public/img_1.png`) },
        { image: require(`../../public/img_2.png`) },
        // ... 更多图片
      ],
      interval: 3000,
    };
  },
};
</script>

<style scoped>
.custom-carousel {
  width: 300px;
  height: 600px;
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