<template>
<div>
<el-table
  :data="tableData"
  style="width: 100%"
  :default-sort = "{prop: 'id', order: 'ascending'}" 
  v-cloak
  stripe
  @row-click="enterData"
  >
  <!-- <template slot-scope="tableData" id="template">
    <div @click="enterData(tableData.row)" id="div"> -->
      <el-table-column
        type="selection"
        width="50">
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="ID" width="100" prop="dataId" align="center">
      </el-table-column> 
      <el-table-column show-overflow-tooltip sortable label="Last Modification Time" width="200" prop="updateTime" align="center">
      </el-table-column> 
      <el-table-column show-overflow-tooltip sortable label="Import Time" width="200" prop="createTime" align="center">
      </el-table-column>  
      <el-table-column show-overflow-tooltip label="Preview" width="200" prop="url" align="center">
        <template slot-scope="scope">
                    <el-popover placement="right" v-if="dataType == 'image'">
                      <img :src="scope.row.url" />
                      <img
                        slot="reference"
                        :src="scope.row.url"
                        :alt="scope.row.url"
                        style="max-height: 50px; max-width: 130px"
                      />
                    </el-popover>
                    <div v-if="dataType == 'text'" style="width:50px;white-space:nowrap;overflow:hidden;">
                      <span>
                        {{scope.row.url}}
                      </span>
                    </div>
          </template>
      </el-table-column>   
    <!-- </div>
  </template> -->
  <!-- <el-table-column show-overflow-tooltip label="Anno" width="100" prop="sta" align="center">
    <template slot-scope="tableData">
      <button @click="enterData(tableData.row)">Enter</button>
    </template>
  </el-table-column>  -->
  <!-- <el-table-column label="Annotation" width="100" prop="anno" align="center"></el-table-column>  -->
  <!-- <el-table-column label="Prediction" width="100" prop="predi" align="center"></el-table-column>        -->
</el-table>
<el-pagination class="pager" layout="prev, pager, next"  :total="10" :current-page.sync="config.page" @current-change="changePage" :page-size="20"></el-pagination>

</div>
</template>

<script>
  export default {
    name: 'DataTable',
    data(){
      return{ 
        tableData:[],
        dataType:null,
        url: '../../../../../GRP202116/files/5/c7a3ec64-b343-40e7-9944-fb0b20151308.jpg'
      }
    },
    props: {
      config: Object,
    },
    methods: {
      enterData(data, event, column){
        console.log('data', data,event,column)
        this.$store.state.currentDataId = data.dataId
        this.$store.state.realDataId = data.realDataId
        console.log('cur', this.$store.state.currentDataId)
        console.log('real', this.$store.state.realDataId)
        console.log('culist', this.$store.state.currentDataList)
        console.log('lisy', this.$store.state.currentDataList[this.$store.state.currentDataId - 1].dataId)
        this.$router.push({  
                    path: '/data',
                    name: 'Data',  
                }) 
      }
    },
    created() {
      this.dataType = this.$store.state.currentProject.type
      if(this.$store.state.currentProject.type == 'image'){
        clearTimeout(this.timer); 
        this.timer = setTimeout(()=>{  
            console.log('currentDataList', this.$store.state.currentDataList)
            this.tableData = this.$store.state.currentDataList
            console.log('curraList', this.tableData)

            for(var i=0; i<this.tableData.length; i++){
              this.tableData[i].realDataId = this.tableData[i].dataId
              this.tableData[i].dataId = i + 1
              this.tableData[i].url = this.tableData[i].url.replaceAll("\\", "/")
              this.tableData[i].url = this.tableData[i].url.replace("../", "")

              if(this.tableData[i].url.indexOf(".jpg")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".jpg", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.jpg' )
                console.log('puic', this.tableData[i].url)
              }
              else if(this.tableData[i].url.indexOf(".JPG")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".JPG", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.JPG' )
                console.log('puic', this.tableData[i].url)
              }
              else if(this.tableData[i].url.indexOf(".JPEG")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".JPEG", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.JPEG' )
                console.log('puic', this.tableData[i].url)
              }          
              else if(this.tableData[i].url.indexOf(".jpeg")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".jpeg", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.jpeg' )
                console.log('puic', this.tableData[i].url)
              }
              else if(this.tableData[i].url.indexOf(".PNG")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".PNG", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.PNG' )
                console.log('puic', this.tableData[i].url)
              }
              else if(this.tableData[i].url.indexOf(".png")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".png", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.png' )
                console.log('puic', this.tableData[i].url)
              }          
              else if(this.tableData[i].url.indexOf(".webp")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".webp", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.webp' )
                console.log('puic', this.tableData[i].url)
              }
              else if(this.tableData[i].url.indexOf(".WEBP")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".WEBP", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.WEBP' )
                console.log('puic', this.tableData[i].url)
              }                            
              console.log('tabelda',this.tableData[i].url)
            }
            this.$store.state.currentDataList = this.tableData

            console.log('asd', this.$store.state.currentDataList)
          },700);

      }else{
        clearTimeout(this.timer); 
        this.timer = setTimeout(()=>{  
          this.tableData = this.$store.state.currentDataList
          for(var i=0; i<this.tableData.length; i++){
            this.tableData[i].realDataId = this.tableData[i].dataId
            this.tableData[i].dataId = i + 1
            console.log('tabelda',this.tableData[i].url)
          }        
          this.$store.state.currentDataList = this.tableData
          console.log('table text', this.tableData)
        },700);

      }
    }, 

    // mounted() {
    //   clearTimeout(this.timer); 
 
    //   this.timer = setTimeout(()=>{  
    //     console.log('currentDataList', this.$store.state.currentDataList)
    //     this.tableData = this.$store.state.currentDataList
    //     console.log('curraList', this.tableData)

    //     // for(var i=0; i<this.tableData.length; i++){
    //     //   this.tableData[i].realDataId = this.tableData[i].dataId
    //     //   this.tableData[i].dataId = i + 1
    //     //   this.tableData[i].url = this.tableData[i].url.replaceAll("\\", "/")
    //     //   this.tableData[i].url = this.tableData[i].url.replace("../", "")

    //     //   if(this.tableData[i].url.indexOf(".jpg")!=-1){
    //     //     this.tableData[i].url = this.tableData[i].url.replace(".jpg", "")
    //     //     this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.jpg' )
    //     //     console.log('puic', this.tableData[i].url)
    //     //   }
    //     //   else if(this.tableData[i].url.indexOf(".JPEG")!=-1){
    //     //     this.tableData[i].url = this.tableData[i].url.replace(".JPEG", "")
    //     //     this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.JPEG' )
    //     //     console.log('puic', this.tableData[i].url)
    //     //   }
    //     //   else if(this.tableData[i].url.indexOf(".PNG")!=-1){
    //     //     this.tableData[i].url = this.tableData[i].url.replace(".PNG", "")
    //     //     this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.PNG' )
    //     //     console.log('puic', this.tableData[i].url)
    //     //   }
    //     //   else if(this.tableData[i].url.indexOf(".webp")!=-1){
    //     //     this.tableData[i].url = this.tableData[i].url.replace(".webp", "")
    //     //     this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.webp' )
    //     //     console.log('puic', this.tableData[i].url)
    //     //   }
    //     //   // else if(this.tableData[i].url.indexOf("txt")!=-1){
    //     //   //   this.tableData[i].url = this.tableData[i].url.replace(".txt", "")
    //     //   //   this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.txt' )
    //     //   // }  
    //     //   // else if(this.tableData[i].url.indexOf(".doc")!=-1){
    //     //   //   this.tableData[i].url = this.tableData[i].url.replace(".doc", "")
    //     //   //   this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.doc' )
    //     //   // }                  
    //     //   console.log('tabelda',this.tableData[i].url)
    //     // }
    //     // this.$store.state.currentDataList = this.tableData

    //     // console.log('asd', this.$store.state.currentDataList)
    //   },500);

    // },      

  }
</script>

<style>

</style>