import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import app.database.Database
import data.repository.PostRepositoryImpl
import kotlinx.coroutines.launch
import nostr.relay.Relay
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SIAMSTR = "relay.siamstr.com"

@Composable
@Preview
fun App() {
    val coroutineScope = rememberCoroutineScope()
    val repo = remember {
        PostRepositoryImpl(
            Relay(SIAMSTR),
            Database(createDriver())
        )
    }
    MaterialTheme {
        val postState = repo.posts.collectAsState(null)
        val posts by remember { postState }
        Column {
            Button({
                coroutineScope.launch { repo.getPosts(
                    authors = null,
                    since = null,
                    until = null,
                    limit = 1
                ) }
            }) {
                Text("Load")
            }
            Button({
                coroutineScope.launch { repo.clearPost() }
            }) {
                Text("Clear")
            }
            LazyColumn {
                items(items = posts?.map { it.content } ?: listOf("empty")) {
                    Text(it)
                    Divider(thickness = 10.dp)
                }
            }
        }
    }

}