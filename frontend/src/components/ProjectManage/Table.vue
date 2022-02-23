<template>
<div>

<el-table
  :data="tableData"
  style="width: 100%"
  :default-sort = "{prop: 'createTime', order: 'descending'}"
  v-cloak
  stripe>
  <el-table-column
    type="selection"
    width="55">
  </el-table-column>
  <el-table-column show-overflow-tooltip label="Project Name" width="205" prop="name">
  </el-table-column> 
<!-- 因为暂时后端没有包含这个变量 到时候要加上!!! -->
  <!-- <el-table-column show-overflow-tooltip label="Process" width="205" prop="propro">
    <template slot-scope="scope">
      <el-progress :text-inside="true" :stroke-width="20" :percentage="scope.row.propro" :color="customColorMethod"></el-progress>
    </template>
  </el-table-column>  -->

  <el-table-column show-overflow-tooltip sortable label="Last Modification Time" width="205" prop="updateTime" align="center">
  </el-table-column>  
  <el-table-column show-overflow-tooltip sortable label="Creation Time" width="205" prop="createTime" align="center">
  </el-table-column> 
  <el-table-column
    label="More"
    align="center">
      <template slot-scope="scope">
      <el-button size="min" @click="handleEdit(scope.row)">Edit</el-button>
      <el-button size="min" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
      </template>
  </el-table-column>        
</el-table>
<el-pagination class="pager" layout="prev, pager, next" :total="config.total" :current-page.sync="config.page" @current-change="changePage" :page-size="20"></el-pagination>

</div>
</template>

<script>
  export default {
    name: 'Table',
    props: {
      tableData: Array,
      config: Object
    },
    methods: {
      handleEdit(row) {
          this.$emit('edit', row)
      },
      handleDelete(row) {
          this.$emit('del', row)
      },
      changePage(page) {
          this.$emit('changePage', page)
      },
      customColorMethod(percentage) {
        if (percentage < 100) {
          return '#FFE7AD';
        } else {
          return '#7EDF96';
        }
      },
      }

  }
</script>

<style>
.el-tab-pane{
    height: 380px;
    
}
</style>