<template>
  <div class="recharge">
    <h2>充值</h2>
    <ul>
      <li v-for="(item, index) in rechargeOptions" :key="index">
        <button @click="selectRechargeAmount(item.value)">{{ item.label }}</button>
      </li>
    </ul>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "recharge",
  data() {
    return {
      rechargeOptions: [
        { value: 10, label: "10元" },
        { value: 50, label: "50元" },
        { value: 100, label: "100元" },
        { value: 200, label: "200元" },
        { value: 500, label: "500元" }
      ]
    };
  },
  methods: {
    selectRechargeAmount(amount) {
      let user={
        phone: JSON.parse(localStorage.getItem('userData')).phone,
        name:JSON.parse(localStorage.getItem('userData')).name,
        password:JSON.parse(localStorage.getItem('userData')).password,
        priority:JSON.parse(localStorage.getItem('userData')).priority,
        balance: JSON.parse(localStorage.getItem('userData')).balance + amount,
        balance_sums: JSON.parse(localStorage.getItem('userData')).balance_sums + amount,
        vip_num: 0,
      };
      user.vip_num = Math.floor(user.balance_sums/1000)
      request.post("admin/updatauser", user).then(res => {
        if (res.code === '0') {
          //更新本地数据
          this.$message.success("充值成功")
          localStorage.setItem('userData', JSON.stringify(user));
        }
      })
      this.$emit("rechargeSuccess");
    }
  }
};
</script>

<style scoped>
.recharge {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.recharge h2 {
  margin-bottom: 20px;
}

.recharge ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.recharge li {
  display: inline-block;
  margin-right: 10px;
}

.recharge button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 8px 16px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
</style>
