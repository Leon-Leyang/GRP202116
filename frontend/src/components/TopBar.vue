<template>
    <div class="topbar">
        <div class="mark" style="height:60px">
            <img class="logo" src="../assets/logo.png" style="width:100%">
        </div>
        <div class="priority">
            <v-breadcrumbs :items="items" divider=">>" style="padding: 7px 0px 0px 70px ">
                <template v-slot:item="{ item }">
                <v-breadcrumbs-item
                    :href="item.href"
                    :disabled="item.disabled"
                >
                    <p class="path" style="font-size:35px;font-weight:500;font-style:italic;color:#303F9F">
                        {{ item.text.toUpperCase() }}
                    </p>
                </v-breadcrumbs-item>
                </template>
            </v-breadcrumbs>
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
          href: 'Home',
        },
        {
          text: 'DataList', //
          disabled: true,
          href: '#/per-project',//未完
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