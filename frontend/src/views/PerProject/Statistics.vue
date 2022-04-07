<!--this is the statistic page
which contains statistic data about data, annotation prediction, ml
@author LinjingSUN YingjiaLI-->
<template>
  <v-container class="co">
 
  <v-row
        no-gutters
        justify="center"
        style="height:100%;padding: 0px 10px 0px 10px"
  >
<div style="display:flex;width:100%;justify-content: space-between;">
        <v-card
           rounded="xl"
           style="width:24%;padding:30px;margin:10px 5px 10px 5px;background-color: rgba(120,144,156,0.3);position:relative"
          >
          <div style="font-size:70px;width:160px;transform:rotate(270deg);position:absolute;right:0;padding-top:40px;font-style:italic;font-weight:900;color:rgba(26,35,126,0.5)">
              Data 
          </div>
          <div style="float:left;width:80%">
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

        <el-row>
            <el-col :span="24" class="inblock">
                <div class="grid-content bg-purple title">Data completed:</div>
                <div class="pagetext-a">{{statistic.labeledDataListNumber}}</div>
            </el-col>
            </el-row>              
          </div>

      </v-card>
          
           <v-card
           rounded="xl"
           style="width:24%;padding:30px;margin:10px 5px 10px 5px;background-color:rgba(38,50,56,0.5);position:relative"
          >
          <div style="height:10%;width:500px;font-size:70px;transform:rotate(270deg);position:absolute;margin-top:70px;padding-top:30px;font-style:italic;font-weight:900;color:rgba(26,35,126,0.5)">
              Annotation
          </div>
          <div style="width:80%;float:left">
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
          </div>
          </v-card>
  
           <v-card
           rounded="xl"
           style="width:24%;padding:30px;margin:10px 5px 10px 5px;background-color: rgba(120,144,156,0.3);position:relative"
          >
          <div style="height:10%;width:450px;font-size:70px;transform:rotate(270deg);position:absolute;margin-top:70px;padding-top:56px;font-style:italic;font-weight:900;color:rgba(26,35,126,0.5)">
            Prediction
          </div>
          <div style="width:80%;float:left">
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
          </div> 
   
        </v-card>

           <v-card
           rounded="xl"
           style="width:24%;padding:30px;margin:10px 5px 10px 5px;background-color:rgba(38,50,56,0.5)"
          >
          <div style="height:10%;width:450px;font-size:70px;transform:rotate(270deg);position:absolute;margin-top:70px;padding-top:60px;font-style:italic;font-weight:900;color:rgba(26,35,126,0.5)">
            ML Model
          </div>
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
</div>  


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
    background-color: #f1f2fa;
}
.c-1{
    height: 100%;
    border-color: /*rgb(35, 157, 232)*/#EDE7F6!important;
    border-width: 3px!important;
    background:white!important;
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