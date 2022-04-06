<template>
  <div id="app">
    <v-app>
      
      <div style="display:flex; height:100%; width:100%">
        <SideBar />
        <div style="width:100%">
         <TopBar />
         <div id="page" style="background-color:#F5F5F5;height:100%">
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
