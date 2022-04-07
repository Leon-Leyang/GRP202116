<template>
  <div id="app">
    <v-app>
      
      <div style="display:flex; height:100%; width:100%">
        <SideBar />
        <div style="width:100%;height:100%">
          <div style="height:10%;background-color:#F5F5F5"><TopBar /></div>
         <div id="page" style="background-color:#F5F5F5;height:90%"><!--#F5F5F5-->
          <router-view />
         </div>
         </div>
      </div>
    </v-app>    
  </div>
</template>

<script>
import TopBar from "@/components/TopBar.vue";
import SideBar from "@/components/Sidebar.vue"
export default {
  name: 'App',

  components: {
    TopBar,
    SideBar
  },

  data: () => ({
    //
  }),
};
</script>

<style>

</style>
