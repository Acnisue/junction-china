import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
// 全局引入echarts
import *as echarts from 'echarts'
Vue.prototype.$echarts = echarts
//引入图标库
import "@/assets/iconfont/iconfont.css"
// 引入图片预览插件
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
Vue.use(Viewer)
Viewer.setDefaults({
  Options: { 'inline': true, 'button': true, 'navbar': true, 'title': true, 'toolbar': true, 'tooltip': true, 'movable': true, 'zoomable': true, 'rotatable': true, 'scalable': true, 'transition': true, 'fullscreen': true, 'keyboard': true, 'url': 'data-source' }
})
Vue.use(ElementUI);
Vue.config.productionTip = false
new Vue({
  render: h => h(App),
    //创建全局事件总线
    beforeCreate() {
      Vue.prototype.$bus = this;
    }
}).$mount('#app')
