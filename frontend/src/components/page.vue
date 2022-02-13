<template>
<!--增删改查-->
  <div>
    <div style="font-size: 30px;color: salmon">表格案例</div>
    <!--按钮、表格-->
    <el-card class="box-card">
      <el-button type="success" size="small" @click="add">增加</el-button>
      <el-table :data="tableData" stripe>
        <el-table-column prop="date" label="日期" align="center">
        </el-table-column>
        <el-table-column prop="name" label="姓名" align="center">
        </el-table-column>
        <el-table-column prop="address" label="地址" align="center">
        </el-table-column>
        <el-table-column prop="age" label="年龄" align="center">
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="remove(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!--弹窗-->
    <el-dialog :title="dialogTitle" width="50%" :visible.sync="iconFormVisible">
      <el-form :inline="true" :model="userInfo" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="userInfo.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="日期">
          <el-input v-model="userInfo.date" placeholder="日期"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="userInfo.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="userInfo.age" placeholder="年龄"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="iconFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUser()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  </template>

<script>
export default {
  name: "test",
  data() {
      return {
        iconFormVisible: false,
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
          age: '12',
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
          age: '45',
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄',
          age: '33',
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄',
          age: '7',
        }],
      };
  },
  created() {
  },
  methods: {

    // 增加
    add() {
      this.dialogTitle = '增加';
      this.userInfo = {};
      this.iconFormVisible = true;
    },
    // 编辑
    handleEdit(index, row) {
      this.dialogTitle = '编辑';
      this.userInfo = row;
      this.iconFormVisible = true;
      this.rowIndex = index;
    },
    // 弹窗确定
    submitUser() {
      if (this.dialogTitle === '编辑') {
        this.tableData.splice(this.rowIndex, 1, this.userInfo);
        this.iconFormVisible = false;
        return;
      }
      this.tableData.splice(0, 0, this.userInfo);
      this.iconFormVisible = false;
    },
    // 删除
    remove(index, row) {
      this.$confirm(`确定删除${row.name}吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error',
      }).then(() => {
        this.tableData.splice(index, 1);
      });
    },
  },
  computed: {

  },
};
</script>