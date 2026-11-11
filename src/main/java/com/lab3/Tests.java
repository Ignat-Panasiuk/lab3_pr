package com.lab3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    private static final String TEST_XML = "test_items.xml";
    private static final String TEST_JSON = "test_items.json";
    private static final String TEST_TXT = "test_file.txt";
    private static final String TEST_ZIP = "test_archive.zip";
    private List<Item> testItems;

    @BeforeEach
    public void setUp() throws IOException {
        testItems = new ArrayList<>();
        testItems.add(new Item(2, "Beta Model", 200.0, 16, new Date()));
        testItems.add(new Item(1, "Alpha Model", 100.0, 8, new Date()));

        try (FileWriter writer = new FileWriter(TEST_TXT)) {
            writer.write("1; Test Laptop; 1500.0; 16; 2026-01-01\n");
            writer.write("invalid_line_format\n");
        }
    }

    @AfterEach
    public void tearDown() {
        new File(TEST_XML).delete();
        new File(TEST_JSON).delete();
        new File(TEST_TXT).delete();
        new File(TEST_ZIP).delete();
    }

    @Test
    public void testItemModel() {
        Date now = new Date();
        Item item = new Item(10, "Test", 300.0, 4, now);
        assertEquals(10, item.getId());
        assertEquals("Test", item.getModelName());
        assertEquals(300.0, item.getPrice());
        assertEquals(4, item.getRamSize());
        assertEquals(now, item.getReleaseDate());

        item.setModelName("NewTest");
        assertEquals("NewTest", item.getModelName());
        assertNotNull(item.toString());
    }

    @Test
    public void testAbstractItemAndGetters() {
        AbstractItem item = new Item(5, "AbstractTest", 500.0, 8, new Date());
        assertEquals(5, item.getId());
        assertEquals("AbstractTest", item.getModel());
        assertEquals(500.0, item.getPrice());
    }

    @Test
    public void testSortingAndCollections() {
        testItems.sort(Comparator.comparing(Item::getPrice));
        assertEquals(100.0, testItems.get(0).getPrice());
        assertEquals(200.0, testItems.get(1).getPrice());

        testItems.sort(Comparator.comparing(Item::getModelName));
        assertEquals("Alpha Model", testItems.get(0).getModelName());
    }

    @Test
    public void testStorageTxtLoading() {
        Storage storage = new Storage();
        storage.loadFromFile(TEST_TXT);
        
        assertNotNull(storage.getItemList());
        assertEquals(1, storage.getItemList().size());
        assertEquals("Test Laptop", storage.getItemList().get(0).getModelName());
        assertNotNull(storage.getItemMap());
        assertEquals(1, storage.getItemMap().size());
    }

    @Test
    public void testXmlDom() {
        XmlDom.saveToFile(testItems, TEST_XML);
        List<Item> loaded = XmlDom.readFromFile(TEST_XML);
        assertNotNull(loaded);
        assertEquals(2, loaded.size());
        assertEquals("Beta Model", loaded.get(0).getModelName());
    }

    @Test
    public void testXmlSax() {
        XmlDom.saveToFile(testItems, TEST_XML);
        List<Item> loaded = XmlSax.readFromFile(TEST_XML);
        assertNotNull(loaded);
        assertEquals(2, loaded.size());
    }

    @Test
    public void testJsonHandler() {
        JsonHandler.saveToFile(testItems, TEST_JSON);
        List<Item> loaded = JsonHandler.readFromFile(TEST_JSON);
        assertNotNull(loaded);
        assertEquals(2, loaded.size());
        assertEquals(100.0, loaded.get(1).getPrice());
    }

    @Test
    public void testEncryptionAndHash() {
        String pass = "secret123";
        String hash1 = EncryptionHandler.hashPassword(pass);
        String hash2 = EncryptionHandler.hashPassword(pass);
        assertEquals(hash1, hash2);

        SecretKey key = EncryptionHandler.generateKey();
        assertNotNull(key);

        String original = "Secret Text";
        String encrypted = EncryptionHandler.encrypt(original, key);
        String decrypted = EncryptionHandler.decrypt(encrypted, key);
        assertEquals(original, decrypted);
    }

    @Test
    public void testArchive() {
        ArchiveHandler.zipFile(TEST_TXT, TEST_ZIP);
        File zip = new File(TEST_ZIP);
        assertTrue(zip.exists());
        assertTrue(zip.length() > 0);
    }
}