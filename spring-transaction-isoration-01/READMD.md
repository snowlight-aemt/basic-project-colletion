# Transaction Isolation

### 경리 수준 isolation (REPEATABLE_READ)
`@Transaction(isolation = REPEATABLE_READ)`

### 이슈
트랜잭션 커밋에 시점에 따라서 조회 결과가 다라지는 이슈  
(예시를 위해서 간단한 예시 사용)

### init schema
스키마 파일 경로  
`/main/resource/data/schema.sql`

```yml
spring:
  sql:
    init:
      schema-locations: classpath:data/schema.sql
      mode: always
```


