<template>
    <div class="home">
      
        <img :src="item" alt="" v-for="(item, index) in imgList" :key="index" />
    </div>
</template>

<script>
export default {
    name: "home",
    data () {
        return {
            imgList: [],
        };
    },
    mounted() {
        let imgList = [];
        const files = require.context("@/assets/new", false, /\.*\.jpg$/).keys();
        console.log("files:",files)
        // imgList = files
        files.forEach((e) => {
            imgList.push(require("@/assets/new" + e.slice(1)));
        });
        this.imgList = imgList
        console.log('tag',this.imgList[0])

    },
}
</script>

<style>
    
</style>