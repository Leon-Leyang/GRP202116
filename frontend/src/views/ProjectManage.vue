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
            width="700"
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
            <v-card>
                <v-card-title class="text-h5">
                {{operateType === 'add' ? 'New' : 'Update'}}
                </v-card-title>
                <v-card-text>
                    <Create :form="operateForm" ref="form" style="width: 100%" />
                </v-card-text>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green darken-1"
                    text
                    @click="isShow = false"
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
            <v-card>
                <v-card-title class="text-h5">
                    {{ item.name }}
                </v-card-title>

                <v-card-subtitle>{{item.description}}</v-card-subtitle>
              
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
import Mock from 'mockjs'
import Create from '../components/ProjectManage/Create.vue';
  export default {
      components:{
          Create
      },
    data () {
      return {
        operateType: 'add',
        isShow: false,
        tableData: [],
        operateForm: {
            configs:'',
            createTime: '',
            description: '',
            name: '',
            process: '',
            projectId: '',
            type:'',
            updateTime: '',
        },

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
      }
    },
    methods: {
        getList() {
            // this.config.loading = true
            // name ? (this.config.page = 1) : ''
            this.$axios.get('/project/list', {
                    params: {
                        // page: this.config.page,
                    }
                })
                .then(res => {
                  console.log('tag', res)
                    this.tableData = res.data.map(item => {
                        // item.updateTime = this.convertTime(item.updateTime)
                        // item.createTime = this.convertTime(item.createTime)
                        return {...item,process:12}
                    })

                    // this.config.total = res.data.length
                    // this.config.loading = false
                    console.log("table",this.tableData)
                // console.log("dew",row)

                })
                .catch((error) => {
        // here you will have access to error.response
        console.log(error.response)
    });
        },
        addProject() {
            this.operateForm = {}
            this.operateType = 'add'
            this.isShow = true
        },
        editProject(row) {
            this.operateType = 'edit'
            this.isShow = true
            this.operateForm = row
            console.log('tag', row)
        },
        confirm() {
            if (this.operateType === 'edit') {
                console.log("test",this.operateForm)
                this.$axios.post(`/project/edit`, JSON.stringify(this.operateForm))
                    .then(res => {
                    console.log(res.data)
                    this.isShow = false
                    this.getList()
                })
            } else {
                console.log("add test",this.operateForm)
                this.operateForm.projectId = 12
                this.$axios.post('/project/add', this.operateForm)
                .then(res => {
                    console.log("new", res.data)
                    this.isShow = false
                    this.getList()

                })
            }
            this.isShow = false

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
                            console.log(res.data)
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
    },
    created() {
        // console.log('tag', '')
        this.getList()
    },
    computed: {
      numberOfPages () {
        return Math.ceil(this.items.length / this.itemsPerPage)
      },
      filteredKeys () {
        return this.keys.filter(key => key !== 'Name')
      },
    },

  }
</script>