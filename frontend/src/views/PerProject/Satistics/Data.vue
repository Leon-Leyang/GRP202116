<template>
    <div>
        <el-row>
        <el-col :span="12">
            <div class="grid-content bg-purple">Data completed</div>
            <div>123</div>
        </el-col>
        <el-col :span="12">
            <div class="grid-content bg-purple-light">Avrage words per piece of data </div>
            <div>adf</div>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="12">
            <div class="grid-content bg-purple">Percentage completed</div>
            <div><el-progress type="circle" :percentage="25"></el-progress></div>

        </el-col>
        <el-col :span="12"><div class="grid-content bg-purple-light"></div></el-col>
        </el-row>        
    </div>
</template>