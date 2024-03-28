package eu.tasgroup.springbootguide.repository.specification;

import eu.tasgroup.springbootguide.repository.model.DemoEntity;
import org.springframework.data.jpa.domain.Specification;

public class DemoSpecifications{

  private DemoSpecifications() {}

  public static Specification<DemoEntity> noticeId (String noticeId) {
    return (root, query, builder) -> builder.equal(root.get("noticeId"), noticeId);
  }

  public static Specification<DemoEntity> iuv (String iuv) {
      return (root, query, builder) -> builder.equal(root.get("iuv"), iuv);
  }
}
