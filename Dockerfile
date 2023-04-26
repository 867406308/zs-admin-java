#基础镜像
FROM openjdk:8

# 作者
MAINTAINER zs

#RUN mvn clean package
WORKDIR /app
COPY ./zs-admin/target/zs-admin.jar /app/

CMD ["java", "-jar", "zs-admin.jar"]

#指定暴露端口
EXPOSE 8081