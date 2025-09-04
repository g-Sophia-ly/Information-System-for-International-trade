<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入订单编号" style="width: 200px" v-model="orderNo"></el-input>
      <el-select v-model="status" placeholder="请选择状态" style="width: 200px; margin-left: 5px">
        <el-option label="已处理" value="已处理"></el-option>
        <el-option label="进行中" value="进行中"></el-option>
        <el-option label="未处理" value="未处理"></el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation"  v-if="user.role === 'ADMIN'">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="orderNo" label="订单编号"  align="center" width="120px"></el-table-column>
        <el-table-column prop="content" label="内容" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column prop="time" label="创建时间"  align="center" width="120px"></el-table-column>
        <el-table-column prop="factory" label="创建人"  align="center" width="120px"></el-table-column>
        <el-table-column prop="staff" label="负责人"  align="center" width="120px"></el-table-column>
        <el-table-column prop="status" label="状态"  align="center" width="150px">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status === '已处理'">已处理</el-tag>
            <el-tag type="primary" v-if="scope.row.status === '进行中'">进行中</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '未处理'">未处理</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="exp" label="审核说明" show-overflow-tooltip width="200px" align="center"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini" v-if="user.role !== 'FACTORY'">审核</el-button>
<!--            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini" v-if="user.role !== 'FACTORY' && scope.row.status !== '已处理'">审核</el-button>-->
            <el-button plain type="danger" size="mini" @click=del(scope.row.id) v-if="user.role === 'FACTORY'" :disabled="scope.row.status !== '未处理'">撤销问题</el-button>
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


    <el-dialog orderNo="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="orderNo" label="订单编号">
          <el-input v-model="form.orderNo" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="状态" prop="status">-->
<!--          <el-select style="width: 100%" v-model="form.status">-->
<!--            <el-option v-for="item in ['已处理','进行中','未处理']" :key="item" :value="item" :label="item"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item prop="status" label="状态">
          <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="已处理" value="已处理"></el-option>
            <el-option label="进行中" value="进行中"></el-option>
            <el-option label="未处理" value="未处理"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="exp" label="审核说明">
          <el-input type="textarea" :rows="5" v-model="form.exp" autocomplete="off"></el-input>
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
  name: "Notice",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      orderNo: null,
      status:null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        orderNo: [
          {required: true, message: '请输入订单编号', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '请输入内容', trigger: 'blur'},
        ]
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/notice/update' : '/notice/add',
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
        this.$request.delete('/notice/delete/' + id).then(res => {
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
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/notice/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/notice/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderNo: this.orderNo,
          status: this.status,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.orderNo = null
      this.status = null
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
