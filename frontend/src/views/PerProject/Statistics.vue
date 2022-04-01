<template>
  <v-container class="co">
 <!--<v-toolbar
 
    color="purple"
    dark
  >
    <v-toolbar-title>Title</v-toolbar-title>

    <v-divider
      class="mx-4"
      vertical
    ></v-divider>

    <span class="subheading">My Home</span>

    <v-spacer></v-spacer>

    <v-toolbar-items class="hidden-sm-and-down">
      <v-btn text>
        News
      </v-btn>

      <v-divider vertical></v-divider>

      <v-btn text>
        Blog
      </v-btn>

      <v-divider vertical></v-divider>

      <v-btn text>
        Music
      </v-btn>

      <v-divider vertical></v-divider>
    </v-toolbar-items>

    <v-app-bar-nav-icon></v-app-bar-nav-icon>
  </v-toolbar>
 -->

  <v-row
        no-gutters
        justify="center"
  >
  <v-spacer></v-spacer> 
  <v-col md="3">
        
        <v-card
           class="c-1"
           outlined
          >
          Data 
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Data completed</div>
            <div class="pagetext-a">12/3</div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Percentage completed</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="25"></el-progress></div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average word counts per piece of data </div>
            <div class="pagetext-a">1130</div>
        </el-col>
        </el-row>      

      </v-card>
          
  </v-col>
  <v-spacer></v-spacer> 
  <v-col md="3">
           <v-card
            class="c-1"
            outlined
          >
            Annotation
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Total annotations:</div>
            <div class="pagetext-a">123</div>
        </el-col>     
        </el-row>
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Annotations Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="25"></el-progress></div>
        </el-col>
        </el-row>   
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average annotations per data</div>
            <div class="pagetext-a">12</div>
        </el-col>
        </el-row>
          </v-card>
  </v-col>
  <v-spacer></v-spacer> 
  <v-col md="3">
           <v-card
            class="c-1"
            outlined
            tile
          >
            Prediction
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Total predictions:</div>
            <div class="pagetext-a">123</div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Predictions Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="35"></el-progress></div>
        </el-col>
        </el-row> 

        <el-row>  
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average predictions per data</div>
            <div class="pagetext-a">14</div>
        </el-col>
        </el-row>
   
        </v-card>
  </v-col>


</v-row>
  </v-container>
  
</template>

<style>


.co{
    background:rgb(228, 92, 92);
    padding: 0;
    margin: 0;
    max-width: 1262.9px;
}
.c-1{
    height: 100%;
    border-color: rebeccapurple!important;;
    border-width: 3px!important;
    /*width:70%;*/
}

.inblock{
    height: /*230px*/160px;/*180px*/
}
.title{
    font-style:italic;
    font-size:0.8em;
    text-decoration :underline;
    color: rgb(124,124,124);
}
.pagetext-a{
    height: 50px;
    font-size:2.5em;
    font-weight: 600;
    text-align: center;
    margin-top: 30px;/*45px*/
    margin-bottom: 45px;/* */  
}

.pagetext-b{
    height: 50px;
    font-size:2.5em;
    font-weight: 600;
    text-align: center;
    margin-top: 15px;/*45px*/
    margin-bottom: 45px;/* */
    
}

</style>