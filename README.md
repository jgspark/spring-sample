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

### 1.  계약 생성 API

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
