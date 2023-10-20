FROM amazoncorretto:11
WORKDIR /app

RUN curl -L https://www.scala-sbt.org/sbt-rpm.repo > sbt-rpm.repo
RUN mv sbt-rpm.repo /etc/yum.repos.d/
RUN yum install -y sbt

COPY ./src/ ./src/
COPY ./build.sbt .
EXPOSE 8080
ENTRYPOINT ["sbt", "run"]
