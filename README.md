# 自动化仓库管理系统
## 一、 功能介绍
### 本系统用于自动化管理仓库存、取货物的过程，支持用户查看个人在仓库中的货物信息。
### 功能如下：

### 用户端
    1、 用户鉴权
    2、 用户存放
    3、 小车自动匹配货架位置并存放货物
    4、 用户查看货物信息
    5、 用户提取货物
    
### 管理员端
    1、 管理用户信息
    2、 审批存放订单与提取请求
    3、 全局管理仓库货物信息

## 二、 环境配置
### 1、 后端环境
    java -1.8
    
### 2、 前端环境
    Vue

### 3、 服务器环境
    数据库 MySQL
    消息队列 Kafka
    缓存 Redis

## 三、目录结构描述

    |——Reademe.md                                 // 项目描述
    |——logs                                      // 日志
    |   |——spring-console-debug.log
    |   |——spring-console-error.log
    |   |——spring-console-info.log
    |——src
    |   |——main
    |       |——java                             // 后端主体代码
    |           |——com.example.ptv
    |               |——bean
    |                   |——ComUser
    |                   |——Const
    |                   |——Result
    |               |——config                   // 部分功能设置
    |                   |——CorsConfig           // 跨域设置
    |                   |——MybaitsPlusConfig    // MybaitsPlus设置
    |                   |——SecurityConfig       // spring security设置
    |               |——controller               // spring boot controller层
    |                   |——adminController
    |                   |——CarController
    |                   |——CargoController
    |                   |——CategoryController
    |                   |——ConsumerListener
    |                   |——KafkaProducerController
    |                   |——operationController
    |                   |——ordersController
    |                   |——RegistrationController
    |                   |——ShelvesController
    |                   |——superAdminController
    |                   |——userController
    |               |——dao                      // spring boot Dao层
    |                   |——adminDao
    |                   |——CarDao
    |                   |——CargoDao
    |                   |——CategoryDao
    |                   |——HistoryDao
    |                   |——itemDao
    |                   |——locationDao
    |                   |——orderinfoDao
    |                   |——ordersDao
    |                   |——RoleDao
    |                   |——ShelvesDao
    |                   |——superAdminDao
    |                   |——UserDao
    |                   |——warehouseDao
    |               |——dto
    |                   |——UserDTO
    |               |——entity                   // spring boot entity层
    |                   |——admin
    |                   |——Car
    |                   |——Cargo
    |                   |——cargo_witelocation
    |                   |——Category
    |                   |——History
    |                   |——item
    |                   |——location
    |                   |——order_user
    |                   |——orderinfo
    |                   |——orders
    |                   |——RoleEntity
    |                   |——ShelvesTntity
    |                   |——superAdmin
    |                   |——User
    |                   |——warehouse
    |               |——sec                      // 包含过滤器，handler等设置文件
    |                   |——filter
    |                       |——JwtAuthenticationFilter
    |                   |——hander
    |                       |——JwtAccessDeniedHandler
    |                       |——LoginFailureHandler
    |                       |——LoginSuccessHandler
    |                   |——JwtAuthenticationEntryPoint
    |               |——service                  // spring boot service层
    |                   |——Imp                  // service的实现类文件
    |                       |——adminServiceImp
    |                       |——CargoServiceImp
    |                       |——CarServiceImp
    |                       |——CategoryServiceImp
    |                       |——CustomUserDetailsServiceImp
    |                       |——loginServiceImp
    |                       |——operationServiceImp
    |                       |——ordersServiceImp
    |                       |——RegistrationServiceImp
    |                       |——ShelvesServiceImp
    |                       |——superAdminServiceImp
    |                       |——userServiceImp
    |                   |——adminService
    |                   |——CargoService
    |                   |——CarService
    |                   |——CategoryService
    |                   |——loginService
    |                   |——operationService
    |                   |——ordersService
    |                   |——RegistrationService
    |                   |——ShelvesService
    |                   |——superAdminService
    |                   |——userService
    |               |——utils                    // 各种工具类
    |                   |——result
    |                       |——SystemResult
    |                   |——Code
    |                   |——Constant
    |                   |——JwtUtils
    |                   |——PageUtils
    |                   |——Query
    |                   |——R
    |                   |——RedisUtil
    |                   |——Rest
    |                   |——RRException
    |               |——validator
    |                   |——group
    |                       |——AddGroup
    |                       |——AliyunGroup
    |                       |——Group
    |                       |——QcloudGroup
    |                       |——QiniuGroup
    |                       |——UpdateGroup
    |                   |——Assert
    |                   |——ValidatorUtils
    |               |——xss
    |                   |——HTMLFilter
    |                   |——SQLFilter
    |               |——PtvApplication
    |       |——resources
    |           |——mapper.PYV
    |               |——RoleDao.xml
    |               |——ShelvesDao.xml
    |               |——UserDao.xml
    |           |——static
    |               |——index.html
    |           |——application.properties
    |           |——application.yml
    |   |——test
    |——pom.xml

## 四、 功能实现描述

### 1、 用户鉴权功能
    通过引入spring sercity，使用Filter通过JWT实现鉴权
### sercityconfig代码展示
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .failureHandler(loginFailureHandler)
                    .successHandler(loginSuccessHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")

                    .antMatchers(URL_WHITELIST).permitAll() //白名单
                    .anyRequest().authenticated()
                // 不会创建 session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 配置自定义的过滤器
                .and()
        ;
    }
### 2、 货物存放与自动化运输
    用户将货物放置与存放区，并填写相关信息。后台系统自动生成待审批订单，管理员审批订单后，小车（通过Kafka模拟）从存放区获取货物后，自动寻路前往合适的货架位置，并将货物存放
### 3、 货物提取
    用户通过系统选择要提取的货物以及数量，后台自动生成未审批提取申请。管理员审批通过后，小车（通过Kafka模拟）从货架提取货物，并送至取货区，用户可将获取拿走
### 4、 查看功能
    查看功能分为用户查看与管理员查看
### （1）用户查看
    系统支持用户查看自身存放的货物的各类信息，包括货物基本信息与货物存放位置
### （2）管理员查看
    管理员可以查看如下信息：    
        系统的用户信息
        仓库所有的货架以及货架上存放格的信息
        仓库所有货物的信息

