package com.fujigames.nestedscrolltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.Modifier
import com.fujigames.nestedscrolltest.ui.theme.NestedScrollTestTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NestedScrollTestTheme {

                val tabList = listOf("Tab1", "Tab2")
                val pagerState: PagerState = rememberPagerState(initialPage = 0)
                val coroutineScope = rememberCoroutineScope()

                LazyColumn() {

                    item {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .background(Color.LightGray), contentAlignment = Alignment.Center
                        ) {
                            Text(text = "HEADER")
                        }

                    }

                    stickyHeader {
                        TabRow(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = Color.White,
                            contentColor = Color.Black,
                            selectedTabIndex = pagerState.currentPage,
                            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                                )
                            }
                        ) {
                            tabList.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    },
                                )
                            }
                        }
                    }

                    item {
                        HorizontalPager(
                            state = pagerState,
                            count = tabList.size
                        ) { page: Int ->
                            when (page) {
                                0 -> ListLazyColum(50)
                                1 -> ListFlowRow(5)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListLazyColum(items: Int){
    LazyColumn(Modifier.height(2500.dp)) {
        items(items){ index ->
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Button $index")
            }
        }
    }
}

@Composable
fun ListFlowRow(items: Int){
    FlowRow() {
        repeat(items){ index ->
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Button $index")
            }
        }

    }
}
