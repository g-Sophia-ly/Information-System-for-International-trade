<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入到达港查询" style="width: 200px" v-model="arrival"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
<!--      <el-button type="primary" plain @click="handleAdd">新增</el-button>-->
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/ship/import'" :headers="{ token: user.token }" :on-success="handleImport" >
<!--        <el-button type="info" plain>批量导入</el-button>-->
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="shipper" label="发货地址" show-overflow-tooltip></el-table-column>
        <el-table-column prop="consignee" label="收货地址" show-overflow-tooltip></el-table-column>
        <el-table-column prop="notify" label="通知人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orderNo" label="订单编号" ></el-table-column>
        <el-table-column prop="types" label="品名"></el-table-column>
        <el-table-column prop="hscode" label="商品编号" width="110px" align="center"></el-table-column>
        <el-table-column prop="depature" label="启运港" width="130px" align="center"></el-table-column>
        <el-table-column prop="arrival" label="到达港" width="110px" align="center"></el-table-column>
        <el-table-column prop="schedule" label="船期" width="110px" align="center"></el-table-column>
        <el-table-column label="货物描述">
          <el-table-column prop="freight" label="货代"></el-table-column>
          <el-table-column prop="cartons" label="箱数"></el-table-column>
        </el-table-column>
        <el-table-column prop="prs" label="双数"></el-table-column>
        <el-table-column prop="containers" label="集装箱描述"></el-table-column>
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


    <el-dialog title="订船资料" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
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
          <el-form-item label="订单编号" prop="orderNo">
            <el-select style="width: 100%" v-model="form.orderNo">
              <el-option v-for="item in orders"  :key="item.id" :value="item.orderNo" :label="item.orderNo"></el-option>
            </el-select>
          </el-form-item>
        <el-form-item label="品名" prop="types">
          <el-input v-model="form.types" placeholder="品名"></el-input>
        </el-form-item>
        <el-form-item label="HS_CODE" prop="hscode">
          <el-select v-model="form.hscode" allow-create filterable placeholder="请选择商品编号" style="width: 200px; margin-left: 5px">
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
<!--        <el-form-item label="集装箱描述" prop="containers">-->
<!--          <el-input v-model="form.containers" placeholder="集装箱种类及数量"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="集装箱描述" prop="containers">
          <el-select v-model="form.containers" placeholder="请选择" style="width: 200px; margin-left: 5px">
            <el-option label="40#GP" value="1个40#GP"></el-option>
            <el-option label="20#GP" value="1个20#GP"></el-option>
            <el-option label="40#HQ" value="1个40#HQ"></el-option>
          </el-select>
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
  name: "Ship",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      orders: [],
      total: 0,
      arrival:null,
      fromVisible: false,
      form: {},
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
  },
  methods: {
    handleStepClick(){
      this.$router.push({ path: '/declare'}); // 跳转到订单信息页面
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