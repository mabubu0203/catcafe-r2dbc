# 開発環境
====

## IDE

IntelliJ IDEAを使用します。

### Plugin

下記の最新版をインストールします。

1. google-java-format Settings
1. Lombok Plugin

## 起動方法

1. 当プロジェクトをIntelliJ IDEAにGradleProjectとしてclone
1. docker-composeよりMysqlを起動  
   `$ cd docker/mac`  
   `$ docker-compose -f docker-compose.yml build`  
   `$ docker-compose -f docker-compose.yml up -d`
1. アプリケーションをbootRun
    * GradleタスクよりbootRun(`:micro-api -> Tasks -> application -> bootRun`)
    * DockerImageを作成して起動
        1. GradleタスクよりjibDockerBuild(`:micro-api -> Tasks -> jib -> jibDockerBuild`)
        1. `$ docker run --name micro-api --network mac_app-net --rm -p 9001:9001 -it play-with-jib/micro-api:0.0.1.SNAPSHOT`

1. Gradleタスクよりstart(`:micro-site -> Tasks -> other -> startDevelopment`)

### Endpoints

[Springdoc(Api)][]
[phpMyAdmin][]
[Redisinsight][]
[Swagger-UI][]
[ReDoc][]

## 停止方法

1. アプリの停止
    * bootRunの停止
    * `$ docker stop micro-api`

1. docker-composeより停止  
   `$ cd docker/mac`  
   `$ docker-compose -f docker-compose.yml stop`
1. docker-composeよりコンテナ破棄  
   `$ docker-compose -f docker-compose.yml down`

[Springdoc(Api)]: http://localhost:9001/CatCafeApi/swagger-ui.html            "Springdoc(Api)"

[phpMyAdmin]: http://localhost:8021/                                          "phpMyAdmin"

[Redisinsight]: http://localhost:8001/                                        "Redisinsight"

[Swagger-UI]: http://localhost:8002/                                          "Swagger-UI"

[ReDoc]: http://localhost:8081/                                               "ReDoc"