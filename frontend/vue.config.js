module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  publicPath: './',
  devServer: {
    port: 8082,
    open: true,
    host: "localhost",
    https: false,
    hotOnly: false,
    proxyTable: {
      '/api': {
        target: 'http://localhost:8088/api', //API服务器的地址
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    },
  }
}
