<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入订单号查询" style="width: 200px; margin-left: 5px" v-model="inv"></el-input>
      <el-input placeholder="请输入商品编号查询" style="width: 200px; margin-left: 5px" v-model="hscode"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
<!--      <el-button type="primary" plain @click="handleAdd">新增</el-button>-->
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/declare/import'" :headers="{ token: user.token }" :on-success="handleImport" >
<!--        <el-button type="info" plain>批量导入</el-button>-->
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="inv" label="合同号" align="center"></el-table-column>
        <el-table-column prop="hscode" label="商品编号" align="center"></el-table-column>
          <el-table-column prop="types" label="商品名称" align="center"></el-table-column>
          <el-table-column prop="quantity" label="数量" align="center"></el-table-column>
          <el-table-column prop="price" label="单价" align="center"></el-table-column>
        <el-table-column prop="totalPrice" label="总价" align="center"></el-table-column>
        <el-table-column prop="fromCountry" label="原产国" align="center"></el-table-column>
        <el-table-column prop="toCountry" label="最终目的地" align="center"></el-table-column>
        <el-table-column prop="goodplace" label="境内货源地" align="center"></el-table-column>
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


    <el-dialog title="报关资料" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
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

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Declare",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      invs: [],
      total: 0,
      inv: null,
      hscode:null,
      fromVisible: false,
      form: {},
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
  },
  methods: {
    handleStepClick(){
      this.$router.push({ path: '/succ'}); // 跳转到订单信息页面
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
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/declare/update' : '/declare/add',
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