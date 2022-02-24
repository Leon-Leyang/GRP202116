<template>
  <div class="page-block">
      <el-dialog :title="operateType === 'add' ? 'New' : 'Update'" :visible.sync="isShow">
          <Create :formLabel="operateFormLabel" :form="operateForm" ref="form" style="width: 100%" />
          <div slot="footer" class="dialog-footer">
              <el-button @click="isShow = false">Cancel</el-button>
              <el-button type="primary" @click="confirm">Finish</el-button>
          </div>
      </el-dialog>
      <div class="manage-header">
          <el-button type="primary" @click="addProject">Create Project</el-button>
      </div>
      <Table :tableData="tableData" :tableLabel="tableLabel" :config="config" @changePage="getList()" @edit="editProject" @del="delProject" />
  </div>

</template>

<script>
import Table from '../components/ProjectManage/Table.vue';
import Create from '../components/ProjectManage/Create.vue';
// import axios from 'axios';
  export default {
    components: {
      Table,
      Create,
    },

    data() {
      return {
        listQuery: {
        page: 1,
        limit: 10
      },
        operateType: 'add',
        isShow: false,
        tableData: [],
          config: {
              page: 1,
              total: 30,
              loading: false
          },
          operateForm: {
              projectId: '',
              name: '',
              description: '',
              process: '',
              updateTime: '',
              createTime: '',
            //   data: '',
            //   ml: '',
          },
      }
    },
    methods: {
        getList(name = '') {
            this.config.loading = true
            name ? (this.config.page = 1) : ''
            this.$axios.get('/projects', {
                    params: {
                        page: this.config.page,
                        name
                    }
                })
                .then(res => {
                    console.log(res)
                    this.tableData = res.data.map(item => {
                        return item
                    })

                    this.config.total = res.data.length
                    this.config.loading = false
                    console.log(this.tableData)
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
                this.$axios.post('/projects/edit', this.operateForm)
                    .then(res => {
                    console.log(res.data)
                    this.isShow = false
                    this.getList()
                })
            } else {
                console.log(this.operateForm)
                this.$axios.post('/projects', this.operateForm).then(res => {
                    console.log(res.data)
                    this.isShow = false
                    this.getList()

                })
            }
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
                    this.$axios.delete('/projects/'+ projectId, {
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
        }
    },
    created() {
        // console.log('tag', '')
        this.getList()
    }
  }
</script>

<style>
.page-block{
    width: 80%;
    margin:0 auto;
    padding-top: 20px;
}
</style>