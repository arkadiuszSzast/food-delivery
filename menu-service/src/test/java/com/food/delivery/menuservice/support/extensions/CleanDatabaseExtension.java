package com.food.delivery.menuservice.support.extensions;

import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class CleanDatabaseExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) {
		final var db = getBean(context, MongoTemplate.class).getDb();
		db.listCollectionNames()
				.map(db::getCollection)
				.map(collection -> collection.deleteMany(new Document()))
				.cursor().forEachRemaining(DeleteResult::wasAcknowledged);
	}

	private <T> T getBean(ExtensionContext context, Class<T> clazz) {
		return SpringExtension.getApplicationContext(context).getBean(clazz);
	}
}
