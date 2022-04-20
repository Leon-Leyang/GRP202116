<!--This is the side bar component
@author LinjingSUN YingjiaLI-->
<template>
<div>
  <v-navigation-drawer
      v-model="drawer"
      :mini-variant.sync="mini"
      width="300"
      mini-variant-width="70"
      class="deep-purple darken-3"
    >
      <v-list-item class="px-2">
        <v-btn
          icon
          @click.stop="mini = !mini"
          style="padding-top:20px;margin-bottom:20px"
          dark
        >
          <v-icon v-if="mini==false" large dark>mdi-arrow-left</v-icon>
          <v-icon v-if="mini!=false" large dark>mdi-arrow-right</v-icon>

        </v-btn>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense dark>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          style="height:70px"
          @click="Jump(item.title)"
        >
          <v-list-item-icon style="margin-right:25px">
            <v-icon large style="min-height:3rem">{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title style="font-size:1.5rem; line-height:5rem;line-height:22px">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>

      <template v-slot:append>
        <div>
          <v-btn block depressed tile color="indigo darken-2" height="72px">
            <a href="https://github.com/Leon-Leyang/GRP202116" target="_blank"><v-icon x-large    
            > mdi-github
            </v-icon></a>
            
          </v-btn>
        </div>
      </template>

    </v-navigation-drawer>
</div>
</template>
<script>
  export default {
    data () {
      return {
        drawer: true,
        items: [
          { title: 'Home', icon: 'mdi-home-city', url: "http://localhost:7000/#/" },
          { title: 'User Manual', icon: 'mdi-account', url: "https://www.overleaf.com/7949523734fhvykwrkmmsk"},
          { title: 'Our Website', icon: 'mdi-account-group-outline', url: "http://cslinux.nottingham.edu.cn/~Team202116/index.html" },
        ],
        mini: true,
      }
    },
    methods:{
      Jump(title){
        if(title == 'Home'){
          this.$router.push({  
              path: '/',
              name: 'Home', 
          }) 
        }else if (title == 'User Manual'){
          var tempwindow=window.open('_blank');
          tempwindow.location='https://www.overleaf.com/7949523734fhvykwrkmmsk';
        }else if (title == 'Our Website'){
          var tempwin=window.open('_blank');
          tempwin.location='http://cslinux.nottingham.edu.cn/~Team202116/index.html';
        }
      }
    }
  }
</script>
<style>

</style>