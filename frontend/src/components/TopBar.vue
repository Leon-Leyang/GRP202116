<template>
    <div class="topbar">
        <div class="mark">
            <img class="logo" src="../assets/logo.png">
        </div>
        <div class="priority">
            <v-breadcrumbs :items="items" divider=">>" style="padding: 7px 0px 0px 70px ">
                <template v-slot:item="{ item }">
                <v-breadcrumbs-item
                    :href="item.href"
                    :disabled="item.disabled"
                >
                    <p class="path">
                        {{ item.text.toUpperCase() }}
                    </p>
                </v-breadcrumbs-item>
                </template>
            </v-breadcrumbs>
        </div>

    <div class="navigation">         
    <el-dropdown @command="handleCommand"
      ><!--  绑定指令,在methods里定义-->
       <span class="el-dropdown-link">
            <v-icon large>
                mdi-widgets
            </v-icon>
        </span>
         <el-dropdown-menu slot="dropdown">
        <!-- icon是修改图标 ，command是点击后传给方法的值-->

        <el-dropdown-item
          ><i class="el-icon-search" command="edit1"></i
          >Guidance</el-dropdown-item
        >

        <el-dropdown-item icon="el-icon-document" command="edit2"
          >Document</el-dropdown-item
        >

        <el-dropdown-item class="link3"><a href='https://github.com/Leon-Leyang/GRP202116/' style="text-decoration: none; color: grey"><i class="iconfont icon-github"></i>GitHub</a></el-dropdown-item
       >
      </el-dropdown-menu>

      </el-dropdown>
    </div>

    </div>
</template>
<script>
export default {
    data: () => ({
      items: [
        {
          text: 'Project',
          disabled: false,
          href: 'temporaryMainpage',
        },
        {
          text: 'Dataset', //
          disabled: false,
          href: 'dataset',//未完
        },
      ],
    }),
}
</script>
<style>
.topbar{
    width: 100%;
    height: 70px;
    background: #F5F5F5;
}
.mark{
    height: 50px;
    display: block;
    float: left;
    margin-top:10px;
    margin-left: 15px;
}
.logo{
    height: 100%;
}
.path{
    height: 100%;
    margin-top:10px;
    font-size: 25px;
}
.priority{
    display: block;
    float: left;
}
.navigation{
    display: block;
    float: right;
    padding-right: 20px;
    padding-top: 5px;
}
/**
.link3{ text-decoration-line: none;
} 
.a{
  text-decoration-line: none; 
}
**/
</style>