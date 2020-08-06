package com.food.delivery.menuservice.support.extensions

import com.mongodb.client.result.DeleteResult
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.junit.jupiter.SpringExtension

class CleanDatabaseExtension : BeforeEachCallback {
	override fun beforeEach(context: ExtensionContext) {
		val reactiveMongoOperations = getBean(context, ReactiveMongoOperations::class.java)
		reactiveMongoOperations
				.collectionNames
				.flatMap<DeleteResult> { collectionName: String -> reactiveMongoOperations.remove(Query(), collectionName) }
				.collectList()
				.block()
	}

	private fun <T> getBean(context: ExtensionContext, clazz: Class<T>): T {
		return SpringExtension.getApplicationContext(context).getBean(clazz)
	}
}
