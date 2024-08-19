# N+1 Query Detector for JPA (Hibernate)

## 📦 Dependence
To integrate the N+1 Query Detector into your project, follow the steps below depending on your build tool

#### Gradle (build.gradle)

```
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {  
    implementation 'com.github.joon6093:jpa-nplus1-detector:1.0.0'  
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
    <version>1.0.0</version>  
</dependency>
```

## 🔧 Configuration
To enable the N+1 query detector, update your Spring Boot configuration file

#### YAML (application.yml)
```
jpa:  
  properties:  
    hibernate:  
      detector:  
        enabled: true     # Enable N+1 query detection (default: false)  
        threshold: 3      # Set the threshold for query execution count (default: 2)
```

#### Properties (application.properties)
```
jpa.properties.hibernate.detector.enabled=true     # Enable N+1 query detection (default: false)
jpa.properties.hibernate.detector.threshold=3      # Set the threshold for query execution count (default: 2)
```

## 📄 Logging Example
Here’s an example of what the log will look like when an N+1 issue is detected
```
2024-08-19T13:04:22.645+09:00 WARN 4296 --- i.j.detector.aop.NPlusOneDetectionAop : N+1 issue detected: 'select b1_0.author_id,b1_0.id,b1_0.title from book b1_0 where b1_0.author_id=?' was executed 2 times.
```

## ✏️ Note
- Please be aware that the N+1 query detector is disabled by default (enabled: false) due to potential performance implications. It is recommended to enable this feature only in your local or development environment to avoid any negative impact on production performance.
- If you encounter any types of N+1 issues that the detector does not catch, please report them by creating an issue in the project repository. This will help us improve the tool by updating and enhancing its detection capabilities.
- If you found this project helpful or interesting, please consider giving it a star on GitHub! ⭐

## 🗓️ Release
- [Version 1.0.0](https://github.com/joon6093/jpa-nplus1-detector/releases/tag/1.0.0) - Released on 2024/08/19

