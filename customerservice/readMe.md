mvn clean package appassembler:assemble  打包【注意，要用命令行进行打包，在idea中进行打包不会把customerservice.jar打进去】
上传到服务器后，解压，进入bin目录执行如下语句。
nohup sh customerservice.sh &
nohup sh customerservice.sh >/dev/null 2>&1 &
jps 才能看到进程，ps -ef|grep customerservice看不到进程。

//swagger-ui 的连接
http://139.198.176.37:8883/customerservice/swagger-ui/index.html



新增好友
新增CS_CUSTOMER_GROUP一条记录，CS_CUSTOMER先2条记录
新增组
新增CS_CUSTOMER_GROUP一条记录，CS_CUSTOMER多条与组关系；


