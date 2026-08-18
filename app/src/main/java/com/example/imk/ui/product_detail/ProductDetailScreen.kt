package com.example.imk.ui.product_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imk.core.composable.IMKButton
import com.example.imk.core.composable.IMKText
import com.example.imk.domain.model.Product
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = koinViewModel(),
    onProductDeleted: () -> Unit,
    onEditClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackHostState = remember { SnackbarHostState() }

    //Muestra la carga al momento que aparece la pantalla
    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is ProductDetailUiState.Error -> snackHostState.showSnackbar(state.message)
            is ProductDetailUiState.Deleted -> onProductDeleted()
            is ProductDetailUiState.Success -> Unit //No hace nada para que en scaffold se pueda dibujar lo necesario
            is ProductDetailUiState.Loading -> Unit
        } //No es necesario Else si se abarcan todos los casos
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Modifier.align(Alignment.Center)
            when (val state = uiState) {
                is ProductDetailUiState.Loading -> {
                    CircularProgressIndicator() //Circulo de carga
                }

                is ProductDetailUiState.Success -> {
                    ProductDetailContent(
                        product = state.product, //Sacar el producto real del estado
                        onEditClick = { onEditClick(state.product.id) }, //Se pasa el id para navegar
                        onDeleteClick = { viewModel.delete(state.product.id) } //Pasa ID para borrar
                    )
                }

                is ProductDetailUiState.Error -> {
                    IMKText(
                        text = "Error al cargar el producto",
                    )
                }

                is ProductDetailUiState.Deleted -> {}
            }
        }
    }
}


@Composable
fun ProductDetailContent(
    product: Product, //Modelo domain
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    //Calcular el total
    val totalValue =
        product.stock * product.price
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IMKText(
            text = product.name
        )
        IMKText(
            text = product.stock.toString()
        )
        IMKText(
            text = product.price.toString()
        )

        IMKText(
            text = totalValue.toString()
        )

        Spacer(modifier = Modifier.height(20.dp))

        IMKButton(
            text = "Editar Producto",
            onclick = onEditClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        IMKButton(
            text = "Eliminar Producto",
            onclick = onDeleteClick,
            containerColor = MaterialTheme.colorScheme.error
        )
    }
}
