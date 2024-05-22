package woowacourse.shopping.view.detail

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.helper.FakeCartRepositoryImpl
import woowacourse.shopping.helper.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private lateinit var testCartRepository: CartRepository
    private val shoppingRepository = mockk<ShoppingRepository>()

    private val product =
        Product(
            id = 0L,
            name = "1 대전 장인약과",
            price = 10000,
            imageUrl =
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver." +
                    "net%2FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0ls" +
                    "IdvAnzUF2h6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPE" +
                    "G%2FIMG_5278.jpg&type=sc960_832",
        )

    @BeforeEach
    fun setUp() {
        testCartRepository = FakeCartRepositoryImpl()
        every { shoppingRepository.findProductById(any()) } returns product
        viewModel = DetailViewModel(testCartRepository, shoppingRepository, 0L)
    }

    @Test
    fun `선택된 상품의 상세 정보를 가져온다`() {
        val actual = viewModel.product

        assertThat(actual.name).isEqualTo("1 대전 장인약과")
        assertThat(actual.price).isEqualTo(10000)
        assertThat(actual.imageUrl).isEqualTo(
            "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver." +
                "net%2FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0ls" +
                "IdvAnzUF2h6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPE" +
                "G%2FIMG_5278.jpg&type=sc960_832",
        )
    }

    @Test
    fun `아무것도 담지 않은 장바구니의 크기는 0 이다`() {
        val actual = testCartRepository.size()

        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `상품을 장바구니에 담으면 장바구니의 사이즈가 증가한다`() {
        viewModel.createCartItem()
        val actual = testCartRepository.size()

        assertThat(actual).isEqualTo(1)
    }
}