<template>
  <v-container class="co">
 
  <v-row
        no-gutters
        justify="center"
        style="height:100%"
  >
  
  <v-col md="3"><!-- style="height:100%"-->
        
        <v-card
           class="c-1"
           outlined
           tile
          >
          Data 
        <el-row style="height:27%">
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Total Data:</div>
            <div class="pagetext-a">1130</div>
        </el-col>
        </el-row> 

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Percentage completed:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="25"></el-progress></div>
        </el-col>
        </el-row>
       <!--
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average word counts per piece of data:</div>
            <div class="pagetext-a">1130</div>
        </el-col>
        </el-row>      
       --> 
       <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Data completed:</div>
            <div class="pagetext-a">257</div>
        </el-col>
        </el-row>
      </v-card>
          
  </v-col>

  <v-col md="3">
           <v-card
            class="c-1"
            outlined
            tile
          >
            Annotation
        <el-row style="height:27%">
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Total annotations:</div>
            <div class="pagetext-a">123</div>
        </el-col>     
        </el-row>
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Annotations Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="25" color="green"></el-progress></div>
        </el-col>
        </el-row>   
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average annotations per piece of data:</div>
            <div class="pagetext-a">12</div>
        </el-col>
        </el-row>
          </v-card>
  </v-col>
  
  <v-col md="3">
           <v-card
            class="c-1"
            outlined
            tile
          >
            Prediction
        <el-row style="height:27%">
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Total predictions:</div>
            <div class="pagetext-a">123</div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Predictions Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="35" color="red"></el-progress></div>
        </el-col>
        </el-row> 

        <el-row>  
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average predictions per piece of data:</div>
            <div class="pagetext-a">14</div>
        </el-col>
        </el-row>
   
        </v-card>
  </v-col>

  <v-col md="3">
           <v-card
            class="c-1"
            outlined
            tile
          >
            ML Model
        <el-row style="height:45%">
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Part1</div>
            <div class=""> </div>
        </el-col>
        </el-row>

         

        <el-row>  
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Part2</div>
            <div class=""></div>
        </el-col>
        </el-row>
   
        </v-card>
  </v-col>


</v-row>
  </v-container>
  
</template>

<style>

.co{
    padding: 0;
    margin: 0;
    max-width: 1262.9px;
    height: 100%;
}
.c-1{
    height: 100%;
    border-color: /*rgb(35, 157, 232)*/rgb(113, 223, 207)!important;
    border-width: 3px!important;
    background:rgb(218, 253, 249)!important;
    /*width:70%;*/
}

.inblock{
    height: /*230px*/33%;/*180px*/
}

.inblocka{
    height: /*230px*/33%;/*180px*/
}

.title{
    font-style:italic;
    font-size:0.8em;
    font-family:Georgia, serif!important;
    /*text-decoration :underline;*/
    color: rgb(124,124,124);
}
.pagetext-a{
   /* height: 50px; */
    font-size:2.5em;
    font-weight: 600;
    text-align: center;
    height: 33%;
    /*margin-top: 30px;45px*/
    /*margin-bottom: 45px; */  
}

.pagetext-b{
    /*height: 50px;*/
    height: 33%;
    font-size:2.5em;
    font-weight: 600;
    text-align: center;
    margin-top: 5px;/*45px*/
    /*margin-bottom: 45px; */
    
}
</style>