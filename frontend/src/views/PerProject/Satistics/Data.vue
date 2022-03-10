<template>
    <div>
        <el-row>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple title">Data completed</div>
            <div class="pagetext">12/3</div>
        </el-col>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple-light title">Avrage words per piece of data </div>
            <div class="pagetext">1130</div>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12" class="inblock">
            <div class="grid-content bg-purple title">Percentage completed</div>
            <div class="pagetext"><el-progress type="circle" :percentage="25"></el-progress></div>

        </el-col>
        <el-col :span="12"><div class="grid-content bg-purple-light"></div></el-col>
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