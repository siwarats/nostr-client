package presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import model.Post
import util.extractImageUrls
import util.extractUrls
import util.toDate
import util.uuid

@Composable
fun FeedItem(modifier: Modifier = Modifier, post: Post) {
    Column(modifier = modifier) {
        postHeader(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            post = post
        )
        postContent(
            modifier = Modifier.fillMaxWidth(),
            post = post
        )
        postBottomMenu(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(40.dp)
        )
    }
}

@Composable
private fun postHeader(modifier: Modifier = Modifier, post: Post) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier.size(48.dp).clip(CircleShape),
            painter = rememberImagePainter("https://www.gstatic.com/devrel-devsite/prod/vcb16788a46369f41192d8988c5149cea86bddfebb91a7fb85ede926f44da4a35/android/images/touchicon-180.png"),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                fontWeight = FontWeight.SemiBold, text = "Mr. Droid"
            )
            Text(
                fontSize = 12.sp, text = post.createdAt.toDate(format = FEED_DATETIME_FORMAT)
            )
        }
    }
}

@Composable
private fun postContent(modifier: Modifier = Modifier, post: Post) {
    val imageUrls = post.content.extractImageUrls()
    val urlExceptImageUrls = post.content.extractUrls().toMutableList()
    urlExceptImageUrls.removeAll(imageUrls)
    val contentWithoutImageUrl = post.content.removeStrings(*imageUrls.toTypedArray()).trim()
    val finalContent = contentWithoutImageUrl.applyHyperLink(*urlExceptImageUrls.toTypedArray())
    val uriHandler = LocalUriHandler.current

    Column(modifier = modifier) {
        // add text content
        if (finalContent.isNotBlank()) {
            ClickableText(
                modifier = Modifier.padding(16.dp),
                text = finalContent,
                style = LocalTextStyle.current,
                onClick = { charPosition ->
                    val clickedLink = findLinkByCharPosition(
                        charPosition,
                        finalContent.text,
                        *urlExceptImageUrls.toTypedArray()
                    )
                    if (clickedLink != null) {
                        uriHandler.openUri(clickedLink)
                    }
                }
            )
        }

        // add preview image content
        if (imageUrls.isNotEmpty()) {
            if (finalContent.isBlank()) {
                Divider(thickness = 16.dp, color = Color.Transparent)
            }
            previewImage(urls = imageUrls)
        }
    }
}

@Composable
private fun previewImage(modifier: Modifier = Modifier, urls: List<String>) {
    when (urls.size) {
        1 -> {
            Image(
                modifier = modifier.aspectRatio(1f),
                painter = rememberImagePainter(urls[0]),
                contentDescription = urls[0],
                contentScale = ContentScale.Crop
            )
        }

        2 -> {
            Row(modifier = modifier.aspectRatio((2f / 1f))) {
                Image(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    painter = rememberImagePainter(urls[0]),
                    contentDescription = urls[0],
                    contentScale = ContentScale.Crop
                )
                Image(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    painter = rememberImagePainter(urls[1]),
                    contentDescription = urls[1],
                    contentScale = ContentScale.Crop
                )
            }
        }

        3 -> {
            Row(modifier = modifier.aspectRatio((3f / 2f))) {
                Image(
                    modifier = Modifier.weight(2f).aspectRatio(1f),
                    painter = rememberImagePainter(urls[0]),
                    contentDescription = urls[0],
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        modifier = Modifier.aspectRatio(1f),
                        painter = rememberImagePainter(urls[1]),
                        contentDescription = urls[1],
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier.aspectRatio(1f),
                        painter = rememberImagePainter(urls[2]),
                        contentDescription = urls[2],
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        else -> {
            Row(modifier = modifier.aspectRatio(1f)) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        modifier = Modifier.aspectRatio(1f),
                        painter = rememberImagePainter(urls[0]),
                        contentDescription = urls[0],
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier.aspectRatio(1f),
                        painter = rememberImagePainter(urls[3]),
                        contentDescription = urls[3],
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        modifier = Modifier.aspectRatio(1f),
                        painter = rememberImagePainter(urls[2]),
                        contentDescription = urls[2],
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        modifier = Modifier.aspectRatio(1f),
                        painter = rememberImagePainter(urls[4]),
                        contentDescription = urls[4],
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
private fun postBottomMenu(modifier: Modifier = Modifier) {
    val menus = listOf("Like", "Comment", "Zap")
    Box(modifier = modifier) {
        Divider(thickness = 1.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            menus.forEach {
                postBottomMenuItem(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    text = it
                )
            }
        }
    }
}

@Composable
private fun postBottomMenuItem(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}

private fun String.removeStrings(vararg removeWords: String): String {
    var result = this
    removeWords.forEach {
        result = result.replace(it, " ")
    }
    return result
}

@Composable
private fun String.applyHyperLink(vararg links: String): AnnotatedString {
    val uuid = uuid()
    var source = this
    links.forEach { link ->
        source = source.replace(link, uuid)
    }
    val sourceSentences = source.split(uuid)
    return buildAnnotatedString {
        sourceSentences.forEachIndexed { sentencesIndex, sentences ->
            append(sentences)
            val link = links.getOrNull(sentencesIndex)
            if (link != null) {
                pushStringAnnotation(tag = link, annotation = link)
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    append(link)
                }
            }
        }
    }
}

private fun findLinkByCharPosition(
    charPosition: Int,
    content: String,
    vararg links: String
): String? {
    links.forEach { link ->
        val startIndex = content.indexOf(link)
        val endIndex = startIndex + link.length
        if (charPosition in startIndex..endIndex) {
            return link
        }
    }
    return null
}

private val FEED_DATETIME_FORMAT = LocalDateTime.Format {
    dayOfMonth()
    char(' ')
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    year()
    char(' ')
    hour()
    char(':')
    minute()
}