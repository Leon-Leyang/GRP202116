<template>
    <div>
        <button @click="postAnno">post</button>
    <div id="label-studio"></div>
        
    </div>
</template>

<script>
import LabelStudio from "@heartexlabs/label-studio";
import "@heartexlabs/label-studio/build/static/css/main.css";
import axios from "axios";

export default {
    data() {
        return {
            labelStudio:{},
            dataId:0,
            LS:{},
            postData:{
                annotationId: 0,
                projectId:0,
                dataId:0,
                type:'',
                // result:'',
                createTime:'',
                updateTime:'',
                fromName:'',
                toName:'',
                value:''
            },
            list:[]
        }
    },
    methods: {
        postAnno(){
          this.$axios.put(`/annotation/data/`+ this.dataId, this.list)
                        .then((res) => {
                             console.log('res', res)
                        })
        }
    },
    mounted() {
        this.postData.projectId = this.$store.state.currentProjectId
        console.log('nij', this.postData.projectId)
        this.postData.dataId = this.$store.state.currentDataId
        this.postData.type = 'image classification'
        this.dataId = this.$store.state.currentDataId
        this.labelStudio = this.$store.state.nowLS.options
        console.log('new', this.labelStudio)
      this.LS = new LabelStudio("label-studio", {
        config: this.labelStudio.config,

        interfaces: this.labelStudio.interfaces,

        user: this.labelStudio.user,

        task: this.labelStudio.task,


        // onLabelStudioLoad: function (LS) {
        //   var c = LS.annotationStore.addAnnotation({
        //     userGenerate: true,
        //   });
        //   LS.annotationStore.selectAnnotation(c.id);
        // },

        onUpdateAnnotation: function (LS, annotation) {
          // retrive an annotation 
    console.log('annotatio', annotation)
    console.log('LSA', LS)

          axios.put('/annotation/data'+ LS.options.task.id, LS.options)
                        .then((res) => {
                             console.log('res', res)
                        })

    
        //   var myDate = new Date();
        //   myDate.toLocaleDateString();                    
        //   myDate.toLocaleString( ); 
        //   var annotationObject = annotation.serializeAnnotation()
        //   var previousAnnotations= LS.task.app.annotationStore.annotations.target
        
        //   var annotationlist = []
        //   for(var i = 0; i < annotationObject.length; i++){
        //     annotationlist[i]=new Object()
        //     annotationlist[i].annotation_id = annotationObject[i].id
        //     annotationlist[i].project_id = LS.task.app.project
        //     annotationlist[i].data_id = LS.task.id
        //     annotationlist[i].type = annotationObject[i].type
        //     annotationlist[i].result = annotationObject[i].value
        //     annotationlist[i].create_time = myDate.toLocaleString()
            
        //     for(annotation in previousAnnotations ){
        //       if(annotation.value.id==annotationObject[i].id){
        //           annotationlist[i].create_time = annotation.value.createdDate
        //       }
        //     }
            
        //     annotationlist[i].update_time = myDate.toLocaleString()
        //     annotationlist[i].from_name = annotationObject[i].from_name
        //     annotationlist[i].to_name = annotationObject[i].to_name          
        //   }

        
        //   console.log(annotationlist)
        //   console.log(annotation.serializeAnnotation())
          
          // console.log('this', LS.task)
          // console.log('tag', this.LabelStudio.task)
          
          
          // this.$axios.post('/annotations/data/1', annotationlist)

        }
      });
      this.postData.value = this.LS.options
      console.log('img', this.postData)
      this.list[0] = this.postData
      console.log('list', this.list)
    }
}

</script>