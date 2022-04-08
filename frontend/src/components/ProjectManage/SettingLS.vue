<!--This is the setting labeling interface component
We provide 5 image template and 4 text template for user to use
@author LinjingSUN YingjiaLI-->
<template>
<div>
    <v-row
    >
        <v-card
        width="1250px"
        height="650px"
        style="margin-top:30px; margin-left:10px;"
        elevation=0
        >
        <v-row justify="center">
            <v-tabs vertical style="width:100%">
                <v-tab>
                <v-icon left>
                    mdi-image-area
                </v-icon>
                For Image
                </v-tab>
            
                <v-tab>
                <v-icon left>
                    mdi-card-text
                </v-icon>
                For Text
                </v-tab>

                
            <!--1st-->
                <v-tab-item class="tabitem"> <!--删掉tabitem,card-->
                <v-container class="con">

                <v-row
                class="fill-height"
                align="center"
                justify="center"
            >
                <template v-for="(item, i) in imageTool">
                <v-col
                    :key="i"
                    cols="8"
                    md="4"
                >
                    <v-hover v-slot="{ hover }">
                    <v-card
                        :elevation="hover ? 12 : 2"
                        :class="{ 'on-hover': hover }"
                        @click="enterConfig(item.config,'image',item.title)"
                    >
                        <v-img
                        :src="item.img"
                        
                        width="300px"
                        >
                        <v-card-title class="text-h6 white--text" style="margin-left:4px!important">
                            <v-row
                            class="fill-height flex-column"
                            justify="space-between"
                            >
                             <div style="white-space:pre">
                            <p class="mt-4 subheading text-left">
                                {{ item.title }}
                            </p>
                            </div> 
                            <!-- 
                            <div><p></p><p></p></div>
                            <div><p></p></div>
                            -->
                            
                            <div> <!--class="align-self-center"-->
                                <v-btn
                                v-for="(icon, index) in icons"
                                :key="index"
                                :class="{ 'show-btns-1': hover }"
                                :color="transparent"
                                x-large
                                icon
                                >
                                
                                <v-icon
                                    :class="{ 'show-btns-1': hover }"
                                    :color="transparent"
                                    
                                ><!-- :size="large" not work-->
                                    {{ icon }}
                                </v-icon>
                                
                                
                                </v-btn>
                            </div>
                            </v-row>
                        </v-card-title>
                        </v-img>
                    </v-card>
                    </v-hover>
                </v-col>
                </template>
            </v-row>

            </v-container>
                
                </v-tab-item>
            


            <!--2nd-->
                <v-tab-item class="tabitem"> 
                <!--删掉card-->
                    
                    <v-container class="con">

                    <v-row
                        class="fill-height"
                        align="center"
                        justify="center"
                    >
                        <template v-for="(item, i) in textTool">
                        <v-col
                            :key="i"
                            cols="12"
                            md="5"
                        >
                            <v-hover v-slot="{ hover }">
                            <v-card
                                :elevation="hover ? 12 : 2"
                                :class="{ 'on-hover': hover }"
                                @click="enterConfig(item.config,'text')"
                            >
                                <v-img
                                :src="item.img"
                                height="225px"
                                
                                >
                                <v-card-title class="text-h6 black--text">
                                    <v-row
                                    class="fill-height flex-column"
                                    justify="space-between"
                                    >
                                    <p class="mt-4 subheading text-left">
                                        {{ item.title }}
                                    </p>
                    <div> <!--class="align-self-center"-->
                                        <v-btn
                                        v-for="(icon, index) in icons"
                                        :key="index"
                                        :class="{ 'show-btns-2': hover }"
                                        :color="transparent"
                                        icon
                                        x-large
                                        >
                                        
                                        <v-icon
                                            :class="{ 'show-btns-2': hover }"
                                            :color="transparent" 
                                        >{{ icon }}
                                        </v-icon>
                                        
                                        
                                        </v-btn>
                                    </div>
                                    </v-row>
                                </v-card-title>
                                </v-img>
                            </v-card>
                            </v-hover>
                        </v-col>
                        </template>
                    </v-row>

            </v-container>
                
                </v-tab-item>
            </v-tabs>


            <v-overlay
            :absolute="absolute"
            opacity= 1
            :value="overlay"
            color="white"
            style="height:600px"
            dark=false
            >
            <v-btn
                @click="overlay = false"
            >
                <v-icon>
                    mdi-arrow-left
                </v-icon>
            </v-btn>
            <Config style="width:1250px;padding:0;height:540px"></Config>
            </v-overlay>
        </v-row>
        </v-card>
    </v-row>
</div>

</template>

<script>
import Config from '../ProjectManage/Config.vue'
export default {
    components:{
        Config
    },
    data() {
        return {
            absolute: true,
            opacity: 1,
            overlay: false,
            // Create Project
            file:'',
            icons: ['mdi-arrow-right'],
            imageTool: [
                {
                title: 'Semantic Segmentation'+"\n"+' with Polygons',
                img: './images/1.png',
                config:`<View>
    <Image name="img" value="$image" showMousePos="true" zoom="true"></Image>
    <PolygonLabels name="tag" toName="img" strokewidth="5" fillcolor="red" pointstyle="circle" pointsize="small">
        <Label value="Hello" background="red"></Label>
        <Label value="World" background="blue"></Label>  
    </PolygonLabels>
</View>`
                },
                {
                title: 'Semantic Segmentation'+"\n"+' with Masks',
                img: './images/2.png',
                config:`<View>
    <Image name="image" value="$image" zoom="true"/>
    <BrushLabels name="tag" toName="image">
            <Label value="Airplane" background="rgba(255, 0, 0, 0.7)"/>
            <Label value="Car" background="rgba(0, 0, 255, 0.7)"/>
    </BrushLabels>
</View>`
                },
                {
                title: 'Object Detection'+"\n"+' with Bounding Boxes',
                img: './images/3.png',
                config:`<View>
    <Image name="img" value="$image"></Image>
    <RectangleLabels name="tag" toName="img" fillOpacity="0.5" strokeWidth="5">
            <Label value="Planet"></Label>
            <Label value="Moonwalker" background="blue"></Label>
    </RectangleLabels>
</View>`
                },
                {
                title: 'Keypoint Labeling',
                img: './images/4.png',
                config:`<View>
    <Image name="img" value="$image" zoom="true"></Image>
    <KeyPointLabels name="tag" toName="img" fillcolor="red">
        <Label value="Hello" background="yellow"></Label>
        <Label value="World" background="blue"></Label>
    </KeyPointLabels>
</View>`
                },
                {
                title: 'Image Classification',
                img: './images/5.png',
                config:` <View>
    <Image name="image" value="$image"/>
    <Choices name="choice" toName="image">
        <Choice value="Adult content"/>
        <Choice value="Weapons" />
    <Choice value="Violence" />
    </Choices>
</View>`
                }
            ],

            textTool: [
                {
                title: 'Named Entity Recognition',
                img: './images/a.png',
                config:`<View>
    <Labels name="label" toName="text">
        <Label value="PER" background="red"/>
        <Label value="ORG" background="darkorange"/>
        <Label value="LOC" background="orange"/>
        <Label value="MISC" background="green"/>
    </Labels>
    <Text name="text" value="$text"/>
</View>`
                },
                {
                title: 'Machine Translation',
                img: './images/b.png',
                config:`<View>
    <Header value="Please read the text" />
    <Text name="text" value="$text" />
    <Header value="Provide one sentence summary" />
    <TextArea name="answer" toName="text" showSubmitButton="true" maxSubmissions="1" editable="true" required="true" />
</View>`
                },
                {
                title: 'Text Classification',
                img: './images/c.png',
                config:`<View>
    <Text name="text" value="$text"/>
    <View style="box-shadow: 2px 2px 5px #999; padding: 20px; margin-top: 2em; border-radius: 5px;">
    <Header value="Choose text sentiment"/>
    <Choices name="sentiment" toName="text" choice="single" showInLine="true">
        <Choice value="Positive"/>
        <Choice value="Negative"/>
        <Choice value="Neutral"/>
    </Choices>
    </View>
</View>`
                },
                {
                title: 'Text Taxonomy',
                img: './images/d.png',
                config:`<View>
    <Text name="text" value="$text"/>
    <Taxonomy name="taxonomy" toName="text">
        <Choice value="Archaea" />
        <Choice value="Bacteria" />
        <Choice value="Eukarya">
            <Choice value="Human" />
            <Choice value="Oppossum" />
            <Choice value="Extraterrestial" />
        </Choice>
    </Taxonomy>
</View>`
                },
                /*
                {
                title: 'Qustion Answering',
                img: '/images/e.jpg',
                config:`<View>
                            <Header value="Please read the passage" />
                            <Text name="text" value="$text" granularity="word"/>
                            <Header value="Select a text span answering the following question:"/>
                            <Text name="question" value="$question"/>
                            <Labels name="answer" toName="text">
                                <Label value="Answer" maxUsage="1" background="red"/>
                            </Labels>
                        </View>`
                },
                */
            ],
            transparent: 'rgba(255, 255, 255, 0)',
            active:'1',
        }
    },
    methods: {
        enterConfig(config,type,title){
            this.$store.state.currentConfig = config
            this.$store.state.currentConfigTitle = title
            console.log('config', this.$store.state.currentConfig)
            this.overlay = !this.overlay
            this.$store.state.dataType = type
        }
    },
    mounted() {
        this.$store.state.pageLocate = 'NotData'
    },

}
</script>