# Test
* http://localhost:8090/api/catalog/country/1
* http://localhost:8090/api/catalog/country/2
* http://localhost:5050/browser/
* ff4j: http://localhost:8090/api/catalog/ff4j-web-console/
  * https://ff4j.org/#10min
  * github
    * https://github.com/ff4j/ff4j 
    * https://github.com/ff4j/ff4j-spring-boot-starter-parent 
    * https://github.com/ff4j/ff4j-samples
    * https://github.com/jeandlv/ff4j-feature-toggling
  * https://ff4j.org/#10min
  * https://stackoverflow.com/questions/78469118/ff4j-web-console-does-not-render-correctly-and-does-not-flip-manually-the-featur
  * https://stackoverflow.com/questions/64029819/ff4j-web-console-is-not-displayed-properly?rq=2
# Sequence Diagrams
```mermaid
sequenceDiagram
    participant Client
    participant CountryController
    participant CurrencyService
    participant CurrencyRepository

    Client->>CountryController: GET /country
    CountryController->>CurrencyService: getAll(name)
    CurrencyService->>CurrencyRepository: findByCode(name)
    CurrencyRepository-->>CurrencyService: List<Currency>
    CurrencyService-->>CountryController: List<CurrencyDTO>
    CountryController-->>Client: ResponseEntity<List<CurrencyDTO>>

    Client->>CountryController: GET /country/{id}
    CountryController->>CurrencyService: getById(id)
    CurrencyService->>CurrencyRepository: findById(id)
    CurrencyRepository-->>CurrencyService: Optional<Currency>
    CurrencyService-->>CountryController: CurrencyDTO
    CountryController-->>Client: ResponseEntity<CurrencyDTO>

    Client->>CountryController: POST /country
    CountryController->>CurrencyService: save(CurrencyDTO)
    CurrencyService->>CurrencyRepository: save(Currency)
    CurrencyRepository-->>CurrencyService: Currency
    CurrencyService-->>CountryController: CurrencyDTO
    CountryController-->>Client: ResponseEntity<CurrencyDTO>

    Client->>CountryController: PUT /country/{id}
    CountryController->>CurrencyService: update(CurrencyDTO)
    CurrencyService->>CurrencyRepository: save(Currency)
    CurrencyRepository-->>CurrencyService: Currency
    CurrencyService-->>CountryController: CurrencyDTO
    CountryController-->>Client: ResponseEntity<CurrencyDTO>

    Client->>CountryController: DELETE /country/{id}
    CountryController->>CurrencyService: delete(id)
    CurrencyService->>CurrencyRepository: deleteById(id)
    CurrencyRepository-->>CurrencyService: void
    CurrencyService-->>CountryController: void
    CountryController-->>Client: ResponseEntity<Void>
```
# Entity Relationship Diagrams
* From Class
```mermaid
---
title:  From Class
---

erDiagram
    
    AUDIT {
        Long id
        LocalDateTime createdOn
        LocalDateTime updatedOn
    }

    BASE {
        Long id
        Long version
    }

    CITY {
        Long id
        String name
    }

    CONTINENT {
        Long id
        String name
    }

    COUNTRY {
        Long id
        String name
    }

    CURRENCY {
        Long id
        String code
        String name
    }

    STATE {
        Long id
        String code
        String name
        Boolean enabled
    }

    BASE ||--o{ AUDIT: "has"
    BASE ||--o{ CITY: "has"
    BASE ||--o{ CONTINENT: "has"
    BASE ||--o{ COUNTRY: "has"
    BASE ||--o{ CURRENCY: "has"
    BASE ||--o{ STATE: "has"
    STATE }o--|| COUNTRY: "belongs to"
    CITY }o--|| STATE: "belongs to"
    COUNTRY }o--|| CONTINENT: "belongs to"
```
* From SQL
```mermaid
---
title:  From SQL
---
erDiagram
    CURRENCY {
        bigint id PK
        varchar code
        varchar symbol
        varchar description
        int decimal_places
        BOOLEAN enabled
        timestamp created_on
        timestamp updated_on
    }

    COUNTRY {
        bigint id PK
        varchar code
        varchar name
        varchar locale
        varchar time_zone
        bigint currency_id FK
        BOOLEAN enabled
        timestamp created_on
        timestamp updated_on
    }

    STATE {
        bigint id PK
        varchar code
        varchar name
        BOOLEAN enabled
        bigint country_id FK
        timestamp created_on
        timestamp updated_on
    }

    CITY {
        bigint id PK
        varchar name
        BOOLEAN enabled
        bigint state_id FK
        timestamp created_on
        timestamp updated_on
    }

    CURRENCY ||--o{ COUNTRY : "has"
    COUNTRY ||--o{ STATE : "has"
    STATE ||--o{ CITY : "has"
```
# Class Diagram
```mermaid
classDiagram
    class Audit {
        -Long id
        -LocalDateTime createdOn
        -LocalDateTime updatedOn
        +Audit()
        +Audit(Long id)
        +Long getId()
        +void setId(Long id)
        +LocalDateTime getCreatedOn()
        +void setCreatedOn(LocalDateTime createdOn)
        +LocalDateTime getUpdatedOn()
        +void setUpdatedOn(LocalDateTime updatedOn)
        +boolean equals(Object o)
        +int hashCode()
    }

    class Base {
        -Long id
        -Audit audit
        -Long version
        +Base()
        +Base(Long id)
        +Long getId()
        +void setId(Long id)
        +Audit getAudit()
        +void setAudit(Audit audit)
        +Long getVersion()
        +void setVersion(Long version)
        +void fillCreatedOn()
        +void fillUpdatedOn()
        +boolean equals(Object o)
        +int hashCode()
    }

    class City {
        -Long id
        -String name
        -State state
        +City()
        +City(Long id, String name, State state)
        +Long getId()
        +void setId(Long id)
        +String getName()
        +void setName(String name)
        +State getState()
        +void setState(State state)
        +boolean equals(Object o)
        +int hashCode()
    }

    class Continent {
        -Long id
        -String name
        +Continent()
        +Continent(Long id, String name)
        +Long getId()
        +void setId(Long id)
        +String getName()
        +void setName(String name)
        +boolean equals(Object o)
        +int hashCode()
    }

    class Country {
        -Long id
        -String name
        -Continent continent
        +Country()
        +Country(Long id, String name, Continent continent)
        +Long getId()
        +void setId(Long id)
        +String getName()
        +void setName(String name)
        +Continent getContinent()
        +void setContinent(Continent continent)
        +boolean equals(Object o)
        +int hashCode()
    }

    class Currency {
        -Long id
        -String code
        -String name
        +Currency()
        +Currency(Long id, String code, String name)
        +Long getId()
        +void setId(Long id)
        +String getCode()
        +void setCode(String code)
        +String getName()
        +void setName(String name)
        +boolean equals(Object o)
        +int hashCode()
    }

    class State {
        -Long id
        -String code
        -String name
        -Boolean enabled
        -Country country
        +State()
        +State(Long id, String code, String name, Boolean enabled, Country country)
        +Long getId()
        +void setId(Long id)
        +String getCode()
        +void setCode(String code)
        +String getName()
        +void setName(String name)
        +Boolean getEnabled()
        +void setEnabled(Boolean enabled)
        +Country getCountry()
        +void setCountry(Country country)
        +boolean equals(Object o)
        +int hashCode()
    }

    Base <|-- Audit
    Base <|-- City
    Base <|-- Continent
    Base <|-- Country
    Base <|-- Currency
    Base <|-- State
    State --> Country
    City --> State
    Country --> Continent
```