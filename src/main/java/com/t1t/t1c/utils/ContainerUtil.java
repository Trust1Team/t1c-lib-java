package com.t1t.t1c.utils;

import com.google.common.base.Preconditions;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.FactoryService;
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

    public static boolean isContainerAvailable(GclCard card) {
        return isContainerAvailable(determineContainer(card));
    }

    public static boolean isContainerAvailable(ContainerType type) {
        List<GclContainer> availableContainers = FactoryService.getGclClient().getContainers();
        for (GclContainer container : availableContainers) {
            if (container.getId().equalsIgnoreCase(type.getId())) return true;
        }
        return false;
    }

}