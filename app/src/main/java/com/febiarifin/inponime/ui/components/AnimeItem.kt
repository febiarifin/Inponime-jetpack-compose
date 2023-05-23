package com.febiarifin.inponime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.febiarifin.inponime.R
import com.febiarifin.inponime.ui.theme.InponimeTheme

@Composable
fun AnimeItem(
    image: Int,
    title: String,
    rating: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(2.dp))
                .background(MaterialTheme.colors.secondary)
                .padding(4.dp, 1.dp, 4.dp, 1.dp)
        ) {
            Row(
                modifier = modifier.padding(),
            ) {
                Text(
                    text = rating,
                    fontSize = 10.sp,
                    color = Color.White,
                )
                Spacer(modifier = modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = modifier
                        .size(13.dp)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AnimeItemPreview() {
    InponimeTheme {
        AnimeItem(R.drawable.naruto, "Naruto Shippuden", "8.90")
    }
}