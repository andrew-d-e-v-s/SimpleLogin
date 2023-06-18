package devs.andrew.simplelogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import devs.andrew.simplelogin.ui.component.AboutText
import devs.andrew.simplelogin.ui.component.AppLogo
import devs.andrew.simplelogin.ui.component.LoginButton
import devs.andrew.simplelogin.ui.component.MarginVertical
import devs.andrew.simplelogin.ui.component.MarginVerticalMax
import devs.andrew.simplelogin.ui.component.RegisterButton
import devs.andrew.simplelogin.ui.component.TextButton
import devs.andrew.simplelogin.ui.component.TextInput
import devs.andrew.simplelogin.ui.component.WelcomeText
import devs.andrew.simplelogin.ui.theme.SimpleLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleLoginTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(22.dp)
                    ) {
                        var isUsernameValid by remember { mutableStateOf(false) }
                        var isPasswordValid by remember { mutableStateOf(false) }

                        MarginVertical(size = 48.dp)
                        AppLogo()
                        MarginVertical(size = 48.dp)
                        WelcomeText(brandName = stringResource(id = R.string.app_name))
                        MarginVertical(size = 12.dp)
                        AboutText(text = "The best experience your ever had!")
                        MarginVertical(size = 48.dp)
                        TextInput(
                            title = "Username",
                            imeAction = ImeAction.Next,
                            regexPattern = "^[a-zA-Z_][a-zA-Z0-9_]*$"
                        ) { text, hasError -> isUsernameValid = hasError.not() }
                        MarginVertical(size = 16.dp)
                        TextInput(
                            title = "Password",
                            inputType = KeyboardType.Password,
                            regexPattern = "^[a-zA-Z0-9 !@#$%^&*()_+]+$"
                        ) { text, hasError -> isPasswordValid = hasError.not() }
                        MarginVertical(size = 32.dp)
                        LoginButton(enabled = isUsernameValid and isPasswordValid) {}
                        MarginVertical(size = 12.dp)
                        TextButton(text = "Forgot password?") {}
                        MarginVerticalMax()
                        RegisterButton()
                    }
                }
            }
        }
    }
}