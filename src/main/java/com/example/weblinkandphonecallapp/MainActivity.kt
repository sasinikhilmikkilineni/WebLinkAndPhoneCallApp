package com.example.weblinkandphonecallapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.weblinkandphonecallapp.ui.theme.WebLinkAndPhoneCallAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebLinkAndPhoneCallAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImplicitIntentScreen(
                        modifier = Modifier.padding(innerPadding),
                        onCloseApp = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun ImplicitIntentScreen(modifier: Modifier = Modifier, onCloseApp: () -> Unit = {}) {
    val context = LocalContext.current
    var urlText by remember { mutableStateOf("http://www.sjsu.edu") }
    var phoneText by remember { mutableStateOf("(XXX) XXX - XXXXX") }

    val backgroundColor = Color(0xFF2C4A6B) // Dark blue matching the image
    val dividerColor = Color(0xFF8B9B6A) // Greenish divider
    val sjsuGold = Color(0xFFE5A823)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White)
                    .border(2.dp, sjsuGold),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = sjsuGold,
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.title_implicit_intents),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            )
        }

        // URL Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_url),
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.width(80.dp)
            )
            TextField(
                value = urlText,
                onValueChange = { urlText = it },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                try {
                    var formattedUrl = urlText.trim()
                    if (formattedUrl.isNotEmpty()) {
                        if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                            formattedUrl = "https://$formattedUrl"
                        }
                        val intent = Intent(Intent.ACTION_VIEW, formattedUrl.toUri())
                        context.startActivity(intent)
                    }
                } catch (e: Exception) {
                    // Fail silently
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
            shape = RoundedCornerShape(4.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            modifier = Modifier.width(170.dp).height(48.dp)
        ) {
            Text(text = stringResource(id = R.string.btn_launch), color = Color.Black, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(color = dividerColor, thickness = 2.dp)
        Spacer(modifier = Modifier.height(32.dp))

        // Phone Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_phone),
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.width(100.dp)
            )
            TextField(
                value = phoneText,
                onValueChange = { phoneText = it },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val digits = phoneText.filter { it.isDigit() }
                if (digits.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, "tel:$digits".toUri())
                    context.startActivity(intent)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC64444)),
            shape = RoundedCornerShape(4.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            modifier = Modifier.width(170.dp).height(48.dp)
        ) {
            Text(text = stringResource(id = R.string.btn_ring), color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(color = dividerColor, thickness = 2.dp)
        
        Spacer(modifier = Modifier.weight(1f))

        // Close App Button
        Button(
            onClick = onCloseApp,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(4.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            Text(text = stringResource(id = R.string.btn_close_app), color = Color.White, fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImplicitIntentScreenPreview() {
    WebLinkAndPhoneCallAppTheme {
        ImplicitIntentScreen()
    }
}
