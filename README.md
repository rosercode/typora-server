Typora-Server 让自己的服务器成为 Typora 的图床 

## 说明

Typora 支持自定义命令（脚本），来完成图片的上传 [相关文档](https://support.typora.io/Upload-Image/)

这里来使用 SpringBoot 来完成一个简单的 example

## 部署

修改图片保存的位置

在终端键入 **java -jar**  命令启动项目

```bash
java -jar typora-serve-0.1.jar
```

## 分支说明 

- Simple 分支提供了一个最简单的，无需用到数据库来存储数据
- master 用到了多种技术，如 mysql 等等

如果你想要为你的 **typora** 运行一个简单的图片服务器（不需要数据库、等等），您需要 **simple**

如果您使用 master 分支的项目，您需要配置一些相关的环境

## 版本列表

## 0.1

测试版本，可使用

> 图片上传模块

- 上传图片大小限制
- 上传图片权限限制

> 图片展示模块

