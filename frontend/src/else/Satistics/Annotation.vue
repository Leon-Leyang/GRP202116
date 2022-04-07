<!-- Statistic部分要合到一起, 可参考css的设置 -->
<template>
    <div>
        <el-row>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple title">Total annotations:</div>
            <div class="pagetext">123</div>
        </el-col>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple-light title">Average annotations per data</div>
            <div class="pagetext">12</div>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple title">Annotations Percentage:</div>
            <div class="pagetext"><el-progress type="circle" :percentage="25"></el-progress></div>

        </el-col>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple-light title">Annotation location:</div>
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
    height: 50px;
    font-size:2em;
    font-weight: 600;
    text-align: center;
    margin-top: 70px;
}
    
</style>