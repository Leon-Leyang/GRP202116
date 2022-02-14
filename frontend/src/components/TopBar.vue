<template>
    <div class="topbar">
        <div class="mark">
            <img class="logo" src="../assets/logo.png">
        </div>
        <div class="priority">
            <v-breadcrumbs :items="items" style="padding: 7px 0px 0px 70px ">
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
            <v-icon large>
                mdi-widgets
            </v-icon>
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
          href: '/views/ProjectManage.vue',
        },
      ],
    }),
}
</script>
<style>
.topbar{
    width: 100%;
    height: 50px;
    background-color:#D0D0D0;
}
.mark{
    height: 50px;
    display: block;
    float: left;
    margin-left: 15px;
}
.logo{
    height: 100%;
}
.path{
    height: 100%;
    margin: auto;
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
</style>