# FastMessage-statistic
FastMessage-statistic API provides an HTTP API for FastMessage. </br>
To send a request, you need a special token that is generated on the service side (see  [FastMessage](https://github.com/miumiuhaskeer/FastMessage) service)</br>
Note: project under development!

## TODO
- [x] User statistic information
- [x] Transfer information from MongoDB to Elasticsearch
- [x] Search messages
- [ ] Chats statistic

## Services architecture
![Architecture](https://github.com/miumiuhaskeer/FastMessage-statistic/blob/master/.github/images/Diagram.jpg)

## Technologies used
- Spring Boot
- Docker - used bridge network driver
- Logstash - needed to transfer information from MongoDB to Elasticsearch
- Elasticsearch - needed to store statistic information about user and messages
- PostgreSQL - needed to store information about user (email, encrypted password, refresh token)
- Kibana - needed to visualize Elasticsearch data
- Apache Kafka - recieve messages from [FastMessage](https://github.com/miumiuhaskeer/FastMessage) service

## Health checking
You can check service availability by sending request
```
http://fast-message-statistic:3173/fms/api/v1/health
```
The response is
```
OK
```

## See project on DockerHub
Follow the [link](https://hub.docker.com/repository/docker/heybitbro/fast-message-statistic) to see FastMessage-statistic project
