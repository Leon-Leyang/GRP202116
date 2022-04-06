<template>
<div style="background:rgb(218, 253, 249);height:100%">
  <v-container style="height:100%">
   <div style="margin: 10px 10px 12px 14px">
<v-row style="height:10%">
  <v-btn
      fab
      small
      icon
      x-large
    >
    <v-icon dark large>
      mdi-delete
    </v-icon>
    </v-btn>
  <v-spacer>
  </v-spacer>
  <v-btn
      large
      color="amber"
      @click="xx()"
      depressed
      rounded
      class="white--text"
      x-large
  >  
      Import<v-icon
        right
        dark
        x-large
      > mdi-database-import
      </v-icon>
  </v-btn>
  <v-btn
      style="margin-left:20px; margin-right:10px"
      color="blue-grey"
      @click="xx()"
      rounded
      depressed
      class="white--text"
      large
      x-large
  >
      Export<v-icon
        right
        dark
        large
      > mdi-cloud-upload
      </v-icon>
  </v-btn>
</v-row>
    </div>
<el-table
  :data="tableData"
  style="width: 100%; height:85%"
  :default-sort = "{prop: 'id', order: 'ascending'}" 
  v-cloak
  stripe
  @row-click="enterData"
  @selection-change="handleSelectionChange"
  >
  <!-- <template slot-scope="tableData" id="template">
    <div @click="enterData(tableData.row)" id="div"> -->
      <el-table-column
        type="selection"
        >
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="ID" prop="dataId" align="center">
      </el-table-column> 
      <el-table-column show-overflow-tooltip sortable label="Last Modification Time" prop="updateTime" align="center">
      </el-table-column> 
      <el-table-column show-overflow-tooltip sortable label="Import Time" prop="createTime" align="center">
      </el-table-column>  
      <el-table-column show-overflow-tooltip label="Preview" prop="url" align="center">
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
</v-container>
</div>
</template>

<script>
  export default {
    name: 'DataTable',
    data(){
      return{ 
        tableData:[],
        multipleSelection: [],
        dataType:null,
        url: '../../../../../GRP202116/files/5/c7a3ec64-b343-40e7-9944-fb0b20151308.jpg'
      }
    },
    props: {
      config: Object,
    },
    watch: {
        multipleSelection: function (val) {
          console.log('val', val)
          let arr = [];
          for (let i in this.multipleSelection) {
            console.log('this.multipleSelection[i]',this.multipleSelection[i])
            arr.push(this.multipleSelection[i].realDataId);
          }
          console.log('multipleSelection',arr);
          this.$store.state.selectData = arr
          console.log('selectData',this.$store.state.selectData)
        }
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
      },
      handleSelectionChange(val) {
          this.multipleSelection = val;
          console.log('valk', val,this.multipleSelection)
      },      
    },
    created() {
      console.log('restart', '')
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
              else if(this.tableData[i].url.indexOf(".jpeg")!=-1){
                this.tableData[i].url = this.tableData[i].url.replace(".jpeg", "")
                this.tableData[i].url = require('..//..//..//..//../GRP202116/' + this.tableData[i].url + '.jpeg' )
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
              console.log('tabelda',this.tableData[i].url)
            }
            this.$store.state.currentDataList = this.tableData

            console.log('asd', this.$store.state.currentDataList)
          },500);

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
        },500);

      }
    },     
  }
</script>

<style>
.el-table th.el-table__cell>.cell{
  padding-left: 14px!important;
}
</style>