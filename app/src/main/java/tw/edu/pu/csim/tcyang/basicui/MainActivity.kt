package tw.edu.pu.csim.tcyang.basicui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

import tw.edu.pu.csim.tcyang.basicui.ui.theme.BasicUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main( modifier: Modifier = Modifier) {
    Box {
        LazyColumnExample(modifier)
        ColumnExample(modifier)
        TextFieldExample(modifier)
        LazyRowExample(modifier,)
        SimpleVideoPlayer()
    }
}
@Composable
fun SimpleVideoPlayer(
    @RawRes videoRes: Int // 確保這裡有接收 videoRes 參數
) {
    val context = LocalContext.current

    // 建立並記住目前的影片狀態與 ExoPlayer...
    var currentVideoRes by remember { mutableStateOf(videoRes) }

    // ...其餘程式碼保持不變
}
@Composable
fun LazyColumnExample( modifier: Modifier = Modifier) {
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Text("標題列") }
            items(count = 50) { index ->
                Text(text = "$index")
            }
            item { Text("結尾列") }
        }
    }
}
    @Composable
    fun ColumnExample(modifier: Modifier = Modifier) {
        var msq by remember { mutableStateOf(value="") }
        Column (
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xffE0BBE4))
            .pointerInput(key1=Unit) {
            detectTapGestures (
                onTap = { msq = "短按"},
                onDoubleTap = {msq = "連按兩下"},
                onLongPress = {msq = "長按"}
                )
        },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("行動應用軟體開發：基本元件")
            Text("作者：陳韋宏")
            Text(msq)
            Spacer(modifier = Modifier.height(8.dp))
            RowExample(modifier)
        }
    }
@Composable
fun RowExample(modifier: Modifier = Modifier) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.android),
            contentDescription = "Android icon",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Yellow),
            alpha = 0.6f,
        )

        Spacer(modifier = Modifier.width(20.dp))

        Image(
            painter = painterResource(id = R.drawable.compose),
            contentDescription = "Compose icon",
            modifier = Modifier.size(100.dp)
        )
    }
}
@Composable
fun TextFieldExample(modifier: Modifier = Modifier) {
    // 管理輸入框的文字狀態
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("請輸入文字") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (inputText.isBlank()) "尚未輸入內容" else "您輸入的是：$inputText"
        )
    }
}
data class Animal(
    val name: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun LazyRowExample(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val animals = listOf(
        Animal("鴨子", R.drawable.animal0),
        Animal("企鵝", R.drawable.animal1),
        Animal("青蛙", R.drawable.animal2),
        Animal("貓頭鷹", R.drawable.animal3),
        Animal("海豚", R.drawable.animal4),
        Animal("牛", R.drawable.animal5),
        Animal("無尾熊", R.drawable.animal6),
        Animal("獅子", R.drawable.animal7),
        Animal("狐狸", R.drawable.animal8),
        Animal("小雞", R.drawable.animal9)
    )
    LazyRow(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 直接傳入 animals 清單，每個迴圈拿到一個 animal 物件
        itemsIndexed(animals) {index, animal ->
            Card(
                modifier = Modifier.fillParentMaxWidth(1.0f)
                    .height(260.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD0BCFF)
                )
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = animal.imageRes),
                        contentDescription = animal.name,
                        modifier = Modifier.size(150.dp)
                            .clickable {
                                Toast.makeText(
                                    context,
                                    "這是 ${animal.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$index ${animal.name}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$index ${animal.name}"
                    )
                }
            }
        }
    }
}
@Composable
fun SimpleVideoPlayer() {
    val context = LocalContext.current

    // 1. 使用 remember 記錄當前的影片資源 ID，預設為 love.mp4 (R.raw.love)
    // 請依您的實際 raw 資源名稱調整 (例如 R.raw.love 與 R.raw.handpan)
    var currentVideoRes by remember { mutableStateOf(R.raw.love) }

    // 追蹤按鈕文字與下一部要切換的影片
    val isLovePlaying = (currentVideoRes == R.raw.love)
    val buttonText = if (isLovePlaying) "切換至手碟影片" else "切換至Love影片"

    // 2. 建立並記住 ExoPlayer 實體
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = true // 載入完成自動播放
        }
    }

    // 3. 當 currentVideoRes 改變時，更新 ExoPlayer 的 MediaItem 並重新準備播放
    LaunchedEffect(currentVideoRes) {
        val uri = "android.resource://${context.packageName}/$currentVideoRes"
        exoPlayer.setMediaItem(MediaItem.fromUri(uri))
        exoPlayer.prepare()
    }

    // 4. 離開畫面時釋放播放器資源
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // 5. UI 畫面佈局：包含影片播放器與切換按鈕
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 影片檢視區塊
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 切換影片按鈕
        Button(onClick = {
            // 點擊按鈕後進行切換
            currentVideoRes = if (isLovePlaying) {
                R.raw.handpan
            } else {
                R.raw.love
            }
        }) {
            Text(text = buttonText)
        }
    }
}