package presentation

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
import androidx.compose.ui.unit.dp
import app.database.Database
import createDriver
import cryptography.getSchnorr
import data.repository.PostRepositoryImpl
import nostr.relay.Relay
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SIAMSTR = "relay.siamstr.com"

@Composable
@Preview
fun App() {
    val rememberSchnoor = remember { getSchnorr() }
    val repo = remember {
        PostRepositoryImpl(
            Relay(SIAMSTR, rememberSchnoor),
            Database(createDriver()),
        )
    }
    MaterialTheme {
        val postState = repo.posts.collectAsState(null)
        val posts by remember { postState }
        Column {
            Button({
                repo.getPosts(
                    authors = null,
                    since = null,
                    until = null,
                    limit = 10
                )
            }) {
                Text("Load")
            }
            Button({
                repo.clearPost()
            }) {
                Text("Clear")
            }
            LazyColumn {
                items(items = posts ?: emptyList()) {
                    FeedItem(post = it)
                    Divider(thickness = 8.dp)
                }
            }
        }
    }

}
