import Vue from 'vue';
import Vuex from 'vuex';
import axios from "axios";

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        currentProjectId: 0,
        currentDataList: [],
        currentDataId: 0,
        realDataId: 0,
        currentProject: [],
        currentConfig: ``,
        nowLS:[],
        prevLS:[],
        currentMLList:[],
        currentMLType:'',
    },
    getters: {
        getProjectId(state){
            return state.currentProjectId
        },
        getDataId(state){
            return state.currentDataId
        },
        getDataList(state){
            return state.currentDataList
        }
    },
    mutations: {
        changeProjectId(state, num){
            state.currentProjectId = num
        },

        changeDataList(state, projectId){
            axios.get('/data/project/'+ projectId)
                .then(res => {
                    console.log('res', res)
                    state.currentDataList = res.data
                    console.log('store datalist', state.currentDataList)
                })
                .catch((error) => {
                // here you will have access to error.response
                console.log(error.response)
                });
        },

        changeDataId(state, num){
            state.currentDataId = num
        }
    }
});

export default store;