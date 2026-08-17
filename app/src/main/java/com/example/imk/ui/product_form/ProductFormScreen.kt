package com.example.imk.ui.product_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imk.core.IMKButton
import com.example.imk.core.IMKText
import com.example.imk.core.IMKTextField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductFormScreen(
    productId: Int?, // null = crear, con valor = editar
    viewModel: ProductFormViewModel = koinViewModel(),
    onFormSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loadedProduct by viewModel.loadedProduct.collectAsStateWithLifecycle()
    // Variables locales para los campos del formulario, deben estar vacias para crear y, para editar(solo al princpio)
    var name by rememberSaveable { mutableStateOf("") }
    var stock by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }


// Esto se ejecuta UNA vez cuando la pantalla aparece o cuando productId cambia
    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.loadProduct(productId)
        }
    }

// Cuando loadedProduct llega con datos, se debe actualizar las variables locales
    LaunchedEffect(loadedProduct) {
        loadedProduct?.let { product ->
            name = product.name
            stock = product.stock.toString()
            price = product.price.toString()//TextField trabaja solo con String
        }
    }

    LaunchedEffect(uiState) {
//        if (uiState is ProductFormUiState.Success) {
//            onFormSuccess()
//        }else{
//            ProductFormUiState.Error(snackbarHostState.showSnackbar(state.message))
//        }
        when (val state = uiState) {
            is ProductFormUiState.Success -> onFormSuccess()
            is ProductFormUiState.Error -> snackbarHostState.showSnackbar(state.message)
            else -> Unit
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        ProductFormContent(
            modifier = Modifier.padding(paddingValues),
            name = name,
            onNameChanged = { name = it },
            stock = stock,
            onStockChanged = { stock = it },
            price = price,
            onPriceChanged = { price = it },
            isEditMode = productId != null, //Decidir entre guardar vs actualizar
            onSaveClick = {
                // UI valida antes de hablar con el ViewModel.
                // Si algo no convierte, ni siquiera se llama al ViewModel.
                val stockValue = stock.toIntOrNull()
                val priceValue = price.toDoubleOrNull()

                if (stockValue != null && priceValue != null) {
                    if (productId != null) {
                        viewModel.editProduct(productId, name, stockValue, priceValue, loadedProduct?.photoUri)
                    } else {
                        viewModel.addProduct(name, stockValue, priceValue, null)
                    }
                }
                // si son null, no pasa nada — todavía no hay feedback visual al usuario, eso es una mejora pendiente
            }
        )
    }

}


@Composable
fun ProductFormContent(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    stock: String,
    onStockChanged: (String) -> Unit,
    price: String,
    onPriceChanged: (String) -> Unit,
    isEditMode: Boolean,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IMKText(
            text = if (isEditMode) "Editar Producto" else "Nuevo Producto",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(30.dp))

        IMKTextField(
            value = name,
            onValueChanged = onNameChanged,
            label = "Nombre del producto",
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        IMKTextField(
            value = stock,
            onValueChanged = onStockChanged,
            label = "Cantidad en stock",
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        IMKTextField(
            value = price,
            onValueChanged = onPriceChanged,
            label = "Precio",
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.weight(1f))
        IMKButton(
            text = if (isEditMode) "Actualizar Producto" else "Guardar Producto",
            onclick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}