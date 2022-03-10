<template>
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
        <v-card-title>    
          <v-btn>
            Create
          </v-btn>
        <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            clearable
            flat
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Search"
            style="width: 50px; margin-right: 20px"
          ></v-text-field>
          <template v-if="$vuetify.breakpoint.mdAndUp">
            <v-select
              v-model="sortBy"
              flat
              hide-details
              :items="keys"
              prepend-inner-icon="mdi-magnify"
              label="Sort by"
              style="width: 20px; margin-right: 20px"
            ></v-select>
            <v-btn-toggle
              v-model="sortDesc"
              mandatory
            >
              <v-btn
                large
                depressed
                :value="false"
              >
                <v-icon>mdi-arrow-up</v-icon>
              </v-btn>
              <v-btn
                large
                depressed
                :value="true"
              >
                <v-icon>mdi-arrow-down</v-icon>
              </v-btn>
            </v-btn-toggle>
          </template>
        </v-card-title>
      </template>

      <template v-slot:default="props">
        <v-row dense>
          <v-col
            v-for="item in props.items"
            :key="item.name"
            cols="12"
            md="4"
          >
            <v-card>
                <v-card-title class="text-h5">
                    {{ item.name }}
                </v-card-title>

                <v-card-subtitle>{{item.description}}</v-card-subtitle>
              
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
                      Created Time: {{item.createTime}}
                    </v-card-subtitle>
                  </v-row>
                  <v-row>
                    <v-card-subtitle style="padding:0px; font-size:10px; height: 15px; color: orange">
                      Last Update Time: {{item.updateTime}}
                    </v-card-subtitle>
                  </v-row>
                </div>

                <v-spacer></v-spacer>
                <v-btn icon>
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>

                <v-btn icon>
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
                </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </template>

      <template v-slot:footer>
        <v-row
          class="mt-2"
          align="center"
          justify="center"
          style="position:absolute; bottom:15px; right:25px"
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
</template>

<script>
import Mock from 'mockjs'
  export default {
    data () {
      return {
        itemsPerPageArray: [6, 8, 12],
        search: '',
        show: false,
        filter: {},
        sortDesc: false,
        page: 1,
        itemsPerPage: 8,
        sortBy: 'name',
        keys: [
          'Name',
          'Process',
          'UpdateTime',
          'CreateTime',
        ],
        items: [
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
          {
            name: Mock.Random.title(1),
            description: Mock.Random.sentence(3, 5),
            process: Mock.Random.integer(0, 100),
            updateTime: Mock.Random.date(),
            createTime: Mock.Random.date(),
          },
        
        ],
      }
    },
    computed: {
      numberOfPages () {
        return Math.ceil(this.items.length / this.itemsPerPage)
      },
      filteredKeys () {
        return this.keys.filter(key => key !== 'Name')
      },
    },
    methods: {
      nextPage () {
        if (this.page + 1 <= this.numberOfPages) this.page += 1
      },
      formerPage () {
        if (this.page - 1 >= 1) this.page -= 1
      },
      updateItemsPerPage (number) {
        this.itemsPerPage = number
      },
    },
  }
</script>