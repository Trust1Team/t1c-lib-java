package com.t1t.t1c.integration;

import com.t1t.t1c.model.rest.GclReader;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class CoreIntegrationTest extends AbstractT1cClientIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(CoreIntegrationTest.class);

    @Test(timeout = 11000)
    public void pollWithoutInsertedReader() throws Exception {
        List<GclReader> readers = getClient().getCore().pollReaders();
        assertTrue(CollectionUtils.isEmpty(readers));
    }

    @Test(timeout = 11000)
    public void pollReadersWhileInsertingReader() throws Exception {
        List<GclReader> readers = getClient().getCore().pollReaders();
        assertTrue(CollectionUtils.isNotEmpty(readers));
    }

    @Test(timeout = 11000)
    public void pollReadersWithoutInsertedCard() throws Exception {
        List<GclReader> readers = getClient().getCore().pollReadersWithCards();
        assertTrue(CollectionUtils.isEmpty(readers));
    }

    @Test(timeout = 11000)
    public void pollReadersWhileInsertingCard() throws Exception {
        List<GclReader> readers = getClient().getCore().pollReadersWithCards();
        assertTrue(CollectionUtils.isNotEmpty(readers));
    }

    @Test(timeout = 11000)
    public void pollReaderWithoutInsertedCard() throws Exception {
        GclReader reader = getClient().getCore().pollCardInserted();
        assertTrue(reader == null);
    }

    @Test(timeout = 11000)
    public void pollReaderWhileInsertingCard() throws Exception {
        GclReader reader = getClient().getCore().pollCardInserted();
        assertTrue(reader != null);
    }

}