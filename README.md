# N+1 Query Detector for JPA (Hibernate)

## ⚙️ Requirements
To use the N+1 Detector, ensure that your project meets the following requirements.

- **Spring Boot:** 3.0.0 or higher
- **Java:** 17 or higher
- **Kotlin:** 1.7.x or higher
- **Hibernate:** 6.x (compatible with Spring Boot 3.x)

## 📦 Dependence
To integrate the N+1 Detector into your project, follow the steps below depending on your build tool.

#### Gradle (build.gradle)

```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {  
    implementation 'com.github.joon6093.jpa-nplus1-detector:detector-core:3.1.0'
    testImplementation 'com.github.joon6093.jpa-nplus1-detector:detector-test:3.1.0'
}
```

#### Maven (pom.xml)

```
<repositories>  
    <repository>  
        <id>jitpack.io</id>  
        <url>https://jitpack.io</url>  
    </repository>  
</repositories>

<dependency>
    <groupId>com.github.joon6093.jpa-nplus1-detector</groupId>
    <artifactId>detector-core</artifactId>
    <version>3.1.0</version>
</dependency>

<dependency>
    <groupId>com.github.joon6093.jpa-nplus1-detector</groupId>
    <artifactId>detector-test</artifactId>
    <version>3.1.0</version>
    <scope>test</scope>
</dependency>
```

## 🔧 Configuration
To enable the N+1 Detector, configure the following options in your Spring Boot configuration file.

#### Options
- **enabled:** Set whether the detector is enabled or disabled (default: false).
- **threshold:** Set the threshold for the number of query executions to detect N+1 queries (default: 2).
- **exclude:** Set the list of specific queries to be excluded from N+1 queries (optional).
- **level:** Set the log level for detected N+1 queries (default: warn).

#### YAML (application.yml)
```
nplus1detector:  
  enabled: true
  threshold: 2
  exclude:
      - select ... from table1 where ...
      - select ... from table2 where ...
  level: warn
```

#### Properties (application.properties)
```
nplus1detector.enabled=true
nplus1detector.threshold=2
nplus1detector.exclude[0]=select ... from table1 where ...      
nplus1detector.exclude[1]=select ... from table2 where ...
nplus1detector.level=warn
```

## 📄 Log
When the N+1 Detector is enabled, a startup log shows the activation status, threshold, and log level.
```
2024-08-27T14:59:54.307+09:00 INFO --- i.j.nplus1detector.core.config.NPlusOneDetectorLoggingConfiguration : N+1 Detector enabled in 'LOGGING' mode. Monitoring queries with a threshold of '2' and logging at 'warn' level.
```

Example log when an N+1 query is detected.
```
2024-08-19T13:04:22.645+09:00 WARN --- i.j.nplus1detector.core.template.NPlusOneQueryLogger : N+1 query detected: 'select b1_0.author_id,b1_0.id,b1_0.title from book b1_0 where b1_0.author_id=?' was executed 2 times.
```

## 🔍 Test
The N+1 Detector can be used in test code with two modes, in combination with @SpringBootTest or @DataJpaTest, and any test-specific settings will take precedence over global configuration.

#### Logging mode
In Logging mode, the N+1 Detector logs detected issues for you to review without interrupting the test flow.
```
@NPlusOneTest(
    mode = NPlusOneTest.Mode.LOGGING,
    threshold = 3,
    exclude = {
        "select ... from table1 where ...",
        "select ... from table2 where ..."
    },
    level = Level.DEBUG
)
@SpringBootTest or @DataJpaTest
class Test {
    // Test cases here
} 
```

If an N+1 query is detected during test execution, it will be logged as follows.
```
2024-09-20T12:18:19.828+09:00 WARN --- i.j.nplus1detector.core.template.NPlusOneQueryLogger : N+1 query detected: 'select o1_0.id,o1_0.order_number from "order" o1_0 where o1_0.id=?' was executed 3 times.
```

#### Exception mode
In Exception mode, the N+1 Detector throws an exception whenever an N+1 query is detected, enforcing stricter query checks during tests.
```
@NPlusOneTest(
    mode = NPlusOneTest.Mode.EXCEPTION,
    threshold = 5,
    exclude = {
        "select ... from table1 where ...",
        "select ... from table2 where ..."
    }
)
@SpringBootTest or @DataJpaTest
class Test {
    // Test cases here
} 
```

If an N+1 query is detected during test execution, an exception is thrown. When multiple N+1 exceptions occur within the same test, they are consolidated into a single exception, with additional exceptions being suppressed and thrown together.
```
io.jeyong.nplus1detector.test.exception.NPlusOneQueryException: N+1 query detected: 'select o1_0.id,o1_0.order_number from "order" o1_0 where o1_0.id=?' was executed 3 times.
Suppressed: io.jeyong.nplus1detector.test.exception.NPlusOneQueryException: N+1 query detected: 'select a1_0.id,a1_0.city,a1_0.street from address a1_0 where a1_0.id=?' was executed 3 times.
Suppressed: io.jeyong.nplus1detector.test.exception.NPlusOneQueryException: N+1 query detected: 'select p1_0.id,p1_0.address_id,p1_0.name from person p1_0 where p1_0.address_id=?' was executed 3 times.
```

## ✏️ Note
- Please be aware that the N+1 Query Detector is disabled by default (enabled: false) due to potential performance implications. It is recommended to enable this feature only in your local or development environment to avoid any negative impact on production performance.
- If you encounter any types of N+1 queries that the detector does not catch, please report them by creating an issue in the project repository. This will help us improve the tool by updating and enhancing its detection capabilities.
- If you found this project helpful or interesting, please consider giving it a star on GitHub! ⭐

## 🗓️ Release
- [Version 1.0.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.0.0) - Released on 2024/08/19
- [Version 1.1.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.1.0) - Released on 2024/08/21
- [Version 1.1.1](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.1.1) - Released on 2024/08/24
- [Version 1.1.2](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.1.2) - Released on 2024/08/27
- [Version 1.2.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.2.0) - Released on 2024/08/29
- [Version 1.3.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.3.0) - Released on 2024/09/04
- [Version 1.3.1](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.3.1) - Released on 2024/09/07
- [Version 1.3.2](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.3.2) - Released on 2024/09/16
- [Version 1.4.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.4.0) - Released on 2024/09/17
- [Version 2.0.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.0.0) - Released on 2024/09/19
- [Version 2.0.1](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.0.1) - Released on 2024/09/19
- [Version 2.0.2](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.0.2) - Released on 2024/09/20
- [Version 2.1.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.1.0) - Released on 2024/09/24
- [Version 2.2.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.2.0) - Released on 2024/09/27
- [Version 2.3.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.3.0) - Released on 2025/04/01
- [Version 2.3.1](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/2.3.1) - Released on 2025/06/24
- [Version 3.0.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/3.0.0) - Released on 2025/07/15
- [Version 3.0.1](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/3.0.1) - Released on 2025/08/29
- [Version 3.1.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/3.1.0) - Released on 2025/09/01
