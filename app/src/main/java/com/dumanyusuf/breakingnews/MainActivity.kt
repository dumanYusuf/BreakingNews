package com.dumanyusuf.breakingnews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dumanyusuf.breakingnews.domain.model.ArticleModel
import com.dumanyusuf.breakingnews.presentation.DetailPage
import com.dumanyusuf.breakingnews.presentation.view.HomePageView
import com.dumanyusuf.breakingnews.ui.theme.BreakingNewsTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreakingNewsTheme {
                Scaffold {
                   NavigationPageController()
                }
            }
        }
    }
}


@Composable
fun NavigationPageController() {


    val controller= rememberNavController()

    NavHost(navController = controller, startDestination = Screan.HomePageView.route){
        composable(Screan.HomePageView.route){
            HomePageView(navController = controller)
        }
        composable(Screan.DetailPageView.route+"/{article}",
            arguments = listOf(
                navArgument("article"){type=NavType.StringType}
            )
        ){
            val jsonCategory = it.arguments?.getString("article")
            val decodedJsonCategory = URLDecoder.decode(jsonCategory, "UTF-8")
            val article = Gson().fromJson(decodedJsonCategory,ArticleModel::class.java)
           DetailPage(articleModel = article)
        }
    }


}

