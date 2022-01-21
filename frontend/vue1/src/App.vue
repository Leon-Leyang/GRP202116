<template>
  <div id="app">
    <button v-on:click="postInfo">TEST</button>
    <br>
    {{this.info}}
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      info: {}
    }
  },
  methods: {
    postInfo: function () {
      this.$axios.get('/api/projects')
        .then(response => (this.info = response))
        .catch(function (error) { // 请求失败处理
          console.log(error);
        });
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
