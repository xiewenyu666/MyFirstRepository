<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return{
      user:{
        name: "",
        phone: "",
        priority: 0,
        vip_num:0,
        balance:0,
        balance_sums:0,
        img:''
      },
      boollogin: false,
      showhomenav:true
    }
  },
  created() {
    localStorage.setItem('userData', JSON.stringify(this.user));
    // localStorage.setItem('u', JSON.stringify(this.user.balance));
    localStorage.setItem('boollogin', JSON.stringify(this.boollogin));
    localStorage.setItem('showhomenav', JSON.stringify(this.showhomenav));
  }
}
</script>
<style>
* {
  margin: 0;
}
</style>