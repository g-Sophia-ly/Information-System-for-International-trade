<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入客户名称查询" style="width: 200px" v-model="customerName"></el-input>
      <el-input placeholder="请输入订单号查询" style="width: 200px; margin-left: 5px" v-model="inv"></el-input>
<!--      <el-input placeholder="请输入港口查询" style="width: 200px; margin-left: 5px" v-model="fromcus"></el-input>-->
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
<!--      <el-button type="primary" plain @click="handleAdd">新增</el-button>-->
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/packlist/import'" :headers="{ token: user.token }" :on-success="handleImport" >
<!--        <el-button type="info" plain>批量导入</el-button>-->
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="customerName" label="客户"></el-table-column>
        <el-table-column prop="fromcus" label="港口"></el-table-column>
        <el-table-column prop="inv" label="合同号"></el-table-column>
        <el-table-column prop="todate" label="日期"></el-table-column>
        <el-table-column label="货名及数量">
          <el-table-column prop="shoes" label="鞋子种类"></el-table-column>
          <el-table-column prop="prs" label="总数"></el-table-column>
          <el-table-column prop="ctns" label="箱数"></el-table-column>
        </el-table-column>
        <el-table-column prop="netw" label="净重"></el-table-column>
        <el-table-column prop="gross" label="毛重"></el-table-column>
        <el-table-column prop="cbm" label="立方米"></el-table-column>
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


    <el-dialog title="装箱单" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">

        <el-form-item label="客户" prop="customerId">
          <el-select style="width: 100%" v-model="form.customerId">
            <el-option v-for="item in customers" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
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

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Order",
  data() {
    return {
      active: 0,
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      customers: [],
      total: 0,
      customerName:null,
      inv: null,
      fromVisible: false,
      form: {},
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
      fromcus: null,
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleStepClick(){
      this.$router.push({ path: '/ship'}); // 跳转到订单信息页面
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