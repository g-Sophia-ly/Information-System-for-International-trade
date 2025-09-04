<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入商品编号查询" style="width: 200px" v-model="goodsNo"></el-input>
      <el-input placeholder="请输入商品属性查询" style="width: 200px; margin-left: 5px" v-model="types"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/goods/import'" :headers="{ token: user.token }" :on-success="handleImport" >
        <el-button type="info" plain>批量导入</el-button>
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="goodsNo" label="商品编号" align="center"></el-table-column>
        <el-table-column prop="types" label="商品属性" align="center"></el-table-column>
        <el-table-column prop="price" label="成本（美金）" align="center"></el-table-column>
        <el-table-column prop="fabric" label="面料" align="center"></el-table-column>
        <el-table-column prop="lining" label="内里" align="center"></el-table-column>
        <el-table-column prop="heel" label="鞋跟" align="center"></el-table-column>
        <el-table-column prop="outsole" label="大底" align="center"></el-table-column>
        <el-table-column label="商品图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="stotal" label="总下单量" align="center"></el-table-column>
        <el-table-column prop="ptotal" label="总下单额（美金）" width="120px" align="center"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status === '可用'">可用</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '不可用'">不可用</el-tag>
          </template>
        </el-table-column>
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


    <el-dialog title="商品" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="商品编号" prop="goodsNo">
          <el-input v-model="form.goodsNo" placeholder="商品编号"></el-input>
        </el-form-item>
        <el-form-item label="商品属性" prop="types">
          <el-input v-model="form.types" placeholder="商品属性"></el-input>
        </el-form-item>
        <el-form-item label="成本" prop="price">
          <el-input v-model="form.price" placeholder="成本"></el-input>
        </el-form-item>
        <el-form-item label="面料" prop="fabric">
          <el-input v-model="form.fabric" placeholder="面料"></el-input>
        </el-form-item>
        <el-form-item label="内里" prop="lining">
          <el-input v-model="form.lining" placeholder="内里"></el-input>
        </el-form-item>
        <el-form-item label="鞋跟" prop="heel">
          <el-input v-model="form.heel" placeholder="鞋跟"></el-input>
        </el-form-item>
        <el-form-item label="大底" prop="outsole">
          <el-input v-model="form.outsole" placeholder="大底"></el-input>
        </el-form-item>
        <el-form-item label="总下单量" prop="stotal">
          <el-input v-model="form.stotal" placeholder="总下单量"></el-input>
        </el-form-item>
        <el-form-item label="总下单额" prop="ptotal">
          <el-input v-model="form.ptotal" placeholder="总下单额"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select style="width: 100%" v-model="form.status">
            <el-option v-for="item in ['可用', '不可用']" :key="item" :value="item" :label="item"></el-option>
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
  name: "Goods",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      goodsNo: null,
      types: null,
      total: 0,
      username: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        goodsNo: [
          {required: true, message: '请输入商品编号', trigger: 'blur'},
        ],
        types: [
          {required: true, message: '请输入商品属性', trigger: 'blur'},
        ],
      },
      ids: [],
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleImport(res) {
      if (res.code === '200') {  // 成功
        this.$message.success('导入成功')
        this.load(1)  // 刷新表格数据
      } else {
        this.$message.error(res.msg)
      }
    },
    exp() {
      window.open(this.$baseUrl + '/goods/export')
    },
    changeStatus(row) {
      this.$request.put('/goods/update', row).then(res => {
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
            url: this.form.id ? '/goods/update' : '/goods/add',
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
        this.$request.delete('/goods/delete/' + id).then(res => {
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
        this.$request.delete('/goods/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/goods/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          goodsNo: this.goodsNo,
          types:this.types,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },


    reset() {
      this.goodsNo = null
      this.types = null
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