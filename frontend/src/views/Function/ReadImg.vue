<!-- 可以读取本地图片并转化为url的格式但是图片尺寸不能太小 -->
<template>
    <div class="home">
        <img src="@/assets/new/0_10.jpg" alt="">
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
        const files = require.context("@/assets/test", ture, /\.*\.jpg|jpeg|png$/).keys();
        console.log("files:",files)
        // imgList = files
        files.forEach((e) => {
            imgList.push(require("@/assets/test" + e.slice(1)));
        });
        this.imgList = imgList
        console.log('tag',this.imgList[0])

    },
}
</script>

<style>
    
</style>