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
            <div class="pagetext-a">{{statistic.dataListNumber}}</div>
        </el-col>
        </el-row> 

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Percentage completed:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="statistic.completionPercentage"></el-progress></div>
        </el-col>
        </el-row>
       <!--
        <el-row>
        <el-col :span="24" class="inblock" v-if="this.$store.state.currentProject.type == 'text'">
            <div class="grid-content bg-purple-light title">Average word counts per piece of data </div>
            <div class="pagetext-a">{{statistic.averageTextWordsNumber}}</div>
        </el-col>
        </el-row>      
       --> 
       <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Data completed:</div>
            <div class="pagetext-a">{{statistic.labeledDataListNumber}}</div>
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
            <div class="pagetext-a">{{statistic.annotationsNumber}}</div>
        </el-col>     
        </el-row>
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Annotations Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="statistic.averageAnnotations" color="green"></el-progress></div>
        </el-col>
        </el-row>   
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average annotations per piece of data:</div>
            <div class="pagetext-a">{{statistic.annotationsNumber/statistic.dataListNumber}}</div>
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
            <div class="pagetext-a">{{statistic.predictionsNumber}}</div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Predictions Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="statistic.averagePredictions" color="red"></el-progress></div>
        </el-col>
        </el-row> 

        <el-row>  
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple-light title">Average predictions per piece of data:</div>
            <div class="pagetext-a">{{statistic.predictionsNumber/statistic.dataListNumber}}</div>
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
<script>
export default {
  data() {
    return {
      statistic:null,
    }
  },
  mounted(){
    this.$axios.get('/statistics/'+ this.$store.state.currentProjectId)
      .then(res => {
          console.log("statistics",res)
          this.statistic = res.data
          console.log(" sta table",this.statistic)
      })
      .catch((error) => {
          // here you will have access to error.response
          console.log('error',error.resposnse)
      });    
  }
}
</script>

<style>

.co{
    padding: 0;
    margin: 0;
    max-width:100%!important;
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
    font-size:1.6em;
    font-family:Georgia, serif!important;
    /*text-decoration :underline;*/
    color: rgb(124,124,124);
}
.pagetext-a{
   
    font-size:5em;
    font-weight: 600;
    text-align: center;
    height: 33%;
    /*margin-top: 30px;45px*/
    /*margin-bottom: 45px; */  
}

.pagetext-b{
    
    height: 33%;
    font-size:2.5em;
    font-weight: 600;
    text-align: center;
    margin-top: 5px;/*45px*/
    /*margin-bottom: 45px; */
    
}
</style>