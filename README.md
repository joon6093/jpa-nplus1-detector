# N+1 Query Detector for JPA (Hibernate)

## ⚙️ Requirements
To use the N+1 Query Detector, ensure that your project meets the following requirements.

- **Spring Boot:** 3.0.0 or higher
- **Java:** 17 or higher
- **Hibernate:** 6.x (compatible with Spring Boot 3.x)

## 📦 Dependence
To integrate the N+1 Query Detector into your project, follow the steps below depending on your build tool.

#### Gradle (build.gradle)

```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {  
    implementation 'com.github.joon6093:jpa-nplus1-detector:1.4.0'  
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
    <groupId>com.github.joon6093</groupId>  
    <artifactId>jpa-nplus1-detector</artifactId>  
    <version>1.4.0</version>  
</dependency>
```

## 🔧 Configuration
To enable the N+1 query detector, update your Spring Boot configuration file.

#### YAML (application.yml)
```
spring:
  jpa:  
    properties:  
      hibernate:  
        detector:  
          enabled: true     # Enable N+1 query detection (default: false)  
          threshold: 2      # Set the threshold for query execution count (default: 2)
          level: warn       # Set the log level for detected N+1 issues (default: warn)
```

#### Properties (application.properties)
```
spring.jpa.properties.hibernate.detector.enabled=true     # Enable N+1 query detection (default: false)
spring.jpa.properties.hibernate.detector.threshold=2      # Set the threshold for query execution count (default: 2)
spring.jpa.properties.hibernate.detector.level=warn       # Set the log level for detected N+1 issues (default: warn)
```

## 📄 Log
When the N+1 Detector is enabled, a startup log shows the activation and threshold.
```
2024-08-27T14:59:54.307+09:00 INFO 9448 --- i.j.detector.config.NPlusOneDetectorConfig : N+1 Detector is enabled with threshold: 2, log level: warn
```

Example log when an N+1 issue is detected.
```
2024-08-19T13:04:22.645+09:00 WARN 4296 --- i.j.detector.aspect.ConnectionProxyAspect : N+1 issue detected: 'select b1_0.author_id,b1_0.id,b1_0.title from book b1_0 where b1_0.author_id=?' was executed 2 times.
```

## ✏️ Note
- Please be aware that the N+1 query detector is disabled by default (enabled: false) due to potential performance implications. It is recommended to enable this feature only in your local or development environment to avoid any negative impact on production performance.
- If you encounter any types of N+1 issues that the detector does not catch, please report them by creating an issue in the project repository. This will help us improve the tool by updating and enhancing its detection capabilities.
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
