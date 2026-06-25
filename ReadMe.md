![SpringBoot Badge](https://img.shields.io/badge/springboot-000000?style=for-the-badge&logo=springboot&logoColor=green)
![React Badge](https://img.shields.io/badge/-React-333333?style=for-the-badge&logo=React)
![TS Badge](https://shields.io/badge/TypeScript-3178C6?logo=TypeScript&logoColor=FFF&style=for-the-badge)
![Postgres Badge](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white&style=for-the-badge)
![Docker Badge](https://img.shields.io/badge/docker-257bd6?style=for-the-badge&logo=docker&logoColor=white)
![ChatMate](/images/banner.png)
(AI Generated)<br>
Welcome to **ChatMate** - a full-stack chatserver deployable with docker-compose using multiple communication protocols
---

## Techstack
- **Backend:**<br>
    - Java Spring
    - PostgreSQL database
    - Docker-compose / Dockerfile<br>
↑<br>
GRpc<br>
↓<br>
- **Bridge Application:**<br>
  - Translating the websocket connection to grpc (For learning purposes)<br>
↑<br>
Websocket to Bridge Application<br>
Rest to Backend for Registration/Login<br>
↓<br>
- **Frontend:**<br>
  - React / Typescript
  - Design System: Ant Design

## Features
![Gif](/images/chat.gif)
I am currently working on this project for the module *distributed system* at university.
- Register / Login -> Returns a jwt token
- The jwt token is sent in the first message to the Bridge-Application
    and there added to the metadata of the grpc stub
- Authentication of Rest-Endpoint is in progress<br><br>
![Static Badge](https://img.shields.io/badge/Author-Luis_Gerlinger-blue)
