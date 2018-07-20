FROM java:jre

ADD ./code/java-backend/target/Xmap.jar /usr/local/

ENTRYPOINT ["java"]

CMD ["-jar","/usr/local/Xmap.jar --spring.profiles.active=prodC"]

EXPOSE 8080

