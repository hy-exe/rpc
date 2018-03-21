# 
基于Netty实现的RPC架构服务。
公共包：rpc-common
注册中心：rpc-registry
服务器端：rpc-server
客户端：rpc-client

服务端注册信息到注册中心，然后定时发送心跳信息到注册中心进行服务刷新，客户端定时发送心跳信息到注册中心拿服务地址，然后与拿到的服务地址建立连接。

序列化技术使用hessian，kyro（kyro序列化map是出问题，hashmap内table属性不支持序列化，导致空指针异常）。
