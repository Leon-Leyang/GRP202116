<template>
    <div>
        <el-row>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple title">Total predictions:</div>
            <div class="pagetext">123</div>
        </el-col>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple-light title">Average predictions per data</div>
            <div class="pagetext">14</div>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple title">Predictions Percentage:</div>
            <div class="pagetext"><el-progress type="circle" :percentage="35"></el-progress></div>

        </el-col>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple-light title">Predictions location:</div>
            <div class="pagetext"></div>    
        </el-col>
        </el-row>        
    </div>
</template>
<style>
.inblock{
    height: 230px;
}
.title{
    font-style:italic;
    font-size:0.8em;
    text-decoration :underline;
    color: rgb(124,124,124);
}
.pagetext{
    height: 30px;
    font-size:2em;
    font-weight: 600;
    text-align: center;
    margin-top: 70px;
}
    
</style>