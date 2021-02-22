### Prerrequisitos del servicio

* JRE 11.

## Testing

Para lanzar los tests, ejecuta:

```
./mvnw verify
```

### Arranque del servicio
```
./mvnw org.springframework.boot:spring-boot-maven-plugin:run
```

El servicio arranca por defecto en el puerto 8080 e inserta en la base de datos (H2 en memoria) los siguientes registros:

| BRAND_ID  | START_DATE  | END_DATE  | PRICE_LIST  | PRODUCT_ID  | PRIORITY  | PRICE  | CURR  |
|---|---|---|---|---|---|---|---|
| 1  | 2020-06-14-00.00.00  | 2020-12-31-23.59.59  | 1  | 35455  | 0  | 35.50  | EUR  |  
| 1  | 2020-06-14-15.00.00  | 2020-06-14-18.30.00  | 2  | 35455  | 1  | 25.45  | EUR  |  
| 1  | 2020-06-15-00.00.00  | 2020-06-15-11.00.00  | 3  | 35455  | 1  | 30.50  | EUR  |
| 1  | 2020-06-15-16.00.00  | 2020-12-31-23.59.59  | 4  | 35455  | 1  | 38.95  | EUR  |

#### Probar el servicio arrancado

Una vez arrancado el servicio se pueden realizar los tests pedidos mediante los siguientes comandos con Curl:

curl --location --request GET "http://localhost:8080/api/prices?brandId=1&productId=35455&date=2020-06-14T10:00:00"

curl --location --request GET "http://localhost:8080/api/prices?brandId=1&productId=35455&date=2020-06-14T16:00:00"

curl --location --request GET "http://localhost:8080/api/prices?brandId=1&productId=35455&date=2020-06-14T21:00:00"

curl --location --request GET "http://localhost:8080/api/prices?brandId=1&productId=35455&date=2020-06-15T10:00:00"

curl --location --request GET "http://localhost:8080/api/prices?brandId=1&productId=35455&date=2020-06-16T21:00:00"

Las respuestas tienen el siguiente formato: 

{
"productId": 35455,
"brandId": 1,
"priceList": 4,
"startDate": "2020-06-15T16:00:00",
"endDate": "2020-12-31T23:59:59",
"price": 38.95,
"currency": "EUR"
}

En el caso de pedir un producto que no se encuentra en la tabla de precios:

{
"timestamp": "2021-02-20T11:20:09.757+00:00",
"status": 404,
"error": "Not Found",
"message": "",
"path": "/api/prices"
}

O si el formato no es el adecuado (por ejemplo pasando una fecha que no sigue el formato ISO):

{
"timestamp": "2021-02-20T11:20:31.089+00:00",
"status": 400,
"error": "Bad Request",
"message": "",
"path": "/api/prices"
}