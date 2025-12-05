# API Gateway - Документация

## Важное примечание о Zuul

**Zuul 1.x не поддерживается в Spring Boot 3.x** из-за несовместимости с Jakarta EE (Zuul использует старые javax.* пакеты).

Вместо Zuul используется **Spring Cloud Gateway**, который:
- ✅ Предоставляет те же возможности, что и Zuul Proxy
- ✅ Поддерживается в Spring Boot 3.x
- ✅ Имеет лучшую производительность (на основе WebFlux)
- ✅ Поддерживает реактивное программирование
- ✅ Имеет более гибкую систему фильтров

Если требуется именно Zuul, необходимо использовать Spring Boot 2.x, что несовместимо с остальными сервисами проекта.

---

## Структура API Gateway

### Компоненты:

1. **ApiGatewayApplication.java** - главный класс приложения
2. **application.properties** - конфигурация маршрутов и настроек
3. **RequestLoggingFilter.java** - фильтр для логирования входящих запросов
4. **ResponseLoggingFilter.java** - фильтр для логирования исходящих ответов

---

## Маршрутизация

### Organizations Service
- **Внешний путь:** `/api/organizations/**`
- **Внутренний сервис:** `organizations-service` (через Eureka)
- **Пример:** 
  - Запрос: `GET http://localhost:8080/api/organizations/1`
  - Проксируется в: `GET http://organizations-service/1`

### OrgDirectories Service
- **Внешний путь:** `/api/orgdirectory/**`
- **Внутренний сервис:** `orgdirectories-service` (через Eureka)
- **Пример:**
  - Запрос: `POST http://localhost:8080/api/orgdirectory/filter/type/PUBLIC`
  - Проксируется в: `POST http://orgdirectories-service/api/orgdirectory/filter/type/PUBLIC`

---

## Конфигурация

### Порт
- Gateway работает на порту **8080**

### Service Discovery
- Использует Eureka для обнаружения сервисов
- Все маршруты используют `lb://` префикс для Load Balancing

### CORS
- Настроен для работы с любыми источниками
- Поддерживает все HTTP методы

### Timeouts
- Connect timeout: 5 секунд
- Response timeout: 30 секунд

---

## Фильтры

### RequestLoggingFilter
- Логирует все входящие запросы
- Добавляет заголовок `X-Gateway-Request: true`
- Порядок выполнения: HIGHEST_PRECEDENCE (выполняется первым)

### ResponseLoggingFilter
- Логирует все исходящие ответы
- Порядок выполнения: LOWEST_PRECEDENCE (выполняется последним)

---

## Использование

### До Gateway:
```
Клиент → organizations-service:8081/organizations/1
Клиент → orgdirectories-service:8082/api/orgdirectory/filter/type/PUBLIC
```

### После Gateway:
```
Клиент → api-gateway:8080/api/organizations/1
         ↓ (проксируется)
         organizations-service/organizations/1

Клиент → api-gateway:8080/api/orgdirectory/filter/type/PUBLIC
         ↓ (проксируется)
         orgdirectories-service/api/orgdirectory/filter/type/PUBLIC
```

---

## Преимущества

1. **Единая точка входа** - все запросы идут через один порт
2. **Service Discovery** - автоматическое обнаружение сервисов через Eureka
3. **Load Balancing** - автоматическое распределение нагрузки
4. **Централизованное логирование** - все запросы логируются в одном месте
5. **CORS** - централизованная настройка CORS
6. **Безопасность** - можно добавить аутентификацию/авторизацию на уровне Gateway

---

## Порядок запуска

1. Eureka Server (порт 8761)
2. Config Server (порт 8888) - опционально
3. Organizations Service (порт 8081)
4. OrgDirectories Service (порт 8082)
5. **API Gateway (порт 8080)** - должен быть запущен последним

---

## Проверка работы

1. Проверьте регистрацию в Eureka: `http://localhost:8761`
2. Проверьте health endpoint: `http://localhost:8080/actuator/health`
3. Проверьте маршруты: `http://localhost:8080/actuator/gateway/routes`
4. Протестируйте проксирование:
   - `GET http://localhost:8080/api/organizations/1`
   - `POST http://localhost:8080/api/orgdirectory/filter/type/PUBLIC`

