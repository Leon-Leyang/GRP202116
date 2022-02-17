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
import axios from 'axios';
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
              proname: '',
              desc: '',
              propro: '',
              modi: '',
              createt: '',
              data: '',
              ml: '',
          },
      }
    },
    methods: {
        getList(name = '') {
            this.config.loading = true
            name ? (this.config.page = 1) : ''
            axios.get('/api/project/getProject', {
                    params: {
                        page: this.config.page,
                        name
                    }
                })
                .then(res => {
                    console.log("1")
                    this.tableData = res.data.list.map(item => {
                        return item
                    })
                    this.config.total = res.data.count
                    this.config.loading = false
                    console.log("2" + res.data.count)
                })
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
        },
        confirm() {
            if (this.operateType === 'edit') {
                console.log(this.operateForm)
                axios.post('/api/project/edit', this.operateForm).then(res => {
                    console.log(res.data)
                    this.isShow = false
                    this.getList()
                })
            } else {
                axios.post('/api/project/add', this.operateForm).then(res => {
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
                    let id = row.id
                    axios.get('/api/project/del', {
                            params: {
                                id
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