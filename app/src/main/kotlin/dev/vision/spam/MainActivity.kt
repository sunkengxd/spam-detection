package dev.vision.spam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.vision.spam.classifier.randomSpamClassifier
import dev.vision.spam.mailbox.repository.emailRepository
import dev.vision.spam.mailbox.ui.MailboxScreen
import dev.vision.spam.mailbox.viewmodel.MailboxViewModel
import dev.vision.spam.ui.component.MainTopAppBar
import dev.vision.spam.ui.theme.SpamTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MailboxViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        MailboxViewModel(emailRepository(), randomSpamClassifier)
                    }
                }
            )
            SpamTheme {
                Scaffold(
                    topBar = {
                        MainTopAppBar(onClickSort = viewModel::sort)
                    }
                ) { scaffoldPadding ->
                    MailboxScreen(viewModel, Modifier.padding(scaffoldPadding))
                }
            }
        }
    }
}
