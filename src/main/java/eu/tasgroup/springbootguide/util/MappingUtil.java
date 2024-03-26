package eu.tasgroup.springbootguide.util;

import org.slf4j.MDC;
import eu.tasgroup.springbootguide.controller.model.ComponentEnum;

import static eu.tasgroup.springbootguide.util.AppConstant.*;

public class MappingUtil {

    private MappingUtil() {
    }

    public static String callerId() {
        return MDC.get(CALLER_ID_KEY);
    }

}
