package com.dumanyusuf.breakingnews

 sealed class Screan (val route:String){


     object HomePageView:Screan("home")
     object DetailPageView:Screan("detail")
}