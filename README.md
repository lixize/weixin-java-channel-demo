### 本Demo基于Spring Boot构建，实现视频号小店功能开发功能。

#### 本项目为WxJava的Demo演示程序，更多信息请查阅：https://github.com/Wechat-Group/WxJava

[![Github](https://img.shields.io/github/stars/lixize/weixin-java-channel-demo?logo=github&style=flat)](https://github.com/lixize/weixin-java-channel-demo)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
-----------------------

### 模块介绍
- **normal-channel-demo**: 不使用starter引入Maven开发Demo
- **open-channel-demo**: 基于第三方平台授权的开发Demo
- **starter-channel-demo**: 使用starter引入Maven开发Demo

## 使用步骤：
1. 新手遇到问题，请务必先阅读[【开发文档首页】](https://github.com/Wechat-Group/WxJava/wiki)的常见问题部分，可以少走很多弯路，节省不少时间。如还有其他问题请[【在此提问】](https://github.com/lixize/weixin-java-channel-demo/issues)，谢谢配合。
2. 修改``pom.xml``中的``wxjava.version``版本，最新版本（包括测试版）为 [![Maven Central](https://img.shields.io/maven-central/v/com.github.binarywang/wx-java.svg)](http://mvnrepository.com/artifact/com.github.binarywang/wx-java)
3. 配置：重新命名模块下面的`/src/main/resources/application.yml.sample` 为``application.yml``，并根据自己需要填写相关配置（需要注意的是：yml文件内的属性冒号后面的文字之前需要加空格，可参考已有配置，否则属性会设置不成功）；	
4. 运行Java程序 ``XXDemoApplication``；
5. 配置微信URL可以看各个模块的``WechatNotifyController.java``
6. 如果使用第三方平台需要引入``jackson-dataformat-xml``，版本可以上个新的
```xml
      <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-xml</artifactId>
        <version>2.13.0</version>
      </dependency>
```
引入该模块，会影响Spring MVC的默认返回。
以下是一些关于这个的一些讨论：
https://stackoverflow.com/questions/41036377/spring-mvc-changing-default-response-format-from-xml-to-json
https://stackoverflow.com/questions/66752245/spring-5-jackson-dataformat-xml-forces-responsebody-with-xml
https://stackoverflow.com/questions/57706610/how-to-set-default-messageconverter-to-json-with-jackson-dataformat-xml-added
https://github.com/FasterXML/jackson-dataformat-xml/issues/530

7. 需要redis支持，如果想用内存，可以修改相关实现。

