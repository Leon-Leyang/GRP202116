<template>

<v-container>
  <v-row ><!--justify="center"-->

    <v-card>    <v-data-iterator
      :items="items" 
    >
      

      <template v-slot:default="props">
        <v-row dense>
          <v-col
            v-for="item in props.items"
            :key="item.name"
            cols="12"
            md="4"
          >
            
                <v-card-subtitle>
                    {{ item.name }}
                </v-card-subtitle>

                <v-card-subtitle>{{item.description}}</v-card-subtitle>
               
                <v-progress-linear
                color="light-blue"
                height="7"
                rounded
                :value="item.process"
                style="width:270px; margin-left: 10px"
                ></v-progress-linear>  
               
               
                <v-progress-linear
                color="light-blue"
                height="7"
                rounded
                :value="item.process"
                style="width:270px; margin-left: 10px"
                ></v-progress-linear> 
                

                <v-card-actions>
                <div style="margin-left:16px">
                  <v-row>
                    <v-card-subtitle style="padding:0px; font-size:10px; height: 15px; color: orange">
                      {{item.createTime}}
                    </v-card-subtitle>
                  </v-row>
                  <v-row>
                    <v-card-subtitle style="padding:0px; font-size:10px; height: 15px; color: orange">
                       {{item.updateTime}}
                    </v-card-subtitle>
                  </v-row>
                </div>

                <v-spacer></v-spacer>
               
                <v-btn>Test</v-btn>
                <v-btn>Train</v-btn>
                
                
                </v-card-actions>
            
          </v-col>
       
        </v-row>
      </template>

    </v-data-iterator></v-card>


    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-card>
            <v-container>
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
        >
          TEST
        </v-btn>
        </v-container>
        </v-card>
      </template>


      <v-card>
        <v-card-title>
          <span class="text-h5">Test</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
             <!--
              <v-col cols="12">
                <v-text-field
                  label="Model Version*"
                  required
                ></v-text-field>
              </v-col>
             -->   
              <v-col cols="12">
                <v-file-input
                label="Upload the Customized Train Script Here"
                outlined
                dense
                ></v-file-input>
              </v-col>

            </v-row>
          </v-container>
          <!--
          <small>*indicates required field</small>-->
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <!--
          <v-btn
            color="blue darken-1"
            text
            @click="dialog = false"
          >
            Close
          </v-btn>
          -->
          <v-btn
            color="blue darken-1"
            text
            @click="dialog = false"
          >
            Run
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    

  </v-row>
  </v-container>
</template>

<script>
  import Mock from 'mockjs'
  export default {
    data () {
      return {
       
        
        keys: [
          'Name',
          'Process',
        ],
        items: [
          {
            name: "xxx",
            description: "xxx",
            process: Mock.Random.integer(0, 100),
           
          },
        ],
        dialog: false,
      }
    },
    
  }
 
</script>