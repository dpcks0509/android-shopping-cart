package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState

class ShoppingViewModel(private val repository: ShoppingItemsRepository) :
    ViewModel(),
    ShoppingButtonClickListener {
    private val _shoppingUiState = MutableLiveData<UIState<List<Product>>>()
    val shoppingUiState: LiveData<UIState<List<Product>>> = _shoppingUiState

    private val _isLoadMoreButtonVisible = MutableLiveData(false)
    val isLoadMoreButtonVisible: LiveData<Boolean>
        get() = _isLoadMoreButtonVisible

    init {
        loadProducts()
    }

    fun loadProducts() {
        try {
            val products = repository.findProductsByPage()
            if (products.isEmpty()) {
                _shoppingUiState.value = UIState.Empty
            } else {
                _shoppingUiState.value = UIState.Success(products)
            }
        } catch (e: Exception) {
            _shoppingUiState.value = UIState.Error(e)
        }
    }

    fun updateLoadMoreButtonVisibility(isVisible: Boolean) {
        if (repository.canLoadMore()) {
            _isLoadMoreButtonVisible.value = isVisible
        } else {
            _isLoadMoreButtonVisible.value = false
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }

    override fun onLoadMoreButtonClick() {
        loadProducts()
        updateLoadMoreButtonVisibility(false)
    }
}
