package com.t1t.t1c.utils;

import com.google.common.base.Preconditions;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.model.rest.GclCard;
import com.t1t.t1c.model.rest.GclContainer;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class ContainerUtil {

    private static final Logger log = LoggerFactory.getLogger(ContainerUtil.class);

    private ContainerUtil() {
    }

    public static ContainerType determineContainer(GclCard card) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(card.getDescription()), "Card description must be present");
        for (String description : card.getDescription()) {
            ContainerType type = ContainerType.valueOfCardDescription(description);
            if (type != null) return type;
        }
        throw ExceptionFactory.genericContainerException("Could not determine container for card");
    }

    public static boolean isContainerAvailable(GclCard card, List<GclContainer> availableContainers) {
        return isContainerAvailable(determineContainer(card), availableContainers);
    }

    public static boolean isContainerAvailable(ContainerType type, List<GclContainer> availableContainers) {
        for (GclContainer container : availableContainers) {
            if (container.getId().equalsIgnoreCase(type.getId())) return true;
        }
        return false;
    }

    public static Integer getPinVerificationRetriesLeftFor(Integer code) {
        if (code != null) {
            switch (code) {
                case 111:
                    return 1;
                case 112:
                    return 3;
                case 103:
                    return 2;
                case 104:
                    return 1;
                case 105:
                    return 0;
                default:
                    log.warn("GCL error code does not match known PIN retries codes: ", code);
                    return null;
            }
        } else {
            return null;
        }
    }
}