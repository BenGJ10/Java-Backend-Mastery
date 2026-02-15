# HTTP Status Codes Guide 

## 1. Overview

HTTP status codes are **standardized response codes** returned by servers to indicate the result of a client’s request.

They are critical for:

* REST API design
* client-server communication
* debugging
* distributed systems
* backend interviews

Proper usage improves:

* API clarity
* error handling
* client behavior
* observability

---

## 2. Status Code Categories

Every HTTP status code belongs to one of five classes.

| Range   | Category      | Meaning                                |
| ------- | ------------- | -------------------------------------- |
| **1xx** | Informational | Request received, processing continues |
| **2xx** | Success       | Request successfully processed         |
| **3xx** | Redirection   | Further action required                |
| **4xx** | Client Errors | Client made a bad request              |
| **5xx** | Server Errors | Server failed to fulfill request       |

---

## 1xx — Informational Responses

Rare in everyday backend development.

### 100 Continue

Client may continue sending request body.

Used in large uploads.

---

### 101 Switching Protocols

Server agrees to switch protocols.

Example:

* HTTP → WebSocket upgrade

---

### 102 Processing

Server has received request but not completed it.

Mostly used in WebDAV.

---

## 2xx — Success Responses

Most important category for API developers.

---

## 200 OK

**Standard success response.**

Used for:

* GET
* PUT
* PATCH
* DELETE (sometimes)

Example:

```
GET /users/1 → 200 OK
```

---

## 201 Created

Used when a resource is successfully created.

Best practice:

* return resource location

```
POST /users → 201 Created
```

Optional header:

```
Location: /users/10
```

---

## 202 Accepted

Request accepted but not processed yet.

Used in:

* async jobs
* message queues
* batch processing

Example:

```
POST /reports → 202 Accepted
```

---

## 204 No Content

Request succeeded but nothing to return.

Common for:

* DELETE
* idempotent updates

No response body allowed.

---

## 206 Partial Content

Used when returning part of a resource.

Example:

* video streaming
* large file downloads

Supports range headers.

---

## 3xx — Redirection

Less common in REST APIs, more in browser workflows.

---

## 301 Moved Permanently

Resource permanently moved.

Search engines update URLs.

---

## 302 Found

Temporary redirect.

Browser automatically follows.

---

## 304 Not Modified

Used with caching.

Client can reuse cached response.

Reduces bandwidth usage.

---

## 307 Temporary Redirect

Same as 302 but preserves HTTP method.

Important for POST safety.

---

## 308 Permanent Redirect

Permanent redirect that preserves method.

---

## 4xx — Client Errors

Extremely important for backend developers.

Indicates **client mistake**, not server failure.

---

## 400 Bad Request

Malformed request.

Common causes:

* invalid JSON
* validation failure
* missing fields

Typical Spring exception:

```
MethodArgumentNotValidException
```

---

## 401 Unauthorized

Authentication is required or failed.

Examples:

* missing JWT
* invalid token
* wrong credentials

**Important:**

401 means **not authenticated**.

---

## 403 Forbidden

User is authenticated but lacks permission.

Example:

```
ROLE_USER accessing admin endpoint
```

**401 vs 403 is a favorite interview question.**

---

## 404 Not Found

Resource does not exist.

Example:

```
GET /users/999
```

Avoid leaking internal details.

---

## 405 Method Not Allowed

Endpoint exists but HTTP method is unsupported.

Example:

```
POST on a GET-only endpoint
```

---

## 406 Not Acceptable

Server cannot produce requested format.

Rare today due to JSON dominance.

---

## 408 Request Timeout

Client took too long to send request.

---

## 409 Conflict

Request conflicts with server state.

Common cases:

* duplicate email
* version conflicts
* concurrent updates

Very useful in REST design.

---

## 410 Gone

Resource permanently deleted.

Stronger than 404.

---

## 412 Precondition Failed

Conditions in headers were not met.

Used with:

* optimistic locking
* ETags

---

## 413 Payload Too Large

Request body exceeds server limits.

Example:

* huge file upload

---

## 415 Unsupported Media Type

Unsupported content type.

Example:

```
Content-Type: text/xml
```

when API expects JSON.

---

## 422 Unprocessable Entity

Valid request format, but semantic error.

Common in modern APIs.

Example:

* invalid business rule
* weak password

Often preferred over 400.

---

## 429 Too Many Requests

Client exceeded rate limits.

Critical for:

* API gateways
* DDoS protection

Usually paired with:

```
Retry-After header
```

---

## 5xx — Server Errors

Indicates backend failure.

Clients typically cannot fix these.

---

## 500 Internal Server Error

Generic server failure.

Avoid exposing stack traces.

Always log internally.

---

## 501 Not Implemented

Server does not support requested functionality.

Rare.

---

## 502 Bad Gateway

Gateway received invalid response from upstream.

Common in:

* microservices
* load balancers

---

## 503 Service Unavailable

Server temporarily overloaded or down.

Examples:

* maintenance
* traffic spikes

Often includes:

```
Retry-After
```

---

## 504 Gateway Timeout

Upstream service failed to respond in time.

Typical in distributed systems.

---

## Most Important Status Codes for Backend APIs

If you remember only these, you are production-ready:

| Code | Meaning                   |
| ---- | ------------------------- |
| 200  | Success                   |
| 201  | Created                   |
| 204  | No Content                |
| 400  | Bad request               |
| 401  | Not authenticated         |
| 403  | Not authorized            |
| 404  | Not found                 |
| 409  | Conflict                  |
| 422  | Validation/business error |
| 429  | Rate limited              |
| 500  | Server error              |
| 503  | Service unavailable       |

---

## REST API Best Practices

### Use Correct Status Codes

Do NOT return:

```
200 for everything
```

Clients rely on status codes for behavior.

---

### Pair Codes with Meaningful Responses

Example error format:

```json
{
  "status": 404,
  "message": "User not found",
  "timestamp": "2026-02-10T10:00:00"
}
```

---

### Align with HTTP Semantics

* POST → 201
* DELETE → 204
* Unauthorized → 401

---

## Key Takeaways

* HTTP status codes communicate request outcomes

* Proper usage improves API quality

* 4xx → client problem

* 5xx → server problem

* Always distinguish 401 vs 403

* Never overuse 200

* Status codes are foundational to REST design

---

