# 계약 관리 시스템

## ERD 다이어그램

![diagram.png](post%2Fdiagram.png)

### 1. 계약 테이블 (CONTRACT)

| 컬럼명        | 타입            | 값                            | 코멘트    | 비고                                                      |
|------------|---------------|------------------------------|--------|---------------------------------------------------------|
| ID         | bigint        | number                       | 기본키    | PK                                                      |
| STATE      | varchar(255)  | NORMAL,WITHDRAWAL,EXPIRATION | 계약 상태  | NORMAL(정상계약)<br/>WITHDRAWAL(청약철회)<br/>EXPIRATION (기간만료) |
| TERM       | integer       | number                       | 계약 기간  |                                                         |
| START_DATE | timestamp     | '2023-01-01 00:00:00'        | 보험 시작일 |                                                         |
| END_DATE   | timestamp     | '2023-01-01 00:00:00'        | 보험 종료일 |                                                         |
| PREMIUM    | decimal(19,2) | 100000                       | 총 보험료  |                                                         |
| PRODUCT_ID | bigint        | number                       | 상품 외래키 | PRODUCT TABLE FK                                        |

### 2. 계약 담보 매핑 테이블 (CONTRACT_WARRANTS)

| 컬럼명         | 타입     | 값      | 코멘트   | 비고  |
|-------------|--------|--------|-------|-----|
| CONTRACT_ID | bigint | number | 계약 PK |     |
| WARRANTS_ID | bigint | number | 담보 PK |     |

### 3. 담보 테이블 (WARRANT)

| 컬럼명                 | 타입            | 값        | 코멘트   | 비고  |
|---------------------|---------------|----------|-------|-----|
| ID                  | bigint        | number   | 기본키   | PK  |
| TITLE               | varchar(255)  | '상해치료비'  | 담보명   |     |
| SUBSCRIPTION_AMOUNT | decimal(19,2) | 10000000 | 가입 금액 |     |
| STANDARD_AMOUNT     | decimal(19,2) | 100      | 기준 금액 |     |

### 4. 상품 테이블 (PRODUCT)

| 컬럼명         | 타입           | 값        | 코멘트      | 비고  |
|-------------|--------------|----------|----------|-----|
| ID          | bigint       | number   | 기본키      | PK  |
| TITLE       | varchar(255) | '상해치료비'  | 상품명      |     |
| START_MONTH | integer      | 10000000 | 시작 계약 기간 |     |
| END_MONTH   | integer      | 100      | 끝 계약 기간  |     |

### 5. 상품 담보 매핑 테이블 (PRODUCT_WARRANTS)

| 컬럼명         | 타입     | 값      | 코멘트   | 비고  |
|-------------|--------|--------|-------|-----|
| PRODUCT_ID  | bigint | number | 상품 PK |     |
| WARRANTS_ID | bigint | number | 담보 PK |     |

## API 정의

### 1.  거래 생성 API

**Request**

```text
curl --location --request POST 'http://localhost:8080/contract' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productId" : 1 ,
    "warrantIds" : [ 1 ] ,
    "term" : 2,
    "startDate"  : "2022-12-01",
    "endDate" : "2022-12-30"
}'
```

**Request Data**

| Key        | 코멘트        | 비고  |
|------------|------------|-----|
| productId  | 상품의 아이디 값  |     |
| warrantIds | 담보의 아이디 값들 |     |
| term       | 기간 값       |     |
| startDate  | 보험 시작일     |     |
| endDate    | 보험 종료일     |     |

**Response**

```json lines
HTTP/1.1 201
{
  "id": 1,
  "productId": 1,
  "warrantIds": [
    1
  ],
  "term": 2,
  "startDate": "2022-12-01T00:00:00.000+00:00",
  "endDate": "2022-12-30T00:00:00.000+00:00",
  "premium": 90000.00,
  "state": "NORMAL"
}
```

### 2. 계약 데이터 조회 API

**Request**

```text
curl --location --request GET 'http://localhost:8080/contracts/1'
```

**Request Data**

| Key         | 코멘트    | 비고                         |
|-------------|--------|----------------------------|
| contract_id | 계약 아이디 | `/contracts/1` 에서 1의 값입니다. |

**Response**

```json lines
HTTP/1.1 200
{
  "startDate": "2022-12-01T00:00:00.000+00:00",
  "endDate": "2022-12-30T00:00:00.000+00:00",
  "premium": 90000.00,
  "warrants": [
    {
      "title": "상해치료",
      "subscriptionAmount": 1000000.00,
      "standardAmount": 100.00,
      "id": 1
    }
  ],
  "product": {
    "range": 9,
    "title": "여행자보험",
    "id": 1
  },
  "term": 2,
  "id": 1,
  "state": "NORMAL"
}
```

### 3. 계약 내역 수정 API

**Request**

```text
curl --location --request PATCH 'http://localhost:8080/contracts/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "warrantIds" : [1] ,
    "term" : 10,
    "state" : "WITHDRAWAL"
}'
```

**Request Data**

| Key        | 코멘트       | 비고  |
|------------|-----------|-----|
| warrantIds | 담보 아이디 값들 |     |
| term       | 기간        |     |
| state      | 계약 상태     |     |

**Response**

```json lines
HTTP/1.1 200
{
  "id": 1,
  "productId": 1,
  "warrantIds": [
    1
  ],
  "term": 10,
  "startDate": "2022-12-01T00:00:00.000+00:00",
  "endDate": "2022-12-30T00:00:00.000+00:00",
  "premium": 90000.00,
  "state": "WITHDRAWAL"
}
```

### 4. 예상 총 보험료 API

**Request**

```text
curl --location --request GET 'http://localhost:8080/products/2/premium?warrantIds=2,3'
```

**Request Data**

| Key        | 코멘트       | 비고                                                                                                |
|------------|-----------|---------------------------------------------------------------------------------------------------|
| product_id | 상품의 아이디 값 | `/products/2` 에서 2의 값입니다.                                                                         |
| warrantIds | 담보 데이터    | `warrantIds=2,3` 에서 담보의 아이다가 2 와 3 을 조회를 합니다.<br/> 하지만 빈값으로 요청을 하면 해당 상품에 매핑된 모든 담보 데이터를 조회를 합니다. |

**Response**

````json lines
HTTP/1.1 200
{
  "productTitle": "여행자보험",
  "premium": 90000.00,
  "term": 9
}
````

### 5. 상품 생성 API

**Request**

```text
curl --location --request POST 'http://localhost:8080/product' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title" : "여행자보험",
    "term" : {
        "startMonth" : 1,
        "endMonth" : 10
    },
    "warrantIds" : [
        1
    ]
}'
```

**Request Data**

| Key             | 코멘트             | 비고  |
|-----------------|-----------------|-----|
| title           | 상품명             |     |
| term.startMonth | 기간의 시작 개월       |     |
| term.endMonth   | 기간의 끝 개월        |     |
| warrantIds      | 매핑될 담보 데이터 입니다. |     |

**Response**

```json lines
HTTP/1.1 201

{
  "id": 1,
  "title": "여행자보험",
  "term": {
    "startMonth": 1,
    "endMonth": 10,
    "range": 9
  },
  "warrantIds": [
    1
  ]
}
```

### 6. 담보 생성 API

**Request**

```text
curl --location --request POST 'http://localhost:8080/warrant' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title" : "상해치료",
    "subscriptionAmount" : 1000000,
    "standardAmount" : 100
}'
```

**Request Data**

| Key                | 코멘트   | 비고  |
|--------------------|-------|-----|
| title              | 담보명   |     |
| subscriptionAmount | 가입 금액 |     |
| standardAmount     | 기준 금액 |     |

**Response**

```json lines
HTTP/1.1 201

{
  "id": 3,
  "title": "상해치료",
  "subscriptionAmount": 1000000,
  "standardAmount": 100
}
```

## 실행 방법 및 테스트 실행 방법

### 1. 인텔리제이

**실행 방법**

* 인텔리제이에서 우측 상단의 `Run` 버튼을 실행을 시켜 주세요.

![run_ide.png](post%2Frun_ide.png)

**테스트 방법**

* Test 패키지에서 우클릭 해주세요.
* 우클릭 후 `Run` 버튼을 눌러 주세요.

![run_ide_test.png](post%2Frun_ide_test.png)

### 2. Gradle

**실행 방법**

```shell
./gradlew bootRun
```

![gradle_bootRun.png](post%2Fgradle_bootRun.png)

**테스트 방법**

```shell
./gradlew test
```

![gradle_test.png](post%2Fgradle_test.png)

**참고용 Postman**

[Postman Path](https://github.com/kakao-insurance-quiz/20230114-pjg/tree/main/postman)

## 고민 포인트 및 생각 & 해결 방법

Q) 계약 기간 값를 어떤 식으로 저장을 할까?

A) 계약 기간은 월 단위로 관리 을 하지만 아래의 예시 처럼 1 ~ 3 개월을 계산 된 값인 2개월을 DB 에 저장을 할 지
아니면 startMonth (시작 계약 기간) : 1 , endMonth (끝 계약 기간) : 3 과 같은 형식으로 저장할 지 고민을 하게 되었습니다.
하지만 제가 생각 하기 에는 `startMonth : 1 , endMonth : 3` 과 같이 저장을 하게 되면 서버 에서 가공해서 사용이 가능하다고 생각 되며 서버에서 로직으로 녹여서 사용하는 것이 좋다고 생각
하게 되었습니다.
서버에서 가공된 데이터를 활용해서 유동적으로 업무를 진행을 할 수 있지 않을 까? 라는 관점에서 해당 데이터 형식으로 저장 하는 것이 좋다고 생각 됩니다.

![img1.png](post%2Fimg1.png)

Q) 상태 데이터를 저장을 어떻게 하지?

A) 상태 데이터를 저장하는 부분에서 고민을 하게 되었습니다. number (0,1,2) 형식으로 저장을 하는 것이 좋을까?
혹은 string 형식으로 저장하는 것이 좋을까? 이 두 가지 관점에서 고민을 하게 되었습니다. 두가지 선택지를 두고 저는 string 형식으로 저장하는 방식을 선택을 하게 되었습니다.
enum class 를 통해서 관리가 되고 있지만, 해당 enum 이 변경이 된다면, 잘못된 데이터를 쌓게 됩니다. 그렇기 때문에 명확하게 데이터를 저장을 하는 것이 좋다고 생각을 하게 되었습니다.
JPA 에서 enum class 를 저장 할 때 `@Enumerated(EnumType.STRING)` 해당 어노테이션을 제공을 해주어서 해당하는 어노테이션을 사용하는 방식을 선택을 하게 되었습니다.

![img2.png](post%2Fimg2.png)

Q) JPA 를 사용을 하면서 N + 1 형태의 쿼리가 실행이 되었는데 어떤 고민을 하였는 가?

A) 계약 테이블에서 데이터 조회시 상품를 조회를 해오면서 N + 1 문제가 발생을 하게 되었습니다.
`OneToOne` 은 기본 값으로 `EAGER` 로딩을 하게 되어 있습니다. 하지만 `양방향` 매핑을 통해서 구현을 하게 되면 `Lazy` 로딩을 구현할 수 없없습니다.
그래서 단방향 매핑을 통해서 설계 하고 해당 문제를 해결하기 위해서 `@entitygraph` 을 통헤서 `Lazy` 로딩을 하는 방식으로 구현을 하게 되었습니다.

Q) 객체간 상태 관리에 대한 고민

A) 개발 및 운영을 하다가 객체에 대한 데이터 변경을 Setter를 통해서 진행하는 것을 경험하게 되었습니다.
필드별 Setter를 통해서 데이터 흐름의 제어를 하게 되는데 데이터 변경에 대한 흐름을 직관적으로 알지 못하는 경험하였습니다.
이때 경험을 통해서 Setter를 사용하지 않으며, 또한 method를 통해서 메소드명으로 직관적으로 알 수 있게, 어떤 데이터가 변경하고 어떤 행위를 하는지 고민하면서 Setter 메소드를 사용하지 않고
구현하게 되었습니다.

Q) 트랜잭션 처리에 대한 고민

A) JPA 를 사용하다 보면 더티 체크를 통해서 데이터가 변경하게 되면 update 되게 되는 특징이 있습니다.
osiv 를 false를 두게 되면 트랜잭션이 끝이 나게 되면 데이터가 변경을 하더라도 update 되지 않게 됩니다.
또한 read only를 통해서 트랜잭션에 대한 최적화를 진행하게 되었습니다.

Q) 테스트 케이스 에 대한 고민

A) 테스트 케이스의 작성을 하면서 에러 발생이 되는 케이스 혹은 어떤 상황에서 정상적으로 동작을 해야 하는지에 대한 테스트 케이스 작성을 하게 되었습니다.
또한 테스트 케이스에 대한 fake 데이터의 경우 JSON을 통해서 각각의 상황별 테스트를 유동적으로 적용하고 관리 할 수 있도록 JSON으로 작성하게 되었습니다.

Q) 코드 문서화 관리 및 문서화 관리

A) Readmd.md 를 작성하면서 생각했던 고민 중 ERD 다이어그램 그리고 각각의 데이터에 대한 속성이 필요하다고 생각이 되어서 작성하게 되었습니다.
또한 코드 관리를 javadocs 를 통해서 작성하게 되었습니다. 코드 또한 문서화가 필요하다고 생각이 됩니다. 코드를 문서화를 하게 되면 메소드를 통해서 분석을 하는 것이 아니라 주석을 통해서 명시적으로 알 수
있습니다.
또한 분석 시간을 단축할 수 있으며, 해당 시간을 통해서 다른일을 할 수 있을 거 같아서 작성하게 되었습니다. 하지만 단점으로 주석 관리를 하지 않는다면 오히려 더 많은 리소스를 잡을 수 있다는 생각이 듭니다.
오히려 가독성을 해칠 수 있다고
생각이 듭니다. 하지만 저는 java docs의 사용을 하면서 더 많은 장점을 가질 수 있다는 생각이 들어서 적용하게 되었습니다.

