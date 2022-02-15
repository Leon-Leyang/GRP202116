<template>
    <div>
        <el-row>
        <el-col :span="12">
            <div class="grid-content bg-purple">Total predictions:</div>
            <div>123</div>
        </el-col>
        <el-col :span="12">
            <div class="grid-content bg-purple-light">Average predictions per data</div>
            <div>adf</div>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12">
            <div class="grid-content bg-purple">Predictions Percentage:</div>
            <div><el-progress type="circle" :percentage="25"></el-progress></div>

        </el-col>
        <el-col :span="12">
            <div class="grid-content bg-purple-light">Predictions location:</div>
            <div></div>    
        </el-col>
        </el-row>        
    </div>
</template>