package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.MuslimNamazViewModel
import com.example.ui.viewmodel.UIState

@Composable
fun ZakatScreen(
    uiState: UIState,
    viewModel: MuslimNamazViewModel,
    onNavigate: (AppDestination) -> Unit
) {
    val zakat = uiState.zakatState

    var cashInput by remember { mutableStateOf(if (zakat.cash > 0) zakat.cash.toString() else "") }
    var goldGramsInput by remember { mutableStateOf(if (zakat.goldGrams > 0) zakat.goldGrams.toString() else "") }
    var goldPriceInput by remember { mutableStateOf(zakat.goldPricePerGram.toString()) }
    var silverGramsInput by remember { mutableStateOf(if (zakat.silverGrams > 0) zakat.silverGrams.toString() else "") }
    var silverPriceInput by remember { mutableStateOf(zakat.silverPricePerGram.toString()) }
    var businessInput by remember { mutableStateOf(if (zakat.businessAssets > 0) zakat.businessAssets.toString() else "") }
    var debtsInput by remember { mutableStateOf(if (zakat.debtsLiabilities > 0) zakat.debtsLiabilities.toString() else "") }
    var nisabStandard by remember { mutableStateOf(zakat.nisabType) } // "GOLD" or "SILVER"

    fun recalculate() {
        val c = cashInput.toDoubleOrNull() ?: 0.0
        val gg = goldGramsInput.toDoubleOrNull() ?: 0.0
        val gp = goldPriceInput.toDoubleOrNull() ?: 85.0
        val sg = silverGramsInput.toDoubleOrNull() ?: 0.0
        val sp = silverPriceInput.toDoubleOrNull() ?: 1.0
        val b = businessInput.toDoubleOrNull() ?: 0.0
        val d = debtsInput.toDoubleOrNull() ?: 0.0

        viewModel.updateZakatState(
            zakat.copy(
                cash = c,
                goldGrams = gg,
                goldPricePerGram = gp,
                silverGrams = sg,
                silverPricePerGram = sp,
                businessAssets = b,
                debtsLiabilities = d,
                nisabType = nisabStandard
            )
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Zakat Calculator",
                subtitle = "Calculate 2.5% of Eligible Wealth",
                showBackButton = true,
                onBack = { onNavigate(AppDestination.Home) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Result Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (zakat.isZakatPayable) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (zakat.isZakatPayable) "TOTAL ZAKAT DUE (2.5%)" else "BELOW NISAB THRESHOLD",
                            style = MaterialTheme.typography.labelSmall,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (zakat.isZakatPayable) Gold700 else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "$${String.format("%,.2f", zakat.zakatAmount)}",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (zakat.isZakatPayable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Net Wealth",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$${String.format("%,.2f", zakat.netZakatableWealth)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Nisab ($nisabStandard)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$${String.format("%,.2f", zakat.activeNisab)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Nisab Standard Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Nisab Standard",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Choose Gold (87.48g) or Silver (612.36g) benchmark",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            FilterChip(
                                selected = nisabStandard == "GOLD",
                                onClick = {
                                    nisabStandard = "GOLD"
                                    recalculate()
                                },
                                label = { Text("Gold Nisab (87.48g)") },
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                selected = nisabStandard == "SILVER",
                                onClick = {
                                    nisabStandard = "SILVER"
                                    recalculate()
                                },
                                label = { Text("Silver Nisab (612.36g)") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Asset Inputs
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Assets & Wealth",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        // Cash
                        OutlinedTextField(
                            value = cashInput,
                            onValueChange = {
                                cashInput = it
                                recalculate()
                            },
                            label = { Text("Cash at Bank & in Hand ($)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        // Gold
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = goldGramsInput,
                                onValueChange = {
                                    goldGramsInput = it
                                    recalculate()
                                },
                                label = { Text("Gold (Grams)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = goldPriceInput,
                                onValueChange = {
                                    goldPriceInput = it
                                    recalculate()
                                },
                                label = { Text("Gold Price/g ($)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        // Silver
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = silverGramsInput,
                                onValueChange = {
                                    silverGramsInput = it
                                    recalculate()
                                },
                                label = { Text("Silver (Grams)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = silverPriceInput,
                                onValueChange = {
                                    silverPriceInput = it
                                    recalculate()
                                },
                                label = { Text("Silver Price/g ($)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        // Business Stock
                        OutlinedTextField(
                            value = businessInput,
                            onValueChange = {
                                businessInput = it
                                recalculate()
                            },
                            label = { Text("Business Merchandise / Trade Goods ($)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        // Liabilities / Debts
                        OutlinedTextField(
                            value = debtsInput,
                            onValueChange = {
                                debtsInput = it
                                recalculate()
                            },
                            label = { Text("Immediate Debts & Liabilities to Deduct ($)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }

            // Guidelines Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "📖 About Zakat in Islam",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Zakat is the 3rd pillar of Islam. It is obligatory upon every sane, adult Muslim who possesses wealth equal to or exceeding the Nisab threshold continuously for one full lunar year (Hawl). The rate is 2.5% (1/40th) of qualifying surplus wealth.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
