import Mock from 'mockjs'

function param2Obj(url) {
  const search = url.split('?')[1]
  if (!search) {
    return {}
  }
  return JSON.parse(
    '{"' +
      decodeURIComponent(search)
        .replace(/"/g, '\\"')
        .replace(/&/g, '","')
        .replace(/=/g, '":"') +
      '"}'
  )
}

let List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(
    Mock.mock({
      id: Mock.Random.guid(),
      proname: Mock.Random.title(1),
      desc: Mock.Random.sentence(3, 5),
      propro: Mock.Random.integer(0, 100),
      modi: Mock.Random.date(),
      createt: Mock.Random.date(),
    })
  )
}

export default {

  getProjectList: config => {
    console.log('tag'+2)
    const { name, page = 1, limit = 7 } = param2Obj(config.url)
    console.log('name:' + name, 'page:' + page, 'limit:' + limit)
    const mockList = List.filter(project => {
      if (name && project.proname.indexOf(name) === -1) return false
      return true
    })
    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))
    return {
      code: 20000,
      // data: {
      //   total: mockList.length,
      //   list: pageList
      // }
      count: mockList.length,
      list: pageList
    }
  },

  createProject: config => {
    const { proname, desc} = JSON.parse(config.body)
    console.log(JSON.parse(config.body))
    List.unshift({
      id: Mock.Random.guid(),
      proname : proname,
      desc : desc,
      propro : 0,
      modi : Mock.Random.now('yyyy-MM-dd'),
      createt : Mock.Random.now('yyyy-MM-dd')
    })
    return {
      code: 20000,
      data: {
        message: 'Create sucess'
      }
    }
  },

  deleteProject: config => {
    const { id } = param2Obj(config.url)
    if (!id) {
      return {
        code: -999,
        message: 'error'
      }
    } else {
      List = List.filter(u => u.id !== id)
      return {
        code: 20000,
        message: 'Delete success'
      }
    }
  },

  // Batch delete
  batchremove: config => {
    let { ids } = param2Obj(config.url)
    console.log(ids);
    ids = ids.split(',')
    List = List.filter(u => !ids.includes(u.id))
    return {
      code: 20000,
      data: {
        message: 'Batch delete successful'
      }
    }
  },
  
  updateProject: config => {
    const { id, proname, desc, propro } = JSON.parse(config.body)
    // const sex_num = parseInt(sex)
    List.some(u => {
      if (u.id === id) {
        u.proname = proname
        u.desc = desc
        u.propro = propro
        u.modi = Mock.Random.now('yyyy-MM-dd')
        return true
      }
    })
    return {
      code: 20000,
      data: {
        message: 'edit sucess'
      }
    }
  }
}
