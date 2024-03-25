# Aggiunta JPA a un Progetto Spring Boot
In questa guida, vedremo come aggiungere JPA (Java Persistence API) a un progetto Spring Boot. JPA è uno standard Java per la gestione della persistenza dei dati in applicazioni Java EE e Spring Boot fornisce un'implementazione semplice e potente di JPA per interagire con il database relazionale.

## Passaggi

Segui questi passaggi per aggiungere JPA al tuo progetto Spring Boot:

### 1. Aggiunta delle Dipendenze Necessarie

- Per iniziare, aggiungi la seguente dipendenza al tuo file `pom.xml` per integrare JPA nel tuo progetto:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### 2. Creazione delle Entity
Le entità sono oggetti Java che corrispondono direttamente alle tabelle del database.
 
- Crea le entità utilizzando l'annotazione `@Entity` ([DemoEntity.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fentity%2FDemoEntity.java)) e definisci le proprietà della tabella come attributi della classe.
- Un'entità deve avere un identificatore univoco che la distingua da altre istanze della stessa classe. Questo è solitamente rappresentato da un campo annotato con `@Id`.
-  Gli attributi di un'entità rappresentano le colonne della tabella nel database. Ogni attributo può essere annotato con varie annotazioni JPA per definire il mapping dei dati.

### 2. Creazione del Repository
Il repository è un'interfaccia che estende `JpaRepository` e fornisce metodi per interagire con il database.

- Crea un'interfaccia di repository che estende `JpaRepository<Entity, ID>`, es. ([DemoRepository.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Frepository%2FDemoRepository.java)), dove Entity è il tipo dell'entità e ID è il tipo della chiave primaria.
- All'interno dell'interfaccia repository, puoi definire metodi per eseguire operazioni di base come il recupero, l'inserimento, l'aggiornamento e l'eliminazione delle entità nel database.