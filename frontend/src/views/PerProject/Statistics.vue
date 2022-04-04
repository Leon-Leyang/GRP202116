<template>
  <v-container class="co">
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
            <div class="pagetext-a">{{statistic.labeledDataListNumber}}/{{statistic.dataListNumber}}</div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Percentage completed</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="(statistic.labeledDataListNumber/statistic.dataListNumber)"></el-progress></div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock" v-if="this.$store.state.currentProject.type == 'txt'">
            <div class="grid-content bg-purple-light title">Average word counts per piece of data </div>
            <div class="pagetext-a">{{averageTextWordsNumber}}</div>
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
            <div class="pagetext-a">{{statistic.annotationsNumber}}</div>
        </el-col>     
        </el-row>
        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Annotations Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="statistic.averageAnnotations"></el-progress></div>
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
            <div class="pagetext-a">{{statistic.predictionsNumber}}</div>
        </el-col>
        </el-row>

        <el-row>
        <el-col :span="24" class="inblock">
            <div class="grid-content bg-purple title">Predictions Percentage:</div>
            <div class="pagetext-b"><el-progress type="circle" :percentage="statistic.averagePredictions"></el-progress></div>
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
          console.log('error',error.response)
      });    
  }
}
</script>

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