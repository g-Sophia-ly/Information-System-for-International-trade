<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0"  v-if="user.role !== 'FACTORY'">
      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
        <div>
          <el-timeline reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time + ' ' +item.factory"
                              v-if="item.status !== '已处理'">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content"
              >
                <span slot="reference">订单号：{{ item.orderNo }}</span>
                <h4 slot="reference" style="margin-top: 8px; margin-bottom: 8px">生产问题：{{ item.content }}</h4>
                <span slot="reference" style="font-weight:bold">处理状态：</span>
                <span slot="reference" v-if="item.status=='进行中'" style="color: #009f5d;font-weight:bold">{{ item.status }}</span>
                <span slot="reference" v-if="item.status=='未处理'" style="color: #ff5675;font-weight:bold">{{ item.status }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: []
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  }
}
</script>
