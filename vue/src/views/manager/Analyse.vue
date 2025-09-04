<template>
  <div>
    <div style="display: flex; margin: 10px 0">
      <div style="width: 100%;" class="card">
        <div style="display: flex; grid-gap: 10px; margin: 10px 0">
          <div class="card" style="flex: 1; display: flex; align-items: center">
            <img src="@/assets/imgs/总下单量.png" alt="" style="width: 50px">
            <div style="margin-left: 40px">
              <div style="color: #666">总下单量</div>
              <div style="margin-top: 5px; font-size: 20px">{{ dataCount.num }}</div>
            </div>
          </div>
          <div class="card" style="flex: 1; display: flex; align-items: center">
            <img src="@/assets/imgs/总下单额.png" alt="" style="width: 50px">
            <div style="margin-left: 40px">
              <div style="color: #666">总下单额（美金）</div>
              <div style="margin-top: 5px; font-size: 20px">{{ dataCount.money }}</div>
            </div>
          </div>
          <div class="card" style="flex: 1; display: flex; align-items: center">
            <img src="@/assets/imgs/总订单量.png" alt="" style="width: 50px">
            <div style="margin-left: 40px">
              <div style="color: #666">总订单量</div>
<!--                <div style="margin-top: 5px; font-size: 20px">-</div>-->
              <div style="margin-top: 5px; font-size: 20px">{{ dataCount.orders }}</div>
            </div>
          </div>
          </div>
        </div>
      </div>

    <div style="flex: 1" class="card">
      <div style="height: 500px" >
        <iframe ref="myIframe" src="world.html" style="border: none;height: 100%;width: 100%;" />
      </div>
    </div>

    <div style="margin: 10px 0;display: flex;grid-gap: 10px">
      <div style="flex: 1" class="card">
        <div style="height: 400px" id="line1">

        </div>
      </div>

      <div style="flex: 1" class="card">
<!--        <div style="height: 400px" id="label1">-->
          <div style="height: 400px" id="line">
        </div>
      </div>

    </div>
    <div style="margin: 10px 0;display: flex;grid-gap: 10px">
      <div style="flex: 1" class="card">
        <div style="height: 400px" id="pie">

        </div>
      </div>

      <div style="flex: 1" class="card">
        <div style="height: 400px" id="line2">

        </div>
      </div>
    </div>

</div>

</template>


<script>
import * as echarts from 'echarts'
//柱形图总下单量
// const labelOption = {
//   title: {
//     text: '总下单量统计图',
//     left: 'center'
//   },
//   tooltip: {
//     trigger: 'item'
//   },
//   legend: {
//     orient: 'vertical',
//     left: 'left'
//   },
//   xAxis: {
//     type: 'category',
//     data: []
//   },
//   yAxis: {
//     type: 'value',
//     // axisLine: {
//     //   show: true,
//     //   lineStyle: {
//     //     color: '#5470C6'
//     //   }
//     // },
//   },
//   series: [
//     {
//       data: [],
//       type: 'bar',
//       itemStyle:{
//         normal:{
//           color:'#4169E1'
//         }
//       },
//     }
//   ]
//   };

//柱形图总下单额
// const labelOption1 = {
//   title: {
//     text: '总下单额统计图',
//     left: 'center'
//   },
//   tooltip: {
//     trigger: 'item'
//   },
//   legend: {
//     orient: 'vertical',
//     left: 'left'
//   },
//   xAxis: {
//     type: 'category',
//     data: []
//   },
//   yAxis: {
//     type: 'value',
//       // axisLine: {
//       //   show: true,
//       //   lineStyle: {
//       //     color: '#91CC75'
//       //   }
//       // },
//   },
//   series: [
//     {
//       data: [],
//       type: 'bar',
//       itemStyle:{
//         normal:{
//           color:'#2E8B57'
//         }
//       },
//     }
//   ]
// };

const lineOption = {
  title: {
    text: '总下单额',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  toolbox: {
    show: true,
    feature: {
      dataZoom: {
        yAxisIndex: 'none'
      },
      dataView: { readOnly: false },
      magicType: { type: ['line', 'bar'] },
      restore: {},
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value',
    axisLabel: {
    },
  },
  series: [
    {
      name: 'Highest',
      type: 'line',
      data: [],
      itemStyle: {
        normal: {
          color: '#006400'
        },
      },
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
  ]
};

const lineOption1 = {
  title: {
    text: '总下单量',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  toolbox: {
    show: true,
    feature: {
      dataZoom: {
        yAxisIndex: 'none'
      },
      dataView: { readOnly: false },
      magicType: { type: ['line', 'bar'] },
      restore: {},
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value',
    axisLabel: {
    },
  },
  series: [
    {
      name: 'Highest',
      type: 'line',
      data: [],
      itemStyle: {
        normal: {
          color: '#B22222'
        },
      },
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
  ]
};

//饼图
const pieOption = {
  title: {
    text: '客户总订单额比例图',
    left: 'center'
  },
  toolbox: {
    show: true,
    feature: {
      mark: { show: true },
      dataView: { show: true, readOnly: false },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      type: 'pie',
      center: ['50%', '60%'],
      // radius: '50%',
      name: 'Nightingale Chart',
      radius: [50, 160],
      roseType: 'area',
      itemStyle: {
        borderRadius: 8
      },
      data: [],
      label: {
        show: true,
        formatter(param) {
          return param.name + ' (' + param.percent + '%)';
        }
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

const option = {
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: []
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    
  ]
};

export default {
  name: 'Analyse',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      dataCount:{},

    }
  },
  mounted() {
    setTimeout(()=>{
      this.$request.get("/customer/selectAll")
          .then(response=>{
            this.$refs.myIframe.contentWindow.postMessage(JSON.stringify(response.data), '*');
          })
    },1000)

    // 柱状图
    // let labelDom = document.getElementById('label')
    // let labelChart = echarts.init(labelDom)
    //
    // this.$request.get('/goodsCount').then(res => {
    //   labelOption.xAxis.data = res.data.map(v => v.name)
    //   labelOption.series[0].data = res.data.map(v => v.value)
    //   labelChart.setOption(labelOption)
    // })

    // let labelDom1 = document.getElementById('label1')
    // let labelChart1 = echarts.init(labelDom1)
    //
    // this.$request.get('/goodsCount1').then(res => {
    //   labelOption1.xAxis.data = res.data.map(v => v.name)
    //   labelOption1.series[0].data = res.data.map(v => v.value)
    //   labelChart1.setOption(labelOption1)
    // })

    let linetDom1 = document.getElementById('line1')
    let lineChart1 = echarts.init(linetDom1)

    this.$request.get('/goodsCount').then(res => {
      lineOption1.xAxis.data = res.data.map(v => v.name)
      lineOption1.series[0].data = res.data.map(v => v.value)
      lineChart1.setOption(lineOption1)
    })

    //折线图
    let linetDom = document.getElementById('line')
    let lineChart = echarts.init(linetDom)

    this.$request.get('/goodsCount1').then(res => {
      lineOption.xAxis.data = res.data.map(v => v.name)
      lineOption.series[0].data = res.data.map(v => v.value)
      lineChart.setOption(lineOption)
    })

    // 饼图
    let pieDom = document.getElementById('pie')
    let pieChart = echarts.init(pieDom)

    this.$request.get(('/order/selectPie')).then(res => {
      pieOption.series[0].data = res.data
      pieChart.setOption(pieOption)
    })


    let linetDom2 = document.getElementById('line2')
    let lineChart2 = echarts.init(linetDom2)
    this.$request.get('/getOption').then(res => {
      option.series = res.data.map(v=>v)
      option.legend.data=res.data.map(c=>c.name)
      lineChart2.setOption(option)
    })
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })

    this.$request.get('/dataCount').then(res => {
      this.dataCount = res.data || {}
    })
  }
}
</script>
