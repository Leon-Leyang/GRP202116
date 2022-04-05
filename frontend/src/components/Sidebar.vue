<template>
  <v-card>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant.sync="mini"
      class="blue lighten-4"
    >
      <v-list-item class="px-2">
        <v-btn
          icon
          @click.stop="mini = !mini"
        >
          <v-icon v-if="mini==false">mdi-arrow-left</v-icon>
          <v-icon v-if="mini!=false">mdi-arrow-right</v-icon>

        </v-btn>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <template v-slot:append>
        <div>
          <v-btn block depressed tile color="rgb(218, 253, 249)">
            <v-icon    
            > mdi-github
            </v-icon>
          </v-btn>
        </div>
      </template>

    </v-navigation-drawer>
  </v-card>
</template>
<script>
  export default {
    data () {
      return {
        drawer: true,
        items: [
          { title: 'Home', icon: 'mdi-home-city' },
          { title: 'User Manual', icon: 'mdi-account' },
          { title: 'Our Website', icon: 'mdi-account-group-outline' },
          { title: 'Documentation', icon: 'mdi-file-document-multiple' },
        ],
        mini: true,
      }
    },
  }
</script>
<style>

</style>