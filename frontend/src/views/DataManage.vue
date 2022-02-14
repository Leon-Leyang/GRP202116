<template>
    <div>
        <div class="page-block">
            <div class="block-top">
                <v-tabs grow>
                    <v-tab>Item One</v-tab>
                    <v-tab>Item Two</v-tab>
                    <v-tab>Item Three</v-tab>
                </v-tabs>
            </div>
            <div class="block-content">

            </div>
        </div>
    </div>
</template>
<script>
export default {
    name:'ProjectManage',
}
</script>

<style>
.page-block{
    width: 80%;
    margin:0 auto;
}
v-tab{
    width: 800px;
}
v-tabs{
    width: 900px;
}
</style>