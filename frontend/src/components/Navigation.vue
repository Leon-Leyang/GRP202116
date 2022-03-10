<template>

  <div class="navbar"> 

 <el-header height = "70px !important"><!--覆盖性？-->
   <i class="el-icon-s-fold" @click="isC"></i>
 </el-header>


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
</template>

<style>
  

.el-icon-s-fold {
   cursor: pointer;
}


.navbar {
  position: absolute;
  width: 200px;
  top: 0px;/*70px;*/ 
  left: 0px;
  bottom: 0px;
  overflow-y: auto; /* 当内容过多时y轴出现滚动条 */  
  
}



</style>

<script>
  export default {
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
  }
</script>