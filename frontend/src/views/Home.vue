<template>
<div>
  <v-container fluid>


    <v-data-iterator
      :items="tableData"
      :items-per-page.sync="itemsPerPage"
      :page.sync="page"
      :search="search"
      :sort-by="sortBy.toLowerCase()"
      :sort-desc="sortDesc"
      hide-default-footer
    >
      <template v-slot:header>
        <v-card-title>    
        <v-row>
          <v-dialog
          v-model="isShow"
          persistent
          width="1400"
          >
          <template v-slot:activator="{ on, attrs }">
              <v-btn
              color="primary"
              dark
              v-bind="attrs"
              v-on="on"
              @click="addProject()"
              >
              Create
              </v-btn>
          </template>
          <v-card
          height="700px"
          v-if="refresh"
          >
              <v-card-title class="text-h5">
              {{operateType === 'add' ? 'New' : 'Update'}}
              </v-card-title>
              <v-card-text style="padding:0">

              <!-- Create Project -->
              <div>
                <el-form :inline="inline" :model="operateForm" ref="operateForm" :rules="rules" label-width="200px" class="demo-ruleForm"  style="padding: 30px 60px 0px 30px; height: 620px;">
                <el-tabs v-model="active">
                    <!-- Basic information -->
                    <el-tab-pane name="1" >
                        <span slot="label">Basic Info</span>
                                <!-- Project Name -->
                                <el-form-item label="Project Name" prop="name">
                                    <el-input v-model="operateForm.name"></el-input>
                                </el-form-item>

                                <!-- Project Description -->
                                <el-form-item label="Description" style="margin-top:50px">
                                    <el-input type="textarea" v-model="operateForm.description" placeholder="(Optional)" rows="10"></el-input>
                                </el-form-item>   
                    </el-tab-pane>

                    <!-- Import ML -->
                  <el-tab-pane name="2" v-if="importAllow">
                      <span slot="label">Import ML model</span>
                      <ML></ML>
                      <!-- Import ML Model -->
                  </el-tab-pane>

                    <!-- Import Data -->
                    <el-tab-pane name="3" v-if="importAllow">
                        <span slot="label">Import Data</span>
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
                    </el-tab-pane>

                    <!-- Setting Label Interface -->
                    <el-tab-pane name="4">
                    <span slot="label">Setup Labeling Interface</span>
                        <SettingLS></SettingLS>
                    </el-tab-pane>                    

                </el-tabs>
                </el-form>
                  <!-- Button block -->
                  <!-- <div style="display: flex; float: right; width:35%">
                      <el-button class="next" @click="nextStep" style="float: right" icon="el-icon-d-arrow-right" circle></el-button>
                  </div> -->
              </div>
                  <!-- <Create :form="operateForm" :folderURL="folderURL" :operateType="operateType" v-model="folderURL"  ref="folderURL" style="width: 100%" /> -->
              </v-card-text>
              <v-card-actions
              style="position:absolute;bottom:0;right:0">

                <v-btn
                    color="green darken-1"
                    text
                    @click="cancelChange()"
                >
                    Cancel
                </v-btn>
                <v-btn
                    color="green darken-1"
                    text
                    @click="confirm()"
                >
                    Finish
                </v-btn>
              </v-card-actions>
          </v-card>
          </v-dialog>
        </v-row>

        <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            clearable
            flat
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Search"
            style="width: 50px; margin-right: 20px"
          ></v-text-field>
          <template v-if="$vuetify.breakpoint.mdAndUp">
            <v-select
              v-model="sortBy"
              flat
              hide-details
              :items="keys"
              prepend-inner-icon="mdi-magnify"
              label="Sort by"
              style="width: 20px; margin-right: 20px"
            ></v-select>
            <v-btn-toggle
              v-model="sortDesc"
              mandatory
            >
              <v-btn
                large
                depressed
                :value="false"
              >
                <v-icon>mdi-arrow-up</v-icon>
              </v-btn>
              <v-btn
                large
                depressed
                :value="true"
              >
                <v-icon>mdi-arrow-down</v-icon>
              </v-btn>
            </v-btn-toggle>
          </template>
        </v-card-title>
      </template>

      <template>
        <v-row dense>
          <v-col
            v-for="item in tableData"
            :key="item.projectId"
            cols="12"
            md="4"
          >
            <v-card
            height="140px">
                <v-card-title class="text-h5" @click="enterProject(item.projectId)">
                    {{ item.name }}
                </v-card-title>

                <v-card-subtitle style="height:30px">{{item.description}}</v-card-subtitle>
              
                <v-progress-linear
                :color="customColorMethod(item.process)"
                height="7"
                rounded
                :value="item.process"
                style="width:270px; margin-left: 10px"
                ></v-progress-linear>             
                <v-card-actions>
                <div style="margin-left:16px">
                  <v-row>
                    <v-card-subtitle style="padding:0px; font-size:10px; height: 15px; color: orange">
                      Created Time: {{item.createTime}}
                    </v-card-subtitle>
                  </v-row>
                  <v-row>
                    <v-card-subtitle style="padding:0px; font-size:10px; height: 15px; color: orange">
                      Last Update Time: {{item.updateTime}}
                    </v-card-subtitle>
                  </v-row>
                </div>

                <v-spacer></v-spacer>
                <v-btn icon @click="editProject(item)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>

                <v-btn icon @click="delProject(item)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
                </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </template>

      <template v-slot:footer>
        <v-row
          class="mt-2"
          align="center"
          justify="center"
          style="position:absolute; bottom:15px; right:25px"
        >
          <span class="grey--text">Items per page</span>
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                dark
                text
                color="primary"
                class="ml-2"
                v-bind="attrs"
                v-on="on"
              >
                {{ itemsPerPage }}
                <v-icon>mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item
                v-for="(number, index) in itemsPerPageArray"
                :key="index"
                @click="updateItemsPerPage(number)"
              >
                <v-list-item-title>{{ number }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-spacer></v-spacer>

          <span
            class="mr-4
            grey--text"
          >
            Page {{ page }} of {{ numberOfPages }}
          </span>
          <v-btn
            fab
            dark
            color="blue darken-3"
            class="mr-1"
            @click="formerPage"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="blue darken-3"
            class="ml-1"
            @click="nextPage"
          >
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </template>
    </v-data-iterator>
    
  </v-container>
  </div>
</template>

<script>
import SettingLS from '../components/ProjectManage/SettingLS.vue'
import ML from './Create_ML.vue'
// import Create from '../components/ProjectManage/Create.vue';
  export default {
      components:{
          SettingLS,
          ML
      },
    data () {
      return {
        scrollInvoked: 0,
        operateType: 'add',
        folderURL: '',
        isShow: false,
        tableData: [],
        tempProcess:0.0,
        operateForm: {
            configs:'',
            createTime: '',
            description: '',
            name: '',
            projectId: '',
            type:'',
            updateTime: '',
        },
        refresh:false,
        importAllow:false,

        itemsPerPageArray: [6, 8, 12],
        search: '',
        show: false,
        filter: {},
        sortDesc: false,
        page: 1,
        itemsPerPage: 8,
        sortBy: 'name',
        keys: [
          'Name',
          'Process',
          'UpdateTime',
          'CreateTime',
        ],
        addNum:0,
        fileList: [],
        i : 0,
        newestId: 0,

      // Create Project
      file:'',
      icons: ['mdi-arrow-right'],
      transparent: 'rgba(255, 255, 255, 0)',
      active:'1',
      }
    },
    methods: {
      onScroll () {
        this.scrollInvoked++
      },
      getList() {
          // this.tableData = null
          // this.config.loading = true
          // name ? (this.config.page = 1) : ''
          this.$axios.get('/project/list', {
                  params: {
                      // page: this.config.page,
                  }
              })
              .then(res => {
                console.log('tag', res.data)
                let temp = res
                console.log('temp',temp)
                let length = temp.data.length
                console.log('lenth', length)
                if(length == 0){
                  this.tableData = []
                }else{
                let newest = temp.data[length-1]
                this.newestId = newest.projectId
                console.log('newr', this.newestId)
                this.tableData = res.data.map(item => {
                  console.log('noew', item)
                  let projectId = item.projectId
                    item.updateTime = this.convertTime(item.updateTime)
                    item.createTime = this.convertTime(item.createTime)
                    this.$axios.get('/project/' + projectId + '/process',{
                      params: {
                            projectId
                        }
                    })
                      .then(res => {
                        this.tempProcess = res.data
                        console.log('temp',res.data)
                      })
                    return {...item,process:this.tempProcess}
                })

                // this.config.total = res.data.length
                // this.config.loading = false
                console.log("table",this.tableData)
              // console.log("dew",row)                  
                }

              })
              .catch((error) => {
              // here you will have access to error.response
                console.log(error.response)
                });
                
      },
      addProject() {
          this.importAllow = true
          this.operateForm = {}
          this.operateType = 'add'
          this.isShow = true
          this.refresh = true
      },
      editProject(row) {
          this.importAllow = false
          this.operateType = 'edit'
          this.isShow = true
          this.operateForm = row
          console.log('tag', row)
          this.refresh = true
      },
      confirm() {
        console.log('this.operateType', this.operateType)
          if (this.operateType === 'edit') {
              this.operateForm.configs = this.$store.state.currentConfig
              console.log('confifg', this.operateForm.configs,this.$store.state.currentConfig)
              console.log("test",this.operateForm)
              this.$axios.post(`/project/edit`, JSON.stringify(this.operateForm))
                  .then(res => {
                  console.log(res.data)
                  this.isShow = false
                  this.getList()
              })
          } else 
          {
              this.operateForm.type = 'image'
              console.log('this.operateForm.type', this.operateForm.type)
              this.operateForm.configs = this.$store.state.currentConfig
              console.log('confifg', this.operateForm.configs,this.$store.state.currentConfig)
              
              console.log("add test",this.operateForm)
              this.$axios.post('/project/add', this.operateForm)
              .then(res => {
                  console.log("new", res)
                  this.isShow = false
                  this.getList()
              })
              setTimeout(()=>{

                console.log('now!!!',this.$store.state.currentMLList)


                var folderURL = this.folderURL.split(",")


                //upload folder address
                if(folderURL != ''){                
                  var projectId = this.newestId
                  console.log('newestId', projectId)
                  console.log('address', folderURL)
                  this.$axios.post('/project/'+ projectId +'/data_url', folderURL)
                  .then(res => {
                    console.log('folderURL', res)
                  })
                }



                //post ml
                if(this.$store.state.currentMLList != []){
                  for(var mln = 0; mln<this.$store.state.currentMLList.length; mln++){
                    console.log('mln', mln)
                    console.log('params', this.$store.state.currentMLList[mln].params)

                    this.$store.state.currentMLList[mln].params = JSON.stringify(this.$store.state.currentMLList[mln].params)
                    console.log('params', this.$store.state.currentMLList[mln].params)
                    this.$axios.post(`/model/create/`+ projectId, JSON.stringify(this.$store.state.currentMLList[mln]))
                        .then(res => {
                        console.log("ml", res)

                    })
                  }
                  this.$store.state.currentMLList = null
                  console.log('this.$store.state.currentMLList', this.$store.state.currentMLList)                  
                }

                

                //upload file
                let fileList = this.fileList
                console.log('fileList', fileList)
                if( fileList != ''){
                  let formData = new FormData()
                  for (let i = 0; i < fileList.length; i++) {
                    formData.append('fileList', fileList[i])
                  }
                  this.$axios.post('/project/'+ projectId +'/data_file', formData,{
                    headers: {
                      'Content-Type': 'multipart/form-data'
                    }
                  })
                  .then(res => {
                    console.log('fileList', res)
                  })
                }
              },1000)
          }
          this.isShow = false
          this.active = '1'
          this.refresh = false
          this.$store.state.currentProjectId = null
          this.$store.state.currentMLList = null
      },
      cancelChange(){
        this.isShow = false 
        this.active = '1'
        this.$store.state.currentMLList = null
        this.refresh = false
        this.$store.state.currentProjectId = null
        this.$store.state.currentMLList = null
        this.$store.state.currentConfig = null   
      },
      delProject(row) {
          this.$confirm('This operation will permanently delete the file, are you sure you want to continue?', 'Hint', {
              confirmButtonText: 'Confirm',
              cancelButtonText: 'Cancel',
              type: 'warning'
          })
              .then(() => {
                  let projectId = row.projectId
                  console.log(typeof(projectId))
                  console.log(projectId)
                  this.$axios.delete('/project/'+ projectId, {
                          params: {
                              projectId
                          }
                      })
                      .then(res => {
                          console.log("as",res.data)
                          this.$message({
                              type: 'success',
                              message: 'Delete success!'
                          })
                      })
                      this.getList()
              })
              .catch(() => {
                  this.$message({
                      type: 'info',
                      message: 'Cancel delete'
                  })
              })


      },
      convertTime(oldTime){
          var newTime = new Date(oldTime)
          var time = newTime.getFullYear() + ". " + (newTime.getMonth()+1) + ". " + newTime.getDate();
          return time
      },
      nextPage () {
        if (this.page + 1 <= this.numberOfPages) this.page += 1
      },
      formerPage () {
        if (this.page - 1 >= 1) this.page -= 1
      },
      updateItemsPerPage (number) {
        this.itemsPerPage = number
      },
      customColorMethod(percentage) {
        if (percentage < 100) {
          return '#FFE7AD';
        } else {
          return '#7EDF96';
        }
      },
      nextStep() {
            console.log('tag', this.file)
            let num = Number(this.active)
            if(num<4){ /*       */
                num++;
            }        
            // num<3 && num++
            this.active = num.toString()
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isLt2M) {
          this.$message.error('Upload file size cannot exceed 2MB!');
        }
        return isLt2M;
      },
      handlePreview(file) {
        console.log(file)
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handleChange(file, fileList){
        console.log(file, fileList);
        //this.fileList  = fileList.map(function (item) { return item.raw; });
        this.fileList.push(file);
        // console.log('a_list',a_list)
        // console.log('type_a_list',typeof(a_list[0]))
        // this.fileList = JSON.parse(JSON.stringify(fileList))
        // this.fileList = JSON.stringify(a_list)
        // this.fileList = a_list
        console.log('this.fileList', this.fileList)
      },
      getFiles: function(event) {
        this.fileList = [];
        let files = event.target.files;
        for (let i = 0; i < files.length; i++) {
          this.fileList.push(files[i]);
        }
      },
      test(){
          this.$axios.get('/data/1', {
                  params: {
                  }
              })
              .then(res => {
                console.log('rws', res)
                console.log('type', typeof(res))
              })
              .catch((error) => {
              // here you will have access to error.response
                console.log(error.response)
                });
      },

      //enter project
      enterProject(projectId){
        console.log(projectId)
        this.$store.state.currentProjectId = projectId
        console.log('tabel',this.tableData)
        this.$store.state.currentProject = this.tableData[projectId-1]
        this.$store.state.currentConfig = this.tableData[projectId-1].configs
        this.$store.state.currentProject = this.tableData[projectId-1]
        console.log('newwjin',this.$store.state.currentProject )
        this.$router.push({  
                    path: '/per-project',
                    name: 'PerProject', 
                }) 
      }
    },
    mounted() {
        // console.log('tag', '')
        this.getList()
    },
    activated: function() {
      this.getList()
    },
    computed: {
      numberOfPages () {
        return Math.ceil(this.tableData.length / this.itemsPerPage)
      },
      filteredKeys () {
        return this.keys.filter(key => key !== 'Name')
      },
    },

  }
</script>