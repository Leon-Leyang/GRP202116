 const state = {
  
    // nowInfo : {
    //   nowPro : "1", // Now project, default is 1
    //   nowData : "1", // Now 'data', default is 1
    // },
  
    dataList : [
      {
        id : "1",
        list : [
          {id : "1"},
          {id : "2"}
        ]
      },
      {
        id : "2",
        list : [
          {id : "3"},
          {id : "4"}
        ]
      }
  
    ],
    projectList : [
        {
            project_id:"1",
            name:"Tree",
            progress:"20",
            create_time:"2016-05-03",
            update_time:"2016-05-03",
            description:"This is tree",
        },
        {
            project_id:"2",
            name:"Dog",
            progress:"30",
            create_time:"2016-11-03",
            update_time:"2016-12-05",
            description:"This is dog",
        },
        {
            project_id:"3",
            name:"Cat",
            progress:"50",
            create_time:"2016-05-09",
            update_time:"2016-06-03",
            description:"This is cat",
        },
        {
            project_id:"4",
            name:"Sheep",
            progress:"100",
            create_time:"2016-05-13",
            update_time:"2016-05-18",
            description:"This is sheep",
        }
    ],
    // FolderList : [

    // ]
  }
  
  export default state
  