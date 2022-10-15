# OOLT Project
****

[![Java CI with Gradle](https://github.com/hahunavth/oolt-project/actions/workflows/test.yml/badge.svg)](https://github.com/hahunavth/oolt-project/actions/workflows/test.yml)

> Nhóm 6  \
> Chủ đề 4

## Dependencies: 

- JDK 1.8 trở lên 
- IDE Intellij IDEA hoặc gradle

## Hướng dẫn thiết lập:

  - Tải và cài đặt JDK 1.8 tại: https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
  - Tải và cài đặt IDE Intellij IDEA tại: https://www.jetbrains.com/idea/download/

## Hướng dẫn cài đặt:

- Tải và giải nến file mã nguồn của project.

- Build và chạy chương trình:
  - Sử dụng IDE Intellij IDEA:

    - Mở Intellij IDEA -> `file` -> `Open` -> Chọn thư mục vừa giải nén -> `ok`
    - Sau khi mở project, mở file `src/main/java/GameLauncher.java`
    - Setup SDK và chạy chương trình.

  - Sử dụng CLI:

    - Linux:
      ```shell
      cd thư_mục_vừa_giải_nén
      ./gradlew
      ./gradlew jar 
      java -jar ./build/libs/oolt-project-1.0-SNAPSHOT.jar
      ```
    
    - Window:
      ```shell
      cd thư_mục_vừa_giải_nén
      ./gradlew.bat
      ./gradlew jar 
      java -jar ./build/libs/oolt-project-1.0-SNAPSHOT.jar
      ```
## Can be improved
- [ ] Use ECS pattern
- [ ] Singleton for Stylesheet
- [ ] Object pool
- [ ] Split state and behavior of animals
- [ ] Random next state of animal
