<template>

   <div class="container">
     <el-steps align-center :active="5" finish-status="success">
       <el-step title="订单信息" ></el-step>
       <el-step title="配码信息" ></el-step>
       <el-step title="装箱资料" ></el-step>
       <el-step title="订船资料" ></el-step>
       <el-step title="报关资料" ></el-step>
       <el-step title="完成" ></el-step>
     </el-steps>
     <img class="center-image" src="../../assets/imgs/succ.png">
     <h1>完成</h1>
   </div>
</template>
<style>
.container {
  text-align: center; /* 设置容器中内容水平居中 */
}

.center-image {
  margin-top: 200px; /* 调整图片与步骤条之间的间距 */
}
</style>
<script>
export default {
  name: "Succ"
}
</script>

<style scoped>

</style>