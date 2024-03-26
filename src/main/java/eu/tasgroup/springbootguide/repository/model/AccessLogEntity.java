package eu.tasgroup.springbootguide.repository.model;

import eu.tasgroup.springbootguide.controller.model.ComponentEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name = "ACA_ACCESS_LOG")
@Data
@NoArgsConstructor
public class AccessLogEntity {

  @SequenceGenerator(
          name = "AccessLogEntityGenerator",
          sequenceName = "SEQ_ACA_ACCESS_LOG")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "AccessLogEntityGenerator")
  @Column(name = "ID")
  private Long id;

  @Column(name = "CREATE_ON")
  private Instant createOn;

  @Column(name = "CALLER_ID")
  private String callerId;

  @Column(name = "LOG_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private AccessLogTypeEnum logType;

  @Column(name = "SERVER_ID")
  private String serverId;

  @Column(name = "COMPONENT", nullable = false)
  @Enumerated(EnumType.STRING)
  private ComponentEnum component;

  @Column(name = "CLIENT_ID")
  private String clientId;

  @Column(name = "METHOD")
  private String method;

  @Column(name = "URI_WITH_QUERY_STRING",length = 1024)
  private String uriWithQueryString;

  @Column(name = "CLIENT_INFO")
  private String clientInfo;

  @Column(name = "SCHEDULED_ID")
  private String scheduledId;

  @Column(name = "STATUS")
  private Long status;

  @Column(name = "HEADERS", columnDefinition = "TEXT")
  @Lob
  private String headers;

  @Column(name = "PAYLOAD", columnDefinition = "TEXT")
  @Lob
  private String payload;

  @Column(name = "ELAPSED")
  private Long elapsed;

  @PrePersist
  public void onPrePersist() {
    this.setCreateOn(Instant.now());
  }

}
