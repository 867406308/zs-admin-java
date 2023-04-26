#基础镜像
FROM openjdk:8

# 作者
MAINTAINER zs

#RUN mvn clean package
WORKDIR /app/
COPY ./target/zs-admin.jar /app/

#指定暴露端口
EXPOSE 8080

CMD ["java", "-jar", "zs-admin.jar"]