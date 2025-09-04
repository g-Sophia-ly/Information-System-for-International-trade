<template>
  <div class="table">
    <br>
    <br>
    <br>
    <div @click="handleStepClick">
      <el-steps align-center :active="4" finish-status="success">
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
        <th>报关资料</th>
        <th>报关资料详情</th>
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

            <el-form-item label="合同号" prop="inv">
              <el-select style="width: 100%" v-model="form.inv">
                <el-option v-for="item in invs" :key="item.id" :value="item.inv" :label="item.inv"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="HS_CODE" prop="hscode">
              <el-select v-model="form.hscode" placeholder="请选择商品编号" style="width: 200px; margin-left: 5px">
                <el-option label="运动鞋（橡胶）" value="6402190090"></el-option>
                <el-option label="过踝鞋靴（橡胶）" value="6402910000"></el-option>
                <el-option label="鞋靴（皮革）" value="6403911990"></el-option>
                <el-option label="其他鞋靴" value="6403990090"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="商品名称" prop="types">
              <el-input v-model="form.types" placeholder="商品名称"></el-input>
            </el-form-item>
            <el-form-item label="数量" prop="quantity">
              <el-input v-model="form.quantity" placeholder="数量"></el-input>
            </el-form-item>
            <el-form-item label="单价" prop="price">
              <el-input v-model="form.price" placeholder="单价"></el-input>
            </el-form-item>
            <el-form-item label="总价" prop="totalPrice">
              <el-input v-model="form.totalPrice" placeholder="总价"></el-input>
            </el-form-item>
            <el-form-item label="原产国" prop="fromCountry">
              <el-input v-model="form.fromCountry" placeholder="原产国"></el-input>
            </el-form-item>
            <el-form-item label="最终目的地" prop="toCountry">
              <el-input v-model="form.toCountry" placeholder="最终目的地"></el-input>
            </el-form-item>
            <el-form-item label="境内货源地" prop="goodplace">
              <el-input v-model="form.goodplace" placeholder="境内货源地"></el-input>
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
  name: "NewDeclare",
  data() {
    return {
      execId:5,
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      invs: [],
      total: 0,
      inv: null,
      hscode:null,
      fromVisible: false,
      form: {orderNo:""},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        inv: [
          {required: true, message: '请输入合同号', trigger: 'blur'},
        ],
        hscode: [
          {required: true, message: '请输入HS_CODE', trigger: 'blur'},
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
     // this.$router.push({ path: '/succ'}); // 跳转到订单信息页面
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
      window.open(this.$baseUrl + '/declare/export')
    },
    changeStatus(row) {
      this.$request.put('/declare/update', row).then(res => {
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
          this.$request.get('/order/checkProdSta/'+that.form.orderNo).then(res=>{
              if (res.fstatus==='已完成'){
                this.$request({
                  url: this.form.id ? '/declare/update' : '/declare/add',
                  method: this.form.id ? 'PUT' : 'POST',
                  data: this.form
                }).then(res => {
                  if (res.code === '200') {  // 表示成功保存
                    this.$message.success('保存成功')
                    this.load(1)
                    this.fromVisible = false
                    this.$router.push({ path: '/succ',query: { id: that.form.orderNo  }});
                    this.$request.get('/order/updateStatus/'+that.form.orderNo)
                    this.$request.get('/order/updateExec/'+that.form.orderNo+"/"+this.execId)
                  } else {
                    this.$message.error(res.msg)  // 弹出错误的信息
                  }
                })
              }else {
                this.$message.error("工厂未完成生产，业务流程无法结束！")
              }
          })


        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/declare/delete/' + id).then(res => {
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
        this.$request.delete('/declare/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/declare/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          inv: this.inv,
          hscode:this.hscode,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
      this.$request.get('/packlist/selectAll').then(res => {
        this.invs = res.data || []
      })
    },
    reset() {
      this.inv = null
      this.hscode = null
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