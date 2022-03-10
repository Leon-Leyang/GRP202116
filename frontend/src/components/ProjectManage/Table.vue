<template>
<div>
  <div>
  <v-container fluid>
    <v-data-iterator
      :items="items"
      :items-per-page.sync="itemsPerPage"
      :page.sync="page"
      :search="search"
      :sort-by="sortBy.toLowerCase()"
      :sort-desc="sortDesc"
      hide-default-footer
    >
      <template v-slot:header>
        <v-toolbar
          dark
          color="blue darken-3"
          class="mb-1"
        >
          <v-text-field
            v-model="search"
            clearable
            flat
            solo-inverted
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Search"
          ></v-text-field>
          <template v-if="$vuetify.breakpoint.mdAndUp">
            <v-spacer></v-spacer>
            <v-select
              v-model="sortBy"
              flat
              solo-inverted
              hide-details
              :items="keys"
              prepend-inner-icon="mdi-magnify"
              label="Sort by"
            ></v-select>
            <v-spacer></v-spacer>
            <v-btn-toggle
              v-model="sortDesc"
              mandatory
            >
              <v-btn
                large
                depressed
                color="blue"
                :value="false"
              >
                <v-icon>mdi-arrow-up</v-icon>
              </v-btn>
              <v-btn
                large
                depressed
                color="blue"
                :value="true"
              >
                <v-icon>mdi-arrow-down</v-icon>
              </v-btn>
            </v-btn-toggle>
          </template>
        </v-toolbar>
      </template>

      <template v-slot:default="props">
    <v-container style="width: 400px">
      <v-row dense>
        <v-col         
          v-for="(item, i) in items"
          :key="i"
          cols="3">
          <v-card
            color="#FFFFFF"
          >
            <v-card-title class="text-h5" >
            </v-card-title>

            <v-card-subtitle>Listen to your favorite artists and albums whenever and wherever, online and offline.</v-card-subtitle>

            <v-card-actions>
              <v-btn text>
                Listen Now
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
        <v-row>
          <v-col
            v-for="item in props.items"
            :key="item.name"
            cols="12"
            sm="6"
            md="4"
            lg="3"
          >
            <v-card>
              <v-card-title class="text-h5">
                {{ item.name }}
              </v-card-title>
              <v-card-subtitle>
                {{item.description}}
              </v-card-subtitle>

              <v-card-actions>
                <v-btn
                  color="orange lighten-2"
                  text
                >
                 <v-icon>{{ icons.mdiPencil }}</v-icon>
                </v-btn>

                <v-spacer></v-spacer>

                <v-btn
                  color="orange lighten-2"
                  text
                >
                 <v-icon>{{ icons.mdiDelete }}</v-icon>
                </v-btn>

                <v-spacer></v-spacer>

                <v-btn
                  icon
                  @click="show = !show"
                >
                  <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                </v-btn>
              </v-card-actions>

              <v-expand-transition>
                <div v-show="show">
                  <v-divider></v-divider>

                  <v-card-text>
                    Create Time: {{item.createTime}}
                  </v-card-text>
                  <v-card-text>
                    Update Time: {{item.updateTime}}
                  </v-card-text>                  
                </div>
              </v-expand-transition>              

              <v-divider></v-divider>

              <v-list dense>
                <v-list-item
                  v-for="(key, index) in filteredKeys"
                  :key="index"
                >
                  <v-list-item-content :class="{ 'blue--text': sortBy === key }">
                    {{ key }}:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === key }"
                  >
                    {{ item[key.toLowerCase()] }}
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card>
          </v-col>
        </v-row>
      </template>

      <template v-slot:footer>
        <v-row
          class="mt-2"
          align="center"
          justify="center"
        >
          <span class="grey--text">Items per page</span>
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                dark
                text
                color="primary"
                class="ml-2"
                v-bind="attrs"
                v-on="on"
              >
                {{ itemsPerPage }}
                <v-icon>mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item
                v-for="(number, index) in itemsPerPageArray"
                :key="index"
                @click="updateItemsPerPage(number)"
              >
                <v-list-item-title>{{ number }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-spacer></v-spacer>

          <span
            class="mr-4
            grey--text"
          >
            Page {{ page }} of {{ numberOfPages }}
          </span>
          <v-btn
            fab
            dark
            color="blue darken-3"
            class="mr-1"
            @click="formerPage"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="blue darken-3"
            class="ml-1"
            @click="nextPage"
          >
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </template>
    </v-data-iterator>
  </v-container>

</div>
<div class="Ttable">
<div style="width:1138px">
<el-table
  :data="tableData"
  style="width: 100%;"
  :default-sort = "{prop: 'createTime', order: 'descending'}"
  v-cloak
  stripe>
  <el-table-column
    type="selection"
    width="55">
  </el-table-column>
  <el-table-column show-overflow-tooltip label="Project Name" width="205" prop="name">
  </el-table-column> 
<!-- 因为暂时后端没有包含这个变量 到时候要加上!!! -->
  <el-table-column show-overflow-tooltip label="Process" width="205" prop="process">
    <template slot-scope="scope">
      <el-progress :text-inside="true" :stroke-width="20" :percentage="scope.row.process" :color="customColorMethod"></el-progress>
    </template>
  </el-table-column> 

  <el-table-column show-overflow-tooltip sortable label="Last Modification Time" width="205" prop="updateTime" align="center">
  </el-table-column>  
  <el-table-column show-overflow-tooltip sortable label="Creation Time" width="205" prop="createTime" align="center">
  </el-table-column> 
  <el-table-column
    label="More"
    align="center">
      <template slot-scope="scope">
      <el-button size="min" @click="handleEdit(scope.row)">Edit</el-button>
      <el-button size="min" type="danger" @click="handleDelete(scope.row)">Delete</el-button>
      </template>
  </el-table-column>        
</el-table>
<div style="text-align: center; height: 64px; margin:0 auto;line-height:64px; border: solid  #EFEEF4; border-width: 0 0px 1px 0px ">
  <el-button @click="handleAdd()" icon="el-icon-plus" circle></el-button>
</div>
</div>
<el-pagination class="pager" 
                layout="prev, pager, next, jumper"
                :hide-on-single-page="value"
                :total="config.total" 
                :current-page.sync="config.page" 
                @current-change="changePage" 
                :page-size="20"
                style="margin-top: auto;">
</el-pagination>

</div>
</div>
</template>

<script>
  export default {
    name: 'Table',
    props: {
      tableData: Array,
      config: Object,
    },
    data() {
      return {
        value: false,
        process: Math.random() * 101
      }
    },
    methods: {
      handleEdit(row) {
          this.$emit('edit', row)
      },
      handleDelete(row) {
          this.$emit('del', row)
      },
      changePage(page) {
          this.$emit('changePage', page)
      },
      customColorMethod(percentage) {
        if (percentage < 100) {
          return '#FFE7AD';
        } else {
          return '#7EDF96';
        }
      },
      handleAdd() {
        console.log(this.operateForm)
        this.$emit('add')
        },

      },

  }
</script>
<style>
  .Ttable{
    height: 700px;
    /* position: relative; */
    display: flex;
    flex-direction: column;
    border-width: 1px 1px 1px 1px;
    border-style: solid;
    border-color: #EFEEF4;
    color: #EFEEF4;
    border-radius: 16px 16px 16px 16px;
    overflow:hidden;
    text-align: center;
  }
</style>