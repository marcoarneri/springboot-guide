package eu.tasgroup.springbootguide.repository;

import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DemoRepository
        extends JpaRepository<DemoEntity, Long> {

//    Metodo di query derivato: Questo è il metodo più comune e più semplice. In questo approccio, scrivi un metodo all'interno
//    dell'interfaccia del repository con un nome specifico seguendo le convenzioni di denominazione di Spring Data JPA.
//    Il framework si occupa di interpretare il nome del metodo e genera automaticamente la query corrispondente. Ad esempio,
//    findByPaFiscalCode(String paFiscalCode) è un metodo di query derivato, che cerca un'entità in base al suo attributo
//    paFiscalCode.
    DemoEntity findByPaFiscalCode(String paFiscalCode);

    @Query("SELECT u FROM DemoEntity u WHERE u.paFiscalCode = :paFiscalCode")
    DemoEntity findByPaFiscalCode2(@Param("paFiscalCode") String paFiscalCode);
//    Annotazione @Query: Questo approccio consente di scrivere query personalizzate in JPQL (Java Persistence Query Language)
//    o SQL direttamente sopra il metodo del repository utilizzando l'annotazione @Query. Questo fornisce maggiore flessibilità
//    nel definire query complesse che non possono essere gestite facilmente tramite metodi di query derivati. Questo è utile
//    soprattutto quando hai bisogno di condizioni di ricerca più complesse, join tra entità o query particolarmente ottimizzate.
}


//    Operazioni CRUD di base: JpaRepository estende CrudRepository, il che significa che erediti automaticamente i metodi per le
//    operazioni CRUD (Create, Read, Update, Delete). Questi metodi includono save, findAll, findById, delete, ecc. Anche se la tua
//    classe repository è vuota, hai ancora accesso a queste operazioni di base attraverso l'ereditarietà.
