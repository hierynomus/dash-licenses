FROM maven:3-adoptopenjdk-11 as builder

COPY . /project

RUN cd /project && \
    mvn clean package

FROM adoptopenjdk:11

COPY --from=builder /project/core/target/org.eclipse.dash.licenses-0.0.1-SNAPSHOT.jar /

VOLUME [ "/data" ]
ENTRYPOINT [ "java", "-jar", "/org.eclipse.dash.licenses-0.0.1-SNAPSHOT.jar", "-summary", "/data/LICENSES" ]
