<template>
    <div class="topbar">
        <div class="mark">
            <img class="logo" src="../assets/logo.png">
        </div>
        <div class="priority">
            <v-breadcrumbs :items="items" divider=">>" style="padding: 2rem 0px 0px 8rem">
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
    height: 100%;
    background: linear-gradient(#D0D0D0,#F5F5F5);/*brown;*/
}
.mark{
    height: 5.5rem;
    display: block;
    float: right;
    margin-top:1.5rem;
    margin-right: 15px;
}
.logo{
    height: 100%;
}
.path{
    height: 100%;
    /* margin-top:1.5rem; */
    font-size: 3rem;
    /* margin-left: 3rem; */
    
}
.priority{
    display: block;
    float: left;
}
.v-breadcrumbs__divider{
    font-size: 2rem!important;
}

</style>