# Spring cloud java11 扩展

## 功能
1. spring-cloud-openfeign-java11 [spring-cloud-openfeign](https://github.com/spring-cloud/spring-cloud-openfeign) 使用 `feign-java11` 扩展。
2. spring-rest-template-java11 Spring RestTemplate 的 `java11 HttpClient` 扩展。

## 专题文章
1. [是时候升级java11了-01-jdk11优势和jdk选择](https://juejin.im/post/5e4df461e51d4526cd1de49a)
2. [是时候升级java11了-02-升级jdk11踩坑记](https://juejin.im/post/5e511a3af265da574b790e87)
3. 是时候升级java11了-03虚拟机Jvm参数设置
4. 是时候升级java11了-04微服务内http2通信之http2 Clear Text（h2c）
5. 是时候升级java11了-05微服务内h2c通信的阻碍和问题解决

## 配置项
### spring-cloud-openfeign-java11
| 配置项 | 默认值 | 说明 |
| ----- | ------ | ------ |
| http.client.feign.connection-timeout | 2s | 连接超时，默认：2秒 |
| http.client.feign.read-timeout | 2s | 读取超时，默认：2秒 |
| http.client.feign.redirect |  | 重定向规则，默认：ALWAYS |
| http.client.feign.version |  | http 版本，默认：HTTP_2 |

### spring-rest-template-java11
| 配置项 | 默认值 | 说明 |
| ----- | ------ | ------ |
| http.client.rest.connection-timeout | 2s | 连接超时，默认：2秒 |
| http.client.rest.level |  | 日志级别（NONE, BASIC, HEADERS, BODY;），默认：BASIC |
| http.client.rest.read-timeout | 2s | 读取超时，默认：2秒 |
| http.client.rest.redirect |  | 重定向规则，默认：ALWAYS |
| http.client.rest.version |  | http 版本，默认：HTTP_2 |

## 开源推荐
- `mica` 微服务框架：[https://github.com/lets-mica/mica](https://github.com/lets-mica/mica)
- `pig` 宇宙最强微服务（架构师必备）：[https://gitee.com/log4j/pig](https://gitee.com/log4j/pig)
- `SpringBlade` 完整的线上解决方案（企业开发必备）：[https://gitee.com/smallc/SpringBlade](https://gitee.com/smallc/SpringBlade)
- `IJPay` 支付 SDK 让支付触手可及：[https://gitee.com/javen205/IJPay](https://gitee.com/javen205/IJPay)
- `JustAuth` 史上最全的整合第三方登录的开源库: [https://github.com/zhangyd-c/JustAuth](https://github.com/zhangyd-c/JustAuth)
- `spring-boot-demo` 深度学习并实战 spring boot 的项目: [https://github.com/xkcoding/spring-boot-demo](https://github.com/xkcoding/spring-boot-demo)

## 注意事项
参考请注明：参考自 `spring-cloud-java11`：https://github.com/lets-mica/spring-cloud-java11

## 微信公众号

![如梦技术](docs/img/dreamlu-weixin.jpg)

精彩内容每日推荐！