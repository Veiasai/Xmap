FROM java:jre

ADD ./target/Xmap-1.0.0.jar /usr/local/

ENTRYPOINT ["java"]

CMD ["-jar","/usr/local/Xmap-1.0.0.jar --spring.profiles.active=prodC"]

EXPOSE 8080

