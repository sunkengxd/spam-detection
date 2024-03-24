package dev.vision.spam.mailbox.api

import io.ktor.client.HttpClient
import io.ktor.http.formUrlEncode
import io.ktor.http.parameters

private const val GMAIL_READONLY = "https://www.googleapis.com/auth/gmail.readonly"
private const val CLIENT_ID =
    "725249399820-gnm2s3mqdc3f5g29lr5hsd9mnjk98hpc.apps.googleusercontent.com"

class GmailApi(private val client: HttpClient) {

    suspend fun zxc() = parameters {
        append("client_id", CLIENT_ID)
        append("scope", GMAIL_READONLY)
        append("response_type", "code")
        append("redirect_uri", "http://127.0.0.1:8080")
        append("access_type", "offline")
    }.formUrlEncode()
}

/* class to demonstrate use of Gmail list labels API */
//object GmailQuickstart {
//
//    /**
//     * Application name.
//     */
//    private const val APPLICATION_NAME = "Gmail API Java Quickstart"
//
//    /**
//     * Global instance of the JSON factory.
//     */
//    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
//
//    /**
//     * Directory to store authorization tokens for this application.
//     */
//    private const val TOKENS_DIRECTORY_PATH = "tokens"
//
//    /**
//     * Global instance of the scopes required by this quickstart.
//     * If modifying these scopes, delete your previously saved tokens/ folder.
//     */
//    private val SCOPES = listOf(GmailScopes.GMAIL_LABELS)
//    private const val CREDENTIALS_FILE_PATH = "/credentials.json"
//
//    /**
//     * Creates an authorized Credential object.
//     *
//     * @param HTTP_TRANSPORT The network HTTP Transport.
//     * @return An authorized Credential object.
//     * @throws IOException If the credentials.json file cannot be found.
//     */
//    @Throws(IOException::class)
//    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
//        // Load client secrets.
//        val `in` =
//            GmailQuickstart::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
//                ?: throw FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH)
//        val clientSecrets =
//            GoogleClientSecrets.load(
//                JSON_FACTORY,
//                InputStreamReader(`in`)
//            )
//
//        // Build flow and trigger user authorization request.
//        val flow =
//            GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
//            )
//                .setDataStoreFactory(
//                    FileDataStoreFactory(
//                        File(
//                            TOKENS_DIRECTORY_PATH
//                        )
//                    )
//                )
//                .setAccessType("offline")
//                .build()
//        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
//        //returns an authorized Credential object.
//        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
//    }
//
//    @Throws(IOException::class, GeneralSecurityException::class)
//    @JvmStatic
//    fun main(args: Array<String>) {
//        // Build a new authorized API client service.
//        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
//        val service = Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//            .setApplicationName(APPLICATION_NAME)
//            .build()
//
//        // Print the labels in the user's account.
//        val user = "me"
//        val listResponse = service.users().labels().list(user).execute()
//        val labels = listResponse.labels
//        if (labels.isEmpty()) {
//            println("No labels found.")
//        } else {
//            println("Labels:")
//            for (label in labels) {
//                System.out.printf("- %s\n", label.name)
//            }
//        }
//    }
//}