<template>
    <div>
        <el-row>
        <el-col :span="12">
            <div class="grid-content bg-purple">Total annotations:</div>
            <div>123</div>
        </el-col>
        <el-col :span="12">
            <div class="grid-content bg-purple-light">Average annotations per data</div>
            <div>adf</div>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12">
            <div class="grid-content bg-purple">Annotations Percentage:</div>
            <div><el-progress type="circle" :percentage="25"></el-progress></div>

        </el-col>
        <el-col :span="12">
            <div class="grid-content bg-purple-light">Annotation location:</div>
            <div></div>    
        </el-col>
        </el-row>        
    </div>
</template>