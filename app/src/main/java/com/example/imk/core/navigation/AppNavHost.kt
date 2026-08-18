package com.example.imk.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.imk.core.util.back
import com.example.imk.core.util.navigateTo
import com.example.imk.ui.inventory.InventoryScreen
import com.example.imk.ui.product_detail.ProductDetailScreen
import com.example.imk.ui.product_form.ProductFormScreen

@Composable
fun AppNavHost(
    startDestination: NavRoutes, //Hereda de NavRoutes para tener acceso a las rutas
    modifier: Modifier = Modifier
) {
    val rootBackStack =
        rememberNavBackStack(startDestination)

    NavDisplay(
        backStack = rootBackStack,
        modifier = modifier,
        onBack = { rootBackStack.back() },
        entryProvider = entryProvider {

            entry<NavRoutes.Inventory> {//Cuando la pila de arriba sea de tipo NavRoutes.Inventory dibuja lo siguiente...
                InventoryScreen(
                    onNavigateForm = { rootBackStack.navigateTo(NavRoutes.Form()) },
                    onNavigateDetail = { id -> rootBackStack.navigateTo(NavRoutes.Detail(id)) }
                )
            }

            entry<NavRoutes.Form> { formRoute ->
                ProductFormScreen(
                    formRoute.productId,
                    onFormSuccess = { rootBackStack.back() },
                    onNavigateBack = { rootBackStack.back() }
                )
            }

            entry<NavRoutes.Detail> { detailRoute ->
                ProductDetailScreen(
                    detailRoute.productId,
                    onEditClick = { id -> rootBackStack.navigateTo(NavRoutes.Form(id))},
                    onProductDeleted = {rootBackStack.back()}
                )
            }
        }
    )
}