#### 端口占用

* kafka-producer: 6000
* kafka-consumer: 6001
* dubbo-provider: 6002
* dubbo-consumer: 6003
* flowable-server: 6004
* swagger-server: 6005
* sentinel-server: 6006
* common-server: 6007
* kafka-api: 6008


#### git Unable to access ‘https://github.com/xxxx/xxxx.git/’:OpenSSL SSL_read:Connection was reset

1. git config --global http.sslVerify false
2. git push