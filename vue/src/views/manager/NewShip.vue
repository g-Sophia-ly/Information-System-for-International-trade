<template>
  <div class="table">
    <br>
    <br>
    <br>
    <div @click="handleStepClick">
      <el-steps align-center :active="3" finish-status="success">
        <el-step title="订单信息" ></el-step>
        <el-step title="配码信息" ></el-step>
        <el-step title="装箱资料" ></el-step>
        <el-step title="订船资料" ></el-step>
        <el-step title="报关资料" ></el-step>
        <el-step title="完成" ></el-step>
      </el-steps>
    </div>
    <br>
    <br>
    <table>
      <colgroup>
        <col style="width: 100px;"> <!-- 设置固定宽度的列 -->
      </colgroup>
      <thead>
      <tr>
        <th>订船资料</th>
        <th>订船资料详情</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>
          <el-row justify="center">
            <el-col :span="12">
              <div class="square">
                订单号：
                <span>{{ this.$route.query.id }}</span>
              </div>
            </el-col>
          </el-row>
        </td>
        <td>
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
              <el-form-item label="发货地址" prop="shipper">
                <el-input v-model="form.shipper" placeholder="发货地址"></el-input>
              </el-form-item>
              <el-form-item label="收货地址" prop="consignee">
                <el-input v-model="form.consignee" placeholder="收货地址"></el-input>
              </el-form-item>
              <el-form-item label="通知人" prop="notify">
                <el-input v-model="form.notify" placeholder="通知人"></el-input>
              </el-form-item>
<!--              <el-form-item label="订单编号" prop="orderNo">-->
<!--                <el-select style="width: 100%" v-model="form.orderNo">-->
<!--                  <el-option v-for="item in orders"  :key="item.id" :value="item.orderNo" :label="item.orderNo"></el-option>-->
<!--                </el-select>-->
<!--              </el-form-item>-->
              <el-form-item label="品名" prop="types">
                <el-input v-model="form.types" placeholder="品名"></el-input>
              </el-form-item>
              <el-form-item label="HS_CODE" prop="hscode">
                <el-select v-model="form.hscode" allow-create filterable  placeholder="请选择商品编号" style="width: 200px; margin-left: 5px">
                  <el-option label="运动鞋（橡胶）" value="6402190090"></el-option>
                  <el-option label="过踝鞋靴（橡胶）" value="6402910000"></el-option>
                  <el-option label="鞋靴（皮革）" value="6403911990"></el-option>
                  <el-option label="其他鞋靴" value="6403990090"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="启运港" prop="depature">
                <el-select v-model="form.depature" allow-create filterable  placeholder="请选择启运港" style="width: 200px; margin-left: 5px">
                  <el-option label="宁波" value="NINGBO,CHINA"></el-option>
                  <el-option label="上海" value="SHANGHAI,CHINA"></el-option>
                  <el-option label="苏州" value="SUZHOU,CHINA"></el-option>
                  <el-option label="青岛" value="QINGDAO,CHINA"></el-option>
                  <el-option label="广州" value="GUANGZHOU,CHINA"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="达到港" prop="arrival">
                <el-input v-model="form.arrival" placeholder="达到港"></el-input>
              </el-form-item>
              <el-form-item label="船期" prop="schedule">
                <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                v-model="form.schedule" style="width: 100%"></el-date-picker>
              </el-form-item>
              <el-form-item label="货代" prop="freight">
                <el-select v-model="form.freight" placeholder="请选择货代" style="width: 200px; margin-left: 5px">
                  <el-option label="Maersk" value="Maersk"></el-option>
                  <el-option label="COSCO" value="COSCO"></el-option>
                  <el-option label="CMA" value="CMA"></el-option>
                  <el-option label="MSC" value="MSC"></el-option>
                  <el-option label="HPL" value="HPL"></el-option>
                  <el-option label="ONE" value="ONE"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="箱数" prop="cartons">
                <el-input v-model="form.cartons" placeholder="箱数"></el-input>
              </el-form-item>
              <el-form-item label="双数" prop="prs">
                <el-input v-model="form.prs" placeholder="双数"></el-input>
              </el-form-item>
              <el-form-item label="集装箱描述" prop="containers">
                <el-select v-model="form.containers" placeholder="请选择" style="width: 200px; margin-left: 5px">
                  <el-option label="40#GP" value="1个40#GP"></el-option>
                  <el-option label="20#GP" value="1个20#GP"></el-option>
                  <el-option label="40#HQ" value="1个40#HQ"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
        </td>
      </tr>
      </tbody>
    </table>
    <div slot="footer" class="dialog-footer">
<!--      <el-button @click="fromVisible = false">取 消</el-button>-->
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewShip",
  data() {
    return {
      execId:4,
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      orders: [],
      total: 0,
      arrival:null,
      fromVisible: false,
      form: {orderNo:""},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        orderNo: [
          {required: true, message: '请输入订单编号', trigger: 'blur'},
        ],
        arrival: [
          {required: true, message: '请输入到达港', trigger: 'blur'},
        ],
      },
      ids: [],
    }
  },
  created() {
    this.load(1)
    this.form.orderNo = this.$route.query.id || '';
  },
  methods: {
    handleStepClick(){
     // this.$router.push({ path: '/declare'}); // 跳转到订单信息页面
    },
    handleImport(res) {
      if (res.code === '200') {  // 成功
        this.$message.success('导入成功')
        this.load(1)  // 刷新表格数据
      } else {
        this.$message.error(res.msg)
      }
    },
    exp() {
      window.open(this.$baseUrl + '/ship/export')
    },
    changeStatus(row) {
      this.$request.put('/ship/update', row).then(res => {
        if (res.code === '200') {   // 表示操作成功
          this.$message.success('操作成功')
          this.load(1)
        } else {
          this.$message.error(res.msg)  // 弹出错误的信息
        }
      })
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      var that = this;
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/ship/update' : '/ship/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
              this.$router.push({ path: '/NewDeclare',query: { id: that.form.orderNo  }});
              this.$request.get('/order/updateExec/'+that.form.orderNo+"/"+this.execId)
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/ship/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/ship/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/ship/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          arrival: this.arrival,
          orderNo:this.orderNo,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
      this.$request.get('/order/selectAll').then(res => {
        this.orders = res.data || []
      })
    },
    reset() {
      this.orderNo = null
      this.arrival = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>