<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
        >
          Train
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="text-h5">Train</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Train Proportion:"
                  hint="range from 0 to 1"
                  persistent-hint
                  required
                ></v-text-field>
              </v-col>
              
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Batch Size:"
                  hint="number"
                  persistent-hint
                  required
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Worker Number:"
                  hint="number"
                  persistent-hint
                  required
                ></v-text-field>
              </v-col>

            <v-col cols="12">
                 <v-switch
      v-model="switch1"
      :label="`Shuffle`"
    ></v-switch>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  label="Epoch Number:"
                  hint="number"
                  persistent-hint
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="Learning Rate:"
                  type="password"
                  hint="number"
                  persistent-hint
                  required
                 
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
              >
                <v-select
                  :items="['SGD', 'ASGD', 'Rprop', 'Adagrad','Adadelta','RMSprop','Adam','Adamax','SparseAdam','LBFGS']"
                  label="Optimizer"
                  required
                ></v-select>
              </v-col>
              <v-col
                cols="12"
                sm="6"
              >
                <v-autocomplete
                  :items="['CrossEntropyLoss', 'NLLLoss']"
                  label="Loss Function"
                  required
                ></v-autocomplete><!--multiple-->
              </v-col>

              <v-col cols="12">
                <v-text-field
                  label="Where to save the model:"
                  hint="local path"
                  persistent-hint
                  required
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-file-input
                label="Upload the Customized Train Script Here"
                outlined
                dense
                ></v-file-input>
              </v-col>
              
            </v-row>
          </v-container>
          <!--<small>*indicates required field</small>-->
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
</template>

<script>
  export default {
    data: () => ({
      dialog: false,
      switch1: false,
    }),
  }
</script>

<style>
.col{
    padding: 0!important;
}
</style>