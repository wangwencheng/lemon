FROM java:8-jre

MAINTAINER tjb@geeboo.cn

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /elib-auth

WORKDIR /elib-auth

EXPOSE 3000

ADD ./target/elib-auth.jar ./

CMD sleep 120;java -Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar elib-auth.jar
