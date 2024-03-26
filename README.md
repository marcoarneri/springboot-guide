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

### 2. Aggiunta properties

```properties
#sostituire il root di AppServerLogging con quello del proprio progetto
logging.level.eu.tasgroup.springbootguide.util.log.AppServerLogging=DEBUG

log.server.request.include-headers=true
log.server.request.include-client-info=true
log.server.request.include-payload=true
log.server.request.max-payload-length=10000
log.server.response.include-headers=true
log.server.response.include-payload=true
log.server.response.max-payload-length=10000
log.client.request.include-headers=true
log.client.request.include-payload=true
log.client.request.max-payload-length=10000
log.client.response.include-headers=true
log.client.response.include-payload=true
log.client.response.max-payload-length=10000

cors.configuration=${CORS_CONFIGURATION:{"origins": ["*"], "methods": ["*"]}}
```

### 3. Dichiarazione Interceptor

- Dichiarazione dell'interceptor nella classe di configurazione [MvcConfiguration.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fconfig%2FMvcConfiguration.java) e passare i parametri da properties per la configurazione

### 4. Creazione AbstractAppServerLogging

- Creazione della classe astratta [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java) e implementare l'interfaccia HandlerInterceptor

### 5. Creazione AppServerLogging

- Creazione della classe [AppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAppServerLogging.java) e estendere la classe astratta [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java)

## Funzionamento

### Log e salvataggio della richiesta

- Quando arriva la richiesta entra nel metodo `preHandle` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java)
- nel metodo `preHandle` viene chiamato il metodo `shouldRequestLog` della classe [AppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAppServerLogging.java) che torna un booleano che indica se i log sono attivi in DEBUG (vedere controllo nel metodo `preHandle` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java)), i log si attivano tramite properties nella classe [application.properties](src%2Fmain%2Fresources%2Fapplication.properties) -> `logging.level.eu.tasgroup.springbootguide.util.log.AppServerLogging=DEBUG`
- entra nel metodo `getRequestMessage` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java), e richiama il metodo `createRequestMessage` che crea il messaggio di log della richiesta.
- entra nel metodo `request` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java) e logga effettivamente la richiesta
- dopo aver loggato la richiesta entra nel metodo `requestDB` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java) che si occupa di chiamare l'[AccessLogService.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fservice%2FAccessLogService.java) e di salvare a db sulla tabella `demo_access_log` i dati della richiesta.

### Log e salvataggio della risposta

- quando ottengo la risposta viene chiamato il metodo `shouldResponseLog` della classe [AppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAppServerLogging.java) che torna un booleano che indica se i log sono attivi in DEBUG, i log si attivano tramite properties nella classe [application.properties](src%2Fmain%2Fresources%2Fapplication.properties) -> `logging.level.eu.tasgroup.springbootguide.util.log.AppServerLogging=DEBUG`
- entra nel metodo `getResposneMessage` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java), e richiama il metodo `createResponseMessage` che crea il messaggio di log della risposta.
- entra nel metodo `response` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java) e logga effettivamente la risposta
- dopo aver loggato la risposta entra nel metodo `responseDB` della classe [AbstractAppServerLogging.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Futil%2Flog%2FAbstractAppServerLogging.java) che si occupa di chiamare l'[AccessLogService.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fservice%2FAccessLogService.java) e di salvare a db sulla tabella `demo_access_log` i dati della risposta.
