package com.dumanyusuf.breakingnews.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dumanyusuf.breakingnews.Screan
import com.dumanyusuf.breakingnews.presentation.HomePageViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePageView(
    viewModel: HomePageViewModel = hiltViewModel(),
    navController: NavController
) {
    var isSearchVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.loadNewsBreaking(country = "us")
    }

    val state by viewModel.state.collectAsState()
    val stateBbc by viewModel.stateBbc.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(searchQuery) {
        viewModel.searchNews(searchQuery)
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    if (isSearchVisible) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            maxLines = 1,
                            placeholder = { Text("Search news...") }
                        )
                    } else {
                        Text("Breaking News")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isSearchVisible) {
                            searchQuery = ""
                        }
                        isSearchVisible = !isSearchVisible
                    }) {
                        Icon(
                            imageVector = if (isSearchVisible) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (isSearchVisible) "Close search" else "Open search"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.error.isNotEmpty() -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
                state.newList.isEmpty() -> {
                    Text(
                        text = "No news found",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        // News Slider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .padding(16.dp)
                        ) {
                            LazyRow(
                                state = listState,
                                userScrollEnabled = false
                            ) {
                                itemsIndexed(state.newList) { index, article ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(end = if (index < state.newList.size - 1) 16.dp else 0.dp)
                                    ) {
                                        // Image
                                        AsyncImage(
                                            model = article.urlToImage,
                                            contentDescription = article.title,
                                            modifier = Modifier
                                                .clickable {
                                                    val productObject = Gson().toJson(article)
                                                    val encodedProductObject = URLEncoder.encode(productObject, "UTF-8")
                                                    navController.navigate(Screan.DetailPageView.route+"/$encodedProductObject")
                                                }
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(16.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        
                                        // Time indicator
                                        Box(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                                                .padding(4.dp)
                                                .align(Alignment.TopStart)
                                        ) {
                                            Text(
                                                text = "17:46",
                                                color = Color.White,
                                                fontSize = 12.sp
                                            )
                                        }
                                        
                                        // Title overlay
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .fillMaxWidth()
                                                .background(Color.Black.copy(alpha = 0.7f))
                                                .padding(16.dp)
                                        ) {
                                            Text(
                                                text = article.title ?: "",
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Page indicators
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            itemsIndexed(state.newList) { index, _ ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            if (listState.firstVisibleItemIndex == index) 
                                                Color(0xFFFF5722) 
                                            else 
                                                Color.LightGray
                                        )
                                        .clickable {
                                            coroutineScope.launch {
                                                listState.animateScrollToItem(index)
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        color = if (listState.firstVisibleItemIndex == index) 
                                            Color.White 
                                        else 
                                            Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        
                        // News List
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            items(if (isSearchVisible) searchResults.newList else stateBbc.newList) { article ->
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            val productObject = Gson().toJson(article)
                                            val encodedProductObject = URLEncoder.encode(productObject, "UTF-8")
                                            navController.navigate(Screan.DetailPageView.route+"/$encodedProductObject")
                                        }
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        ,
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                    ) {
                                        // News Image
                                        AsyncImage(
                                            model = article.urlToImage,
                                            contentDescription = article.title,
                                            modifier = Modifier
                                                .width(120.dp)
                                                .fillMaxHeight()
                                                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                                                ),
                                            contentScale = ContentScale.Crop
                                        )
                                        
                                        // News Content
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(12.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            // Title
                                            Text(
                                                text = article.title ?: "",
                                                style = MaterialTheme.typography.titleMedium,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            
                                            // Source and Time
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                // Source
                                                Text(
                                                    text = article.description?: "",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                
                                                // Time badge
                                                Surface(
                                                    color = MaterialTheme.colorScheme.primaryContainer,
                                                    shape = RoundedCornerShape(4.dp)
                                                ) {
                                                    Text(
                                                        text = "17:46",
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}