### Settings

---

Kafka를 시행하는데 관한 Setting입니다.

단, Window를 기준으로 설명합니다.

1. Kafka 홈페이지에 들어가서 `Scala 2.12`를 다운받습니다.

(Kafka를 사용하려면, JDK가 설치되어있어야 합니다. `JDK8 이상`으로 맞춰야합니다.)

2. 다운을 받은후 압축 받은 폴더로 이동합니다.



3. Kafka Server를 실행하기 전에 ZooKeeper Server를 먼저 실행해 줘야 합니다.

cmd창을 켜서 실행하도록 합니다.

```bash
cd {해당하는 Kafka 폴더}
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```



4. Kafka Server를 실행합니다.

위와 마찬가지입니다.

```bash
cd {해당하는 Kafka 폴더}
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

4번을 실행하는 중에, 다음과 같은 에러를 만날수 있습니다.

```bash
ERROR Shutdown broker because all log dirs in D:\tmp\kafka-logs have failed (kafka.log.LogManager)
```

(참고로 저는 현재 Directory 위치가 D 드라이브 입니다)

저런 경우는 해당하는 드라이브의 tmp로 이동하여, `kafka-logs 폴더와 zookeeper 폴더를 지우고` 다시 실행해 보시길 바랍니다.

<br/><br/>

### Example

---

해당 Example은 topic을 만들고, producer로 message를 write하고, consumer에서 message를 가져가는 경우입니다.

1. topic을 만듭니다.

```bash
.\bin\windows\kafka-topics.bat --create --topic streams-plaintext-input --bootstrap-server localhost:9092
```

- bootstrap-server : 연결할 Kafka 서버( host:port ),(기본 port가 9092입니다)

2. 해당 topic이 만들어졌는지 확인을 해봅니다.

```bash
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```

3.  cmd창을 새로 켜서, producer로 만든 topic에 message를 write합니다.

```bash
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic streams-plaintext-input
```

4. cmd창을 새로 켜서, consumer로 message를 가져오겠습니다.

```bash
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic streams-plaintext-input --from-beginning
```

- from-beginning : 가장 먼저 온 message부터 읽겠다는 의미입니다.

출처 : [https://kafka.apache.org/quickstart](https://kafka.apache.org/quickstart)

(위의 출처는 Linux 기준)

<br/><br/>

### SampleCode

---

myapps의 sample에 있습니다.

SampleCode를 실행하기에 앞서서, `ZooKeeper와 Kafka Server를 실행을 먼저 시키고 원하는 topic을 만들어야 합니다.`