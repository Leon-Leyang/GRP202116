<template>
<div>
<el-table
  :data="tableData"
  style="width: 100%"
  :default-sort = "{prop: 'id', order: 'ascending'}" 
  v-cloak
  stripe>
  <el-table-column
    type="selection"
    width="50">
  </el-table-column>


  <el-table-column show-overflow-tooltip sortable label="ID" width="100" prop="id" align="center">
  </el-table-column> 
  <el-table-column show-overflow-tooltip sortable label="Last Modification Time" width="200" prop="modi" align="center">
  </el-table-column> 
  <el-table-column show-overflow-tooltip sortable label="Import Time" width="200" prop="crea" align="center">
  </el-table-column>  
  <el-table-column show-overflow-tooltip label="Preview" width="200" prop="pre" align="center">
    <template slot-scope="scope">
                <el-popover placement="right">
                  <img :src="scope.row.pre" />
                  <img
                    slot="reference"
                    :src="scope.row.pre"
                    :alt="scope.row.pre"
                    style="max-height: 50px; max-width: 130px"
                  />
                </el-popover>
      </template>
  </el-table-column> 
  <el-table-column show-overflow-tooltip label="Status" width="100" prop="sta" align="center">
    
  </el-table-column> 
  <el-table-column label="Annotation" width="100" prop="anno" align="center"></el-table-column> 
  <el-table-column label="Prediction" width="100" prop="predi" align="center"></el-table-column>       
</el-table>
<el-pagination class="pager" layout="prev, pager, next"  :total="10" :current-page.sync="config.page" @current-change="changePage" :page-size="20"></el-pagination>

</div>
</template>

<script>
  export default {
    name: 'DataTable',
    data(){
      return{ //
        tableData:[{id:'1',modi:'2021/11/06',crea:'2021/11/04',pre:'/images/cars1.jpeg',sta:'Done',anno:'3',predi:'0'},
        {id:'2',modi:'2022/01/04',crea:'2022/01/04',pre:"/images/cars.jpeg",sta:'Done',anno:'4',predi:'0'},
        {id:'5',modi:'2022/01/17',crea:'2022/01/06',pre:'/images/dogs.jpeg',sta:'Done',anno:'3',predi:'0'},
        {id:'6',modi:'2022/01/10',crea:'2022/01/10',pre:'/images/trees.jpeg',sta:'Queue',anno:'0',predi:'0'},
        {id:'4',modi:'2022/01/06',crea:'2022/01/06',pre:'/images/shirts.jpeg',sta:'Done',anno:'2',predi:'0'},
        {id:'3',modi:'2022/01/01',crea:'2022/01/04',pre:'/images/houses.jpeg',sta:'Queue',anno:'0',predi:'0'}]
      }
    },
    props: {
      //tableData: Array,
      config: Object
    },
    methods: {

      }

  }
</script>

<style>

</style>