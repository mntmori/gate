FROM alpine:3.8

WORKDIR /opt/gate

RUN cd /opt; \
    wget https://download.java.net/java/early_access/alpine/25/binaries/openjdk-11-ea+25_linux-x64-musl_bin.tar.gz \
    && tar zxf openjdk-11-ea+25_linux-x64-musl_bin.tar.gz \
    && ln -s jdk-11 java \
    && rm -f openjdk-11-ea+25_linux-x64-musl_bin.tar.gz

ENV JAVA_HOME=/opt/java
ENV PATH="$PATH:$JAVA_HOME/bin"


COPY target/gate.jar /opt/gate/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/gate/gate.jar"]