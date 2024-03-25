# Aggiunta di MapStruct a un Progetto Spring Boot

In questa guida, vedremo come aggiungere e utilizzare MapStruct, un framework di mappatura degli oggetti in Java, in un progetto Spring Boot.

## Passaggi

Segui questi passaggi per aggiungere MapStruct al tuo progetto Spring Boot:

### 1. Aggiunta delle Dipendenze e Plugin Necessari

- Per iniziare, aggiungi la dipendenza MapStruct al file pom.xml del tuo progetto:

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

<!--Consente di integrare Lombok e MapStruct nel processo di compilazione del progetto Maven-->
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-compiler-plugin</artifactId>
<version>3.11.0</version>
<configuration>
    <release>17</release>
    <annotationProcessorPaths>
        <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
        </path>
        <path>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>1.5.5.Final</version>
        </path>
        <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok-mapstruct-binding</artifactId>
            <version>0.2.0</version>
        </path>
    </annotationProcessorPaths>
</configuration>
</plugin>
```

### 2. Creazione della Classe Astratta Mapper:
La classe astratta Mapper è una classe fornita da MapStruct che contiene i metodi di mappatura tra oggetti.
- Crea la classe astratta Mapper ([MapperDemoDto.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fservice%2Fmapper%2FMapperDemoDto.java)) annotando la classe con `@Mapper` per indicare a MapStruct di generare l'implementazione della classe per te.  Si può configurare con vari attributi, come `componentModel`, che specifica il modello di componente da utilizzare (ad esempio, "spring" per l'integrazione con Spring), e `unmappedTargetPolicy`, che gestisce il comportamento quando si verificano campi non mappati.
- Definisci i metodi di mappatura astratti che ti possono servire, come un mapping da [DemoRequest.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fcontroller%2Fmodel%2FDemoRequest.java) a [DemoRequestDto.java](src%2Fmain%2Fjava%2Feu%2Ftasgroup%2Fspringbootguide%2Fservice%2Fmodel%2FDemoRequestDto.java).
- Utilizza l'annotazione `@Mapping` per definire la mappatura tra campi di origine e destinazione. È possibile personalizzare la mappatura specificando l'attributo `target`, che indica il campo di destinazione, e `source`, che indica il campo di origine. È anche possibile utilizzare l'attributo `expression` per fornire una logica personalizzata per la mappatura di un campo.