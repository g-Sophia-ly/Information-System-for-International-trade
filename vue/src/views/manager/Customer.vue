<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入姓名查询" style="width: 200px" v-model="name"></el-input>
      <el-input placeholder="请输入国家查询" style="width: 200px; margin-left: 10px" v-model="country"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch" v-if="user.role === 'ADMIN'">批量删除</el-button>
      <el-button type="warning" plain @click="exp">批量导出</el-button>
      <el-upload :show-file-list="false" style="display: inline-block; margin-left: 10px" :action="$baseUrl + '/customer/import'" :headers="{ token: user.token }" :on-success="handleImport" >
                <el-button type="info" plain v-if="user.role === 'ADMIN'">批量导入</el-button>
      </el-upload>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column prop="country" label="国家" align="center"></el-table-column>
        <el-table-column prop="tel" label="电话" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column prop="payment" label="付款方式" align="center"></el-table-column>
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


    <el-dialog title="客户" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="国家" prop="country">
          <el-select v-model="form.country" placeholder="请选择国家" style="width: 200px; margin-left: 5px">
            <el-option label = "Afghanistan"  value = "阿富汗"></el-option>
            <el-option label = "Angola"  value = "安哥拉"></el-option>
            <el-option label = "Albania"  value = "阿尔巴尼亚"></el-option>
            <el-option label = "Algeria"  value = "阿尔及利亚"></el-option>
            <el-option label = "Argentina"  value = "阿根廷"></el-option>
            <el-option label = "Armenia"  value = "亚美尼亚"></el-option>
            <el-option label = "Australia"  value = "澳大利亚"></el-option>
            <el-option label = "Austria"  value = "奥地利"></el-option>
            <el-option label = "Azerbaijan"  value = "阿塞拜疆"></el-option>
            <el-option label = "Bahamas"  value = "巴哈马"></el-option>
            <el-option label = "Bangladesh"  value = "孟加拉国"></el-option>
            <el-option label = "Belgium"  value = "比利时"></el-option>
            <el-option label = "Benin"  value = "贝宁"></el-option>
            <el-option label = "Burkina Faso"  value = "布基纳法索"></el-option>
            <el-option label = "Burundi"  value = "布隆迪"></el-option>
            <el-option label = "Bulgaria"  value = "保加利亚"></el-option>
            <el-option label = "Bosnia and Herz."  value = "波斯尼亚和黑塞哥维那"></el-option>
            <el-option label = "Belarus"  value = "白俄罗斯"></el-option>
            <el-option label = "Belize"  value = "伯利兹"></el-option>
            <el-option label = "Bermuda"  value = "百慕大群岛"></el-option>
            <el-option label = "Bolivia"  value = "玻利维亚"></el-option>
            <el-option label = "Brazil"  value = "巴西"></el-option>
            <el-option label = "Brunei"  value = "文莱"></el-option>
            <el-option label = "Bhutan"  value = "不丹"></el-option>
            <el-option label = "Botswana"  value = "博茨瓦纳"></el-option>
            <el-option label = "Cambodia"  value = "柬埔寨"></el-option>
            <el-option label = "Cameroon"  value = "喀麦隆"></el-option>
            <el-option label = "Canada"  value = "加拿大"></el-option>
            <el-option label = "Central African Rep."  value = "中非共和国"></el-option>
            <el-option label = "Chad"  value = "乍得"></el-option>
            <el-option label = "Chile"  value = "智利"></el-option>
            <el-option label = "China"  value = "中国"></el-option>
            <el-option label = "Colombia"  value = "哥伦比亚"></el-option>
            <el-option label = "Congo"  value = "刚果"></el-option>
            <el-option label = "Costa Rica"  value = "哥斯达黎加"></el-option>
            <el-option label = "Côte d'Ivoire"  value = "科特迪瓦"></el-option>
            <el-option label = "Croatia"  value = "克罗地亚"></el-option>
            <el-option label = "Cuba"  value = "古巴"></el-option>
            <el-option label = "Cyprus"  value = "塞浦路斯"></el-option>
            <el-option label = "Czech Rep."  value = "捷克共和国"></el-option>
            <el-option label = "Dem. Rep. Korea"  value = "韩国"></el-option>
            <el-option label = "Dem. Rep. Congo"  value = "民主刚果"></el-option>
            <el-option label = "Denmark"  value = "丹麦"></el-option>
            <el-option label = "Djibouti"  value = "吉布提"></el-option>
            <el-option label = "Dominican Rep."  value = "多米尼加共和国"></el-option>
            <el-option label = "Ecuador"  value = "厄瓜多尔"></el-option>
            <el-option label = "Egypt"  value = "埃及"></el-option>
            <el-option label = "El Salvador"  value = "萨尔瓦多"></el-option>
            <el-option label = "Eq. Guinea"  value = "赤道几内亚"></el-option>
            <el-option label = "Eritrea"  value = "厄立特里亚"></el-option>
            <el-option label = "Estonia"  value = "爱沙尼亚"></el-option>
            <el-option label = "Ethiopia"  value = "埃塞俄比亚"></el-option>
            <el-option label = "Falkland Is."  value = "福克兰群岛"></el-option>
            <el-option label = "Fiji"  value = "斐济"></el-option>
            <el-option label = "Finland"  value = "芬兰"></el-option>
            <el-option label = "France"  value = "法国"></el-option>
            <el-option label = "French Guiana"  value = "法属圭亚那"></el-option>
            <el-option label = "Fr. S. Antarctic Lands"  value = "法属南部领地"></el-option>
            <el-option label = "Gabon"  value = "加蓬"></el-option>
            <el-option label = "Gambia"  value = "冈比亚"></el-option>
            <el-option label = "Germany"  value = "德国"></el-option>
            <el-option label = "Georgia"  value = "佐治亚州"></el-option>
            <el-option label = "Ghana"  value = "加纳"></el-option>
            <el-option label = "Greece"  value = "希腊"></el-option>
            <el-option label = "Greenland"  value = "格陵兰"></el-option>
            <el-option label = "Guatemala"  value = "危地马拉"></el-option>
            <el-option label = "Guinea"  value = "几内亚"></el-option>
            <el-option label = "Guinea-Bissau"  value = "几内亚比绍"></el-option>
            <el-option label = "Guyana"  value = "圭亚那"></el-option>
            <el-option label = "Haiti"  value = "海地"></el-option>
            <el-option label = "Honduras"  value = "洪都拉斯"></el-option>
            <el-option label = "Hungary"  value = "匈牙利"></el-option>
            <el-option label = "Iceland"  value = "冰岛"></el-option>
            <el-option label = "India"  value = "印度"></el-option>
            <el-option label = "Indonesia"  value = "印度尼西亚"></el-option>
            <el-option label = "Iran"  value = "伊朗"></el-option>
            <el-option label = "Iraq"  value = "伊拉克"></el-option>
            <el-option label = "Ireland"  value = "爱尔兰"></el-option>
            <el-option label = "Israel"  value = "以色列"></el-option>
            <el-option label = "Italy"  value = "意大利"></el-option>
            <el-option label = "Ivory Coast"  value = "象牙海岸"></el-option>
            <el-option label = "Jamaica"  value = "牙买加"></el-option>
            <el-option label = "Japan"  value = "日本"></el-option>
            <el-option label = "Jordan"  value = "乔丹"></el-option>
            <el-option label = "Kashmir"  value = "克什米尔"></el-option>
            <el-option label = "Kazakhstan"  value = "哈萨克斯坦"></el-option>
            <el-option label = "Kenya"  value = "肯尼亚"></el-option>
            <el-option label = "Kosovo"  value = "科索沃"></el-option>
            <el-option label = "Kuwait"  value = "科威特"></el-option>
            <el-option label = "Kyrgyzstan"  value = "吉尔吉斯斯坦"></el-option>
            <el-option label = "Laos"  value = "老挝"></el-option>
            <el-option label = "Lao PDR"  value = "老挝人民民主共和国"></el-option>
            <el-option label = "Latvia"  value = "拉脱维亚"></el-option>
            <el-option label = "Lebanon"  value = "黎巴嫩"></el-option>
            <el-option label = "Lesotho"  value = "莱索托"></el-option>
            <el-option label = "Liberia"  value = "利比里亚"></el-option>
            <el-option label = "Libya"  value = "利比亚"></el-option>
            <el-option label = "Lithuania"  value = "立陶宛"></el-option>
            <el-option label = "Luxembourg"  value = "卢森堡"></el-option>
            <el-option label = "Madagascar"  value = "马达加斯加"></el-option>
            <el-option label = "Macedonia"  value = "马其顿"></el-option>
            <el-option label = "Malawi"  value = "马拉维"></el-option>
            <el-option label = "Malaysia"  value = "马来西亚"></el-option>
            <el-option label = "Mali"  value = "马里"></el-option>
            <el-option label = "Mauritania"  value = "毛里塔尼亚"></el-option>
            <el-option label = "Mexico"  value = "墨西哥"></el-option>
            <el-option label = "Moldova"  value = "摩尔多瓦"></el-option>
            <el-option label = "Mongolia"  value = "蒙古"></el-option>
            <el-option label = "Montenegro"  value = "黑山"></el-option>
            <el-option label = "Morocco"  value = "摩洛哥"></el-option>
            <el-option label = "Mozambique"  value = "莫桑比克"></el-option>
            <el-option label = "Myanmar"  value = "缅甸"></el-option>
            <el-option label = "Namibia"  value = "纳米比亚"></el-option>
            <el-option label = "Netherlands"  value = "荷兰"></el-option>
            <el-option label = "New Caledonia"  value = "新喀里多尼亚"></el-option>
            <el-option label = "New Zealand"  value = "新西兰"></el-option>
            <el-option label = "Nepal"  value = "尼泊尔"></el-option>
            <el-option label = "Nicaragua"  value = "尼加拉瓜"></el-option>
            <el-option label = "Niger"  value = "尼日尔"></el-option>
            <el-option label = "Nigeria"  value = "尼日利亚"></el-option>
            <el-option label = "Korea"  value = "朝鲜"></el-option>
            <el-option label = "Northern Cyprus"  value = "北塞浦路斯"></el-option>
            <el-option label = "Norway"  value = "挪威"></el-option>
            <el-option label = "Oman"  value = "阿曼"></el-option>
            <el-option label = "Pakistan"  value = "巴基斯坦"></el-option>
            <el-option label = "Panama"  value = "巴拿马"></el-option>
            <el-option label = "Papua New Guinea"  value = "巴布亚新几内亚"></el-option>
            <el-option label = "Paraguay"  value = "巴拉圭"></el-option>
            <el-option label = "Peru"  value = "秘鲁"></el-option>
            <el-option label = "Republic of the Congo"  value = "刚果共和国"></el-option>
            <el-option label = "Philippines"  value = "菲律宾"></el-option>
            <el-option label = "Poland"  value = "波兰"></el-option>
            <el-option label = "Portugal"  value = "葡萄牙"></el-option>
            <el-option label = "Puerto Rico"  value = "波多黎各"></el-option>
            <el-option label = "Qatar"  value = "卡塔尔"></el-option>
            <el-option label = "Romania"  value = "罗马尼亚"></el-option>
            <el-option label = "Russia"  value = "俄罗斯"></el-option>
            <el-option label = "Rwanda"  value = "卢旺达"></el-option>
            <el-option label = "Samoa"  value = "萨摩亚"></el-option>
            <el-option label = "Saudi Arabia"  value = "沙特阿拉伯"></el-option>
            <el-option label = "Senegal"  value = "塞内加尔"></el-option>
            <el-option label = "Serbia"  value = "塞尔维亚"></el-option>
            <el-option label = "Sierra Leone"  value = "塞拉利昂"></el-option>
            <el-option label = "Slovakia"  value = "斯洛伐克"></el-option>
            <el-option label = "Slovenia"  value = "斯洛文尼亚"></el-option>
            <el-option label = "Solomon Is."  value = "所罗门群岛"></el-option>
            <el-option label = "Somaliland"  value = "索马里兰"></el-option>
            <el-option label = "Somalia"  value = "索马里"></el-option>
            <el-option label = "South Africa"  value = "南非"></el-option>
            <el-option label = "S. Sudan"  value = "南苏丹"></el-option>
            <el-option label = "Spain"  value = "西班牙"></el-option>
            <el-option label = "Sri Lanka"  value = "斯里兰卡"></el-option>
            <el-option label = "Sudan"  value = "苏丹"></el-option>
            <el-option label = "Suriname"  value = "苏里南"></el-option>
            <el-option label = "Swaziland"  value = "斯威士兰"></el-option>
            <el-option label = "Sweden"  value = "瑞典"></el-option>
            <el-option label = "Switzerland"  value = "瑞士"></el-option>
            <el-option label = "Syria"  value = "叙利亚"></el-option>
            <el-option label = "Tajikistan"  value = "塔吉克斯坦"></el-option>
            <el-option label = "Tanzania"  value = "坦桑尼亚"></el-option>
            <el-option label = "Thailand"  value = "泰国"></el-option>
            <el-option label = "The Kingdom of Tonga"  value = "汤加王国"></el-option>
            <el-option label = "Timor-Leste"  value = "东帝汶"></el-option>
            <el-option label = "Togo"  value = "多哥"></el-option>
            <el-option label = "Tunisia"  value = "突尼斯"></el-option>
            <el-option label = "Turkey"  value = "土耳其"></el-option>
            <el-option label = "Turkmenistan"  value = "土库曼斯坦"></el-option>
            <el-option label = "Uganda"  value = "乌干达"></el-option>
            <el-option label = "Ukraine"  value = "乌克兰"></el-option>
            <el-option label = "United Arab Emirates"  value = "阿拉伯联合酋长国"></el-option>
            <el-option label = "United Kingdom"  value = "英国"></el-option>
            <el-option label = "United States"  value = "美国"></el-option>
            <el-option label = "Uruguay"  value = "乌拉圭"></el-option>
            <el-option label = "Uzbekistan"  value = "乌兹别克斯坦"></el-option>
            <el-option label = "Vanuatu"  value = "瓦努阿图"></el-option>
            <el-option label = "Venezuela"  value = "委内瑞拉"></el-option>
            <el-option label = "Vietnam"  value = "越南"></el-option>
            <el-option label = "West Bank"  value = "西岸"></el-option>
            <el-option label = "W. Sahara"  value = "西撒哈拉"></el-option>
            <el-option label = "Yemen"  value = "也门"></el-option>
            <el-option label = "Zambia"  value = "赞比亚"></el-option>
            <el-option label = "Zimbabwe"  value = "津巴布韦" ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="付款方式" prop="payment">
          <el-radio v-model="form.payment" label="信用证">信用证</el-radio>
          <el-radio v-model="form.payment" label="电汇">电汇</el-radio>
          <el-radio v-model="form.payment" label="托收">托收</el-radio>
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
  name: "Customer",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      payment:null,
      country:null,
      email:null,
      name:null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
        ],
      },
      ids: []
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
      window.open(this.$baseUrl + '/customer/export')
    },
    changeStatus(row){
      this.$request.put('/customer/update',row).then(res=>{
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
            url: this.form.id ? '/customer/update' : '/customer/add',
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
        this.$request.delete('/customer/delete/' + id).then(res => {
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
        this.$request.delete('/customer/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/customer/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          country: this.country,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.country = null
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