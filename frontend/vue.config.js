module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  publicPath: './',
  devServer: {
    port: '7000',
    open: true,
    host: "localhost",
    https: false,
    hotOnly: false,
    proxy: {
      '/api': {
        target: 'http://localhost:7902', //API服务器的地址
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    },
  }
}
