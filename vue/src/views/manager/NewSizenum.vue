<template>
  <div class="table">
    <br>
    <br>
    <br>
    <div @click="handleStepClick">
      <el-steps align-center :active="1" finish-status="success">
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
        <th>配码信息</th>
        <th>配码信息详情</th>
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
            <el-form-item label="订单编号" prop="orderNo" v-if="shouldShowOrderNo">
              <el-select style="width: 100%" v-model="form.orderNo">
                <el-option v-for="item in orders"  :key="item.id" :value="item.orderNo" :label="item.orderNo"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="配码代码" prop="asscode">
              <el-input v-model="form.asscode" placeholder="配码代码"></el-input>
            </el-form-item>
            <el-form-item label="颜色" prop="color">
              <el-input v-model="form.color" placeholder="颜色"></el-input>
            </el-form-item>
            <el-form-item label="36" prop="tsi" v-if="orders.size === '36-37' || orders.size === '36-38' || orders.size === '36-39' || orders.size === '36-40' || orders.size === '36-41' || orders.size === '36-42' || orders.size !== '36'">
              <el-input-number size="mini" v-model="form.tsi"></el-input-number>
            </el-form-item>
            <el-form-item label="37" prop="tse" v-if="orders.size === '36-37' || orders.size === '36-38' || orders.size === '36-39' || orders.size === '36-40' || orders.size === '36-41' || orders.size === '36-42' || orders.size !== '37' || orders.size === '36-37' || orders.size === '37-38' || orders.size === '37-39' || orders.size === '37-40' || orders.size === '37-41' || orders.size === '37-42'">
              <el-input-number size="mini" v-model="form.tse"></el-input-number>
            </el-form-item>
            <el-form-item label="38" prop="te" v-if="orders.size !== '36-37' || orders.size !== '39-40' || orders.size !== '39-41' || orders.size !== '39-42' || orders.size !== '40-41' || orders.size !== '40-42' || orders.size !== '41-42'|| orders.size !== '42' || orders.size !== '41' || orders.size !== '40' || orders.size !== '39' || orders.size !== '37' || orders.size !== '36'">
              <el-input-number size="mini" v-model="form.te"></el-input-number>
            </el-form-item>
            <el-form-item label="39" prop="tn" v-if="orders.size !== '36-37' || orders.size !== '36-38' || orders.size !== '37-38' || orders.size !== '40-41' || orders.size !== '40-42' || orders.size !== '41-42'|| orders.size !== '42' || orders.size !== '41' || orders.size !== '40' || orders.size !== '38' || orders.size !== '37' || orders.size !== '36'">
              <el-input-number size="mini" v-model="form.tn"></el-input-number>
            </el-form-item>
            <el-form-item label="40" prop="ft" v-if="orders.size !== '36-37' || orders.size !== '36-38' || orders.size !== '36-39' || orders.size !== '37-38' || orders.size !== '37-39' || orders.size !== '38-39' || orders.size !== '41-42'|| orders.size !== '42' || orders.size !== '41' || orders.size !== '38' || orders.size !== '39' || orders.size !== '37' || orders.size !== '36'">
              <el-input-number size="mini" v-model="form.ft"></el-input-number>
            </el-form-item>
            <el-form-item label="41" prop="fo" v-if="orders.size === '36-41' || orders.size === '36-42' || orders.size === '37-41' || orders.size === '37-42' || orders.size !== '38-41' || orders.size === '38-42' || orders.size === '39-41' || orders.size === '39-42' || orders.size === '40-41' || orders.size === '40-42' || orders.size === '41-42' || orders.size !== '41'">
              <el-input-number size="mini" v-model="form.fo"></el-input-number>
            </el-form-item>
            <el-form-item label="42" prop="fw" v-if="orders.size === '36-42' || orders.size === '37-42' || orders.size === '38-42' || orders.size === '39-42' || orders.size === '40-42' || orders.size === '41-42' || orders.size === '42'">
              <el-input-number size="mini" v-model="form.fw"></el-input-number>
            </el-form-item>
            <el-form-item label="规格" prop="unit">
              <el-input v-model="form.unit" placeholder="规格"></el-input>
            </el-form-item>
            <el-form-item label="箱数" prop="boxes">
              <el-input v-model="form.boxes" placeholder="箱数"></el-input>
            </el-form-item>
            <el-form-item label="总数" prop="stotal">
              <el-input v-model="form.stotal" placeholder="总数"></el-input>
            </el-form-item>
            <el-form-item label="箱号" prop="boxesNum">
              <el-input v-model="form.boxesNum" placeholder="箱号"></el-input>
            </el-form-item>
          </el-form>
        </td>
      </tr>
      </tbody>
    </table>
    <div slot="footer" class="dialog-footer">
<!--      <el-button @click="fromVisible1()">返 回</el-button>-->
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </div>
</template>

<script>
import router from "@/router";
export default {
  name: "NewSizenum",
  data() {
    return {
      execId:2,
      shouldShowOrderNo: false, // 控制是否显示订单编号表单项,
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      username: null,
      fromVisible: false,
      orders:[],
      form: {orderNo:""},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        orderNo: [
          {required: true, message: '请输入订单编号', trigger: 'blur'},
        ],
        asscode: [
          {required: true, message: '请输入配码代码', trigger: 'blur'},
        ]
      },
      ids: [],
    }
  },
  created() {
    this.load(1)
    this.form.orderNo = this.$route.query.id || '';
  },
  methods: {
    fromVisible1(){
      this.$request.get('/order/selectByOrderId/'+this.form.orderNo).then(res=>{
        this.$router.push({ path: '/newOrder',query: { form: res.data }});
      })


    },
    handleStepClick(){
     // this.$router.push({ path: '/newPacklist'}); // 跳转到订单信息页面
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
      window.open(this.$baseUrl + '/sizenum/export')
    },
    changeStatus(row) {
      this.$request.put('/sizenum/update', row).then(res => {
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
            url: this.form.id ? '/sizenum/update' : '/sizenum/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
              this.$router.push({ path: '/newPacklist',query: { id: that.form.orderNo  }});
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
        this.$request.delete('/sizenum/delete/' + id).then(res => {
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
        this.$request.delete('/sizenum/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/sizenum/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderNo: this.orderNo,
          asscode: this.asscode,
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
      this.asscode = null
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