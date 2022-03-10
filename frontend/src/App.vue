<template>
    <el-container class="n" style="height: 750px"><!--满屏如何？-->
    <el-header height="70px"><!-- 头部区 -->     
    <TopBar></TopBar>  
    </el-header>
        <!-- 页面主体区域 -->
      <el-container>
            <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="navbar"> 

          <div class="nav">
          <el-header height="70px">
          <i class="el-icon-s-fold" @click="isC"></i>
          </el-header>
          </div>

      <el-menu default-active="1-4-1" :collapse="isCollapse" background-color="azure">

        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
          <span slot="title">导航一</span>
          </template>
            <el-menu-item-group>
                <span slot="title">分组一</span>
                <el-menu-item index="1-1">选项1</el-menu-item>
                <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
                <el-menu-item-group title="分组2">
                <el-menu-item index="1-3">选项3</el-menu-item>
                </el-menu-item-group>
                <el-submenu index="1-4">
                <span slot="title">选项4</span>
                  <el-menu-item index="1-4-1">选项1</el-menu-item>
                </el-submenu>
        </el-submenu>

         <el-menu-item index="2">
            <i class="el-icon-menu"></i>
             <span slot="title">导航二</span>
          </el-menu-item>

          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
           </el-menu-item>

          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>

      </el-menu>

       </div>
      </el-aside>
      <!-- 右侧内容主体 -->
      <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
</template>

<style scoped>
 .el-header{
     background-color: transparent;
     /*background: linear-gradient(#D0D0D0,#ffffff);*/
     /*height: 100px; 不好使*/
     
 }
  .el-aside{
     background-color: /*#333744*/azure;
 }
 
 /* .el-main{
     background-color: #eaedf1;  
 }*/
  .n{
    height: 100%;
  } /*不好使*/

.el-icon-s-fold {
   cursor: pointer;
   /* align-items: center; */
}


.navbar {
  position: absolute;
 /* width: 不要写值，而是随着aside走就好; */
  top: 0px;/*70px;*/ 
  left: 0px;
  bottom: 0px;
  overflow-y: auto; 
  
}

.nav /deep/.el-header{  /*无效*/
  background-color: transparent; 
}

</style>

<script>
import TopBar from "@/components/TopBar.vue";
//import SideBar from "@/components/Navigation.vue";

export default {
    

  components: {
    TopBar,
    //SideBar,
  },

  data() {
      return {
        isCollapse: true
      };
    },
    methods: {
   
      isC(){
        this.isCollapse = !this.isCollapse
      }
    }
  
};
</script>
 
