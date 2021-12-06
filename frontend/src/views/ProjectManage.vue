<template>
    <div>
        <div class="choose-btns">
            <div class="fliter">

            </div>
            <div class="sort">

            </div>
        </div>
        <div class="page-block">
            <v-data-table
                :headers="headers"
                :items="projects"
                sort-by="calories"
                class="elevation-1"
            >
                <template v-slot:top>
                <v-toolbar
                    flat
                >
                    <v-toolbar-title>
                        projects
                    </v-toolbar-title>
                    <v-spacer></v-spacer>
                        <v-text-field
                            v-model="search"
                            append-icon="mdi-magnify"
                            label="Search"
                            single-line
                            hide-details
                        ></v-text-field>
                    <v-divider
                    class="mx-4"
                    inset
                    vertical
                    style="margin-right:10%"
                    ></v-divider>
                    <v-dialog
                    v-model="dialog"
                    max-width="500px"
                    >
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                        color="primary"
                        dark
                        class="mb-2"
                        v-bind="attrs"
                        v-on="on"
                        style="float: left"
                        >
                        New Item
                        </v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                        <span class="text-h5">{{ formTitle }}</span>
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
                                v-model="editedItem.name"
                                label="project Name"
                                ></v-text-field>
                            </v-col>
                            </v-row>
                            <v-row>
                            <v-col
                                cols="12"
                                sm="6"
                                md="4"
                            >
                                <v-text-field
                                v-model="editedItem.calories"
                                label="Description(Optional)"
                                ></v-text-field>
                            </v-col>
                            </v-row>
                        </v-container>
                        </v-card-text>

                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                            color="blue darken-1"
                            text
                            @click="close"
                        >
                            Cancel
                        </v-btn>
                        <v-btn
                            color="blue darken-1"
                            text
                            @click="save"
                        >
                            Save
                        </v-btn>
                        </v-card-actions>
                    </v-card>
                    </v-dialog>
                    <v-dialog v-model="dialogDelete" max-width="500px">
                    <v-card>
                        <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
                        <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
                        <v-spacer></v-spacer>
                        </v-card-actions>
                    </v-card>
                    </v-dialog>
                </v-toolbar>
                </template>
                <template v-slot:item.actions="{ item }">
                <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
                >
                    mdi-pencil
                </v-icon>
                <v-icon
                    small
                    @click="deleteItem(item)"
                >
                    mdi-delete
                </v-icon>
                </template>
                <template v-slot:no-data>
                <v-btn
                    color="primary"
                    @click="show"
                >
                    Reset
                </v-btn>
                </template>
            </v-data-table>
        </div>
    </div>
</template>
<script>
export default {
    name:'projectManage',
    data () {
      return {
        search: '',
        headers: [
          {
            text: 'project Name',
            align: 'start',
            filterable: false,
            value: 'name',
          },
          { text: 'Process', value: 'process' },
          { text: 'Last Modification Time', value: 'modificationtime' },
          { text: 'Creation Time', value: 'creationtime' },
          { text: 'More', value: 'more' },
        ],
        projects: [
          {
            name: 'Frozen Yogurt',
            process: '98%',
            modificationtime: '2021-11-23 22:40:11',
            creationtime: '2021-11-23 22:40:11',
            more: '...',
          },
        ],
      }
    },
}
</script>
<script>
  export default {
    data: () => ({
      dialog: false,
      dialogDelete: false,
      search: '',
      headers: [
            {
            text: 'Project Name',
            align: 'start',
            filterable: false,
            value: 'name',
            },
            { text: 'Process', value: 'process' },
            { text: 'Last Modification Time', value: 'modificationtime' },
            { text: 'Creation Time', value: 'creationtime' },
            { text: 'More', value: 'more' },
      ],
      project: [],
      editedIndex: -1,
      editedItem: {
        name: '',
        description: '',
      },
      defaultItem: {
        name: '',
        description: "",
      },
    }),

    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
    },

    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },

    created () {
      this.show();
    },

    methods: {
      show(){
      const that = this;
      this.$axios.get('http://localhost:8081/projects/')
        .then(function (response) {
          console.log(response);
          that.projects = response.data;
        })
      },
      editItem (item) {
        this.editedIndex = this.projects.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        this.editedIndex = this.projects.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialogDelete = true
      },

      deleteItemConfirm () {
        this.projects.splice(this.editedIndex, 1)
        this.closeDelete()
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
            console.log(this.projects);
            this.$axios.post("http://localhost:8081/projects",this.project).then(res=>{
                this.user={}; 
                alert('Add successfully');
                this.listAll();
            }).catch(err=>{
                alert('Add failed')
            });
        },
    },
  }
</script>

<style>
.page-block{
    width: 80%;
    margin:0 auto;
    padding-top: 20px;
}
v-tab{
    width: 800px;
}
v-tabs{
    width: 900px;
}
.list-intro{
    display: flex;
    align-content: space-between;
}
</style>