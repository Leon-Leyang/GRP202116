<template>
<div>
<v-btn>Delete</v-btn>
<el-table
  :data="tableData"
  style="width: 100%"
  :default-sort = "{prop: 'id', order: 'ascending'}" 
  v-cloak
  stripe
  @row-click="enterData"
  @selection-change="handleSelectionChange"
  >

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

</el-table>
<el-pagination
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
  :current-page="currentPage"
  :page-sizes="[10, 20, 30, 40]"
  :page-size="pagesize"
  layout="total, sizes, prev, pager, next, jumper"
  :total="totalCount"
  class="pager"></el-pagination>

</div>
</template>

<script>
  export default {
    name: 'DataTable',
    data(){
      return{ 
        tableData:[],
        highlightId: -1,
        currentPage: 1,
        start: 1,
        totalCount: 1000,
        pagesize: 10,
        base_url:'./',
        multipleSelection: [],
        dataType:null,
        url: '../../../../../GRP202116/files/5/c7a3ec64-b343-40e7-9944-fb0b20151308.jpg',
        listQuery: {
          page: 1,
          limit: 20
          },
        total:null,
        list:null,  
      }
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
      loadData(projectId, pageNum, pageSize){
        this.$axios.get('/data/page/'+ projectId +"/"+ pageNum +"/"+ pageSize)
        .then((res) => {
                console.log("currentPageList", res)
                this.$store.state.currentPageList = res.data
                this.total = res.data.total
                // this.total = res.data.total
                // this.$store.state.currentDataList = res.data
                // console.log('total, list', this.total, this.list)
        })
      },  
          
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
      handleSizeChange(val){
        this.pagesize = val;
        this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);        
      },
      handleCurrentChange(val) {
          this.currentPage = val;
          this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);
      },
      multiDelete(){
        if(this.multipleSelection.length == 0)
        return;
        var array = [];
        this.multipleSelection.forEach((item) => {
          array.push(item.realDataId)
        })
        for(var i = 0;i<array.length;i++){
          this.$axios.delete('/data/'+ array[i])
          .then((res)=>{
            console.log('res delete daata', res)
          })
        }
      },
      convertData(){
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
                this.tableData[i].url = this.tableData[i].url.replace("../frontend/public/", "")
                console.log('tabelda',this.tableData[i].url)
              }
              this.$store.state.currentDataList = this.tableData

              console.log('asd', this.$store.state.currentDataList)
            },200);

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
          },200);

        }        
      }   
    },
    created() {
      console.log('restart', '')

      this.dataType = this.$store.state.currentProject.type
      this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize)
    },     
  }
</script>

<style>

</style>