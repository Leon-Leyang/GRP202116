<template>
    <div>
        <el-upload
            action=""
            multiple
            :limit="3"
            :file-list="[{name: 'name', url: 'url'}]">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
    </div>
</template>
