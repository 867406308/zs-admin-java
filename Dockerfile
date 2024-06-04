#基础镜像
#FROM openjdk:8 
# 镜像加速
FROM registry.cn-zhangjiakou.aliyuncs.com/my-admin/openjdk:17-jdk-alpine

#RUN mvn clean package
WORKDIR /app
COPY ./zs-admin/target/zs-admin.jar /app/

CMD ["java", "-jar", "zs-admin.jar"]

#指定暴露端口
EXPOSE 8085