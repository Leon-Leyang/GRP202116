<template>

<v-container>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        
        <v-btn
          color="yellow"
          v-bind="attrs"
          v-on="on"
          width="72px"
        >
          TEST
        </v-btn>
        
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
  export default {
    data () {
      return {
        dialog: false,
      }
    },
    
  }
 
</script>