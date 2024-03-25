# Aggiunta MVC a un Progetto Spring Boot

In questa guida, vedremo come aggiungere la parte MVC (Model-View-Controller) a un progetto Spring Boot. Il pattern MVC è ampiamente utilizzato nello sviluppo web per separare la logica di business (Model), la presentazione dei dati (View) e la gestione delle richieste degli utenti (Controller).

## Passaggi

Segui questi passaggi per aggiungere la parte MVC al tuo progetto Spring Boot:

### 1. Aggiunta delle Dipendenze Necessarie

- Per iniziare, aggiungi le seguenti dipendenze al tuo file `pom.xml` per gestire la validazione dei dati e semplificare la creazione di classi modello:

```xml
<!--VALIDATION-->
<!--Fornisce supporto per la validazione dei dati-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!--LOMBOK-->
<!--Semplifica la creazione di classi modello aggiungendo automaticamente getter, setter e altri metodi-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

### 2. Creazione del Controller:
Il controller gestisce le richieste HTTP degli utenti e coordina l'interazione tra il modello e la vista.

- Crea una nuova classe per il controller, ad esempio [DemoController.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fcontroller%2FDemoController.java).
- All'interno del controller, definisci i metodi per gestire le diverse richieste HTTP, come `@GetMapping`, `@PostMapping`, ecc.
- Definisci i modelli di richiesta ([DemoRequest.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fcontroller%2Fmodel%2FDemoRequest.java)) e di risposta ([DemoResponse.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fcontroller%2Fmodel%2FDemoResponse.java)) utilizzati nei metodi del controller per mappare i dati inviati dall'utente e le risposte restituite al client.

### 3. Creazione del Servizio:
Il servizio contiene la logica di business dell'applicazione e comunica con il repository dei dati.
- Crea una nuova classe per il servizio, ad esempio [DemoService.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fservice%2FDemoService.java).
- All'interno del servizio, implementa i metodi per eseguire le operazioni di business, come il recupero dei dati dal database, l'elaborazione dei dati e altro ancora.
- Utilizza i Data Transfer Object (DTO) come [DemoRequestDto.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fservice%2Fmodel%2FDemoRequestDto.java) per trasferire i dati tra il controller e il servizio in modo sicuro e efficiente.