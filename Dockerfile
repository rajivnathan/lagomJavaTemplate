FROM maven:3.5.2-jdk-8-alpine AS builder
COPY pom.xml .
COPY hello-api hello-api/
COPY hello-impl hello-impl/
RUN mvn install

FROM fabric8/java-alpine-openjdk8-jre
ENV AB_OFF=1 JAVA_MAIN_CLASS=play.core.server.ProdServerStart JAVA_APP_JAR=hello-impl-1.0-SNAPSHOT.jar
LABEL com.lightbend.rp.endpoints.1.protocol="tcp" com.lightbend.rp.endpoints.0.name="hello" com.lightbend.rp.app-version="1.0" com.lightbend.rp.app-name="hello-impl" com.lightbend.rp.config-resource="rp-application.conf" com.lightbend.rp.endpoints.0.ingress.0.paths.0="/api/hello/" com.lightbend.rp.endpoints.0.ingress.0.ingress-ports.0="80" com.lightbend.rp.app-type="lagom" com.lightbend.rp.endpoints.0.protocol="http" com.lightbend.rp.endpoints.0.ingress.0.ingress-ports.1="443" com.lightbend.rp.endpoints.1.name="akka-remote" com.lightbend.rp.reactive-maven-app-version="0.3.1-SNAPSHOT" com.lightbend.rp.modules.status.enabled="true" com.lightbend.rp.modules.common.enabled="true" com.lightbend.rp.modules.akka-management.enabled="true" com.lightbend.rp.modules.service-discovery.enabled="true" com.lightbend.rp.applications.0.name="default" com.lightbend.rp.applications.0.arguments.0="deployments/run-java.sh" com.lightbend.rp.modules.akka-cluster-bootstrap.enabled="true" com.lightbend.rp.modules.play-http-binding.enabled="true" com.lightbend.rp.endpoints.2.name="akka-mgmt-http" com.lightbend.rp.endpoints.2.protocol="tcp" com.lightbend.rp.endpoints.0.ingress.0.type="http"
COPY --from=builder hello-api ./
COPY --from=builder hello-impl ./
COPY --from=builder hello-impl/target/alternateLocation /deployments/
EXPOSE 9000
