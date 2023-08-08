package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profilecardlayout.ui.theme.MyTheme
import com.example.profilecardlayout.ui.theme.lightGreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                MainScreen()
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(topBar = { AppBar() }) {it ->
        Surface(modifier = Modifier
                .fillMaxSize()
                .padding(it)){
            Column() {
              for (i in userProfiles){
                  ProfileCard(userProfile = i)
              }
            }


        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
    TopAppBar(navigationIcon = { Icon(Icons.Default.Home, contentDescription = "Icon", modifier = Modifier.padding(horizontal = 16.dp)) }, title = { Text(text = "App users") },
            colors = TopAppBarDefaults.smallTopAppBarColors(Color.Cyan) )
}

@Composable
fun ProfileCard(userProfile: UserProfile){
    Card(modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
         elevation =
    CardDefaults
        .cardElevation(8.dp), colors = CardDefaults.cardColors(Color.White), shape = CutCornerShape(topEnd = 60f) ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment
            .CenterVertically, horizontalArrangement = Arrangement.Start){
            ProfilePicture(userProfile.drawableId, userProfile.status)
            ProfileContent(userProfile.name, userProfile.status)
        }
    }
}


@Composable
fun ProfilePicture(drawableId: Int, status: Boolean) {
    Card(shape = CircleShape, border = BorderStroke(width = 2.dp, color = if(status) lightGreen else Color.Red), modifier
    = Modifier.padding(16.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        Image(painter = painterResource(id = drawableId), contentDescription =
        "Content description", modifier = Modifier.size(72.dp), contentScale = ContentScale.Crop)
    }

}


@Composable
fun ProfileContent(name: String, status: Boolean) {
    Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {
        Text(text = name, style = MaterialTheme.typography.headlineMedium)

        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.outline) {
            Text(text = if(status) "Active Now" else "Offline", style = MaterialTheme.typography.bodyLarge)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyTheme() {
        MainScreen()
    }

}