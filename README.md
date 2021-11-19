#### *Distributed seckill system*

---

##### Introduction

*For this project, it is mainly set up to simluate how to complete the second kill of goods in the case of large concurrency. It is built to optimize and deal with the case that is the large concyrrency in the case of second kill as well. Futhermore, this project is a distributed application with the responsibilities that are divided to reduce the business coupling of single application.*

#### *Development Environment*

---

*For the development environment, we would have to use JDK1.8、Maven、Mysql、IntelliJ IDEA、SpringBoot1.5.10、zookeeper3.4.6、kafka_2.11、redis-2.8.4、curator-2.10.0. Before running this project, just make sure all these environment is installed and meet the requirements*.

#### *To Do Before Starting The Project* 

---

1. *First of all, you would have to install the development environment including the redis, zk and kafka*

2. *Make sure that the database file is stored under this direction: src/main/resource/sql before starting this project*

3.  *After seting up all these requirement, run the main method under the Application, try this: http://localhost:8080/seckill/swagger-ui.html to do the API testing*

4. And then, you will have the main page: [http://localhost:8080/seckill/index.shtml](http://localhost:8080/seckill/index.shtml) 

   

├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─itstyle
│  │  │          └─seckill
│  │  │              │  Application.java
│  │  │              │  
│  │  │              ├─common
│  │  │              │  ├─aop
│  │  │              │  │      LockAspect.java
│  │  │              │  │      Servicelock.java
│  │  │              │  │      
│  │  │              │  ├─api
│  │  │              │  │      SwaggerConfig.java
│  │  │              │  │      
│  │  │              │  ├─config
│  │  │              │  │      IndexController.java
│  │  │              │  │      SpringUtil.java
│  │  │              │  │      
│  │  │              │  ├─dynamicquery
│  │  │              │  │      DynamicQuery.java
│  │  │              │  │      DynamicQueryImpl.java
│  │  │              │  │      NativeQueryResultEntity.java
│  │  │              │  │      

│  │  │              │  ├─encrypt

│  │  │ 			   │  │	  EncrypSHA
│  │  │              │  │      MD5Util 
│  │  │              │  │      SecurityAES 

│  │  │              │  │      SecurityDES 
│  │  │              │  │      SecurityRSA  

│  │  │              │  │      MD5Util              									

│  │  │              │  ├─entity
│  │  │              │  │      Result.java
│  │  │              │  │      Seckill.java
│  │  │              │  │      SuccessKilled.java
│  │  │              │  │      
│  │  │              │  ├─enums
│  │  │              │  │      SeckillStatEnum.java
│  │  │              │  │      
│  │  │              │  ├─interceptor
│  │  │              │  │      MyAdapter.java
│  │  │              │  │      
│  │  │              │  ├─lock
│  │  │              │  │      LockDemo.java
│  │  │              │  │      
│  │  │              │  └─redis
│  │  │              │          RedisConfig.java
│  │  │              │          RedisUtil.java
│  │  │              │          
│  │  │              ├─distributedlock
│  │  │              │  ├─redis
│  │  │              │  │      RedissLockDemo.java
│  │  │              │  │      RedissLockUtil.java
│  │  │              │  │      RedissonAutoConfiguration.java
│  │  │              │  │      RedissonProperties.java
│  │  │              │  │      
│  │  │              │  └─zookeeper
│  │  │              │          ZkLockUtil.java
│  │  │              │          
│  │  │              ├─queue
│  │  │              │  ├─disruptor
│  │  │              │  │      DisruptorUtil.java
│  │  │              │  │      SeckillEvent.java
│  │  │              │  │      SeckillEventConsumer.java
│  │  │              │  │      SeckillEventFactory.java
│  │  │              │  │      SeckillEventMain.java
│  │  │              │  │      SeckillEventProducer.java
│  │  │              │  │      
│  │  │              │  ├─jvm
│  │  │              │  │      SeckillQueue.java
│  │  │              │  │      TaskRunner.java
│  │  │              │  │      
│  │  │              │  ├─kafka
│  │  │              │  │      KafkaConsumer.java
│  │  │              │  │      KafkaSender.java
│  │  │              │  │      
│  │  │              │  └─redis
│  │  │              │          RedisConsumer.java
│  │  │              │          RedisSender.java
│  │  │              │          RedisSubListenerConfig.java
│  │  │              │          
│  │  │              ├─repository
│  │  │              │      SeckillRepository.java
│  │  │              │      
│  │  │              ├─service
│  │  │              │  │  ICreateHtmlService.java
│  │  │              │  │  ISeckillDistributedService.java
│  │  │              │  │  ISeckillService.java
│  │  │              │  │  
│  │  │              │  └─impl
│  │  │              │          CreateHtmlServiceImpl.java
│  │  │              │          SeckillDistributedServiceImpl.java
│  │  │              │          SeckillServiceImpl.java
│  │  │              │          
│  │  │              └─web
│  │  │                      CreateHtmlController.java
│  │  │                      SeckillController.java
│  │  │                      SeckillDistributedController.java
│  │  │                      
│  │  ├─resources
│  │  │  │  application.properties
│  │  │  │  logback-spring.xml
│  │  │  │  
│  │  │  ├─sql
│  │  │  │      seckill.sql
│  │  │  │      
│  │  │  ├─static
│  │  │  │  ├─goods
│  │  │  │  │  ├─images
│  │  │  │  │  │  │      
│  │  │  │  │  │  └─shopdetail
│  │  │  │  │  │          
│  │  │  │  │  ├─js
│  │  │  │  │  │      common.js
│  │  │  │  │  │      jquery-1.9.1.min.js
│  │  │  │  │  │      
│  │  │  │  │  └─style
│  │  │  │  │          shopdetail.css
│  │  │  │  │          
│  │  │  │  ├─iview
│  │  │  │  │  │  iview.css
│  │  │  │  │  │  iview.min.js
│  │  │  │  │  │  
│  │  │  │  │  └─fonts
│  │  │  │  │          ionicons.eot
│  │  │  │  │          ionicons.svg
│  │  │  │  │          ionicons.ttf
│  │  │  │  │          ionicons.woff
│  │  │  │  │          
│  │  │  │  └─template
│  │  │  │          goods.flt
│  │  │  │          
│  │  │  └─templates
│  │  │          1000.html
│  │  │          1001.html
│  │  │          1002.html
│  │  │          1003.html
│  │  │          index.html
│  │  │          
