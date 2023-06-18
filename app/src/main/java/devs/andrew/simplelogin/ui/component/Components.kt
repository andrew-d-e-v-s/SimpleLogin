package devs.andrew.simplelogin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import devs.andrew.simplelogin.R
import devs.andrew.simplelogin.ui.theme.LightGray
import devs.andrew.simplelogin.ui.theme.Purple
import devs.andrew.simplelogin.ui.theme.TextInputBackground

@Composable
fun MarginVertical(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun ColumnScope.MarginVerticalMax() {
    Spacer(modifier = Modifier.weight(1F))
}

@Composable
fun MarginHorizontal(size: Dp) {
    Spacer(modifier = Modifier.width(size))
}

@Composable
fun AppLogo(size: Dp = 120.dp) {
    Image(
        modifier = Modifier.size(size),
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = stringResource(id = R.string.app_name)
    )
}

@Composable
fun WelcomeText(brandName: String) {
    Text(
        text = "Welcome to $brandName!",
        style = TextStyle(fontWeight = FontWeight.W600, fontSize = 28.sp)
    )
}

@Composable
fun AboutText(text: String) {
    Text(
        text = text,
        style = TextStyle(color = LightGray, fontWeight = FontWeight.W300, fontSize = 18.sp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    title: String,
    inputType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    regexPattern: String? = null,
    onTextChanged: (text: String, hasError: Boolean) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = (if (isError) 1 else -1).dp,
                color = Color.Red,
                shape = RoundedCornerShape(8.dp)
            ),
        value = text,
        onValueChange = {
            text = it
            regexPattern?.let { pattern ->
                isError = it.matches(Regex(pattern)).not() and it.isNotEmpty()
            }
            onTextChanged(text, isError)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TextInputBackground,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            cursorColor = Purple,
            unfocusedLabelColor = LightGray,
            focusedLabelColor = LightGray
        ),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = title) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType,
            imeAction = imeAction
        ),
        visualTransformation = if (inputType == KeyboardType.Password && isPasswordVisible.not()) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (inputType == KeyboardType.Password) {
            { PasswordVisibilityIcon { isPasswordVisible = it } }
        } else null,
        isError = isError
    )
}

@Composable
fun PasswordVisibilityIcon(onVisibilityChanged: ((isVisible: Boolean) -> Unit)) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val iconRes =
        if (isPasswordVisible) R.drawable.ic_hide_password else R.drawable.ic_show_password
    val contentDesc = if (isPasswordVisible) "Hide password" else "Show password"

    IconButton(onClick = {
        isPasswordVisible = isPasswordVisible.not()
        onVisibilityChanged(isPasswordVisible)
    }) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDesc,
            tint = LightGray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(TextFieldDefaults.MinHeight),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Purple),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(
            text = "Login",
            style = TextStyle(color = Color.White, fontWeight = FontWeight.W500, fontSize = 18.sp)
        )
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = TextStyle(color = Purple, fontWeight = FontWeight.W500, fontSize = 18.sp),
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    )
}

@Composable
fun RegisterButton() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Don't have an account?",
            style = TextStyle(color = LightGray, fontWeight = FontWeight.W500, fontSize = 18.sp)
        )
        MarginHorizontal(size = 2.dp)
        TextButton(text = "Register!") {}
    }
}