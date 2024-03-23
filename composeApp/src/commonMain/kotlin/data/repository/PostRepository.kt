package data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.database.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import model.Post
import nostr.Constant.EVENT_KIND_TEXT_NOTE
import nostr.message.CloseMessage
import nostr.message.EoseMessage
import nostr.message.EventMessage
import nostr.message.RequestFilter
import nostr.message.RequestMessage
import nostr.relay.Relay
import util.uuid
import kotlin.coroutines.CoroutineContext

interface PostRepository {
    val posts: Flow<List<Post>>
    fun getPosts(
        authors: List<String>?,
        since: Long?,
        until: Long?,
        limit: Int
    ): String

    fun unsubscribe(uuid: String)
    fun addPost(post: Post)
    fun clearPost()
}

class PostRepositoryImpl(
    private val relay: Relay,
    private val database: Database
) : CoroutineScope, PostRepository {

    init {
        applyAutoCacheIntoDatabase()
        applyAutoUnsubscribe()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override val posts: Flow<List<Post>>
        get() = database.postQueries.selectAllPost()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map {
                    Post(
                        id = it.id,
                        pubKey = it.pubKey,
                        content = it.content,
                        createdAt = it.createdAt
                    )
                }
            }

    override fun getPosts(
        authors: List<String>?,
        since: Long?,
        until: Long?,
        limit: Int
    ): String {
        val uuid = uuid()
        val event = RequestMessage(
            subscribeId = uuid,
            filter = RequestFilter(
                authors = authors,
                kinds = listOf(EVENT_KIND_TEXT_NOTE),
                limit = limit,
                since = since,
                until = until
            )
        )
        relay.send(event)
        return uuid
    }

    override fun unsubscribe(uuid: String) {
        relay.send(CloseMessage(uuid))
    }

    override fun addPost(post: Post) {
        TODO("Not yet implemented")
    }

    override fun clearPost() {
        database.postQueries.deletePost()
    }

    private fun applyAutoCacheIntoDatabase() {
        launch {
            relay.response()
                .filterIsInstance(EventMessage::class)
                .collect {
                    database.postQueries.insertPost(
                        id = it.eventContent.id,
                        pubKey = it.eventContent.pubKey,
                        content = it.eventContent.content,
                        createdAt = it.eventContent.createdAt
                    )
                }
        }
    }

    private fun applyAutoUnsubscribe() {
        launch {
            relay.response()
                .filterIsInstance(EoseMessage::class)
                .collect {
                    relay.send(CloseMessage(it.subscribeId))
                }
        }
    }
}