package com.example.imk.ui.inventory

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imk.R
import com.example.imk.core.composable.IMKText
import com.example.imk.core.composable.IMKTextField
import com.example.imk.domain.model.Product
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel = koinViewModel(),
    onNavigateForm: () -> Unit,
    onNavigateDetail: (Int) -> Unit
) {
    val products by viewModel.productsFlow.collectAsStateWithLifecycle()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    InventoryContent(
        products = products,
        searchQuery = searchQuery,
        onSearchQueryChanged = { newValue ->
            searchQuery = newValue
            viewModel.onSearchQueryChanged(newValue) //Se lo manda al ViewModel
        },
        onProductClick = { id -> onNavigateDetail(id) },
        onAddProductClick = { onNavigateForm()}
    )
}

@Composable
fun InventoryContent(
    products: List<Product>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit, //Se lo manda a InventoryScreen
    onProductClick: (Int) -> Unit, //Recibe el id del producto
    onAddProductClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProductClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InventoryHeader()

            IMKTextField(
                value = searchQuery,
                onValueChanged = onSearchQueryChanged,
                label = "Buscar producto...",
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (products.isEmpty()) {
                Spacer(modifier = Modifier.height(130.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    painter = painterResource(R.drawable.sin_productos_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                IMKText(text = "No hay productos agregados")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InventoryHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        IMKText(
            text = "Inventario",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}