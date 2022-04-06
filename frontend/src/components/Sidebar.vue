<template>
  <v-card>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant.sync="mini"
      class="deep-purple darken-3"
      width="300px"
      mini-variant-width="100px"
      dark
    >
      <v-list-item class="px-2" style="margin-top:5rem">
        <v-btn
          icon
          @click.stop="mini = !mini"
        >
          <v-icon v-if="mini==false" x-large>mdi-arrow-left</v-icon>
          <v-icon v-if="mini!=false" x-large>mdi-arrow-right</v-icon>

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
            <v-icon x-large style="min-height:5rem;">{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title style="font-size:1.5rem; line-height:5rem">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <template v-slot:append>
        <div>
          <v-btn block depressed tile color="indigo darken-2" height="72px">
            <v-icon x-large    
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
.h{
  color: rgb(19, 2, 255);
}
</style>