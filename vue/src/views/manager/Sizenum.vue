<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入订单编号查询" style="width: 200px" v-model="orderNo"></el-input>
      <el-input placeholder="请输入配码代码查询" style="width: 200px; margin-left: 5px" v-model="asscode"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
<!--      <el-button type="primary" plain @click="handleAdd">新增</el-button>-->
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/sizenum/import'" :headers="{ token: user.token }" :on-success="handleImport" >
<!--        <el-button type="info" plain>批量导入</el-button>-->
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="orderNo" label="订单编号"></el-table-column>
        <el-table-column prop="asscode" label="配码代码"></el-table-column>
        <el-table-column prop="color" label="颜色"></el-table-column>
        <el-table-column prop="tsi" label="36"></el-table-column>
        <el-table-column prop="tse" label="37"></el-table-column>
        <el-table-column prop="te" label="38"></el-table-column>
        <el-table-column prop="tn" label="39"></el-table-column>
        <el-table-column prop="ft" label="40"></el-table-column>
        <el-table-column prop="fo" label="41"></el-table-column>
        <el-table-column prop="fw" label="42"></el-table-column>
        <el-table-column prop="unit" label="规格"></el-table-column>
        <el-table-column prop="boxes" label="箱数"></el-table-column>
        <el-table-column prop="stotal" label="总数"></el-table-column>
        <el-table-column prop="boxesNum" label="箱号"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)" v-if="user.role === 'ADMIN'">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="配码单" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="订单编号" prop="orderNo">
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
        <el-form-item label="36" prop="tsi">
          <el-input-number size="mini" v-model="form.tsi" v-if="order.size === '36-37' || order.size === '36-38' || order.size === '36-39' || order.size === '36-40'"></el-input-number>
        </el-form-item>
        <el-form-item label="37" prop="tse">
          <el-input-number size="mini" v-model="form.tse"></el-input-number>
        </el-form-item>
        <el-form-item label="38" prop="te">
          <el-input-number size="mini" v-model="form.te"></el-input-number>
        </el-form-item>
        <el-form-item label="39" prop="tn">
          <el-input-number size="mini" v-model="form.tn"></el-input-number>
        </el-form-item>
        <el-form-item label="40" prop="ft">
          <el-input-number size="mini" v-model="form.ft"></el-input-number>
        </el-form-item>
        <el-form-item label="41" prop="fo">
          <el-input-number size="mini" v-model="form.fo"></el-input-number>
        </el-form-item>
        <el-form-item label="42" prop="fw">
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

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import order from "@/views/manager/Order.vue";

export default {
  name: "Sizenum",
  computed: {
    order() {
      return order
    }
  },
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      username: null,
      fromVisible: false,
      orders:[],
      form: {},
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
  },
  methods: {
    handleStepClick(){
      this.$router.push({ path: '/packlist'}); // 跳转到订单信息页面
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
      let user = JSON.parse(localStorage.getItem("xm-user") || '{}')
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/sizenum/selectPage?role='+user.role+"&userId="+user.id, {
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