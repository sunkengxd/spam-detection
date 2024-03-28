package dev.vision.spam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.vision.spam.classifier.randomSpamClassifier
import dev.vision.spam.core.cache.Cache
import dev.vision.spam.core.client.client
import dev.vision.spam.mailbox.api.MailtrapApi
import dev.vision.spam.mailbox.repository.MailtrapEmailRepository
import dev.vision.spam.mailbox.ui.AuthScreen
import dev.vision.spam.mailbox.ui.MailboxScreen
import dev.vision.spam.mailbox.viewmodel.MailboxViewModel
import dev.vision.spam.ui.component.MainTopAppBar
import dev.vision.spam.ui.theme.SpamTheme

class MainActivity : ComponentActivity() {

    private val cache by lazy {
        Cache(this)
    }

    private val client by lazy {
        client(cache)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var apiKey: String? by remember {
                mutableStateOf(null)
            }

            LaunchedEffect(Unit) {
                apiKey = cache.getKey()
            }

            val loggedIn = remember(apiKey) {
                apiKey != null
            }
            SpamTheme {
                AnimatedContent(targetState = loggedIn, label = "") {
                    if (it) {
                        val viewModel: MailboxViewModel = viewModel(
                            factory = viewModelFactory {
                                initializer {
                                    MailboxViewModel(
                                        repository = MailtrapEmailRepository(MailtrapApi(client)),
                                        classifier = randomSpamClassifier
                                    )
                                }
                            }
                        )
                        Scaffold(
                            topBar = {
                                MainTopAppBar(
                                    onClickSort = viewModel::sort,
                                    onClickRefresh = viewModel::load
                                )
                            }
                        ) { scaffoldPadding ->
                            MailboxScreen(viewModel, Modifier.padding(scaffoldPadding))
                        }
                    } else {
                        Surface(Modifier.fillMaxSize()) {
                            AuthScreen({ apiKey = cache.getKey() }, cache)
                        }
                    }
                }
            }
        }
    }
}
