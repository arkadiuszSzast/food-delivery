package support.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class CleanDatabaseExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) {
		final var reactiveMongoOperations = getBean(context, ReactiveMongoOperations.class);
		reactiveMongoOperations
				.getCollectionNames()
				.flatMap(collectionName -> reactiveMongoOperations.remove(new Query(), collectionName))
				.collectList()
				.block();
	}

	private <T> T getBean(ExtensionContext context, Class<T> clazz) {
		return SpringExtension.getApplicationContext(context).getBean(clazz);
	}
}
