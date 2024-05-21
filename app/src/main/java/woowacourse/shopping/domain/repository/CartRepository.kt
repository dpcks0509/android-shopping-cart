package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart

interface CartRepository {
    fun insert(
        product: Product,
        quantity: Int,
    )

    fun update(
        productId: Long,
        quantity: Int,
    )

    fun size(): Int

    fun findOrNullWithProductId(productId: Long): CartItem?

    fun find(cartItemId: Long): CartItem

    fun findAll(): ShoppingCart

    fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): ShoppingCart

    fun delete(cartItemId: Long)

    fun deleteAll()
}
