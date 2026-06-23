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
I am currently working on this project for the module *distributed system* at university.
- Register / Login -> Returns a jwt token
- The jwt token is sent in the first message to the Bridge-Application
    and there added to the metadata of the grpc stub
- Authentication of Rest-Endpoint is in progress
