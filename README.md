# 거래 시스탬

## 목차

> 링크 변경 할 것

* [RD 다이어그램](ERD-다이어그램)
* [API 정의](API-정의)
* [실행 방법 및 테스트 실행 방법 ](실행-방법-및-테스트-실행-방법)
* [고민 포인트 및 생각 & 해결 방법](고민-포인트-및-생각-&-해결-방법)

## ERD 다이어그램

## API 정의

### 1. 결제 생성 API

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

**Response**

### 2. 거래 데이터 조회 API

```text
curl --location --request GET 'http://localhost:8080/contracts/1'
```

### 3. 거래 내역 수정 API

```text
curl --location --request PATCH 'http://localhost:8080/contracts/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "warrantIds" : [1 , 2] ,
    "term" : 10,
    "state" : "WITHDRAWAL"
}'
```

### 4. 예상 총 보험료 API

```text
curl --location --request GET 'http://localhost:8080/products/2/premium?warrantIds=2,3'
```

### 5. 상품 생성 API

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

### 6. 담보 생성 API

```text
curl --location --request POST 'http://localhost:8080/warrant' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title" : "상해치료",
    "subscriptionAmount" : 1000000,
    "standardAmount" : 100
}'
```

## 실행 방법 및 테스트 실행 방법

## 고민 포인트 및 생각 & 해결 방법
