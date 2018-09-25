package com.indizen;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;

public class Helpers {

    public static void printJson(Document document) {

        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(
                JsonMode.SHELL, true
        ));

        new DocumentCodec().encode(jsonWriter, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());

        System.out.println(jsonWriter.getWriter());
        System.out.flush();
    }

    public static void augmentCollection(MongoCollection<Document> collection, long elements, boolean queryDocs) {

        Supplier<Document> randomDocumentSupplier = !queryDocs ? randomDocument() : randomQueryDocument();
        Stream<Document> infiniteStreamOfRandomDocuments = Stream.generate(randomDocumentSupplier);

        List<Document> randomDocs = infiniteStreamOfRandomDocuments
                .skip(10)
                .limit(elements)
                .collect(Collectors.toList());

        collection.insertMany(randomDocs);
    }

    private static Supplier<Document> randomDocument() {
        return () -> new Document("x", randomUUID().toString());
    }

    private static Supplier<Document> randomQueryDocument() {
        return () -> new Document()
                .append("y", new Random().nextInt(2))
                .append("z", new Random().nextInt(100));
    }
}
