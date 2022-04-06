<template>
    <div class="topbar">
        <div class="mark">
            <img class="logo" src="../assets/logo.png">
        </div>
        <div class="priority">
            <v-breadcrumbs :items="items" divider=">>" style="padding: 6px 0px 0px 70px">
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
    height: 150px;
    background: linear-gradient(#D0D0D0,#F5F5F5);
}
.mark{
    height: 28px;
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
   /* margin-top:10px;*/
    font-size: 25px;
    
}
.priority{
    display: block;
    float: left;
}


</style>