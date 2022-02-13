import Mock from 'mockjs'
import projectApi from './project'

Mock.setup({
  timeout: '200-600'
})


Mock.mock(/\/project\/getProject/, 'get', projectApi.getProjectList)
Mock.mock(/\/project\/del/, 'get', projectApi.deleteProject)
Mock.mock(/\/project\/batchremove/, 'get', projectApi.batchremove)
Mock.mock(/\/project\/add/, 'post', projectApi.createProject)
Mock.mock(/\/project\/edit/, 'post', projectApi.updateProject)

