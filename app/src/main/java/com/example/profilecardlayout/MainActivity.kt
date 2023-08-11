package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.example.profilecardlayout.ui.theme.MyTheme
import com.example.profilecardlayout.ui.theme.lightGreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                UsersApplication()
                }
            }
        }
    }

@Composable
fun UsersApplication() {
     val navController = rememberNavController()
     NavHost(navController = navController, startDestination = "users_list") {
         composable("users_list"){
             UserListScreen(userProfileList, navController)
         }
         composable(route = "users_details/{userId}", arguments = listOf(navArgument(name = "userId"){
             type = NavType.IntType
         })){
             UserProfileDetailsScreen(it.arguments!!.getInt("userId"), navController)
         }
     }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(userProfiles: List<UserProfile>, navController: NavHostController?) {
    Scaffold(topBar = { AppBar(
        "Users List", image = Icons.Default.Home, { }) }) {it ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
          LazyColumn(){
              items(userProfiles) {
                  userProfile -> ProfileCard(userProfile = userProfile){
                      navController?.navigate("users_details/${userProfile.id}")
              }
              }
          }


        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, image: ImageVector, iconClickAction: ()-> Unit){
    TopAppBar(navigationIcon = { Icon(image, contentDescription = "Icon", modifier = Modifier.padding(horizontal = 16.dp).clickable { iconClickAction() }) }, title = { Text(text = title) },
            colors = TopAppBarDefaults.smallTopAppBarColors(Color.Cyan) )
}

@Composable
fun ProfileCard(userProfile: UserProfile, clickEvent: () -> Unit){
    Card(modifier = Modifier
        .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.Top).clickable { clickEvent() },
         elevation =
    CardDefaults
        .cardElevation(8.dp), colors = CardDefaults.cardColors(Color.White), shape = CutCornerShape(topEnd = 60f) ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment
            .CenterVertically, horizontalArrangement = Arrangement.Start){
            ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
            ProfileContent(userProfile.name, userProfile.status, alignment = Alignment.Start)
        }
    }
}


@Composable
fun ProfilePicture(pictureUrl: String, status: Boolean, imageSize: Dp) {
    Card(shape = CircleShape, border = BorderStroke(width = 2.dp, color = if(status) lightGreen else Color.Red), modifier
    = Modifier.padding(16.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        AsyncImage(model = pictureUrl , contentDescription =
        "Content description", modifier = Modifier.size(imageSize))
    }

}


@Composable
fun ProfileContent(name: String, status: Boolean, alignment: Alignment.Horizontal) {
    Column(modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment) {
        Text(text = name, style = MaterialTheme.typography.headlineMedium)

        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.outline) {
            Text(text = if(status) "Active Now" else "Offline", style = MaterialTheme.typography.bodyLarge)
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileDetailsScreen(userId: Int, navController: NavHostController?) {
    val userProfile = userProfileList.first{ userProfile -> userId == userProfile.id }
    Scaffold(topBar = { AppBar(title = "User Profile Details", image = Icons.Default.ArrowBack){
        navOptions { navController!!.navigateUp() }
    } }) {it ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)){

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment =  Alignment.CenterHorizontally, ){
                ProfilePicture(userProfile.pictureUrl, userProfile.status, 240.dp)
                ProfileContent(userProfile.name, userProfile.status, alignment = Alignment.CenterHorizontally)
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun UserProfileDetailsPreview() {
    MyTheme() {
        UserProfileDetailsScreen(3, null)
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyTheme {
       UserListScreen(userProfileList, null)
    }

}