## Spring Boot + Thymeleaf 实现的任务发布网站

- 角色：
  - 管理员
  - 雇主
  - 雇员
- 功能
  - 雇主：登录、注册、发布任务、选择中标雇员、评价雇员
  - 雇员：登录、注册、查看任务列表、投标任务、收藏任务、完成任务
  - 管理员、登录、任务管理、雇主管理、雇员管理

- 部分功能截图

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-53-32.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-51-09.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-52-01.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-52-33.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-53-02.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-54-35.png)

  

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-55-41.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-56-25.png)

  ![](https://yuu-blog.oss-cn-shenzhen.aliyuncs.com/Yuu_2020-09-05_23-56-56.png)

## 部署

- 导入数据库脚本 `sql/recruit.sql`
- 修改 `application.yml` 数据库连接
- 启动项目 `localhost:8080`
- 管理员登录地址：localhost:8080/admin/login
