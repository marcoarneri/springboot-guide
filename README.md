# Aggiunta della Gestione dei Log a un Progetto Spring Boot

Questa guida ti illustrerà come integrare la gestione dei log in un progetto Spring Boot per monitorare e registrare le attività e gli eventi all'interno dell'applicazione.
## Passaggi

Segui questi passaggi per aggiungere la gestione dei log al tuo progetto Spring Boot:

### 1. Aggiunta delle Dipendenze Necessarie

- Per iniziare, aggiungi le seguenti dipendenze al tuo file `pom.xml`:

```xml
<dependencys>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.0.0.Final</version>
    </dependency>
</dependencys>
```