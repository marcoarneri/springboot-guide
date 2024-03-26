package eu.tasgroup.springbootguide.util;

import org.slf4j.MDC;
import eu.tasgroup.springbootguide.controller.model.ComponentEnum;

import static eu.tasgroup.springbootguide.util.AppConstant.*;

public class MappingUtil {

    private MappingUtil() {}

    public static ComponentEnum component() {
        return ComponentEnum.valueOf(MDC.get(MDC_COMPONENT));
    }

    public static String callerId() {
        return MDC.get(CALLER_ID_KEY);
    }

    public static String scheduledId() {
        return MDC.get(MDC_SCHEDULED_ID);
    }
}
