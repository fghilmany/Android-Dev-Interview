package com.fghilmany.qikiinterview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fghilmany.qikiinterview.R

@Composable
fun MenuRoute(
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MenuScreen(
        state,
        modifier,
        onRetry = viewModel::load,
        onPlus = viewModel::plus,
        onMinus = viewModel::minus,
        onPlaceOrder = viewModel::placeOrder
    )
}

@Composable
fun MenuScreen(
    state: UiState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onPlus: (Int) -> Unit,
    onMinus: (Int) -> Unit,
    onPlaceOrder: () -> Unit
) {
    when {
        state.isLoading -> CircularProgressIndicator()
        state.error != null -> Column(modifier.fillMaxWidth()) {
            Text(state.error)
            Button({ onRetry() }) { Text("Retry") }
        }

        state.items.isEmpty() -> Box(modifier.fillMaxSize()) {
            Text("Data is Empty")
        }

        else -> Column(modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(state.items, key = { it.id }) { item ->
                    Card(
                        onClick = {/*showDetail()*/ },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(item.title, style = TextStyle())
                                Text(item.category)
                                Text("$${item.price}")

                            }
                            TextButton({ onMinus(item.id) }) { Text("-") }
                            Text("${state.cart[item.id.toString()] ?: 0}")
                            TextButton({ onPlus(item.id) }) { Text("+") }
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                BadgedBox(
                    badge = {
                        if (state.cart.isNotEmpty()) {
                            Badge {
                                Text("${state.itemCount}")
                            }
                        }
                    },
                    modifier = modifier,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_add_shopping_cart_24),   // sesuaikan nama file
                        contentDescription = "Cart, ${state.itemCount} items",
                    )
                }
                Button(
                    { onPlaceOrder() },
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    enabled = !state.isSubmitting
                ) { Text(if (state.isSubmitting) "Sending..." else "Place Order") }
            }

        }
    }
}