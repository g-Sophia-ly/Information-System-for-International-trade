<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入商品编号查询" style="width: 200px" v-model="goodNo"></el-input>
      <el-input placeholder="请输入订单编号查询" style="width: 200px; margin-left: 5px" v-model="orderNo"></el-input>
      <el-select v-model="status" placeholder="请选择状态" style="width: 200px; margin-left: 5px">
        <el-option label="已出货" value="已出货"></el-option>
        <el-option label="未出货" value="未出货"></el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation" v-if="user.role !== 'FACTORY'">
<!--      <el-button type="primary" plain @click="handleAdd">新增</el-button>-->
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/order/import'" :headers="{ token: user.token }" :on-success="handleImport" >
<!--        <el-button type="info" plain>批量导入</el-button>-->
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="customerName" label="客户" v-if="user.role !== 'FACTORY'" width="130px" align="center" ></el-table-column>
        <el-table-column prop="orderNo" label="订单编号" width="120px"></el-table-column>
        <el-table-column prop="factoryName" label="工厂" v-if="user.role !== 'FACTORY'"></el-table-column>
        <el-table-column prop="goodNo" label="商品编号"></el-table-column>
        <el-table-column prop="color" label="颜色"></el-table-column>
        <el-table-column prop="size" label="码段"></el-table-column>
        <el-table-column prop="price" label="单价（美金）" width="120px"></el-table-column>
        <el-table-column prop="quantity" label="数量"></el-table-column>
        <el-table-column show-overflow-tooltip prop="notice" label="特别要求"></el-table-column>
        <el-table-column label="商品图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="date" label="工厂交货日期" width="100px"></el-table-column>
        <el-table-column prop="status" label="状态" v-if="user.role !== 'FACTORY'">
          <template v-slot="scope">
            <el-tag type="danger" v-if="scope.row.status === '未出货'">未出货</el-tag>
            <el-tag type="success" v-if="scope.row.status === '已出货'">已出货</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="fstatus" label="生产状态">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.fstatus === '已完成'">已完成</el-tag>
            <el-tag type="danger" v-if="scope.row.fstatus === '未完成'">未完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="staffName" label="跟单员"></el-table-column>
        <el-table-column label="操作" align="center" width="250">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)" v-if="user.role !== 'FACTORY'">编辑</el-button>
            <el-button size="mini" type="warning" plain @click="unsuc(scope.row)" v-if="user.role !== 'FACTORY'" >返工</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)" v-if="user.role === 'ADMIN'">删除</el-button>
            <el-button size="mini" type="success" plain @click="suc(scope.row)" v-if="user.role === 'FACTORY'">生产完成</el-button>
            <el-button plain type="primary" size="mini" @click="initNotice(scope.row)" :disabled="scope.row.status !== '未出货'" v-if="user.role === 'FACTORY'">提交生产问题</el-button>
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


    <el-dialog title="订单" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">

        <el-form-item label="客户" prop="customerId">
          <el-select style="width: 100%" v-model="form.customerId">
            <el-option v-for="item in customers" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="订单编号"></el-input>
        </el-form-item>
        <el-form-item label="工厂" prop="factoryId">
          <el-select style="width: 100%" v-model="form.factoryId">
            <el-option v-for="item in factories" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品编号" prop="goodNo">
          <el-select style="width: 100%" v-model="form.goodNo">
            <el-option v-for="item in goodss"  :key="item.id" :value="item.goodsNo" :label="item.goodsNo"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="颜色"></el-input>
        </el-form-item>
        <el-form-item label="码段" prop="size">
          <el-input v-model="form.size" placeholder="码段"></el-input>
        </el-form-item>
        <el-form-item label="单价$" prop="price">
          <el-input v-model="form.price" placeholder="单价"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="数量"></el-input>
        </el-form-item>
          <el-form-item label="工厂交货日期" prop="date">
            <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                            v-model="form.date" style="width: 100%"></el-date-picker>
          </el-form-item>
          <el-form-item prop="notice" label="特别要求">
            <el-input type="textarea" :rows="5" v-model="form.notice" autocomplete="off"></el-input>
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
        <el-form-item label="负责人" prop="staffId">
          <el-select style="width: 100%" v-model="form.staffId">
            <el-option v-for="item in staffs" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="生产问题通知" :visible.sync="fromVisible2" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="content" label="生产问题内容">
          <el-input type="textarea" :rows="5" v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="noticesave">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import router from "@/router";
import {post} from "axios";

export default {
  name: "Order",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      customers: [],
      factories: [],
      goodss:[],
      staffs:[],
      goodNo:null,
      status:null,
      orderNo:null,
      total: 0,
      username: null,
      fromVisible: false,
      fromVisible2:false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        customerId: [
          {required: true, message: '请输入客户名称', trigger: 'blur'},
        ],
        goodNo: [
          {required: true, message: '请输入商品编号', trigger: 'blur'},
        ],
        quantity: [
          {required: true, message: '请输入商品数量', trigger: 'blur'},
        ],
        price: [
          {required: true, message: '请输入订单价格', trigger: 'blur'},
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
      this.$router.push({ path: '/sizenum'}); // 跳转到订单信息页面
    },
    initNotice(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.fromVisible2 = true
    },
    router() {
      return router
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
      window.open(this.$baseUrl + '/order/export')
    },
    changeStatus(row) {
      this.$request.put('/order/update', row).then(res => {
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
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新\
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/order/update' : '/order/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form,
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
    noticesave() {
      let data = {
        staff: this.form.staffName,
        orderNo: this.form.orderNo,
        content: this.form.content,
        status: '未处理'
      }
      this.$request.post('/notice/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.fromVisible2 = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    suc(row) {
     // alert(row.goodNo)
      let data = {
        fstatus: '已完成',
        id: row.id,
        goodNo:row.goodNo,
        quantity: row.quantity,
        price: row.price
      }
      console.log(data)
      this.$request.put('/order/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.load();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    unsuc(row) {
      // alert(row.goodNo)
      let data = {
        fstatus: '未完成',
        id: row.id,
        goodNo:row.goodNo,
        quantity: row.quantity,
        price: row.price
      }
      console.log(data)
      this.$request.put('/order/update', data).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          this.load();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/order/delete/' + id).then(res => {
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
        this.$request.delete('/order/delete/batch', {data: this.ids}).then(res => {
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
      let user = JSON.parse(localStorage.getItem("xm-user") || '{}')
      this.$request.get('/order/selectPage?role='+user.role+"&userId="+user.id, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          goodNo: this.goodNo,
          status: this.status,
          orderNo: this.orderNo,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
      this.$request.get('/customer/selectAll').then(res => {
        this.customers = res.data || []
      })
      this.$request.get('/factory/selectAll').then(res => {
        this.factories = res.data || []
      })
      this.$request.get('/goods/selectAll').then(res => {
        this.goodss = res.data || []
      })
      this.$request.get('/staff/selectAll').then(res => {
        this.staffs = res.data || []
      })
    },


    reset() {
      this.status = null
      this.goodNo = null
      this.orderNo = null
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