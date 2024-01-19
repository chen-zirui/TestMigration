FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# 更换清华大学的镜像源
RUN sed -i 's#http://archive.ubuntu.com/#http://mirrors.tuna.tsinghua.edu.cn/#' /etc/apt/sources.list

# 安装 apt-utils
RUN apt-get update && \
    apt-get install -y apt-utils && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*


# 安装Python 3.8及其开发包
RUN apt-get update && \
    apt-get install -y python3.8 python3.8-dev && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 在 /usr/local/bin 中创建 python 指向 python3.8 的符号链接
RUN ln -s /usr/bin/python3.8 /usr/local/bin/python



# 安装pip
RUN apt-get update && \
    apt-get install -y python3-pip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 安装Java 8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*


ENV MAVEN_HOME=/opt/apache-maven-3.8.8


RUN apt-get update && \
    apt-get install -y wget && \
    mkdir -p /opt

RUN wget -qO- "https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz" | tar -xz -C /opt/ 

RUN ln -s /opt/apache-maven-$MAVEN_VERSION $MAVEN_HOME && \
    ln -s $MAVEN_HOME/bin/mvn /usr/local/bin/mvn && \
    apt-get remove -y wget && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV PATH="${MAVEN_HOME}/bin:${PATH}"


# 设置工作目录
WORKDIR /VESTA

# 复制当前目录下的所有文件到工作目录
COPY . /VESTA

# 设置java环境变量

# 设置maven本地仓库位置
ENV JAVA_HOME=/VESTA/java/jdk1.8.0_391
ENV CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib
ENV PATH=$JAVA_HOME/bin:$PATH