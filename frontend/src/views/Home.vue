<!--
This is the Home page of our project 
which show all project card
@author LinjingSUN YingjiaLi
-->
<template>
<div>
  <v-container fluid>


    <v-data-iterator
      :items="items"
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
              style="width:150px;height:60px;font-size:25px; border-radius:15px;margin-left:15px"
              @click="addProject()"
              >
              Create
              </v-btn>
          </template>
          <v-card
          height="800px"
          v-if="refresh"
          >
              <v-card-title class="text-h5">
              {{operateType === 'add' ? 'New' : 'Update'}}
              </v-card-title>
              <v-card-text style="padding:0">

              <!-- Create Project -->
              <div>
                <el-form :inline="inline" :model="operateForm" ref="operateForm" :rules="rules" label-width="200px" class="demo-ruleForm"  style="padding: 30px 60px 0px 30px; height: 760x;">
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

                    <!-- Import Data -->
                    <el-tab-pane name="2" v-if="importAllow">
                        <span slot="label">Import Data</span>
                        <div style="margin-left: 45%">
                          <div>Choose Data Type:</div>
                          <v-chip-group
                            v-model="operateForm.type"
                            active-class="deep-purple--text text--accent-4"
                            mandatory
                          >
                          <v-chip
                            v-for="type in oType"
                            :key="type"
                            :value="type"

                          >
                            {{ type }}
                          </v-chip>
                          </v-chip-group>                          
                        </div>
                        <div style="display:flex; justify-content: space-between;">
                          <div style="border:5px solid purple; border-radius: 25px; padding: 10px; height:150px">
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
                          <div style="border:5px solid purple; border-radius: 25px; padding: 10px; height:150px">
                            Please upload the appropriate type of file
                            <input @change="getFiles($event)" name="files" type="file" multiple="multiple" /><br />
                          </div>
                        </div>
                    </el-tab-pane>

                    <!-- Setting Label Interface -->
                    <el-tab-pane name="3">
                    <span slot="label">Setup Labeling Interface</span>
                        <SettingLS></SettingLS>
                    </el-tab-pane>    

                    <!-- Import ML -->
                  <el-tab-pane name="4" v-if="importAllow">
                      <span slot="label">Import ML model</span>
                      <ML></ML>
                      <!-- Import ML Model -->
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

      <template v-slot:default="props">
        <v-row dense>
          <v-col
            v-for="item in props.items"
            :key="item.projectId"
            cols="12"
            md="4"
            style="padding:20px"
          >
            <v-card
            height="250px"
            style="border-radius:25px;padding-left:10px">
                <v-card-title style="font-size:47px;color:#1A237E;padding-top:25px" @click="enterProject(item.projectId)">
                    {{ item.name }}
                </v-card-title>

                <v-card-subtitle style="height:80px;font-size:24px;margin-top:5px">{{item.description}}</v-card-subtitle>
              
                <v-progress-linear
                :color="customColorMethod(item.process)"
                height="12"
                rounded
                :value="item.process"
                style="width:400px; margin-left: 10px"
                ></v-progress-linear>             
                <v-card-actions>
                <div style="margin-left:20px">
                  <v-row style="margin-top:10px">
                    <v-card-subtitle style="padding:0px; font-size:16px; height: 30px; color:#7986CB">
                      Created Time: {{item.createTime}}
                    </v-card-subtitle>
                  </v-row>
                  <v-row>
                    <v-card-subtitle style="padding:0px; font-size:16px; height: 30px; color:#7986CB">
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
  export default {
      components:{
          SettingLS,
          ML
      },
    data () {
      return {
        operateType: 'add',
        folderURL: '',
        isShow: false,
        items: [],
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
        itemsPerPage: 6,
        sortBy: 'createTime',
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
        oType: ['image','text'],

      // Create Project
      file:'',
      icons: ['mdi-arrow-right'],
      transparent: 'rgba(255, 255, 255, 0)',
      active:'1',
      }
    },
    methods: {
      /**
       * This function is to get the list of projects and
       * Assign the value to items to make it appear on the page
       */
      getList() {          // this.items = null
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
                  this.items = []
                }else{
                let newest = temp.data[length-1]
                this.newestId = newest.projectId
                console.log('newr', this.newestId)
                this.items = res.data.map(item => {
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
                console.log("table1")
              // console.log("dew",row)                  
                }

              })
              .catch((error) => {
              // here you will have access to error.response
                console.log('error',error.response)
                });
                
      },
      /**
       * This is called after click "Create" Button
       */
      addProject() {
          this.folderURL = null
          this.importAllow = true
          this.operateForm = {}
          this.operateType = 'add'
          this.isShow = true
          this.refresh = true
      },
      /**
       * This is called after click "Edit" Button icon
       * @param row Thu id of select project
       */      
      editProject(row) {
          this.importAllow = false
          this.operateType = 'edit'
          this.isShow = true
          this.operateForm = row
          console.log('tag', row)
          this.refresh = true
      },
      /**
       * This is called after finish all the information about the project and click 'Confirm'
       */      
      confirm() {
        console.log('this.operateType', this.operateType)
          if (this.operateType === 'edit') {
              this.operateForm.configs = this.$store.state.currentConfig
              console.log('confifg', this.operateForm.configs)
              this.operateForm.createTime = "2022-04-03T16:00:00.000+00:00"
              this.operateForm.updateTime = "2022-04-03T16:00:00.000+00:00"
              console.log("test",this.operateForm, JSON.stringify(this.operateForm))
              this.$axios.post('/project/edit', JSON.stringify(this.operateForm))
                  .then(res => {
                  console.log("edit", res.data)
                  this.isShow = false
                  this.getList()
              })
          } else 
          {
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
                console.log('newr', this.newestId)
                console.log('address', this.folderURL)
                console.log('now!!!',this.$store.state.currentMLList)


                var folderURL = (this.folderURL || "").split(",")
                var projectId = this.newestId
                //upload folder address
                if(folderURL != ''){                
                  console.log('newestId', this.newestId)
                  console.log('address', folderURL)
                  this.$axios.post('/project/'+ projectId +'/data_url', folderURL)
                  .then(res => {
                    console.log('folderURL', res)
                  })
                }



                //post ml
                console.log('this.$store.state.currentMLList1', this.$store.state.currentMLList)
                if(this.$store.state.currentMLList != null){
                  for(var mln = 0; mln<this.$store.state.currentMLList.length; mln++){
                    this.$store.state.currentMLList[mln].params = JSON.stringify(this.$store.state.currentMLList[mln].params)
                    console.log('params', this.$store.state.currentMLList[mln].params)
                  }
                  this.$axios.post(`/model/create/`+ projectId, JSON.stringify(this.$store.state.currentMLList))
                      .then(res => {
                      console.log("ml", res)
                  })            
                  this.$store.state.currentMLList = null
                  console.log('this.$store.state.currentMLList', this.$store.state.currentMLList)                  
                }

                console.log('this.$store.state.currentMLList', this.$store.state.currentMLList)

                //upload file
                let fileList = this.fileList
                console.log('fileList', fileList)
                if(fileList != ''){
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
      },
      /**
       * This is for user do not want continue create or edit project, is called when press 'Cancel' button
       */      
      cancelChange(){
        this.isShow = false 
        this.active = '1'
        this.$store.state.currentMLList = null
        this.folderURL = null
        this.refresh = false
        this.$store.state.currentProjectId = null
        this.$store.state.currentMLList = null
        this.$store.state.currentConfig = null   
      },
      /**
       * This is called after click "Delete" Button icon
       * @param row Thu id of select project
       */        
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
                          console.log("as",res)
                          this.$message({
                              type: 'success',
                              message: 'Delete success!'
                          })
                  this.getList()

                      })
              })
              .catch(() => {
                  this.$message({
                      type: 'info',
                      message: 'Cancel delete'
                  })
              })


      },
      /**
       * This is for convert time form from "yyyy-MM-dd HH:mm:ss" to "yyyy-MM-dd"
       * @param oldTime Time fetched from the back end
       */
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
      /**
       * This is used to change the colour of the progress bar, 
       * blue means that it is still in progress 
       * green means that all the data in the item is marked.
       * @param percentage Current project's percentage
       */
      customColorMethod(percentage) {
        if (percentage < 100) {
          return '#90CAF9';
        } else {
          return '#7EDF96';
        }
      },
      /**
       * this is for upload files directly
       * @param event Click event
       */
      getFiles: function(event) {
        this.fileList = [];
        let files = event.target.files;
        for (let i = 0; i < files.length; i++) {
          this.fileList.push(files[i]);
        }
      },

      /**
       * This is to enter per project
       * @param projectId Item id of the selected item
       */
      enterProject(projectId){
        console.log(projectId)
        this.$store.state.currentProjectId = projectId
        console.log('tabel',this.items);
        for(var q = 0;q < this.items.length; q++){
          if(projectId == this.items[q].projectId){
            this.$store.state.currentProject = this.items[q];
            this.$store.state.currentConfig = this.items[q].configs;
            this.$store.state.currentProject = this.items[q];
          }
        }
        console.log('newwjin',this.$store.state.currentProject )
        this.$router.push({  
                    path: '/per-project',
                    name: 'PerProject', 
                }) 
      }
    },
    mounted() {
        // console.log('tag', '')
        this.$store.state.pageLocate = 'NotData'
        this.getList()
    },
    activated: function() {
      this.getList()
    },
    computed: {
      numberOfPages () {
        return Math.ceil(this.items.length / this.itemsPerPage)
      },
      filteredKeys () {
        return this.keys.filter(key => key !== 'CreateTime')
      },
    },

  }
</script>
