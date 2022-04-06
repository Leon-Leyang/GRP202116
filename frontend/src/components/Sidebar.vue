<template>
<div>
  <v-navigation-drawer
      v-model="drawer"
      :mini-variant.sync="mini"
      width="200"
      class="deep-purple darken-3"
    >
      <v-list-item class="px-2">
        <v-btn
          icon
          @click.stop="mini = !mini"
          dark
        >
          <v-icon v-if="mini==false" dark>mdi-arrow-left</v-icon>
          <v-icon v-if="mini!=false" dark>mdi-arrow-right</v-icon>

        </v-btn>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense dark>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
        >
          <v-list-item-icon style="margin-right:25px">
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
</div>
</template>
<script>
  export default {
    data () {
      return {
        drawer: true,
        items: [
          { title: 'Home', icon: 'mdi-home-city' },
          { title: 'My Account', icon: 'mdi-account' },
          { title: 'Users', icon: 'mdi-account-group-outline' },
        ],
        mini: true,
      }
    },
  }
</script>
<style>

</style>