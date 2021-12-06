<template>
  <div id="app">
    <v-app>
      <TopBar />
        <div id="page">
          <router-view />
        </div>
    </v-app>    
  </div>
</template>

<script>
import TopBar from "@/components/TopBar.vue";
export default {
  name: 'App',

  components: {
    TopBar,
  },

  data: () => ({
    //
  }),
};
</script>

<style>

</style>
