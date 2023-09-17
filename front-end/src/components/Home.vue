<template>
  <div class="root">
    <!-- 导航 -->
    <div class="navgation">
      <span>DDMBC</span>
    </div>
    <!-- 内容区域 -->
    <div class="content">
      <!-- 左边柱状图 -->
      <div class="pillar">
        <div class="one">
          <h3>摄像头实时信息
            <span class="iconfont icon-goutongye_zuojiantou_fanhui"></span>
          </h3>
          <div class='testDiv' style='height: 260px;width:100%;' @mouseout='mouseout' @mouseover='mouseover'>
            <el-table @row-click="showPhoto" :data='tableData' :height='240'>
              <el-table-column align='center' label='路段' prop="locations">
              </el-table-column>
              <el-table-column align='center' label='日期' prop='createTime'>
              </el-table-column>
            </el-table>
          </div>
          <div class="photoShow">
          </div>
        </div>
        <div class="two">
          <piechar class="piechar" />
        </div>
        <div class="three">
          <pillar />
        </div>
      </div>
      <!-- 中间地图 -->
      <div class="chinaMap">
        <!-- 滚动导航 -->
        <chinaMap class="chinaMapItem" />
        <!-- 固定显示每个市违规数量 -->
        <div class="fixedNumber">
          <div v-for="(item, index) in randomData" :key="index">
            <piecharItem :data="item" />
          </div>
        </div>
      </div>
      <!-- 右边折线图 -->
      <div class="line">
        <div class="container">
          <div class="wrapper">
            {{ listData }}
          </div>
        </div>
        <brokenLine />
        <piecharRight class="piecharRight" />
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import { nowDates } from '@/utils/nowDate'
// 引入外部饼状图
import chinaMap from "@/echarts/map.vue"
import piechar from "@/echarts/piechar.vue"
import pillar from "@/echarts/pillar.vue"
import brokenLine from "@/echarts/brokenLine.vue"
import piecharRight from "@/echarts/piecharRight.vue"
import piecharItem from "@/echarts/piecharItem.vue"
export default {
  name: 'Home',
  data() {
    return {
      listData: '1.把握好你生命的时速和方向盘2.不开争强斗气车、争做礼让文明人3.红灯前面不要急,安全第一最要紧。',
      classOption: {
        limitMoveNum: 5,
        direction: 2,
      },
      tableData: [],
      randomData: [[735, 510, 434, 335, 1548, '雅安'], [595, 510, 694, 235, 1390, '巴中'], [430, 510, 290, 835, 1720, '攀枝花']],
      photo: []
    }
  },
  components: {
    chinaMap,
    piechar,
    pillar,
    brokenLine,
    piecharRight,
    piecharItem,
  },
  methods: {
    mouseover() {
      clearInterval(this.timer)
    },
    mouseout() {
      this.autoScroll(false)
    },
    autoScroll(init) {
      this.$nextTick(() => {
        const t = 50
        const box = this.$el.querySelector('.el-table__body-wrapper')
        const content = this.$el.querySelector('.el-table__body')
        if (init) box.scrollTop = 0
        this.timer = setInterval(() => {
          this.rollStart(box, content)
        }, t)
      })
    },
    rollStart(box, content) {
      if (box.scrollTop >= (content.scrollHeight - box.offsetHeight)) {
        box.scrollTop = 0
      } else {
        box.scrollTop++
      }
    },
    /**
     * 实时获取摄像头数据
     */
    getxcamera() {
      axios({ url: '/api/camera?num=1&size=5', method: 'GET' }).then(res => {
        this.tableData = res.data.data.records
        this.tableData.map((item) => {
          item.createTime = nowDates(new Date(item.createTime));
        })
      }, err => {
        console.log(err);
      })
    },
    /**
     * 点击显示摄像头下的图片或视频数据
     * @param {*} row 
     */
    showPhoto(row) {
      axios({ url: `/api/camera/image?camera_id=${row.id}&num&size`, method: 'GET' }).then(res => {
        if (res.data.success) {
          axios({ url: `/api/camera/image/view?res_id=${res.data.data.records[0].resourceId}`, method: 'GET' }).then(photoRes => {
            if (photoRes.data.success) {
              const $viewer = this.$viewerApi({
                images: [`http://172.16.129.18:5000/${photoRes.data.data.url}`]
              });
            }
          }, err => {
            console.log(err);
          })
        }
      }, err => {
        console.log(err);
      })
    },
    /**
     * 显示展示失败的图片
     */
    errorImg() {

    }
  },
  mounted() {
    this.mouseout()
    this.getxcamera()
  }

}
</script>
<style scoped>
.root {
  background: url('../assets/img/background.png') no-repeat center;
  background-size: 110% 100%;
  overflow-x: hidden;
  position: absolute;
  width: 100%;
  height: 100%;
}

/* 导航栏 */
.navgation {
  background: url('../assets/img/navigation.png') no-repeat center;
  background-size: 100% 100%;
  height: 8%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.navgation>span {
  background: linear-gradient(to right, #ffffff, #93d5ff);
  -webkit-background-clip: text;
  font-family: '黑体';
  font-size: 25px;
  font-weight: bold;
  color: transparent;
}

/* 中间内容区 */
.content {
  display: flex;
  height: 92%;
}

/* 左边柱状图 */
.pillar {
  flex-basis: 25%;
  height: 100%;
}

.pillar>.one>h3 {
  text-align: end;
  margin-right: 10px;
  color: white;
}

.pillar>.one>h3>span {
  color: #ff962f;
  font-size: 10px;
  margin-left: 5px;
}

.pillar>.one>.trikeTable {

  border-spacing: 15px;
  margin-top: 10px;
  background: linear-gradient(to right, #0a6476, transparent);
  width: 100%;
}

.pillar>.one>.trikeTable>thead,
.pillar>.one>.trikeTable>tbody {
  text-align: center;
}

/* 表格 */
.el-table,
.el-table__expanded-cell {
  background-color: transparent !important;
}

/* 表格内背景颜色 */
/*最外层透明*/
::v-deep .el-table,
::v-deep .el-table__expanded-cell {
  /* background-color: transparent; */
  background: linear-gradient(to right, #0a6476, transparent);
  border: none;
}

/* 表格内背景颜色 */
::v-deep .el-table th,
::v-deep .el-table tr,
::v-deep .el-table td {
  background-color: transparent;
  color: white;
  border: none;
}

::v-deep .el-table__body-wrapper::-webkit-scrollbar-thumb {
  background-color: transparent;
  border-radius: 6px;
}

::v-deep .el-table__body-wrapper::-webkit-scrollbar {
  width: 8px;
  height: 25px;
  background: transparent;
}

::v-deep .el-table__body-wrapper::-webkit-scrollbar-track {
  border-radius: 10px;
  /*滚动条的背景区域的圆角*/
  background-color: transparent;
  /*滚动条的背景颜色*/
}

/* 取消表格悬浮 */
::v-deep .el-table tbody tr:hover>td {
  cursor: pointer;
  background-color: transparent !important
}


/* 删除表格下最底层的横线 */
.el-table::before {
  left: 0;
  bottom: 0;
  width: 100%;
  height: 0px;
}



td {
  color: white;
  font-family: '黑体';
  font-size: 15px;
  font-weight: bold;
}

.piechar {
  margin-top: 10px;
}

/* 中间的地图内容 */
.chinaMap {
  margin-left: 20px;
  flex-basis: 50%;
  height: 100%;
}

.chinaMap>.fixedNumber {
  background: linear-gradient(to top, #0a6476, transparent);
  display: flex;
  flex-wrap: nowrap;
  position: fixed;
  bottom: 0;
  margin: 0 auto;
  width: 50%;
  height: 15%;
}

.chinaMap>.fixedNumber>div {
  flex-basis: 33%;
  height: 100%;
}

.line {
  flex-basis: 25%;
  height: 100%;
}

.container {
  display: flex;
  align-items: center;
  overflow: hidden;
  white-space: nowrap;
  height: 5%;
  background: linear-gradient(to left, #0a6476, transparent);
  width: 23.5vw;
}

.wrapper {
  display: inline-block;
  color: white;
  animation: marquee 10s linear infinite;
}

/* .photoShow{
  position: absolute;
  left: 100px;
} */

@keyframes marquee {
  0% {
    transform: translateX(0);
  }

  100% {
    transform: translateX(-100%);
  }
}
</style>
