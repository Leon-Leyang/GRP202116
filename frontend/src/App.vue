<template>
  <div id="app">
    <v-app>
      <div  style="display:flex;height:100%">
        <SideBar />
          <div id="page" style="background-color:#F5F5F5;width:100%;height:100%">
          <TopBar />
            <router-view />
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
