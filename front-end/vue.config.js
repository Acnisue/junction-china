const { defineConfig } = require('@vue/cli-service')
const CompressionPlugin = require('compression-webpack-plugin');
const PORT_HOST = 'http://172.16.128.141:20035'
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false, //关闭语法检查
  devServer:{
    // 设置自定义端口
    port:8888,
    proxy: {
      '/api': {
        target: PORT_HOST,
        secure: true,
        changOrigin: true,//允许跨域
        pathRewrite: { '^/api': '' }//截取以/api开头字符串
      }
    }
  },
    // webpack配置
    productionSourceMap: false,
    transpileDependencies: ['element-ui'],
    chainWebpack(config) {
      config.plugins.delete('prefetch');
      if (process.env.NODE_ENV !== 'development') {
        config.plugin('compressionPlugin').use(
          new CompressionPlugin({
            test: /\.(js|css|html)$/,// 匹配文件名
            threshold: 10240,
          })
        );
      }
    },
})
