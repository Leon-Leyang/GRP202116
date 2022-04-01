<template>
  <div id="app">
    <v-app>
      <TopBar />
      <div style="display:flex; height:100%; width:100%">
        <SideBar />
        <div id="page" style="width:100%">
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
