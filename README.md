# Tutorial SpringBoot - Payroll

A little SpringBoot tutorial to refresh my memory ([source](https://spring.io/guides/tutorials/rest/)).

I wanted to apply DDD by strictly cutting each layer. This is why there are several DTOs (one at the "presentation" level and another in "application").
There is also a distinction between the "Order" object and the "OrderJPA" object. The latter is only used for the DB, whereas the first is limited to the domain.

TODO :
- I haven't managed the initial state of an order and the automatic generation of the UUID via a POST yet.
- Tests (even if they should have been done first ;) )

## Command

- curl -v localhost:8080/orders/ | json_pp
- curl -v -X DELETE "http://localhost:8080/orders/5cd223aa-9dc2-454d-9124-85f8c56d6e77/cancel" | json_pp
- curl -v -X PUT "http://localhost:8080/orders/e0ddb9cc-b87b-45ed-8572-3bc166706189/complete" | json_pp
- curl -v -X POST localhost:8080/order -H 'Content-Type:application/json' -d '{"uuid": "9994709f-a932-4a8d-9515-487faeae681d", "description": "Dell Latitude", "status": "IN_PROGRESS"}' | json_pp

More about [json_pp](https://stackoverflow.com/a/42012541).
