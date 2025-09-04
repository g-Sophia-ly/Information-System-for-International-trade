<template>
  <div class="table">
    <br>
    <br>
    <br>
    <div @click="handleStepClick">
      <el-steps align-center :active="2" finish-status="success">
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
        <col style="width: 100px;">
        <!-- 设置固定宽度的列 -->
      </colgroup>
      <thead>
      <tr>
        <th>装箱资料</th>
        <th>装箱资料详情</th>
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

            <el-form-item label="客户" prop="customerId">
              <el-select style="width: 100%" v-model="form.customerId">
                <el-option v-for="item in customers" :key="item.id" :value="item.id" :label="item.name"></el-option>
              </el-select>
            </el-form-item>
<!--            <el-form-item label="港口" prop="fromcus">-->
<!--              <el-select v-model="form.fromcus" placeholder="请选择港口" style="width: 200px; margin-left: 5px">-->
<!--                <el-option label="宁波" value="NINGBO,CHINA"></el-option>-->
<!--                <el-option label="上海" value="SHANGHAI,CHINA"></el-option>-->
<!--                <el-option label="苏州" value="SUZHOU,CHINA"></el-option>-->
<!--                <el-option label="青岛" value="QINGDAO,CHINA"></el-option>-->
<!--                <el-option label="广州" value="GUANGZHOU,CHINA"></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
            <el-form-item label="港口" prop="fromcus">
              <el-select v-model="form.fromcus" allow-create filterable placeholder="请选择港口" style="width: 200px; margin-left: 5px">
                <el-option label="宁波" value="NINGBO,CHINA"></el-option>
                <el-option label="上海" value="SHANGHAI,CHINA"></el-option>
                <el-option label="苏州" value="SUZHOU,CHINA"></el-option>
                <el-option label="青岛" value="QINGDAO,CHINA"></el-option>
                <el-option label="广州" value="GUANGZHOU,CHINA"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="合同号" prop="inv">
              <el-input v-model="form.inv" placeholder="合同号"></el-input>
            </el-form-item>
            <el-form-item label="日期" prop="todate">
              <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                              v-model="form.todate" style="width: 100%"></el-date-picker>
            </el-form-item>
            <el-form-item label="鞋子种类" prop="shoes">
              <el-input v-model="form.shoes" placeholder="鞋子种类"></el-input>
            </el-form-item>
            <el-form-item label="总数" prop="prs">
              <el-input v-model="form.prs" placeholder="总数"></el-input>
            </el-form-item>
            <el-form-item label="箱数" prop="ctns">
              <el-input v-model="form.ctns" placeholder="箱数"></el-input>
            </el-form-item>
            <el-form-item label="净重" prop="netw">
              <el-input v-model="form.netw" placeholder="净重"></el-input>
            </el-form-item>
            <el-form-item label="毛重" prop="gross">
              <el-input v-model="form.gross" placeholder="毛重"></el-input>
            </el-form-item>
            <el-form-item label="立方数" prop="cbm">
              <el-input v-model="form.cbm" placeholder="立方数"></el-input>
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
  name: "NewPacklist",
  data() {
    return {
      execId:3,
      active: 0,
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      customers: [],
      total: 0,
      customerName:null,
      inv: null,
      fromcus:null,
      fromVisible: false,
      form: {orderNo:""},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        customerId: [
          {required: true, message: '请输入客户名称', trigger: 'blur'},
        ],
        inv: [
          {required: true, message: '请输入合同号', trigger: 'blur'},
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
     // this.$router.push({ path: '/NewShip'}); // 跳转到订单信息页面
    },
    next() {
      if (this.active++ > 5){
        this.active = 0;
      }
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
      window.open(this.$baseUrl + '/packlist/export')
    },
    changeStatus(row) {
      this.$request.put('/packlist/update', row).then(res => {
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
      if (this.form.netw  > this.form.gross) {
        this.$message.warning('毛重净重数量关系有误，请修改')
        return
      }
      var that = this;
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/packlist/update' : '/packlist/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
              this.$router.push({ path: '/NewShip',query: { id: that.form.orderNo  }});
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
        this.$request.delete('/packlist/delete/' + id).then(res => {
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
        this.$request.delete('/packlist/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/packlist/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          inv: this.inv,
          fromcus:this.fromcus,
          customerName:this.customerName,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
      this.$request.get('/customer/selectAll').then(res => {
        this.customers = res.data || []
      })
    },
    reset() {
      this.inv = null
      this.fromcus = null
      this.customerName = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把头像属性换成上传的图片的链接
      this.form.avatar = response.data
    }
  }
}
</script>

<style scoped>

</style>