<template>
  <div class="table">
    <br>
    <br>
    <br>
    <div @click="handleStepClick">
      <el-steps align-center :active="0" finish-status="success">
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
        <th>订单编号</th>
        <th>订单信息详情</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>
          <el-row justify="center">
            <el-col :span="12">
              <div class="square">
                <div class="input-container">
                  订单编号（二选一）：
                <el-input v-model="form.orderNo" placeholder="新建订单编号"></el-input>
                <br/>
                <el-select style="width: 100%" filterable v-model="form.orderNoOld" @change="fetchData" placeholder="选择已创建的订单">
                  <el-option v-for="item in orderss"  :key="item.id" :value="item.orderNo" :label="item.orderNo" v-if="item.status !== '已出货'"></el-option>
                </el-select>
              </div>
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
            <el-form-item prop="status" label="状态">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="已出货" value="已出货"></el-option>
                <el-option label="未出货" value="未出货"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="负责人" prop="staffId">
              <el-select style="width: 100%" v-model="form.staffId">
                <el-option v-for="item in staffs" :key="item.id" :value="item.id" :label="item.name"></el-option>
              </el-select>
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
<style>
table {
  border-collapse: collapse;
  width: 100%;
  border: 1px solid #dddddd; /* 设置整个表格的边框样式 */
}

th, td {
  border: 1px solid #dddddd; /* 设置每个单元格的边框样式 */
  text-align: left;
  padding: 8px;
}
.square {
  width: 200px; /* 正方形的宽度 */
  height: 200px; /* 正方形的高度 */
  border: none; /* 隐藏边框 */
  display: flex;
  justify-content: center;
  align-items: center;
}
.input-container {
  display: flex;
  flex-direction: column;
}
col:first-child {
  width: 100px; /* 这里设置您想要的固定宽度 */
}
.label {
  margin-bottom: 10px; /* 设置文本和输入框之间的间距 */
}

th {
  background-color: #f2f2f2;
}
</style>
<script>
import router from "@/router";
export default {
  name: "NewOrder",
  data() {
    return {
      execId:1,
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
      orderNoOld:null,
      orderss:[],
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
    fetchData(orderNoOld){
      //alert(this.user.id)
      this.$request.get('/order/selectByOrderId/'+orderNoOld, {
        params: {
          orderNoOld:this.orderNoOld
        }
      }).then(res => {
        console.log(res)
        if(res.code === '200'){
            if(res.data.status==="已出货"){
              this.$message.success('该订单已完成！')
            }else {
                  var execId = res.data.execId;
                  //alert(execId)
                  if (execId===1){
                    this.$router.push({ path: '/newSizenum',query: { id: res.data.orderNo  }});
                  }else if (execId===2){
                    this.$router.push({ path: '/newPacklist',query: { id: res.data.orderNo  }});
                  }else if (execId===3){
                    this.$router.push({ path: '/newShip',query: { id: res.data.orderNo  }});
                  }else if (execId===4){
                    this.$router.push({ path: '/newDeclare',query: { id: res.data.orderNo  }});
                  }
                }
        }
      })
    },

      handleStepClick(){
        //this.$router.push({ path: '/newSizenum'}); // 跳转到订单信息页面
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
      save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
        var that = this;
        //alert(that.form.orderNoOld)
        if(that.form.orderNoOld ===undefined){

        }
        this.$refs.formRef.validate((valid) => {
          if (valid) {
            this.$request({
              url: this.form.id ? '/order/update' : '/order/add',
              method: this.form.id ? 'PUT' : 'POST',
              data: this.form
            }).then(res => {
              if (res.code === '200') {  // 表示成功保存
                this.$message.success('保存成功')
                this.load(1)
                this.fromVisible = false
                this.$router.push({ path: '/newSizenum',query: { id: that.form.orderNo  }});
                this.$request.get('/order/updateExec/'+that.form.orderNo+"/"+this.execId)
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
      //alert(this.$route.query.form)
      var that = this;
        if (pageNum) this.pageNum = pageNum
        this.$request.get('/order/selectPage', {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            goodNo: this.goodNo,
            status: this.status,
          }
        }).then(res => {
          //console.log(res.data.list[0].staffId)
          this.tableData = res.data.list
          this.total = res.data?.total
          console.log("33",res.data.list[0])
          this.$request.get('/order/selectAllByStaffId').then(res => {
            this.orderss = res.data || []
          })
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