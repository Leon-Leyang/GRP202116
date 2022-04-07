<template>
<div style="background:#f1f2fa;height:100%">
  <v-container style="height:100%">
    <div style="margin: 10px 10px 12px 14px">
      <v-row style="height:10%">
        <v-btn
            icon
            @click="multiDelete()"
            :key="Math.random()"
          >
          <v-icon dark large>
            mdi-delete
          </v-icon>
        </v-btn>
        <v-spacer>
        </v-spacer>
        <v-dialog
          v-model="dialog"
          width="500"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              dark
              color="#FFA726"
              v-bind="attrs"
              depressed
              rounded
              class="white--text"
              v-on="on"
              large
            >
              Import
              <v-icon
                right
                dark
                large
              > mdi-database-import
              </v-icon>
            </v-btn>
          </template>

          <v-card>
            <div style="display:flex">
              <div>
                Please enter the path to the folder where you want to use the file:

                <el-input
                  type="textarea"
                  :rows="2"
                  placeholder="Address"
                  v-model="folderURL"
                  >
                </el-input>
                <div slot="tip" class="el-upload__tip">If there are multiple paths please separate them with commas(",").</div>
              </div>
              <div>
                Please upload the appropriate type of file
                <input @change="getFiles($event)" name="files" type="file" multiple="multiple" /><br />
              </div>
            </div>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="primary"
                text
                @click="CancelImport()"
              >
                Cancel
              </v-btn>      
              <v-btn
                color="primary"
                text
                @click="ImportData()"
              >
                Accept
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-btn
            style="margin-left:20px; margin-right:10px"
            color="blue-grey"
            @click="xx()"
            rounded
            depressed
            class="white--text"
            large
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
      style="width: 100%;border-radius:10px;opacity:0.9"
      :header-cell-style="{color: '#848484', fontSize: '20px', backgroundColor: '#qua'}"
      :cell-style="{color: '#848484', fontSize: '17px', backgroundColor: '#qua'}"
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
          <el-table-column show-overflow-tooltip sortable label="ID" prop="listNumber" align="center">
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
          <el-table-column label="Annotation" prop="anno" align="center"></el-table-column> 
          <el-table-column label="Prediction" prop="predi" align="center"></el-table-column>       
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pagesize"
      layout="total, prev, pager, next"
      :total="total"
      class="pager">
    </el-pagination>
  </v-container>
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
        pagesize: 7,
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
        folderURL:'',
        fileList: [],
        dialog: false,

      }
    },
    methods: {
      loadData(projectId, pageNum, pageSize){
        this.$axios.get('/data/page/'+ projectId +"/"+ pageNum +"/"+ pageSize)
        .then((res) => {
                console.log("currentPageList", res)
                this.$store.state.currentPageList = res.data.list
                console.log('this.$store.state.currentPageList', this.$store.state.currentPageList)
                this.total = res.data.total
                this.tableData = this.$store.state.currentPageList
                this.tableData = this.tableData.map(item => {
                  console.log('noew', item)
                    item.updateTime = this.convertTime(item.updateTime)
                    item.createTime = this.convertTime(item.createTime)
                    return {...item}
                })                     
                this.convertData()
        })
        // this.$set(this.tableData,1,this.tableData)
      },  
      convertTime(oldTime){
          var newTime = new Date(oldTime)
          var time = newTime.getFullYear() + ". " + (newTime.getMonth()+1) + ". " + newTime.getDate();
          return time
      },          
      enterData(data, event, column){
        console.log('dataenter', data,event,column)
        this.$store.state.currentDataId = data.listNumber
        this.$store.state.realDataId = data.dataId
        console.log('cur', this.$store.state.currentDataId)
        console.log('real', this.$store.state.realDataId)
        console.log('lisy', this.$store.state.currentPageList[this.$store.state.currentDataId - 1])
        this.$store.state.totalNum = this.total
        this.$router.push({  
                    path: '/data',
                    name: 'Data',  
                }) 
      },
      handleSelectionChange(val) {
          this.multipleSelection = val;
          console.log('valk', val,this.multipleSelection)
      },   
      // handleSizeChange(val){
      //   this.pagesize = val;
      //   this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);        
      // },
      handleCurrentChange(val) {
          this.currentPage = val;
          console.log('currentPage', this.currentPage)
          this.$store.state.currentPage = val
          this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize);
      },
      multiDelete(){
        if(this.multipleSelection.length == 0)
        return;
        var array = [];
        this.multipleSelection.forEach((item) => {
          array.push(item.dataId)
        })
        console.log('arr', array)
        for(var i = 0;i<array.length;i++){
          this.$axios.delete('/data/'+ this.$store.state.currentProjectId + "/" + array[i])
          .then((res)=>{
            console.log('res delete daata', res)
          })
        }
        this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize)
      },
      convertData(){   
        if(this.$store.state.currentProject.type == 'image'){
          clearTimeout(this.timer); 
          this.timer = setTimeout(()=>{  
              console.log('currentDataList', this.$store.state.currentPageList)
              console.log('curraList', this.tableData)

              for(var i=0; i<this.tableData.length; i++){
                this.tableData[i].url = this.tableData[i].url.replaceAll("\\", "/")
                this.tableData[i].url = this.tableData[i].url.replace("../frontend/public/", "")
                console.log('tabelda',this.tableData[i].url)
              }
              this.$store.state.currentPageList = this.tableData

              console.log('asd', this.$store.state.currentPageList)
            },200);

        }else{
          clearTimeout(this.timer); 
          this.timer = setTimeout(()=>{  
            for(var i=0; i<this.tableData.length; i++){
              console.log('tabelda',this.tableData[i].url)
            }        
            this.$store.state.currentPageList = this.tableData
            console.log('table text', this.tableData)
          },200);

        }        
      },
      getFiles: function(event) {
        this.fileList = [];
        let files = event.target.files;
        for (let i = 0; i < files.length; i++) {
          this.fileList.push(files[i]);
        }
      },   
      ImportData(){
        //upload folder address
        var folderURL = this.folderURL.split(",")
          console.log('this.$store.state.currentProjectId',this.$store.state.currentProjectId)

        if(folderURL != ''){                
          console.log('address', folderURL)
          this.$axios.post('/project/'+ this.$store.state.currentProjectId +'/data_url', folderURL)
          .then(res => {
            console.log('folderURL', res)
          })
        }        
        //upload file
        let fileList = this.fileList
        console.log('fileList', fileList)
        if(fileList != ''){
          let formData = new FormData()
          for (let i = 0; i < fileList.length; i++) {
            formData.append('fileList', fileList[i])
          }
          this.$axios.post('/project/'+ this.$store.state.currentProjectId +'/data_file', formData,{
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
          .then(res => {
            console.log('fileList', res)
          })
          }
          this.folderURL = null
          this.fileList = []          
          this.dialog = false
          this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize)
      }, 
      CancelImport(){
        this.folderURL = null
        this.fileList = []
        this.dialog = false
      }         
    },
    created() {
      console.log('restart', '')

      this.dataType = this.$store.state.currentProject.type
      this.loadData(this.$store.state.currentProjectId, this.currentPage, this.pagesize)
      console.log('currentPage', this.currentPage)
    },     
  }
</script>

